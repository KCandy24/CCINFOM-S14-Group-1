package src.controller;

import java.awt.event.*;

import javax.swing.JComponent;

import src.model.AnimeSystem;
import src.view.gui.Subtab;
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

        int user_id = this.getUserIDFromTopView();
        int anime_id = this.getAnimeIDFromTopView();

        switch (name) {
            // Watch episode
            case "searchUserWatchEpisode":
                this.searchUserWatchEpisode();
                break;
            case "searchAnimeWatchEpisode":
                this.searchAnimeWatchEpisode();
                break;
            case "watchEpisode":
                this.watchEpisode(user_id, anime_id);
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

        if (user_id != 0 && anime_id != 0) {
            Subtab subtab = topView.getSubtab(TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB);
            subtab.setComponentText(subtab.getComponent("episode"), 
            animeSystem.getProcedureSingleResult(String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
        }
    }

    // Watch episode transaction

    public void searchAnimeWatchEpisode() {
        topView.selectFromTable("animes");
    }

    public int getUserIDFromTopView(){
        try {
            Subtab subtab = topView.getSubtab(TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB);
            String userIDString = subtab.getComponentText("userId");
            int user_id = Integer.parseInt(userIDString);
            return user_id;
        } catch (Exception e) {
            return 0;
        }
    } 

    public void searchUserWatchEpisode() {
        topView.selectFromTable("users");
    }

    
    public int getAnimeIDFromTopView(){
        try {
            Subtab subtab = topView.getSubtab(TopView.TRANSACTIONS_TAB, TopView.WATCH_EPISODE_TRANSACTION_SUBTAB);
            String animeIDString = subtab.getComponentText("animeId");
            int anime_id = Integer.parseInt(animeIDString);
            return anime_id;
        } catch (Exception e) {
            return 0;
        }
    } 

    public void watchEpisode(int user_id, int anime_id) {
        if (user_id == 0 || anime_id == 0) {
            topView.dialogPopUp("Watch Episode", "User ID and Anime ID cannot be empty");
        }
        else{
            int lastWatched = Integer.parseInt(animeSystem.getProcedureSingleResult
                            (String.format("GetLastWatchedQ(%d, %d)", user_id, anime_id)));
            int maxEpisodes = Integer.parseInt(animeSystem.singleQuery("SELECT num_of_episodes FROM animes WHERE anime_id = " + anime_id));
            if (lastWatched == maxEpisodes) {
                topView.dialogPopUp("Watch Episode", "User has watched all episodes of this anime.");
            }
            else
            {
                try {
                    animeSystem.callProcedure("WatchAnime(?, ?)", Integer.toString(user_id), Integer.toString(anime_id));
                    topView.dialogPopUp("Watch Episode", "Successfully watched anime episode " + (lastWatched + 1));
                } catch (Exception e) {
                    topView.dialogPopUp("Watch Episode", "An error occured, cannot watch episode.");
                }
            }
        }
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
