package Learning.Management.System.ApplicationLayer.UserManagement;

import Learning.Management.System.DB.StudentDB;

public class Student {
    private String name;
    private String email;
    private String password;

    // Uncomment if needed, but for proper design, consider using a singleton or dependency injection for DB class.
    // private static StudentDB studentDB = new StudentDB(); // Use static DB for all instances

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password; // Note: Password should ideally be hashed for security.
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

        StudentDB studentDB = new StudentDB();
        boolean result = studentDB.addStudent(this); // Use the new StudentDB to save student

        // Print registration result
        System.out.println("Registration " + (result ? "successful!" : "failed."));
        return result;
    }

    // Login student by checking if the student exists in the database
    public boolean loginStudent(String email, String password) {
        // Print statement before login
        System.out.println("Attempting to login student:");
        System.out.println("Email: " + email);

        StudentDB studentDB = new StudentDB();
        Student foundStudent = studentDB.findStudent(email, password); // Check if student is registered


        boolean result = foundStudent != null; // Login successful if student is found

        // Print login result
        System.out.println("Login " + (result ? "successful!" : "failed."));
        return result;

    }


}