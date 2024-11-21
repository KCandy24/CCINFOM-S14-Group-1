package src.controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;

import src.model.AnimeSystem;
import src.model.Records;
import src.view.gui.Subtab;
import src.view.gui.TopView;

public class ReportsTabListener implements ActionListener {

    AnimeSystem animeSystem;
    TopView topView;

    public ReportsTabListener(AnimeSystem animeSystem, TopView topView) {
        this.animeSystem = animeSystem;
        this.topView = topView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        System.out.println("\nTransactions/?buttonName=" + name);
        Subtab subtab;

        switch (name) {
            case "checkButton":
                subtab = topView.getSubtab(TopView.REPORTS_TAB, TopView.HIGHEST_RATED_ANIME_REPORT_SUBTAB);
                String period = subtab.getComponentText("periodComboBox", "period");
                String genre = subtab.getComponentText("genreComboBox", "genre_with_none");
                HashMap<String[], String> data = generateHighestRatedAnime(period, genre);
                topView.displayHighestRatedAnimes(data, period);
                break;

            case "checkRecommendedAnime":
                subtab = topView.getSubtab(TopView.REPORTS_TAB, TopView.RECOMMEND_ANIME_REPORT_SUBTAB);
                try {
                    String username = subtab.getComponentText("username");
                    String year = subtab.getComponentText("yearRecommComboBox", "years");
                    int user_id = Integer.parseInt( subtab.getComponentText("userId"));
                    System.out.println(user_id + username);
                    String mode = subtab.getComponentText("recommendationComboBox", "recommendations");
                    this.generateRecommendations(mode, Integer.parseInt(year), user_id, username);
                } catch (Exception ex) {
                    topView.dialogPopUp("Recommend Anime", "Error User field cannot be empty");
                }
                break;
            case "checkTopStudio":
                subtab = topView.getSubtab(TopView.REPORTS_TAB, TopView.TOP_STUDIOS_REPORT_SUBTAB);
                String year = subtab.getComponentText("yearStudioComboBox", "years");
                System.out.println(year);
                this.generateTopStudios(Integer.parseInt(year), subtab);
                break;
            case "checkUserProfile":
                
                break;
            case "searchUser":
                this.searchUser();
                break;
            case "searchStudio":
                this.searchStudio();
                break;
            default:
                System.err.println("No associated action for " + name);
                break;
        }
    }

    // General
    public void searchUser() {
        topView.selectFromTable(Records.USER.name);
    }

    public void searchStudio() {
        topView.selectFromTable(Records.STUDIO.name);
    }
    //

    public String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[0];
        }
        
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);
        return newArray;
    }


    public HashMap<String[], String> generateHighestRatedAnime(String period, String genre){
        try {
            animeSystem.callProcedure("SelectBestAnime" + period + "(?)", genre);
            String[][] results = animeSystem.rawQuery("SELECT * FROM `best_anime`");

            return stringArrayToMap(results);
        } catch (Exception e) {
            topView.dialogPopUp("Highest Rated Anime", "Error cant make report.");
            return null;
        }
    }

    public void generateRecommendations(String mode, int year, int user_id, String username){
        String[][] data;

        try {
            switch (mode) {
                case "Continue Watching":
                    data = animeSystem.getProcedureResults("RecommendFromWatchList(?, ?)", user_id, year);
                    topView.displayRecommendations(data, new String[]{"Anime Title", "Watched Episodes", "Total Episodes", "Date Released"}, mode, username);
                    break;
                case "From Following":
                    data = animeSystem.getProcedureResults("RecommendFromFollows(?, ?)", user_id, year);
                    topView.displayRecommendations(data, new String[]{"Followed User","Anime Title", "Comments", "Rating", "Date Released"}, mode, username);
                    break;
                case "From Top Genres Watched":
                    data = animeSystem.getProcedureResults("RecommendFromGenre(?, ?)", user_id, year);
                    topView.displayRecommendations(data, new String[]{"Genre", "Total episodes watched of Genre", "Top Unwatched Anime", "Date Released"}, mode, username);
                    break;
            }
        } catch (Exception e) {
            topView.dialogPopUp("Recommend Anime", "Error cannot fetch recommendations");
        }
    }

    public void generateTopStudios(int year, Subtab subtab){
        try {
            String[][] data;
            // if (year.equals("0")) 
            //     data = animeSystem.getProcedureResults("ViewBestStudio(0)");
            // else
                data = animeSystem.getProcedureResults("ViewBestStudio(?)", year);

            // for (String[] strings : data) {
            //     for (String strings2 : strings) {
            //         System.out.println(strings2);
            //     }
            //     System.out.println();
            // }

            topView.displayTopStudios(data, Integer.toString(year));
        } catch (Exception e) {
            topView.dialogPopUp("Top Studios", "Error cannot fetch studio data");
        }
    }

    private HashMap<String[], String> stringArrayToMap (String[][] data){
        int rows = data.length;
        HashMap<String[], String> resultMap = new HashMap<>();

        for (int i = 0; i < rows; i++) {
            String value = data[i][0];
            String[] key = data[i];
            resultMap.put(key, value);
        }
        return resultMap;
    }
}
