package Learning.Management.System.ApplicationLayer.UserManagement;

import Learning.Management.System.DB.StudentDB;

public class Student {
    private String name;
    private String email;
    private String password;

//    private static StudentDB studentDB = new StudentDB(); // Use static DB for all instances

    public Student(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getName(){return name;}
    public String getPassword(){return password;}

    // Update registerStudent to return boolean
    public boolean registerStudent(String name, String email, String password) {
        // Print statement before registration
        System.out.println("Attempting to register student:");
        System.out.println("Name: " + name + ", Email: " + email);

        StudentDB studentDB = new StudentDB();
        boolean result = studentDB.addStudent(this); // Use the new StudentDB to save student
         // Return the result of the registration

        // Print registration result
        System.out.println("Registration " + (result ? "successful!" : "failed."));
        return result;

    }

    public boolean loginStudent(String email, String password) {
        // Print statement before login
        System.out.println("Attempting to login student:");
        System.out.println("Email: " + email);

        boolean result = true; // Delegate the login logic to StudentDB

        // Print login result
        System.out.println("Login " + (result ? "successful!" : "failed."));
        return result;
    }

//    public void deleteStudent() {
//        studentDB.deleteStudent(email);
//    }
//
//    public void getAllStudents() {
//        studentDB.getAllStudents();
//    }
}