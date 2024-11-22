package src.controller;

import java.awt.event.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.HashMap;

import javax.swing.JComponent;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import src.model.AnimeSystem;
import src.model.Records;
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
                break;
            case "deleteAnime":
                deleteAnime();
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
                break;
            case "deleteUser":
                deleteUser();
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
                break;
            case "deleteStaff":
                deleteStaff();
                break;
            case "staffHistory":
                checkStaffHistory();
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
                break;

            case "deleteStudio":
                deleteStudio();
                break;
            default:
                System.err.println("No action associated for " + name);
                break;
        }
    }

    // General

    public void refreshRecordTableData(Records record) {
        String recordName = record.name;
        String[] columns;
        String[][] data;
        if (record.name == Records.ANIME.name) {
            columns = animeSystem.getRecordColNames(Records.ANIME.name, Records.STUDIO.name);
            data = animeSystem.selectColumns(columns, "animes JOIN studios ON animes.studio_id = studios.studio_id");
        } else {
            columns = animeSystem.getRecordColNames(recordName);
            data = animeSystem.selectColumns(columns, recordName);
        }
        topView.setRecordTableData(record.name, data, columns);
    }

    public void setTopViewWithNewest(Records record) {
        this.refreshRecordTableData(record);
        HashMap<String, String> rowData = topView.getLastRowData(record);
        topView.setFieldsFromData(rowData);
    }

    // Anime records management

    public void searchAnime() {
        topView.selectFromTable(Records.ANIME.name);

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(true);
    }

    public void addNewAnime() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
        topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);

        subtab.setComponentText(topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "airDate"),
                String.valueOf(LocalDate.now()));

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
            topView.errorPopUp("Anime", "Title must not be empty");
        else {
            try {
                String query = """
                        INSERT INTO `animes` (`studio_id`, `title`, `genre`, `air_date`, `num_of_episodes`) VALUES
                        (?, ?, ?, NOW(), ?)
                        """;
                animeSystem.safeUpdate(query, studioId, animeTitle, genre, episodes);
                this.setTopViewWithNewest(Records.ANIME);
            } catch (MysqlDataTruncation exception) {
                topView.errorPopUp("Anime", (animeTitle.length() > 64) ? "Title is too long" : "Invalid Date");
            } catch (SQLException exception) {
                topView.errorPopUp("Anime", "Invalid Number of Episodes");
            }
        }
    }

    public void updateAnime(String animeId, String studioId, String animeTitle, String genre, String airDate,
            String episodes) {
        String checkCurrentEpisodeCount = """
                SELECT num_of_episodes
                FROM animes
                WHERE anime_id = ?
                """;
        int currentEpisodeCount = 1;

        try {
            HashMap<String, String> data = animeSystem.safeSingleQuery(checkCurrentEpisodeCount, animeId);
            currentEpisodeCount = Integer.parseInt(data.get("num_of_episodes"));
        } catch (SQLIntegrityConstraintViolationException exception) {
            topView.errorPopUp("Anime", "Anime Title must be unique");
        } catch (Exception e) {
            System.out.println("error occurred: " + e);
        }

        if (animeTitle.equals(""))
            topView.errorPopUp("Anime", "Title must not be empty");
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
                this.refreshRecordTableData(Records.ANIME);
            } catch (MysqlDataTruncation exception) {
                topView.errorPopUp("Anime", (animeTitle.length() > 64) ? "Title is too long" : "Invalid Date");
            } catch (SQLException exception) {
                topView.errorPopUp("Anime", "Invalid Number of Episodes");
            }
        }
    }

    public void deleteAnime() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
        String animeId = subtab.getComponentText("animeId");

        try {
            animeSystem.safeUpdate("DELETE FROM `animes` WHERE `anime_id` = ?", animeId);
            this.refreshRecordTableData(Records.ANIME);

        } catch (SQLIntegrityConstraintViolationException Exception) {
            topView.errorPopUp("Anime", "Could not delete due to existing transactions connected to "
                    + subtab.getComponentText("animeTitle"));
        } catch (SQLException exception) {
            topView.errorPopUp("SQLException", exception.getMessage());
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB, "deleteAnime").setEnabled(false);
    }

    // User records management

    public void searchUser() {
        topView.selectFromTable("users");

        topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "deleteUser").setEnabled(true);
    }

    /**
     * Reset the fields of the user record subtab -- this "prompts" the user to
     * input data for a new user.
     */
    public void addNewUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        topView.resetFields(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);

        subtab.setComponentText(topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "joinDate"),
                String.valueOf(LocalDate.now()));

        topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "deleteUser").setEnabled(false);
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

        topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "deleteUser").setEnabled(true);
    }

    public void createUser(String username, String region, String joinDate) {
        if (username.equals(""))
            topView.errorPopUp("User", "Username cannot be empty");
        else {
            try {
                animeSystem.safeUpdate(
                        "INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES (?, ?, ?)",
                        username, region, joinDate);
                this.setTopViewWithNewest(Records.USER);
            } catch (SQLIntegrityConstraintViolationException exception) {
                topView.errorPopUp("User", "Username must be unique");
            } catch (SQLException exception) {
                System.out.println("Exception class = " + exception.getClass());
                topView.errorPopUp("SQLException", exception.getMessage());
            }
        }
    }

    public void updateUser(String userId, String username, String region, String joinDate) {
        try {
            animeSystem.safeUpdate(
                    "UPDATE `users` SET `user_name` = ?, `region` = ?, `join_date` = ? WHERE `user_id` = ?",
                    username, region, joinDate, userId);
            this.refreshRecordTableData(Records.USER);
        } catch (SQLException exception) {
            System.out.println("Exception class = " + exception.getClass());
            topView.errorPopUp("SQLException", exception.getMessage());
        }
    }

    public void deleteUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        String userId = subtab.getComponentText("userId");

        try {
            animeSystem.safeUpdate("DELETE FROM `users` WHERE `user_id` = ?", userId);
            this.refreshRecordTableData(Records.USER);
        } catch (SQLIntegrityConstraintViolationException Exception) {
            topView.errorPopUp("User", "Could not delete due to existing transactions connected to "
                    + subtab.getComponentText("username"));
        } catch (SQLException exception) {
            topView.errorPopUp("SQLException", exception.getMessage());
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "deleteUser").setEnabled(false);
    }

    // Staff records management

    public void searchStaff() {
        topView.selectFromTable("staff");

        topView.getComponent(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB, "deleteStaff").setEnabled(true);
    }

    public void addNewStaff() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
        topView.resetFields(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);

        subtab.setComponentText(topView.getComponent(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB, "birthday"),
                "1970-01-01");

        topView.getComponent(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB, "deleteStaff").setEnabled(false);
    }

    public void saveStaff() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
        String staffId = subtab.getComponentText("staffId");
        String firstName = subtab.getComponentText("firstName");
        String lastName = subtab.getComponentText("lastName");
        String occupation = subtab.getComponentText("occupation");
        String birthday = subtab.getComponentText("birthday");

        try {
            Integer.parseInt(staffId);
            updateStaff(staffId, firstName, lastName, occupation, birthday);
            topView.dialogPopUp("Staff", "Successfully updated staff entry!");
        } catch (NumberFormatException exception) {
            createStaff(firstName, lastName, occupation, birthday);
            topView.dialogPopUp("Staff", "Successfully created staff entry!");
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB, "deleteStaff").setEnabled(true);
    }

    public void createStaff(String firstName, String lastName, String occupation, String birthday) {
        if (firstName.equals("") || lastName.equals(""))
            topView.errorPopUp("Staff", "First and last name cannot be empty");
        else {
            try {
                animeSystem.safeUpdate(
                        "INSERT INTO `staff` (`first_name`, `last_name`, `occupation`, `birthday`) VALUES (?, ?, ?, ?)",
                        firstName, lastName, occupation, birthday);
                this.setTopViewWithNewest(Records.STAFF);
            } catch (MysqlDataTruncation exception) {
                topView.errorPopUp("Staff",
                        (firstName.length() > 16) ? "First Name is too long"
                                : (lastName.length() > 16) ? "Last Name is too long"
                                        : (occupation.length() > 32) ? "Occupation name is too long" : "Invalid Date");
            } catch (SQLException exception) {
                System.out.println("Exception class = " + exception.getClass());
                topView.errorPopUp("SQLException", exception.getMessage());
            }
        }
    }

    public void updateStaff(String staffId, String firstName, String lastName, String occupation, String birthday) {
        try {
            animeSystem.safeUpdate(
                    "Update `staff` SET `first_name` = ?, `last_name` = ?, `occupation` = ?, `birthday` = ? WHERE `staff_id` = ?",
                    firstName, lastName, occupation, birthday, staffId);
            this.refreshRecordTableData(Records.STAFF);
        } catch (MysqlDataTruncation exception) {
            topView.errorPopUp("Staff",
                    (firstName.length() > 16) ? "First Name is too long"
                            : (lastName.length() > 16) ? "Last Name is too long"
                                    : (occupation.length() > 32) ? "Occupation name is too long" : "Invalid Date");
        } catch (SQLException exception) {
            System.out.println("Exception class = " + exception.getClass());
            topView.errorPopUp("SQLException", exception.getMessage());
        }

    }

    public void deleteStaff() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
        String staffId = subtab.getComponentText("staffId");

        try {
            animeSystem.safeUpdate("DELETE FROM `staff` WHERE `staff_id` = ?", staffId);
            this.refreshRecordTableData(Records.STAFF);
        } catch (SQLIntegrityConstraintViolationException Exception) {
            topView.errorPopUp("Staff", "Could not delete due to existing transactions connected to "
                    + subtab.getComponentText("firstName") + " " + subtab.getComponentText("lastName"));
        } catch (SQLException exception) {
            topView.errorPopUp("SQLException", exception.getMessage());
        }

        topView.getComponent(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB, "deleteStaff").setEnabled(false);
    }

    public void checkStaffHistory() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
        String[][] data;
        String staffId = subtab.getComponentText("staffId");
        String firstName = subtab.getComponentText("firstName");
        String lastName = subtab.getComponentText("lastName");

        String queryA = """
                SELECT CONCAT(s.first_name, " ", s.last_name) AS staff_name,
                a.title, c.episode, c.position, c.department
                FROM credits c
                JOIN staff s ON s.staff_id = c.staff_id
                JOIN animes a ON c.anime_id = a.anime_id
                WHERE s.staff_id = """;
        String queryB = """
                
                ORDER BY a.anime_id;
                """;

        try {
            Integer.parseInt(staffId);
            data = animeSystem.rawQuery(queryA + staffId + queryB);
            topView.displayTable(data,
                new String[]{"Staff Name", "Anime Title", "Episode", "Position", "Department"},
                new String(firstName + " " + lastName + "'s Work History"));
        } catch (Exception exception) {
            topView.errorPopUp("Staff", "Cannot fetch staff history");
            System.out.println(exception.getMessage());
        }
    }

    // Studio records management

    public void searchStudio() {
        topView.selectFromTable("studios");
    }

    public void addNewStudio() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB);
    }

    public void saveStudio() {

        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB);
        String studioId = subtab.getComponentText("studioId");
        String studio_name = subtab.getComponentText("studioName");
 
        try {
            Integer.parseInt(studioId);
            // User ID field was parsed successfully; this must be an existing record
            updateStudio(studioId, studio_name);
            topView.dialogPopUp("Studio", "Studio name successfully changed to "+studio_name+".");
        } catch (NumberFormatException exception) {
            createStudio(studio_name);
            topView.dialogPopUp("Studio", "Studio "+studio_name+" successfully created.");
        }
    }

    public void createStudio(String studio_name){
        try {
            animeSystem.safeUpdate(
                    "INSERT INTO `studios` (`studio_name`) VALUES (?)",
                    studio_name);
            this.refreshRecordTableData(Records.STUDIO);
        } catch (SQLException exception) {
            topView.dialogPopUp("SQLException", exception.getMessage());
        }
    }


    public void updateStudio(String studioID, String studio_name) {
        try {
            animeSystem.safeUpdate(
                    "UPDATE `studios` SET `studio_name` = ? WHERE `studio_id` = ?",
                    studio_name, studioID);
            this.refreshRecordTableData(Records.STUDIO);
        } catch (SQLException exception) {
            topView.dialogPopUp("SQLException", exception.getMessage());
        }
    }

    public void deleteStudio() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.STUDIO_RECORD_SUBTAB);
        String studio_id = subtab.getComponentText("studioId");
        String studio_name = subtab.getComponentText("studioName");

        try {
            animeSystem.safeUpdate("DELETE FROM `studios` WHERE `studio_id` = ?", studio_id);
            this.refreshRecordTableData(Records.STUDIO);
            topView.dialogPopUp("Studio", "Deletion of Studio "+studio_name+" successful.");
        } catch (SQLIntegrityConstraintViolationException Exception) {
            topView.errorPopUp("Studio", "Could not delete due to existing animes.");
        } catch (SQLException exception) {
            topView.errorPopUp("SQLException", exception.getMessage());
        }

    }

}
