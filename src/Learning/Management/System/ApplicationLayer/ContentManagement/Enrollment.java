package Learning.Management.System.ApplicationLayer.ContentManagement;

import Learning.Management.System.ApplicationLayer.UserManagement.Student;
import Learning.Management.System.DB.DbCon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Enrollment {
    private String studentId; // Unique identifier for the student
    private String courseId;
    // Unique identifier for the course
    private DbCon dbCon;       // Database connection instance

    public Enrollment(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.dbCon = new DbCon(); // Initialize database connection
    }

    // Method to enroll a student in a course
    public boolean enrollStudent(String courseId, String studentId) {
        // Print the courseId and studentId for debugging purposes
        System.out.println("Enrolling student with ID: " + studentId + " in course with ID: " + courseId);

        String query = "INSERT INTO enrollment (student_id, course_id, is_enrollment_done) VALUES (?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            preparedStatement.setBoolean(3, true);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Returns true if the enrollment was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if there was an error
        }
    }
    // Method to remove a student's enrollment from a course
    public boolean removeEnrollment() {
        String query = "DELETE FROM enrollment WHERE student_id = ? AND course_id = ?";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, courseId);
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Returns true if the deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if there was an error
        }
    }

    // Method to view all students enrolled in a specific course
    public static List<Student> viewAllEnrolledStudents(String courseId) {
        List<Student> enrolledStudents = new ArrayList<>();
        String query = "SELECT s.id, s.name, s.email FROM enrollment e " +
                "JOIN students s ON e.student_id = s.id WHERE e.course_id = ?";

        try (DbCon dbCon = new DbCon();
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                enrolledStudents.add(new Student(id, name, email, null)); // Password is null for display purposes
            }

            System.out.println("Students enrolled in course ID " + courseId + ": " + enrolledStudents.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrolledStudents;
    }

    // Method to view specific student's enrollment in courses
    public static List<Course> viewSpecificStudentEnrollment(String studentId) {
        List<Course> enrolledCourses = new ArrayList<>();
        String query = "SELECT c.id, c.name, c.description, c.price,c.isComplete, e.is_enrollment_done " +
                "FROM enrollment e " +
                "JOIN course c ON e.course_id = c.id " +
                "WHERE e.student_id = ?";

        try (DbCon dbCon = new DbCon();
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String price = resultSet.getString("price");
                boolean isEnrollmentDone = resultSet.getBoolean("is_enrollment_done"); // Fetch enrollment status
                boolean isComplete = resultSet.getBoolean("isComplete");

                // Create Course object with enrollment status
                enrolledCourses.add(new Course(id, name, description, price,isComplete,isEnrollmentDone));
            }

            System.out.println("Courses for student ID " + studentId + ": " + enrolledCourses.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrolledCourses;
    }
    // Getters for studentId and courseId if needed later
    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }
}