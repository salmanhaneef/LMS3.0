package Learning.Management.System.DB;
import Learning.Management.System.ApplicationLayer.ContentManagement.Chapter;
import Learning.Management.System.ApplicationLayer.ContentManagement.Course;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ChapterDB {
    private DbCon dbCon;
    private static CourseDB instance;
    public ChapterDB() {
        dbCon = new DbCon(); // Initialize the database connection
    }
    public static CourseDB getInstance() {
        if (instance == null) {
            instance = new CourseDB(); // Lazy initialization
        }
        return instance;
    }

    public boolean addChapter(Chapter chapter, Course course) {
        String query = "INSERT INTO Chapter (id, name, description,  course_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,chapter.getCno());
            preparedStatement.setString(2, chapter.getName());
            preparedStatement.setString(3, chapter.getDescription());
            preparedStatement.setString(4, course.getId());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Chapter> getAllChapters() {
        List<Chapter> chapterList = new ArrayList<>();
        String query = "SELECT c.id AS course_id, c.name AS course_name, ch.id AS chapter_id, ch.name AS chapter_name, ch.description " +
                "FROM Course c " +
                "LEFT JOIN Chapter ch ON c.id = ch.course_id";
        //

        try (Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Retrieve fields from the result set
                String courseId = resultSet.getString("course_id");
                String courseName = resultSet.getString("course_name");
                String chapterId = resultSet.getString("chapter_id");
                String chapterName = resultSet.getString("chapter_name");
                String chapterDescription = resultSet.getString("description");

                // Create a Chapter object with course details
                Chapter chapter = new Chapter(chapterId, chapterName, chapterDescription, courseId, courseName);
                chapterList.add(chapter);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
        }

        return chapterList;
    }

}
