package com.isep.T2;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class SQLite {
    protected static Connection conn = null;

    /*
     * Check if a table is in db
     *
     * @param fileName the database file name
     */
    public static boolean CheckTableExist(String tableName) throws Exception {
        try {
            Statement stmt = conn.createStatement();
            final DatabaseMetaData metaData = conn.getMetaData();
            final ResultSet tables = metaData.getTables(null, null, tableName, null);
            if (tables.next()) {
                System.out.println("The table named " + tableName + " already exists");
                stmt.close();
                return true;
            } else {
                stmt.close();
                return false;
                //}
            }
        } catch (Exception ex) {
            throw new Exception("Failed to create table named " + tableName + " in database", ex);
        }
    }

    /*
     * Connect to the Database
     */
    public static void connect(String dbName) throws Exception {
        if (conn != null) {
            return;
        }
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db/" + dbName);
        System.out.println("Connected to the db");
    }

    /*
     * Create a table in db
     */
    public static void CreateTable(String tableName) throws Exception {
        try {
            Statement stmt = conn.createStatement();

            String createTableSQL = "CREATE TABLE " + tableName + "("
                    + "date_time text NOT NULL,"
                    + "Classement text NOT NULL "
                    + ")";
            System.out.println(tableName + " is created");
            stmt.execute(createTableSQL);
            stmt.close();
        } catch (Exception ex) {
            throw new Exception("Failed to create table named " + tableName + " in database", ex);
        }

    }

    public static void SendData(String data, String tableName) throws Exception {
        String sql = "INSERT INTO " + tableName + "(date_time,classement) VALUES(?,?)";
        try {
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            String date = getDateTime();
            pstmt.setString(1, date);
            pstmt.setString(2, data);
            pstmt.executeUpdate();
            System.out.println("Data send");
            pstmt.close();
        } catch (Exception ex) {
            throw new Exception("Failed to send the data in database", ex);
        }

    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
