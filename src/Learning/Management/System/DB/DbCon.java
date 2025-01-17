package Learning.Management.System.DB;

import java.sql.DriverManager;
import java.sql.Statement;

public class DbCon {
    private java.sql.Connection connection;
    private Statement statement;

    public DbCon() {
        try {
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

    // Method to close the connection and statement to prevent resource leaks
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
