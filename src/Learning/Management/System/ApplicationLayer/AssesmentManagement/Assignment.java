package Learning.Management.System.ApplicationLayer.AssesmentManagement;
import java.util.ArrayList;
import java.util.List;
public class Assignment {
    private String title;
    private int totalMarks;
    private List<QuestionAnswer> questions;

    public Assignment(String title, int totalMarks) {
        this.title = title;
        this.totalMarks = totalMarks;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(QuestionAnswer question) {
        questions.add(question);
    }

    public void updateAssignment(String title, int totalMarks) {
        this.title = title;
        this.totalMarks = totalMarks;
    }

    public void deleteAssignment() {
        questions.clear();
    }

    public void viewAssignment() {
        System.out.println("Assignment: " + title + "\nTotal Marks: " + totalMarks);
    }
}
