package com.isep.T2;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
    protected static Connection conn = null;


	public static void main() throws Exception {
		
	}
	
	/*
    * Create a Database
    *
    * @param fileName the database file name
    */
   public static boolean createNewDatabase(String db) throws SQLException {
	   
	   File file = new File ("db/" + db);
	   if(file.exists()) {
		   System.out.println("The db already exists");
		   return false;
		   
	   }
	   else {
	           if (conn != null) {
	               DatabaseMetaData meta = conn.getMetaData();
	               System.out.println("The driver name is " + meta.getDriverName());
	               System.out.println("A new database has been created.");
	               return true;
	           }
	   }  
	   return false;
       
   }

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
	        }} catch (Exception ex) {
	            throw new Exception("Failed to create table named " + tableName + " in database", ex);
	        }
	       }

   /*
    * Connect to the Database
    */
   public static void connect(String dbName){
       if(conn != null){
           return;
       }
       try {
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection("jdbc:sqlite:db/" + dbName);
           System.out.println("Connected to the db");
       }
       catch(ClassNotFoundException | SQLException e){
           System.err.println(e.getClass().getName() + ": " + e.getMessage());
       }
   }

   /*
    * Create a table in db
    */
   public static void CreateTable(String tableName) throws Exception {
	   try {
		   Statement stmt = conn.createStatement();
		   
		   String createTableSQL = "CREATE TABLE " + tableName  + "("
					+ "USER_ID NUMBER(5) NOT NULL, "
					+ "USERNAME VARCHAR(20) NOT NULL, "
					+ "CREATED_BY VARCHAR(20) NOT NULL, "
					+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
					+ ")";
		   System.out.println( tableName + " is created");
		   stmt.execute(createTableSQL);
		   stmt.close();
	   } catch (Exception ex) {
           throw new Exception("Failed to create table named " + tableName + " in database", ex);
       }

   }
}
