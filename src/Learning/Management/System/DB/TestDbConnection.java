package Learning.Management.System.DB;

public class TestDbConnection {
    public static void main(String[] args) {
        DbCon dbConnection = new DbCon();

        // Check if the connection was established
        if (dbConnection.isConnectionEstablished()) {
            System.out.println("Connection established successfully.");
        } else {
            System.out.println("Failed to establish connection.");
        }

        // Close the connection when done
        dbConnection.close();
    }
}
