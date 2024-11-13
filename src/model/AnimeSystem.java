package src.model;

import java.util.ArrayList;
import java.sql.*;

/**
 * TODO: Actually use a SQL Database instead of hardcoding these lists
 */
public class AnimeSystem {

    public final static String PATH = "jdbc:mysql://localhost:3306/dbanime";

    private Connection dbConnection;
    private Statement dbStatement;
    private ResultSet dbResultSet;
    private ResultSetMetaData dbMetaData;
    private ArrayList<String> titles;
            // "Ao no Kanata Four Rhythms Across the Blue",
            // "Cory in the House",
            // "Dragon Ball Z",
            // "Goblin Slayer",
            // "Konosuba: God's Gift on this Wonderful World",
            // "Love Live School Idol Project",
            // "Love Live Sunshine",
            // "Mayo Chiki!",
            // "Miss Kobayashi's Dragon Maid",
            // "Nichijou",
            // "Sankarea",
            // "Shrek",
            // "Tokyo Ghoul",
            // "The Helpful Fox Senko-san",
            // "Trinity Seven"

    public AnimeSystem(String username, String password) {
        titles = new ArrayList<String>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(PATH, username, password);
            dbStatement = dbConnection.createStatement();
        } catch (Exception e) {
            System.err.println("Unable to establish connection");
            e.printStackTrace();
        }
        this.getTitles();
    }

    // Titles
    
    public void getTitles() {
        try {
            dbResultSet = dbStatement.executeQuery("SELECT * FROM animes");
            while (dbResultSet.next()) {
                String title = dbResultSet.getString("title");
                if (!title.equals("")) {
                    titles.add(title);
                }
            }
        } catch (Exception e) {
            System.err.println("Query to 'dbanime' Failed.");
            e.printStackTrace();
        }
    }

    public void callProcedure(String procedure){
        try {
            dbStatement.executeQuery("CALL " + procedure);
        } catch (Exception e) {
            System.err.println("Procedure" + " Failed to Execute.");
            e.printStackTrace();
        }
    }

    public String[][] getAnimes(){
        ArrayList<String[]> data = new ArrayList<String[]>();
        try {
            dbResultSet = dbStatement.executeQuery("SELECT title, genre, air_date FROM animes");
            dbMetaData = dbResultSet.getMetaData();
            int columnCount = dbMetaData.getColumnCount();
            while (dbResultSet.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = dbResultSet.getString(i + 1);
                } 
                data.add(rowData);
            }
        } catch (Exception e) {
            System.err.println("Query to 'dbanime' Failed.");
            e.printStackTrace();
        }
        return data.toArray(new String[0][0]);
    }

    public String minimize(String string) {
        string = string.toLowerCase();
        string.replaceAll(" ", "");
        return string;
    }

    public String[] searchTitles(String text) {
        ArrayList<String> results = new ArrayList<>();
        String minimizedSearch = minimize(text);
        String minimizedTitle;
        for (String title : titles) {
            minimizedTitle = minimize(title);
            if (minimizedTitle.contains(minimizedSearch)) {
                results.add(title);
            }
        }
        return results.toArray(new String[0]);
    }
}
