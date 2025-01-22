package Learning.Management.System.ApplicationLayer.AssesmentManagement;
import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Quiz {
    private String ChapterId;
    private String CourseName;
    private String ChapterName;
    private String Id;
    private String title;
    private String totalMarks;
    private List<QuestionAnswer> questions;

    public Quiz(String ChapterId,String CourseName,String ChapterName,String Id,String title, String totalMarks) {
        this.ChapterId =ChapterId;
        this.CourseName =CourseName;
        this.ChapterName =ChapterName;
        this.Id =Id;
        this.title = title;
        this.totalMarks =totalMarks;
        this.questions = new ArrayList<>();
    }
    public String getChapterId(){return ChapterId;}
    public String getCourseName(){return CourseName;}
    public String getChapterName(){return ChapterName;}
    public String getId(){return Id;}
    public String getTitle(){return title;}
    public String getTotalMarks(){return totalMarks;}

    public boolean addAssignment(String cid, String aid, String title, String Marks) {
        System.out.println("Attempting to add assignment:");
        System.out.printf("Chapter No: %s, AssignmentNo: %s, Title: %s, Marks: %s%n", cid, aid, title,Marks);

        String query = "INSERT INTO Quiz (id, title, totalMarks, chapter_id) VALUES (?, ?, ?, ?)";

        // Using try-with-resources to ensure the connection is closed properly
        try (DbCon dbCon = new DbCon(); // Create a new database connection
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, getId()); // Set chapter number
            preparedStatement.setString(2, getTitle()); // Set chapter name
            preparedStatement.setString(3, getTotalMarks()); // Set chapter description
            preparedStatement.setString(4, cid); // Set course ID

            int affectedRows = preparedStatement.executeUpdate(); // Execute update
            return affectedRows > 0; // Check if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if insertion fails
        }
    }
    public static List<Quiz> showAllQuiz() {
        List<Quiz> quizList = new ArrayList<>();
        String query = "SELECT q.id AS quiz_id, q.title AS quiz_title, q.totalMarks, " +
                "ch.id AS chapter_id, ch.name AS chapter_name, c.name AS course_name " +
                "FROM Quiz q " +
                "JOIN Chapter ch ON q.chapter_id = ch.id " +
                "JOIN Course c ON ch.course_id = c.id";

        try (DbCon dbCon = new DbCon();
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String quizId = resultSet.getString("quiz_id");
                String quizTitle = resultSet.getString("quiz_title");
                String totalMarks = resultSet.getString("totalMarks");
                String chapterId = resultSet.getString("chapter_id");
                String chapterName = resultSet.getString("chapter_name");
                String courseName = resultSet.getString("course_name");

                    Quiz quiz = new Quiz(chapterId, courseName, chapterName, quizId, quizTitle, totalMarks);
                quizList.add(quiz);
            }

            System.out.println("Assignments retrieved: " + quizList.size());
            for (Quiz q : quizList) {
                System.out.printf("Assignment ID: %s, Title: %s, Chapter: %s, Course: %s, Marks: %s%n",
                        q.getId(), q.getTitle(), q.getChapterName(), q.getCourseName(), q.getTotalMarks());
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving assignments: " + e.getMessage());
        }
        return quizList;
    }


}
