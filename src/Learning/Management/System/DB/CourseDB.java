package Learning.Management.System.DB;

import Learning.Management.System.ApplicationLayer.ContentManagement.Course;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDB {
    private static CourseDB instance;

    private DbCon dbCon;

    public CourseDB() {
        dbCon = new DbCon(); // Initialize the database connection
    }


    public boolean addCourse(Course course) {
        String query = "INSERT INTO course (name, description, price) VALUES (?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setBigDecimal(3, new BigDecimal(course.getPrice()));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static CourseDB getInstance() {
        if (instance == null) {
            instance = new CourseDB(); // Lazy initialization
        }
        return instance;
    }



    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        String query = "SELECT * FROM course"; // Assuming "course" table exists
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String price = resultSet.getString("price");
                Course course = new Course(name, description, price);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Debugging: Print the retrieved courses
        System.out.println("Courses retrieved: " + courseList.size());
        for (Course c : courseList) {
            System.out.println("Course - Name: " + c.getName() + ", Description: " + c.getDescription() + ", Price: " + c.getPrice());
        }

        return courseList;
    }
}