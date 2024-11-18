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



    public AnimeSystem(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(PATH, username, password);
            dbStatement = dbConnection.createStatement();
        } catch (Exception e) {
            System.err.println("Unable to establish connection");
            e.printStackTrace();
        }
    }

    // # Records

    // ## Anime

    public String[] getRecordColNames(String recordName){
        ArrayList<String> returnVal = new ArrayList<String>();
        try {
            dbResultSet = dbStatement.executeQuery(
                                "SELECT `COLUMN_NAME` \r\n" + //
                                "FROM `INFORMATION_SCHEMA`.`COLUMNS` \r\n" + //
                                "WHERE `TABLE_SCHEMA`= 'dbanime'\r\n" + //
                                "AND `TABLE_NAME`='" +  recordName +  "';");
            while (dbResultSet.next()) {
                returnVal.add(dbResultSet.getString("COLUMN_NAME"));
            }
        } catch (Exception e) {
            System.err.println("Query to 'dbanime' Failed.");
            e.printStackTrace();
        }

        return returnVal.toArray(new String[0]);
    }

    // public boolean checkIfExists(int value, String column, String table){

    // }

    public void callProcedure(String procedure) {
        try {
            dbStatement.executeQuery("CALL " + procedure);
        } catch (Exception e) {
            System.err.println("Procedure" + " Failed to Execute.");
            e.printStackTrace();
        }
    }

    public String getProcedureSingleResult(String procedure) {
        try {
            dbResultSet = dbStatement.executeQuery("CALL " + procedure + ";");
            return dbResultSet.getString(1);
        } catch (Exception e) {
            System.err.println("Procedure" + " Failed to Execute.");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] getProcedureResults(String procedure) {
        ArrayList<String[]> results = new ArrayList<String[]>();
        try {
            dbResultSet = dbStatement.executeQuery("CALL " + procedure);
            dbMetaData = dbResultSet.getMetaData();
            int columnCount = dbMetaData.getColumnCount();
            while (dbResultSet.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = dbResultSet.getString(i + 1);
                }
                results.add(rowData);
            }
            return results.toArray(new String[0][0]);
        } catch (Exception e) {
            System.err.println("Procedure" + " Failed to Execute.");
            e.printStackTrace();
            return null;
        }
    }

    public String[][] query(String[] columns, String record) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        String columnsString = new String();
        for (String column : columns) {
            columnsString += column + ", ";
        }
        columnsString = columnsString.substring(0, columnsString.length() - 2);
        String query = "SELECT " + columnsString + " FROM " + record;
        System.out.println(query);
        try {
            dbResultSet = dbStatement.executeQuery(query);
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

    public String[][] getAnimes() {
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

}
