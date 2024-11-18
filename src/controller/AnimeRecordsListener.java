package src.controller;

import java.awt.event.*;

import src.model.AnimeSystem;
import src.view.gui.TopView;

/**
 * Handles events in the Records/Anime subtab.
 */
public class AnimeRecordsListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public AnimeRecordsListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("\nRecords/Anime/?button=" + action);

        switch (action) {
            case "Search":
                searchAnime();
                break;
            case "Add new":
                topView.resetFields(TopView.RECORDS_TAB, TopView.ANIME_RECORD_SUBTAB);
                break;
            case "Save":
                break;
            case "Delete":
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unimplemented method 'actionPerformed'");
        }
    }

    public void searchAnime() {
        topView.selectFromTable("animes");
    }

}
