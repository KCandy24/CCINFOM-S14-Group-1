package src.controller;

import java.awt.event.*;
import java.util.HashMap;
import javax.swing.JComponent;

import src.model.AnimeSystem;
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

        Subtab subtab = topView.getSubtab(TopView.REPORTS_TAB, TopView.HIGHEST_RATED_ANIME_REPORT_SUBTAB);

        switch (name) {
            case "checkButton":
                String period = subtab.getComponentText("periodComboBox", "period");
                String genre = subtab.getComponentText("genreComboBox", "genre_with_none");
                HashMap<String[], String> data = generateHighestRatedAnime(period, genre);
                topView.displayHighestRatedAnimes(data, period);
                // for (Map.Entry<String, String[]> entry : data.entrySet()) {
                //     String key = entry.getKey();
                //     String[] value = entry.getValue();
                //     System.out.println(key);
                //     for (String string : value) {
                //         System.out.println(string);
                //     }
                //     System.out.println();
                // }
                break;
            default:
                System.err.println("No associated action for " + name);
                break;
        }
    }



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
            animeSystem.callProcedure("SelectBestAnime" + period + "()");
            String[][] results = animeSystem.rawQuery("SELECT * FROM `best_anime`" +
                                                    " WHERE genre = \"" + genre + 
                                                    "\" OR \"" + genre + "\" = \"None\"");

            return stringArrayToMap(results);
        } catch (Exception e) {
            topView.dialogPopUp("Highest Rated Anime", "Error cant make report.");
            return null;
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
