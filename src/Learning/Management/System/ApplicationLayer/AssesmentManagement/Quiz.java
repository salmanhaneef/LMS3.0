package Learning.Management.System.ApplicationLayer.AssesmentManagement;
import java.util.ArrayList;
import java.util.List;
public class Quiz {
    private String title;
    private int totalMarks;
    private List<QuestionAnswer> questions;

    public Quiz(String title, int totalMarks) {
        this.title = title;
        this.totalMarks = totalMarks;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(QuestionAnswer question) {
        questions.add(question);
    }

    public void updateQuiz(String title, int totalMarks) {
        this.title = title;
        this.totalMarks = totalMarks;
    }

    public void deleteQuiz() {
        questions.clear();
    }

    public void viewQuiz() {
        System.out.println("Quiz: " + title + "\nTotal Marks: " + totalMarks);
    }
}
