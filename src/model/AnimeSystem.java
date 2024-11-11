package src.model;

import java.util.ArrayList;
import java.sql.*;

/**
 * TODO: Actually use a SQL Database instead of hardcoding these lists
 */
public class AnimeSystem {

    public final static String PATH = "jdbc:mysql://localhost:3306/dbanime";
    public final static String USER = "root";
    public final static String PASS = "1234";

    Connection dbConnection;
    Statement dbStatement;
    ResultSet dbResultSet;
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

    public AnimeSystem() {
        titles = new ArrayList<String>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(PATH, USER, PASS);
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
            System.err.println("Query to 'dbanime' Failed");
            e.printStackTrace();
        }
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
