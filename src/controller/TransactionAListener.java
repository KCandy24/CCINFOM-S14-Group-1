package src.controller;

import java.awt.event.*;

import javax.swing.JComponent;

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
        String name = ((JComponent) e.getSource()).getName();
        System.out.println("\nRecords/User/actionPerformed?value=" + action + "&name=" + name);

        switch (action) {
            case "Check":
                break;
            case "Watch next episode":
                int user_id = 2; // Get From topview
                int anime_id = 2; // Get From topview
                
                int lastWatched = Integer.parseInt(animeSystem.getProcedureSingleResult(String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
                topView.dialogPopUp("Watch Episode", "Successfully watched anime episode " + lastWatched);
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unimplemented method 'actionPerformed'");
        }
    }
    
}
