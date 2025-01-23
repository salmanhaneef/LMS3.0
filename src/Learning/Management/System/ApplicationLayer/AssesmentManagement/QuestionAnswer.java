package Learning.Management.System.ApplicationLayer.AssesmentManagement;
import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class QuestionAnswer {
    private String CourseName;
    private String ChapterName;
    private String AssignmentId;
    private String QuizId;
    private String Id;
    private String question;
    private String answer;
    private String marks;

    public QuestionAnswer(String courseName, String chapterName, String assignmentId,String QuizId, String id,
                          String question, String answer, String marks) {
        this.CourseName = courseName;
        this.ChapterName = chapterName;
        this.AssignmentId = assignmentId;
        this.QuizId = QuizId;
        this.Id = id;
        this.question = question;
        this.answer = answer;
        this.marks = marks;
    }

    // Getter methods for all fields
    public String getCourseName() {return CourseName;}
    public String getChapterName() {return ChapterName;}
    public String getAssignmentId() {return AssignmentId;}
    public String getQuizId(){return  QuizId;}
    public String getId() {return Id;}
    public String getQuestion() {return question;}
    public String getAnswer() {return answer;}
    public String getMarks() {return marks;}

    public boolean addAssignment(String id, String question, String answer, String marks, String assignmentId, String quizId) {
        System.out.println("Attempting to add assignment:");
        System.out.printf("ID: %s, Question: %s, Answer: %s, Marks: %s, Assignment ID: %s, Quiz ID: %s%n",
                id, question, answer, marks, assignmentId, quizId ) ;

        // SQL statement to insert a new entry into QuestionAnswer table
        String query = "INSERT INTO QuestionAnswer (id, question, answer, Marks, assignment_id, Quiz_id) VALUES (?, ?, ?, ?, ?, ?)";

// Using try-with-resources to ensure the database connection is closed properly
        try (DbCon dbCon = new DbCon(); // Create a new database connection
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Setting parameters for the SQL query
            preparedStatement.setString(1, id); // Set ID
            preparedStatement.setString(2, question); // Set question
            preparedStatement.setString(3, answer); // Set answer
            preparedStatement.setString(4, marks); // Set marks

            // Check for null or empty value for assignmentId
            if (assignmentId == null || assignmentId.trim().isEmpty()) {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);  // Use appropriate type for your column
            } else {
                preparedStatement.setString(5, assignmentId); // Set assignment ID
            }

            // Check for null or empty value for quizId
            if (quizId == null || quizId.trim().isEmpty()) {
                preparedStatement.setNull(6, java.sql.Types.INTEGER);  // Use appropriate type for your column
            } else {
                preparedStatement.setString(6, quizId); // Set Quiz ID
            }

            // Execute the update and check if a row was inserted
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0; // Return true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if insertion fails
        }
    }
    public static List<QuestionAnswer> showAllQA() {
        List<QuestionAnswer> qaList = new ArrayList<>();
        String query = "SELECT qa.id AS qa_id, " +
                "qa.question, " +
                "qa.answer, " +
                "qa.marks, " +
                "qa.assignment_id, " +
                "qa.Quiz_id, " +
                "ch.name AS chapter_name, " +
                "c.name AS course_name " +
                "FROM Course c " +
                "JOIN Chapter ch ON c.id = ch.course_id " +
                "JOIN Quiz q ON ch.id = q.chapter_id " +
                "JOIN QuestionAnswer qa ON q.id = qa.Quiz_id ";
//                "ORDER BY qa.id, c.name, ch.name, q.id;";

        try (DbCon dbCon = new DbCon();
             Connection connection = dbCon.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String chapterName = resultSet.getString("chapter_name");
                String quizId = resultSet.getString("Quiz_id");
                String assignmentId = resultSet.getString("assignment_id");
                String id = resultSet.getString("qa_id");
                String question = resultSet.getString("question");
                String answer = resultSet.getString("answer");
                String marks = resultSet.getString("marks");

                qaList.add(new QuestionAnswer(courseName, chapterName, assignmentId, quizId, id, question, answer, marks));
            }

            System.out.println("Assignments retrieved: " + qaList.size());
            for (QuestionAnswer qa : qaList) {
                System.out.printf("ID: %s, Question: %s, Chapter: %s, Course: %s, Marks: %s%n",
                        qa.getId(), qa.getQuestion(), qa.getChapterName(), qa.getCourseName(), qa.getMarks());
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving assignments: " + e.getMessage());
        }
        return qaList;
    }
}
