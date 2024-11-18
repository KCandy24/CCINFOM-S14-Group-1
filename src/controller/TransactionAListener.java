package src.controller;

import java.awt.event.*;

import javax.swing.JButton;

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
        JButton button = ((JButton)e.getSource());
        String name = button.getName();
        switch (name) {
            case "searchUser":
                searchUser();
                break;
            case "searchAnime":
                searchAnime();
                break;
            case "watchEpisode":
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

    public void searchAnime() {
        topView.selectFromTable("animes");
    }
    
    public void searchUser() {
        topView.selectFromTable("users");
    }
}
