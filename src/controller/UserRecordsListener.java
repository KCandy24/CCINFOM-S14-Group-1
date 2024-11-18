package src.controller;

import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.plaf.synth.Region;

import src.model.AnimeSystem;
import src.model.Genre;
import src.model.UserRegion;
import src.view.gui.TopView;

/**
 * Handles events in the Records/User subtab.
 */
public class UserRecordsListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public UserRecordsListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("\nRecords/User/?button=" + action);

        switch (action) {
            case "Search":
                this.topView.selectFromTable("users");
                break;
            case "Add new":
                this.topView.resetFields(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB);
                break;
            case "Save":
                this.save();
                this.updateFields();
                break;
            case "Delete":
                break;
            default:
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }

    public void updateFields() {
        String[] columns = animeSystem.getRecordColNames("users");
        String[][] data = animeSystem.selectColumns(columns, "users");
        topView.setRecordTableData("users", data, columns);
    }

    public void save() {
        String userName = ((JTextField) topView.getComponent(
                TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB, "username")).getText();
        String region = UserRegion.findCode(((String) ((JComboBox<String>) topView.getComponent(TopView.RECORDS_TAB,
                TopView.USER_RECORD_SUBTAB,
                "region")).getSelectedItem()));
        String joinDate = ((String) ((JTextField) topView.getComponent(TopView.RECORDS_TAB, TopView.USER_RECORD_SUBTAB,
                "joinDate")).getText());
        animeSystem.safeUpdate(
                "INSERT INTO `users` (`user_name`, `region`, `join_date`) VALUES (?, ?, ?)",
                userName, region, joinDate);

    }

}
