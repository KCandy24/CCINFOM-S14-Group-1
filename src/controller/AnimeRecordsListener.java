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
            case "Check":
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

}
