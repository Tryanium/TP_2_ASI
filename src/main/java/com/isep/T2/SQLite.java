package com.isep.T2;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.ResultSetMetaData;

public class SQLite {
	
    protected static Connection conn = null;
    protected static String dbName = "test.db";


	public static void main() throws Exception {
		
		boolean createDB = createNewDatabase(dbName);
		if(createDB) {
			System.out.println("Database Created");
		} else {
			System.out.println("DB already Exists");
		}
		boolean createTable = createNewTable("TEST");
		if(createTable) {
			System.out.println("Table Created");
		} else {
			System.out.println("Table already exists");
		}
	}
	
	/*
    * Connect to a sample database
    *
    * @param fileName the database file name
    */
   public static boolean createNewDatabase(String db) throws SQLException {
	   
	   File file = new File ("db/" + db);
	   if(file.exists()) {
		   return false;
		   
	   }
	   else {
		   connect();
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
    * Create a table in db
    *
    * @param fileName the database file name
    */
   
   public static boolean createNewTable(String tableName) throws Exception {
	        connect();
	        try (Statement stmt = conn.createStatement()) {
	            final DatabaseMetaData metaData = conn.getMetaData();
	            final ResultSet tables = metaData.getTables(null, null, tableName, null);
	            if (tables.next()) {
	                System.out.println("The table named " + tableName + " already exists");
	                return false;
	            } else {

	                // SQL statement for creating a new table
	                String sql = "CREATE TABLE IF NOT EXISTS "+ tableName + "(\n"
	                        + "	id integer PRIMARY KEY,\n"
	                        + "	name text NOT NULL,\n"
	                        + "	capacity real\n"
	                        + ");";
	              
	                    // create a new table
	                    stmt.execute(sql);
	            	return true;
	            }
	        } catch (Exception ex) {
	            throw new Exception("Failed to create table named " + tableName + " in database", ex);
	        }
	       }

   public static void connect(){
       if(conn != null){
           return;
       }
       try {
           Class.forName("org.sqlite.JDBC");
           conn = DriverManager.getConnection("jdbc:sqlite:db/" + dbName);
       }
       catch(ClassNotFoundException | SQLException e){
           System.err.println(e.getClass().getName() + ": " + e.getMessage());
       }
   }
}
