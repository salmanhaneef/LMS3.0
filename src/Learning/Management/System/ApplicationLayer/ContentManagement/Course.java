package Learning.Management.System.ApplicationLayer.ContentManagement;

import Learning.Management.System.DB.DbCon;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String id;
    private String name;
    private String description;
    private String price;
    private boolean isComplete;
    private DbCon dbCon; // Instance variable for database connection
    private List<Chapter> chapters;

    public Course(String id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isComplete = false;
        this.chapters = new ArrayList<>();
        dbCon = new DbCon(); // Initialize database connection
    }

    public boolean addCourse(String id,String name, String description, String price) {
        System.out.println("Attempting to add course:");
        System.out.println("Id: " + id + ", Name: " + name + ", Description: " + description + ", Price: " + price);

        String query = "INSERT INTO course (id, name, description, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, getId());
            preparedStatement.setString(2, getName());
            preparedStatement.setString(3, getDescription());
            preparedStatement.setBigDecimal(4, new BigDecimal(getPrice()));
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateCourse(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void deleteCourse() {
        chapters.clear();
        // Additional logic for deleting the course from the database may be added here
    }

    public static List<Course> showAllCourses() {
        List<Course> courseList = new ArrayList<>(); // Initialize course list
        String query = "SELECT * FROM course"; // Query to fetch courses

        try (DbCon dbCon =new DbCon(); // Use a new instance of database connection here
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String price = resultSet.getString("price");
                Course course = new Course(id, name, description, price);
                courseList.add(course);
            }

            // Debugging: Print the retrieved courses
            System.out.println("Courses retrieved: " + courseList.size());
            for (Course c : courseList) {
                System.out.println("Course - Name: " + c.getName() + ", Description: " + c.getDescription() + ", Price: " + c.getPrice());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList; // Return the list of courses
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public String getId() {
        return id;
    }
}