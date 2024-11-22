package src.controller;

import java.awt.event.ActionListener;

import src.model.AnimeSystem;
import src.model.Records;
import src.view.gui.TopView;

/**
 * This abstract class holds some common functionality between the
 * RecordsTabListener, TransactionsTabListener, and ReportsTabListener classes.
 */
public abstract class TabListener implements ActionListener {
    AnimeSystem animeSystem;
    TopView topView;

    public TabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    /**
     * Validate an id read as a String.
     * 
     * @param id
     * @param errorTitle
     * @param errorBody
     * @return true if `id` is valid, false otherwise
     */
    public boolean validateId(String id, String errorTitle, String errorBody) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            topView.errorPopUp(errorTitle, errorBody);
            return false;
        }
        return true;
    }

    public void searchAnime() {
        topView.selectFromTable(Records.ANIME);
    }

    public void searchUser() {
        topView.selectFromTable(Records.USER);
    }

    public void searchStaff() {
        topView.selectFromTable(Records.STAFF);
    }

    public void searchStudio() {
        topView.selectFromTable(Records.STUDIO);
    }
}
