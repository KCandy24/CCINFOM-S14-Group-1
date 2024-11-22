package src.controller;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import src.model.AnimeSystem;
import src.model.Records;
import src.view.gui.Subtab;
import src.view.gui.TopView;

public class TransactionsTabListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public TransactionsTabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        System.out.printf("%s/%s?%s\n", topView.getCurrentTabName(), topView.getCurrentSubtabName(), name);

        int user_id = this.getUserIDFromTopView();
        int anime_id = this.getAnimeIDFromTopView();

        switch (name) {
            // General
            case "searchUser":
                this.searchUser();
                break;
            case "searchAnime":
                this.searchAnime();
                break;
            case "searchStaff":
                this.searchStaff();
                break;

            // Watch episode
            case "watchEpisode":
                this.watchEpisode(user_id, anime_id);
                this.refreshLastWatched(user_id, anime_id);
                break;

            // Rate anime
            case "saveRating":
                this.saveRating();
                break;
            case "deleteRating":
                this.deleteRating();
                break;
            case "loadRating":
                this.loadRating();
                break;

            // Edit credits
            case "saveCredits":
                this.saveCredits();
                break;
            case "deleteCredits":
                this.deleteCredits();
                break;
            case "loadCredits":
                this.loadCredits();
                break;

            // Follow user
            case "searchFollower":
                this.searchFollower();
                break;
            case "searchFollowed":
                this.searchFollowed();
                break;
            case "follow":
                this.follow();
                break;
            case "unfollow":
                this.unfollow();
                break;

            default:
                System.err.println("No associated action for " + name);
                break;
        }
    }

    // General

    public void searchAnime() {
        topView.selectFromTable(Records.ANIME.name);
    }

    public void searchUser() {
        topView.selectFromTable(Records.USER.name);
    }

    public void searchStaff() {
        topView.selectFromTable(Records.STAFF.name);
    }

    // Watch episode transaction

    public int getUserIDFromTopView() {
        try {
            Subtab subtab = topView.getCurrentSubtab();
            String userIDString = subtab.getComponentText("userId");
            int user_id = Integer.parseInt(userIDString);
            return user_id;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getAnimeIDFromTopView() {
        try {
            Subtab subtab = topView.getCurrentSubtab();
            String animeIDString = subtab.getComponentText("animeId");
            int anime_id = Integer.parseInt(animeIDString);
            return anime_id;
        } catch (Exception e) {
            return 0;
        }
    }

    public void watchEpisode(int user_id, int anime_id) {
        if (user_id == 0 || anime_id == 0) {
            topView.errorPopUp("Watch Episode", "User ID and Anime ID cannot be empty");
            return;
        }

        int lastWatched = Integer.parseInt(
                animeSystem.getProcedureSingleResult(String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
        int maxEpisodes = Integer
                .parseInt(animeSystem.singleQuery("SELECT num_of_episodes FROM animes WHERE anime_id = " + anime_id));
        if (lastWatched == maxEpisodes) {
            topView.errorPopUp("Watch Episode", "User has watched all episodes of this anime.");
            return;
        }

        try {
            animeSystem.callProcedure("WatchAnime(?, ?)", Integer.toString(user_id), Integer.toString(anime_id));
            topView.dialogPopUp("Watch Episode", "Successfully watched anime episode " + (lastWatched + 1));
        } catch (Exception e) {
            topView.errorPopUp("Watch Episode", "An error occured, cannot watch episode.");
        }
    }

    /**
     * Refresh the last watched episode -- used after a user successfully watches a
     * new episode.
     * 
     * @param user_id
     * @param anime_id
     */
    public void refreshLastWatched(int user_id, int anime_id) {
        if (user_id != 0 && anime_id != 0) {
            Subtab subtab = topView.getCurrentSubtab();
            subtab.setComponentText("episode",
                    animeSystem.getProcedureSingleResult(
                            String.format(
                                    "GetLastWatchedQ(%d, %d)",
                                    user_id, anime_id)));
        }
    }

    // Rate anime

    public boolean validateId(String id, String errorTitle, String errorBody) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            topView.errorPopUp(errorTitle, errorBody);
            return false;
        }
        return true;
    }

    public void saveRating() {
        // Grab data from GUI
        Subtab subtab = topView.getCurrentSubtab();
        String user_id = subtab.getComponentText("userId");
        String anime_id = subtab.getComponentText("animeId");
        String rating = subtab.getComponentText("rating");
        String comment = subtab.getComponentText("comment");
        String last_episode_watched = subtab.getComponentText("episode");
        String query = """
                INSERT INTO ratings
                (user_id, anime_id, rating, comment, last_episode_watched, last_edited_timestamp)
                VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)""";
        String checkExistingQuery = """
                SELECT EXISTS(SELECT *
                FROM ratings
                WHERE user_id = ? AND anime_id = ?)
                AS checkExistingQuery
                """;

        // TODO ? Minor inconvenience: must refresh episode if user previously didn't
        // watch, watched, then tried to save a rating

        if ((validateId(user_id, "No user selected", "Please select a user before making a rating.") &&
                validateId(anime_id, "No anime selected", "Please select an anime to rate.")) == false) {
            return;
        }

        int lastEpisodeWatchedValue = Integer.parseInt(last_episode_watched);
        if (lastEpisodeWatchedValue < 1) {
            topView.errorPopUp("Cannot rate unwatched anime", "You cannot rate an anime you haven't watched.");
            return;
        }

        try {
            float ratingValue = Float.parseFloat(rating);
            if (ratingValue < 1 || ratingValue > 5) {
                topView.errorPopUp("Invalid rating value", "Please input a rating from 1-5.");
                return;
            }
        } catch (NumberFormatException e) {
            topView.errorPopUp("No rating value", "Please input a rating from 1-5.");
            return;
        }

        boolean ratingExists = false;

        try {
            HashMap<String, String> data = animeSystem.safeSingleQuery(checkExistingQuery, user_id, anime_id);
            ratingExists = data.get("checkExistingQuery").equals("1");
        } catch (Exception e) {
            System.out.println("error occurred: " + e);
        }

        // Attempt to make the rating

        if (ratingExists) {
            try {
                query = """
                        UPDATE ratings
                        SET rating = ?,
                        comment = ?,
                        last_episode_watched = ?,
                        last_edited_timestamp = CURRENT_TIMESTAMP
                        WHERE user_id = ? AND anime_id = ?
                        """;
                animeSystem.safeUpdate(query,
                        rating, comment, last_episode_watched, user_id, anime_id);
                topView.dialogPopUp("Rate An Anime", "Successfully updated rating.");
            } catch (SQLException e) {
                topView.errorPopUp("Rate An Anime", "Something went wrong.");
                System.out.println(e.getStackTrace());
            }
        } else {
            try {
                animeSystem.safeUpdate(query,
                        user_id, anime_id, rating, comment, last_episode_watched);
                topView.dialogPopUp("Rate An Anime", "Successfully saved rating.");
            } catch (SQLException e) {
                topView.errorPopUp("Rate An Anime", "Something went wrong.");
                System.out.println(e.getStackTrace());
            }
        }

    }

    /**
     * Attempt to load a rating.
     */
    public void loadRating() {
        // Get rating data
        Subtab subtab = topView.getCurrentSubtab();
        String user_id = subtab.getComponentText("userId");
        String anime_id = subtab.getComponentText("animeId");

        if ((validateId(user_id, "No user selected", "Please select a user.") &&
                validateId(anime_id, "No anime selected", "Please select an anime.")) == false) {
            return;
        }

        String checkExistingQuery = """
                SELECT EXISTS(SELECT *
                FROM ratings
                WHERE user_id = ? AND anime_id = ?)
                AS checkExistingQuery
                """;

        boolean ratingExists = false;

        try {
            HashMap<String, String> data = animeSystem.safeSingleQuery(checkExistingQuery, user_id, anime_id);
            ratingExists = data.get("checkExistingQuery").equals("1");
        } catch (Exception e) {
            System.out.println("error occurred: " + e);
        }

        if (ratingExists) {
            try {
                HashMap<String, String> data = animeSystem.safeSingleQuery("""
                        SELECT * FROM ratings
                        WHERE user_id = ? AND anime_id = ?
                        """, user_id, anime_id);
                for (Map.Entry<String, String> pair : data.entrySet()) {
                    System.out.println(pair.getKey() + " : " + pair.getValue());
                }

                // Set to GUI
                subtab = topView.getCurrentSubtab();
                subtab.setComponentText("rating", data.get("rating"));
                subtab.setComponentText("comment", data.get("comment"));
                subtab.setComponentText("episode", data.get("last_episode_watched"));

                topView.dialogPopUp("Rate An Anime", "Successfully loaded the rating.");

            } catch (SQLException e) {
                topView.errorPopUp("SQL Exception", e.getStackTrace().toString());
            }
        } else {
            topView.errorPopUp("Rate An Anime", "Rating entry does not exist.");
        }

    }

    /**
     * Attempt to delete a rating.
     */
    public void deleteRating() {
        Subtab subtab = topView.getCurrentSubtab();
        String user_id = subtab.getComponentText("userId");
        String anime_id = subtab.getComponentText("animeId");
        String query = """
                DELETE FROM rating
                WHERE user_id = ?
                AND anime_id = ?""";

        if ((validateId(user_id, "No user selected", "Please select a user.") &&
                validateId(anime_id, "No anime selected", "Please select an anime.")) == false) {
            return;
        }

        try {
            animeSystem.safeUpdate(query, user_id, anime_id);
            topView.dialogPopUp("Rate An Anime", "Successfully deleted the rating.");
        } catch (SQLException e) {
            System.out.println(e);
            topView.errorPopUp("Rate An Anime", "Rating does not exist.");
        }
    }

    // Edit credits
    public void saveCredits() {
        // TODO: IMPLEMENTATION
    }

    public void deleteCredits() {
        // TODO: IMPLEMENTATION
    }

    public void loadCredits() {
        // TODO: IMPLEMENTATION
        // ? Need a procedure, maybe
    }

    // Follow

    /**
     * Prompt the user to select the "follower" user.
     */
    private void searchFollower() {
        Subtab subtab = this.topView.getSubtab(TopView.TRANSACTIONS_TAB,
                TopView.FOLLOW_USER_TRANSACTION_SUBTAB);
        subtab.setAssociatedComponent("users.user_id", "userId");
        subtab.setAssociatedComponent("users.user_name", "username");
        this.searchUser();
    }

    /**
     * Prompt the user to select the "followed" user.
     */
    private void searchFollowed() {
        Subtab subtab = this.topView.getSubtab(TopView.TRANSACTIONS_TAB,
                TopView.FOLLOW_USER_TRANSACTION_SUBTAB);
        subtab.setAssociatedComponent("users.user_id", "user2Id");
        subtab.setAssociatedComponent("users.user_name", "user2name");
        this.searchUser();
    }

    /**
     * Perform validation on the followerId and followedId fields.
     * 
     * @param followerId
     * @param followedId
     * @return false if either id is not valid or if both ids are the same, else
     *         true
     */
    public boolean validateFollowIds(String followerId, String followedId) {
        if ((validateId(followerId, "Follower not selected", "Please select the follower user.") &&
                validateId(followedId, "Followed user not selected", "Please select the followed user.")) == false) {
            return false;
        }
        // Validate if the follower and the followed are the same person
        if (followerId.equals(followedId)) {
            topView.errorPopUp("Follow User", "Follower and followed must not be the same user.");
            return false;
        }
        return true;
    }

    /**
     * Attempt to let a user follow another.
     */
    public void follow() {
        Subtab subtab = topView.getCurrentSubtab();
        String followerId = subtab.getComponentText("userId");
        String followedId = subtab.getComponentText("user2Id");

        if (validateFollowIds(followerId, followedId) == false) {
            return;
        }

        // "Follow"-specific validation
        boolean errorEntryExists;
        try {
            String checkExistingQuery = """
                    SELECT EXISTS(
                        SELECT * FROM follows
                        WHERE follower_id = ? AND followed_id = ?
                    ) AS checkExistingQuery
                    """;
            HashMap<String, String> data = animeSystem.safeSingleQuery(checkExistingQuery, followerId, followedId);
            errorEntryExists = data.get("checkExistingQuery").equals("1");
        } catch (Exception e) {
            System.out.println("error occurred: " + e);
            errorEntryExists = false;
        }

        if (errorEntryExists) {
            topView.errorPopUp("Follow User", "Follow entry already exists.");
            return;
        }

        // Passed validation; save to model
        String query = """
                INSERT INTO follows
                (follower_id, followed_id, following_since_date)
                VALUES (?, ?, NOW())""";
        try {
            animeSystem.safeUpdate(query, followerId, followedId);
            topView.dialogPopUp("Follow User", "Successfully added follow entry.");
        } catch (SQLException e) {
            topView.errorPopUp("Follow User", "Something went wrong.");
            System.out.println(e);
        }
    }

    /**
     * Attempt to let a user unfollow another.
     */
    public void unfollow() {
        Subtab subtab = topView.getCurrentSubtab();
        String followerId = subtab.getComponentText("userId");
        String followedId = subtab.getComponentText("user2Id");

        if (validateFollowIds(followerId, followedId) == false) {
            return;
        }

        try {
            String query = """
                    DELETE FROM follows
                    WHERE follower_id = ?
                    AND followed_id = ?""";
            animeSystem.safeUpdate(query, followerId, followedId);
            topView.dialogPopUp("Follow User", "Successfully deleted follow entry.");
        } catch (SQLException e) {
            System.out.println(e);
            topView.errorPopUp("Follow User", "Follow entry doesn't exist.");
        }
    }
}
