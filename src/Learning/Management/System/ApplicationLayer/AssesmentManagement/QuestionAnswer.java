package Learning.Management.System.ApplicationLayer.AssesmentManagement;
import java.util.ArrayList;
import java.util.List;
public class QuestionAnswer {
    private String question;
    private String answer;
    private int marks;

    public QuestionAnswer(String question, String answer, int marks) {
        this.question = question;
        this.answer = answer;
        this.marks = marks;
    }

    public void updateQuestionAnswer(String question, String answer, int marks) {
        this.question = question;
        this.answer = answer;
        this.marks = marks;
    }

    public void deleteQuestionAnswer() {
        this.question = "";
        this.answer = "";
        this.marks = 0;
    }

    public void viewQuestionAnswer() {
        System.out.println("Question: " + question + "\nAnswer: " + answer + "\nMarks: " + marks);
    }
}
