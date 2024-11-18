package src.controller;

import java.awt.event.*;

import src.model.AnimeSystem;
import src.view.gui.TopView;

public class TransactionAListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public TransactionAListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Check":
                break;
            case "Watch next episode":
                topView.dialogPopUp("Watch Episode", "Successfully watched anime episode n");
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unimplemented method 'actionPerformed'");
        }
    }
    
}
