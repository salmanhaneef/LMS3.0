package Learning.Management.System.ApplicationLayer.UserManagement;

import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private String id; // Unique identifier for each student
    private String name;
    private String email;
    private String password;
    private DbCon dbCon;
    private static Student currentUser; // To hold the currently logged-in user

    public Student(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password; // Note: Password should ideally be hashed for security.
        dbCon = new DbCon();
    }

    public String getId() {
        return id; // Get the student's ID
    }

    public String getEmail() {
        return email; // Get the student's email
    }

    public String getName() {
        return name; // Get the student's name
    }

    public String getPassword() {
        return password; // For security purposes, consider not exposing the password.
    }

    public static Student getCurrentUser() {
        return currentUser; // Returns the currently logged-in user
    }

    public boolean registerStudent(String name, String email, String password) {
        System.out.println("Attempting to register student:");
        System.out.println("Name: " + name + ", Email: " + email);
        String insertSQL = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password); // Ensure password is hashed before calling this method

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loginStudent(DbCon dbCon, String email, String password) {
        System.out.println("Attempting to login student:");
        System.out.println("Email: " + email);
        String selectSQL = "SELECT id, name, email FROM students WHERE email = ? AND password = ?"; // Use hashed password in practice

        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password); // Ensure to hash password

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Create a Student object with ID as String
                currentUser = new Student(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("email"), password);
                return true; // Successful login
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Login failed
    }

    public static void logout() {
        if (currentUser != null) {
            System.out.println("Logging out user: " + currentUser.getName());
            currentUser = null; // Clear the current user
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}