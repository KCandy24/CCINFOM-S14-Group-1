package src.controller;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mysql.cj.protocol.a.SqlDateValueEncoder;

import src.model.AnimeSystem;
import src.model.UserRegion;
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
        System.out.println("\nRecords/Anime/?buttonName=" + name);

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
    }

    public void addNewAnime() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
    }

    public void saveAnime() {
        // TODO: IMPLEMENTATION
    }

    public void deleteAnime() {
        // TODO: IMPLEMENTATION
    }

    // User records management

    public void searchUser() {
        topView.selectFromTable("users");
    }

    public void addNewUser() {
        topView.resetFields(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
    }

    public void saveUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        JTextField userNameField = (JTextField) subtab.getComponent("username");
        JComboBox<String> regionComboBox = (JComboBox<String>) subtab.getComponent("region");
        JTextField joinDateField = (JTextField) subtab.getComponent("joinDate");
        try {
            animeSystem.safeUpdate(
                "INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES (?, ?, ?)",
                userNameField.getText(), regionComboBox.getSelectedItem().toString(), joinDateField.getText());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void deleteUser() {
        Subtab subtab = topView.getSubtab(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
        JLabel userIdLabel = (JLabel) subtab.getComponent("userId");
        String userId = userIdLabel.getText();
        try {
            animeSystem.safeUpdate("DELETE FROM `users` WHERE `user_id` = ?", userId);
        } catch (SQLException exception) {
            exception.printStackTrace();
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
