package src.model;

import java.util.ArrayList;
import java.sql.*;

/**
 * TODO: MySQL Connections
 */
public class AnimeSystem {

    public final static String PATH = "jdbc:mysql://localhost:3306/dbanime";

    private Connection dbConnection;
    private Statement dbStatement;
    private ResultSet dbResultSet;
    private ResultSetMetaData dbMetaData;
    private ArrayList<String> titles;
    private ArrayList<String> procedureList;

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
        procedureList = new ArrayList<String>();
        try {
            dbResultSet = dbStatement.executeQuery
            (
                "SELECT * " +
                "FROM information_schema.routines " +
                "WHERE routine_type = 'PROCEDURE' " +
                    "AND ROUTINE_SCHEMA = 'dbanime';"
            );
            while (dbResultSet.next()) {
                procedureList.add(dbResultSet.getString("SPECIFIC_NAME"));
                System.out.println(dbResultSet.getString("SPECIFIC_NAME"));
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch procedure list.");
            e.printStackTrace();
        }
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
