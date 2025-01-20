package Learning.Management.System.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbCon implements AutoCloseable {  // Implement AutoCloseable
    private Connection connection;
    private Statement statement;

    public DbCon() {
        try {
            // Initialize the database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234SALman#");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to establish connection to the database.");
        }
    }

    // Method to check if the connection is established
    public boolean isConnectionEstablished() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get the connection
    public Connection getConnection() {
        return connection; // Returns the established connection
    }

    // Method to close resources
    @Override
    public void close() { // Override close to implement AutoCloseable
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}