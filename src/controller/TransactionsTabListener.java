package src.controller;

import java.awt.event.*;

import javax.swing.JComponent;

import src.model.AnimeSystem;
import src.view.gui.TopView;

public class TransactionsTabListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public TransactionsTabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        System.out.printf("%s/%s?%s\n", topView.getCurrentTabName(), topView.getCurrentSubtabName(), name);

        switch (name) {
            // Watch episode
            case "searchUserWatchEpisode":
                this.searchUserWatchEpisode();
                break;
            case "searchAnimeWatchEpisode":
                this.searchAnimeWatchEpisode();
                break;
            case "watchEpisode":
                this.watchEpisode();
                break;

            // Rate anime
            case "searchUserRateAnime":
                this.searchUserRateAnime();
                break;
            case "searchAnimeRateAnime":
                this.searchAnimeRateAnime();
                break;
            case "rateAnime":
                this.rateAnime();
                break;
            // Edit credits

            // Follow user

            default:
                System.err.println("No associated action for " + name);
                break;
        }
    }

    // Watch episode transaction

    public void searchAnimeWatchEpisode() {
        topView.selectFromTable("animes");
    }

    public void searchUserWatchEpisode() {
        topView.selectFromTable("users");
    }

    public void watchEpisode() {
        int user_id = 2; // Get From topview
        int anime_id = 2; // Get From topview

        int lastWatched = Integer.parseInt(animeSystem
                .getProcedureSingleResult(String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
        topView.dialogPopUp("Watch Episode", "Successfully watched anime episode " + lastWatched);
    }

    // Rate anime

    public void searchAnimeRateAnime() {
        topView.selectFromTable("animes");
    }

    public void searchUserRateAnime() {
        topView.selectFromTable("users");
    }

    public void rateAnime() {
        // TODO: IMPLEMENTATION
    }

    // Edit credits

    // Follow user

}
