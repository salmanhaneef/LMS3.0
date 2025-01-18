package Learning.Management.System.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbCon {
    private Connection connection;
    private Statement statement;

    public DbCon() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234SALman#");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to check if the connection is established
    public boolean isConnectionEstablished() {
        try {
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get the connection (the new method added)
    public Connection getConnection() {
        return connection; // Returns the established connection
    }

    // Method to close resources
    public void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}