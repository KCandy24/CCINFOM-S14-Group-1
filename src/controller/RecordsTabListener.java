package src.controller;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.JComponent;

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

        // TODO: ENABLE DELETE BUTTON IF DISABLED
    }

    public void addNewAnime() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);

        // TODO: DISABLE DELETE BUTTON
    }

    public void saveAnime() {
        // TODO: IMPLEMENTATION

        // TODO: ENABLE DELETE BUTTON IF DISABLED
    }

    public void deleteAnime() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);

        // TODO: IMPLEMENTATION
        // TODO: DISABLE DELETE BUTTON
    }

    // User records management

    public void searchUser() {
        topView.selectFromTable("users");

        // TODO: ENABLE DELETE BUTTON IF DISABLED
    }

    public void addNewUser() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);

        // TODO: DISABLE DELETE BUTTON
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
        // TODO: ENABLE DELETE BUTTON IF DISABLED
    }

    public void createUser(String username, String region, String joinDate) {
        try {
            animeSystem.safeUpdate(
                    "INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES (?, ?, ?)",
                    username, region, joinDate);
        } catch (SQLException exception) {
            topView.dialogPopUp("SQLException", exception.getMessage());
        }
    }

    public void updateUser(String userId, String username, String region, String joinDate) {
        try {
            animeSystem.safeUpdate(
                    "UPDATE `users` SET `user_name` = ?, `region` = ?, `join_date` = ? WHERE `user_id` = ?",
                    username, region, joinDate, userId);
        } catch (SQLException exception) {
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
