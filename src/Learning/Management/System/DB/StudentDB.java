package Learning.Management.System.DB;

import Learning.Management.System.ApplicationLayer.UserManagement.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    // Method to find a student in the database based on email and password
    public Student findStudent(String email, String password) {
        String selectSQL = "SELECT name, email FROM students WHERE email = ? AND password = ?";
        Student student = null;

        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password); // Assume the password should be hashed.
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, create a Student object
            if (resultSet.next()) {
                student = new Student(resultSet.getString("name"), resultSet.getString("email"), password);
                // Note: Do not return the password directly or store it in the object in practice.
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider better error handling)
        }

        return student; // Return the found student or null if not found
    }
}