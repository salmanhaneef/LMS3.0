package Learning.Management.System.DB;

import Learning.Management.System.ApplicationLayer.UserManagement.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDB {
    private DbCon dbCon;

    public StudentDB() {
        dbCon = new DbCon();
    }

    // Method to insert a new student into the database
    public boolean addStudent(Student student) {
        String insertSQL = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Assuming that the password has been hashed before being passed to this method
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // return true if a student was added successfully

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider better error handling)
            return false; // Return false if there was an error
        }
    }
}
