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
    private String cno;                           // Chapter number
    private String name;                          // Chapter name
    private String description;                   // Chapter description
    private boolean isComplete;
    private String quizTitles; // Titles of the associated quizzes
    private String assignmentTitles;
    // Titles of the associated assignments
    private String courseId;                      // Associated Course ID
    private String courseName;                    // Associated Course Name

    // Constructor
    public Chapter(String cno, String name, String description, String courseId, String courseName, String quizTitles, String assignmentTitle) {
        this.cno = cno;
        this.name = name;
        this.description = description;
        this.courseId = courseId;
        this.courseName = courseName;
        this.quizTitles = quizTitles;
        this.assignmentTitles = assignmentTitle;
    }
    public String getQuizTitles() {
        return quizTitles; // Return quiz titles
    }
    public String getAssignmentTitles() {
        return assignmentTitles; // Return assignment titles
    }

    public boolean addChapter(String cno, String name, String description, String courseId) {
        String query = "INSERT INTO Chapter (id, name, description, course_id) VALUES (?, ?, ?, ?)";

        try (DbCon dbCon = new DbCon();                             // Create a new database connection
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, cno);                  // Set chapter number
            preparedStatement.setString(2, name);                 // Set chapter name
            preparedStatement.setString(3, description);          // Set chapter description
            preparedStatement.setString(4, courseId);             // Set course ID

            int affectedRows = preparedStatement.executeUpdate();  // Execute update
            return affectedRows > 0;                                // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;                                           // Return false if insertion fails
        }
    }

    public static List<Chapter> showAllChapters() {
        List<Chapter> chapterList = new ArrayList<>();
        String query = "SELECT c.id AS course_id, c.name AS course_name, ch.id AS chapter_id, ch.name AS chapter_name, ch.description " +
                "FROM Course c " +
                "LEFT JOIN Chapter ch ON c.id = ch.course_id";

        try (DbCon dbCon = new DbCon();                             // Use a new instance of database connection here
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

                // Create a new Chapter instance and add it to the list
                Chapter chapter = new Chapter(chapterId, chapterName, chapterDescription, courseId, courseName,"","");
                chapterList.add(chapter);
            }
            System.out.println("Chapters retrieved: " + chapterList.size());
            for (Chapter c : chapterList) {
                System.out.println("Chapter - Name: " + c.getName() + ", Description: " + c.getDescription() + ", Course Name: " + c.getCourseName() + ", Chapter No: " + c.getCno());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapterList; // Return the list of chapters
    }

    public String getName() {
        return name; // Return chapter name
    }
    public boolean isComplete() {
        return isComplete;
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