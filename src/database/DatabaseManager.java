package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/DOJ_Lawyers_MDT";
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static Connection connection;

    
    private DatabaseManager() {}

    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                    "MySQL JDBC Driver not found.\nPlease add mysql-connector-j.jar to your project.", 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Database connection failed.\nPlease check your MySQL settings:\n" +
                    "URL: " + URL + "\n" +
                    "User: " + USER + "\n" +
                    "Error: " + e.getMessage(), 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        return connection;
    }

    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

