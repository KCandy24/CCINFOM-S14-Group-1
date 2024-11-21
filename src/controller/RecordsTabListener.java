package src.controller;

import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JComponent;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import src.model.AnimeSystem;
import src.view.gui.Subtab;
import src.view.gui.TopView;

/**
 * Handles events in the Records/Anime subtab.
 */
public class RecordsTabListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public RecordsTabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        System.out.printf("%s/%s?%s\n", topView.getCurrentTabName(), topView.getCurrentSubtabName(), name);

        switch (name) {
            // Anime subtab
            case "searchAnime":
                searchAnime();
                break;
            case "addNewAnime":
                addNewAnime();
                break;
            case "saveAnime":
                saveAnime();
                updateFields("animes");
                break;
            case "deleteAnime":
                deleteAnime();
                updateFields("animes");
                break;

            // User subtab
            case "searchUser":
                searchUser();
                break;
            case "addNewUser":
                addNewUser();
                break;
            case "saveUser":
                saveUser();
                updateFields("users");
                break;
            case "deleteUser":
                deleteUser();
                updateFields("users");
                break;

            // Staff subtab
            case "searchStaff":
                searchStaff();
                break;
            case "addNewStaff":
                addNewStaff();
                break;
            case "saveStaff":
                saveStaff();
                updateFields("staff");
                break;
            case "deleteStaff":
                deleteStaff();
                updateFields("staff");
                break;

            // Studio subtab
            case "searchStudio":
                searchStudio();
                break;
            case "addNewStudio":
                addNewStudio();
                break;
            case "saveStudio":
                saveStudio();
                updateFields("studio");
                break;
            case "deleteStudio":
                deleteStudio();
                updateFields("studio");
                break;
            default:
                System.err.println("No action associated for " + name);
                break;
        }
    }

    public void updateFields(String recordName) {
        String[] columns = animeSystem.getRecordColNames(recordName);
        String[][] data = animeSystem.selectColumns(columns, recordName);
        topView.setRecordTableData(recordName, data, columns);
    }

    // Anime records management

    public void searchAnime() {
        topView.selectFromTable("animes");

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(true);
    }

    public void addNewAnime() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
        topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);

        subtab.setComponentText(topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "airDate"), String.valueOf(LocalDate.now()));

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(false);
    }

    public void saveAnime() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
        String animeId = subtab.getComponentText("animeId");
        String studioId = subtab.getComponentText("studioId");
        String animetitle = subtab.getComponentText("animeTitle");
        String genre = subtab.getComponentText("genre", "genre");
        String airDate = subtab.getComponentText("airDate");
        String episodes = subtab.getComponentText("episodes");

        try {
            Integer.parseInt(animeId);
            updateAnime(animeId, studioId, animetitle, genre, airDate, episodes);
        } catch (NumberFormatException exception) {
            createAnime(studioId, animetitle, genre, episodes);
        }
        
        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(true);
    }

    public void createAnime(String studioId, String animeTitle, String genre, String episodes) {
        if (animeTitle.equals(""))
            topView.dialogPopUp("Anime", "Title must not be empty");
        else {
            try {
                String query = """
                        INSERT INTO `animes` (`studio_id`, `title`, `genre`, `air_date`, `num_of_episodes`) VALUES
                        (?, ?, ?, NOW(), ?)
                        """;
                animeSystem.safeUpdate(query, studioId, animeTitle, genre, episodes);
            } catch (MysqlDataTruncation exception) {
                topView.dialogPopUp("Anime", (animeTitle.length() > 64) ? "Title is too long" : "Invalid Date");
            } catch (SQLException exception) {
                topView.dialogPopUp("Anime", "Invalid Number of Episodes");
            }
        }
    }

    public void updateAnime(String animeId, String studioId, String animeTitle, String genre, String airDate, String episodes) {
        String checkCurrentEpisodeCount = """
                SELECT num_of_episodes
                FROM animes
                WHERE anime_id = ?
                """;
        int currentEpisodeCount = 1;

        try {
            HashMap<String, String> data = animeSystem.safeSingleQuery(checkCurrentEpisodeCount, animeId);
            currentEpisodeCount = Integer.parseInt(data.get("num_of_episodes"));
        } catch (Exception e) {
            System.out.println("error occurred: " + e);
        }

        if (animeTitle.equals(""))
            topView.dialogPopUp("Anime", "Title must not be empty");
        else {
            try {
                String query = """
                        UPDATE  `animes`
                        SET     `studio_id` = ?,
                        `title` = ?,
                        `genre` = ?,
                        `air_date` = ?,
                        `num_of_episodes` = ?
                        WHERE `anime_id` = ?
                        """;
                if (currentEpisodeCount > Integer.parseInt(episodes))
                    throw new SQLException();
                animeSystem.safeUpdate(query, studioId, animeTitle, genre, airDate, episodes, animeId);
            } catch (MysqlDataTruncation exception) {
                topView.dialogPopUp("Anime", (animeTitle.length() > 64) ? "Title is too long" : "Invalid Date");
            } catch (SQLException exception) {
                topView.dialogPopUp("Anime", "Invalid Number of Episodes");
            }
        }
    }

    public void deleteAnime() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
        String animeId = subtab.getComponentText("animeId");

        try {
            animeSystem.safeUpdate("DELETE FROM `animes` WHERE `anime_id` = ?", animeId);
        } catch (SQLException exception) {
            topView.dialogPopUp("SQLException", exception.getMessage());
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(false);
    }

    // User records management

    public void searchUser() {
        topView.selectFromTable("users");

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteUser").setEnabled(true);
    }

    public void addNewUser() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteUser").setEnabled(false);
    }

    /**
     * Save user button either updates an existing user or creates a new user.
     */
    public void saveUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        String userId = subtab.getComponentText("userId");
        String username = subtab.getComponentText("username");
        String region = subtab.getComponentText("region", "region");
        String joinDate = subtab.getComponentText("joinDate");
        try {
            Integer.parseInt(userId);
            // User ID field was parsed successfully; this must be an existing record
            updateUser(userId, username, region, joinDate);
        } catch (NumberFormatException exception) {
            // This must be a new record
            createUser(username, region, joinDate);
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteUser").setEnabled(true);
    }

    public void createUser(String username, String region, String joinDate) {
        try {
            animeSystem.safeUpdate(
                    "INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES (?, ?, ?)",
                    username, region, joinDate);
        } catch (SQLException exception) {
            System.out.println("Exception class = " + exception.getClass());
            topView.dialogPopUp("SQLException", exception.getMessage());
        }
    }

    public void updateUser(String userId, String username, String region, String joinDate) {
        try {
            animeSystem.safeUpdate(
                    "UPDATE `users` SET `user_name` = ?, `region` = ?, `join_date` = ? WHERE `user_id` = ?",
                    username, region, joinDate, userId);
        } catch (SQLException exception) {
            System.out.println("Exception class = " + exception.getClass());
            topView.dialogPopUp("SQLException", exception.getMessage());
        }
    }

    public void deleteUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        String userId = subtab.getComponentText("userId");

        try {
            animeSystem.safeUpdate("DELETE FROM `users` WHERE `user_id` = ?", userId);
        } catch (SQLException exception) {
            topView.dialogPopUp("SQLException", exception.getMessage());
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteUser").setEnabled(false);
    }

    // Staff records management

    public void searchStaff() {
        topView.selectFromTable("staff");
    }

    public void addNewStaff() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
    }

    public void saveStaff() {
        // TODO: IMPLEMENTATION
    }

    public void deleteStaff() {
        // TODO: IMPLEMENTATION
    }

    // Studio records management

    public void searchStudio() {
        topView.selectFromTable("studios");
    }

    public void addNewStudio() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB);
    }

    public void saveStudio() {
        // TODO: IMPLEMENTATION
    }

    public void deleteStudio() {
        // TODO: IMPLEMENTATION
    }
}
