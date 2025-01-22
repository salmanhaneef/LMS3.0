package Learning.Management.System.ApplicationLayer.ContentManagement;

import Learning.Management.System.ApplicationLayer.AssesmentManagement.Assignment;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;
import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private String cno;                // Chapter number
    private String name;               // Chapter name
    private String description;        // Chapter description
    private boolean isComplete;        // Status if chapter is complete
    private Assignment assignment;     // Associated Assignment
    private Quiz quiz;                 // Associated Quiz
    private String courseId;           // Associated Course ID
    private String courseName;         // Associated Course Name

    public Chapter(String cno, String name, String description, String courseId, String courseName) {
        this.cno = cno;
        this.name = name;
        this.description = description;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public boolean addChapter(String cno, String name, String description, String courseId) {
        System.out.println("Attempting to add chapter:");
        System.out.printf("Chapter No: %s, Name: %s, Description: %s, Course ID: %s%n", cno, name, description, courseId);

        String query = "INSERT INTO Chapter (id, name, description, course_id) VALUES (?, ?, ?, ?)";

        // Using try-with-resources to ensure the connection is closed properly
        try (DbCon dbCon = new DbCon(); // Create a new database connection
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, getCno()); // Set chapter number
            preparedStatement.setString(2, getName()); // Set chapter name
            preparedStatement.setString(3, getDescription()); // Set chapter description
            preparedStatement.setString(4, courseId); // Set course ID

            int affectedRows = preparedStatement.executeUpdate(); // Execute update
            return affectedRows > 0; // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if insertion fails
        }
    }

    public static List<Chapter> showAllChapters() {

        List<Chapter> chapterList = new ArrayList<>();
        String query = "SELECT c.id AS course_id, c.name AS course_name, ch.id AS chapter_id, ch.name AS chapter_name, ch.description " +
                "FROM Course c " +
                "LEFT JOIN Chapter ch ON c.id = ch.course_id";
        try (DbCon dbCon =new DbCon(); // Use a new instance of database connection here
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Retrieve fields from the result set
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                String chapterId = resultSet.getString("chapter_id");
                String chapterName = resultSet.getString("chapter_name");
                String chapterDescription = resultSet.getString("description");
                Chapter chapter = new Chapter(chapterId, chapterName, chapterDescription, courseId, courseName);
                chapterList.add(chapter);


            }
            System.out.println("Chapters retrieved: " + chapterList.size());
            for (Chapter c : chapterList) {
                System.out.println("Chapter - Name: " + c.getName() + ", Description: " + c.getDescription() + ", Course Name: " +c.getCourseName() +"Chapter no" + c.getCno() );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapterList; // Return the list of courses

         // Return the list of chapters
    }

    public String getName() {
        return name; // Return chapter name
    }

    public String getDescription() {
        return description; // Return chapter description
    }

    public String getCno() {
        return cno; // Return chapter number
    }

    public void updateChapter(String name, String description) {
        this.name = name; // Update chapter name
        this.description = description; // Update chapter description
    }

    public void deleteChapter() {
        this.assignment = null; // Clear the assignment reference
        this.quiz = null; // Clear the quiz reference
    }

    public void viewChapter() {
        System.out.println("Chapter: " + name + "\nDescription: " + description); // Print chapter details
    }

    public String getCourseId() {
        return courseId; // Return course ID
    }

    public String getCourseName() {
        return courseName; // Return course name
    }
}