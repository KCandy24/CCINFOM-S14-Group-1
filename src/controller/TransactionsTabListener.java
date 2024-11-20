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
                break;
            case "refreshLastWatched":
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

        refreshLastWatched(user_id, anime_id);

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
            topView.dialogPopUp("Watch Episode", "User ID and Anime ID cannot be empty");
            return;
        }

        int lastWatched = Integer.parseInt(
                animeSystem.getProcedureSingleResult(String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
        int maxEpisodes = Integer
                .parseInt(animeSystem.singleQuery("SELECT num_of_episodes FROM animes WHERE anime_id = " + anime_id));
        if (lastWatched == maxEpisodes) {
            topView.dialogPopUp("Watch Episode", "User has watched all episodes of this anime.");
            return;
        }

        try {
            animeSystem.callProcedure("WatchAnime(?, ?)", Integer.toString(user_id), Integer.toString(anime_id));
            topView.dialogPopUp("Watch Episode", "Successfully watched anime episode " + (lastWatched + 1));
        } catch (Exception e) {
            topView.dialogPopUp("Watch Episode", "An error occured, cannot watch episode.");
        }
    }

    public void refreshLastWatched(int user_id, int anime_id) {
        if (user_id != 0 && anime_id != 0) {
            Subtab subtab = topView.getCurrentSubtab();
            subtab.setComponentText(
                    subtab.getComponent("episode"),
                    animeSystem.getProcedureSingleResult(
                            String.format(
                                    "GetLastWatchedQ(%d, %d)",
                                    user_id, anime_id)));
        }
    }

    // Rate anime
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
        // Save to model
        try {
            animeSystem.safeUpdate(query,
                    user_id, anime_id, rating, comment, last_episode_watched);
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }

    }

    public void loadRating() {
        // Get rating data
        Subtab subtab = topView.getCurrentSubtab();
        String user_id = subtab.getComponentText("userId");
        String anime_id = subtab.getComponentText("animeId");
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

        } catch (SQLException e) {
            topView.dialogPopUp("SQL Exception", e.getStackTrace().toString());
        }
    }

    public void deleteRating() {
        // TODO: IMPLEMENTATION
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

    // Follow user
    private void searchFollowed() {
        Subtab subtab = this.topView.getSubtab(TopView.TRANSACTIONS_TAB,
                TopView.FOLLOW_USER_TRANSACTION_SUBTAB);
        subtab.setAssociatedComponent("users.user_id", "user2Id");
        subtab.setAssociatedComponent("users.user_name", "user2name");
        this.searchUser();
    }

    private void searchFollower() {
        Subtab subtab = this.topView.getSubtab(TopView.TRANSACTIONS_TAB,
                TopView.FOLLOW_USER_TRANSACTION_SUBTAB);
        subtab.setAssociatedComponent("users.user_id", "userId");
        subtab.setAssociatedComponent("users.user_name", "username");
        this.searchUser();
    }

    public void follow() {
        // TODO: IMPLEMENTATION

    }

    public void unfollow() {
        // TODO: IMPLEMENTATION

    }
}
