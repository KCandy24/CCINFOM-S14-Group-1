package src.controller;

import java.awt.event.*;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * Handles events in the Records/Staff subtab.
 */
public class StaffRecordsListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public StaffRecordsListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("\nRecords/Staff/?button=" + action);

        switch (action) {
            case "Search":
                topView.selectFromTable("staff");
            case "Add new":
                topView.resetFields(TopView.RECORDS_TAB, TopView.STAFF_RECORD_SUBTAB);
                break;
            case "Save":
                break;
            case "Delete":
                break;
            default:
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
        }
    }

}
