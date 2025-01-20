package Learning.Management.System.ApplicationLayer.UserManagement;

import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String name;
    private String email;
    private String password;
    private DbCon dbCon;

    // Uncomment if needed, but for proper design, consider using a singleton or dependency injection for DB class.
    // private static StudentDB studentDB = new StudentDB(); // Use static DB for all instances

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password; // Note: Password should ideally be hashed for security.
        dbCon = new DbCon();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password; // For security purposes, consider not exposing the password.
    }

    // Register a new student and return boolean for success or failure
    public boolean registerStudent(String name, String email,String password) {
        // Print statement before registration
        System.out.println("Attempting to register student:");
        System.out.println("Name: " + name + ", Email: " + email);
        String insertSQL = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            // Assuming that the password has been hashed before being passed to this method
            preparedStatement.setString(1, getName());
            preparedStatement.setString(2, getEmail());
            preparedStatement.setString(3, getPassword());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // return true if a student was added successfully

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider better error handling)
            return false; // Return false if there was an error
        }

    }

    // Login student by checking if the student exists in the database
    public boolean loginStudent(String email, String password) {
        // Print statement before login
        System.out.println("Attempting to login student:");
        System.out.println("Email: " + email);
        String selectSQL = "SELECT name, email FROM students WHERE email = ? AND password = ?"; // Ideally, consider using hashed passwords
        Student student = null;

        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password); // Make sure to hash the password in practice
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, create a Student object
            if (resultSet.next()) {
                student = new Student(resultSet.getString("name"), resultSet.getString("email"), password);
                // Password should not be stored or returned; consider removing it from Student.
                return true; // Return true indicating successful login
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider better error handling)
        }

        return false; // Return false if login failed
    }

}