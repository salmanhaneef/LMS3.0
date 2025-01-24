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
    private boolean enrollment;
    private boolean isEnrollmentDone;
    private DbCon dbCon; // Instance variable for database connection
    private List<Chapter> chapters;

    public Course(String id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isEnrollmentDone = isEnrollmentDone;
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
    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
        System.out.println("Added chapter: " + chapter.getName() + " to course: " + this.name);
    }

    public static Course showCourseDetails(String courseId) {
        Course courseDetail = null;
        String query = """  
        SELECT   
            c.id AS course_id,  
            c.name AS course_name,  
            c.description AS course_description,  
            c.price AS course_price,  
            c.isComplete AS course_isComplete,  
            ch.id AS chapter_id,  
            ch.name AS chapter_name,  
            (SELECT COUNT(*) FROM Quiz q WHERE q.chapter_id = ch.id) AS quiz_count,  
            (SELECT COUNT(*) FROM Assignment a WHERE a.chapter_id = ch.id) AS assignment_count,  
            (SELECT GROUP_CONCAT(q.title SEPARATOR ', ') FROM Quiz q WHERE q.chapter_id = ch.id) AS quiz_titles,  
            (SELECT GROUP_CONCAT(a.title SEPARATOR ', ') FROM Assignment a WHERE a.chapter_id = ch.id) AS assignment_titles  
        FROM   
            Course c  
        JOIN   
            Chapter ch ON c.id = ch.course_id  
        WHERE   
            c.id = ?;  
    """;

        System.out.println("Executing query to retrieve details for course ID: " + courseId);

        try (DbCon dbCon = new DbCon(); // Use a new instance of database connection
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Course found: " + resultSet.getString("course_name"));

                String courseName = resultSet.getString("course_name");
                String courseDescription = resultSet.getString("course_description");
                String coursePrice = resultSet.getString("course_price");
                boolean courseIsComplete = resultSet.getBoolean("course_isComplete");

                // Create CourseDetail object
                courseDetail = new Course(courseId, courseName, courseDescription, coursePrice);

                System.out.println("Course details: Name = " + courseName +
                        ", Description = " + courseDescription +
                        ", Price = " + coursePrice +
                        ", Is Complete = " + courseIsComplete);

                do {
                    // Extract chapter details from the current row
                    String chapterId = resultSet.getString("chapter_id");
                    String chapterName = resultSet.getString("chapter_name");
                    int quizCount = resultSet.getInt("quiz_count");
                    String quizTitles = resultSet.getString("quiz_titles");
                    int assignmentCount = resultSet.getInt("assignment_count");
                    String assignmentTitles = resultSet.getString("assignment_titles");

                    // Create a ChapterDetail object and add it to the CourseDetail
                    Chapter chapterDetail = new Chapter(
                            chapterId, chapterName,  quizTitles, "","" ,assignmentTitles,quizTitles);
                    courseDetail.addChapter(chapterDetail);

                    // Debugging: Print chapter details
                    System.out.println("Chapter added: Name = " + chapterName +
                            ", Quiz Count = " + quizCount +
                            ", Assigned Titles = " + assignmentTitles);

                } while (resultSet.next());
            } else {
                System.out.println("No course found with ID: " + courseId);
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception occurred while retrieving course details for ID: " + courseId);
            e.printStackTrace();
        }

        return courseDetail; // Return the populated CourseDetail object
    }

    public boolean isEnrollmentDone() { return isEnrollmentDone; }
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