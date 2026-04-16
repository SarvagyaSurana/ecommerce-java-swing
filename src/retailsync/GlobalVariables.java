package ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GlobalVariables {
	 String url = "jdbc:mysql://localhost:3306/e-commerce";
     String username = "root";
     String password = "root123";
     //replace username password using config file
     

     public static Connection connection;
     public static Statement statement;
     public static int userID;
     
   public GlobalVariables() {
	   try {
           // Load the MySQL JDBC driver class
           Class.forName("com.mysql.cj.jdbc.Driver");

           // Establish connection to the database
           connection = DriverManager.getConnection(url, username, password);
           statement = connection.createStatement();
           System.out.println("Connected Succesfully");
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
       }
   }
}
