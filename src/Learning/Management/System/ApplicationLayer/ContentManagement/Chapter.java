package Learning.Management.System.ApplicationLayer.ContentManagement;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Assignment;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;

import java.util.ArrayList;
import java.util.List;
public class Chapter {
    private String name;
    private String description;
    private boolean isComplete;
    private Assignment assignment;
    private Quiz quiz;

    public Chapter(String name, String description) {
        this.name = name;
        this.description = description;
        this.isComplete = false;
        this.assignment = new Assignment("", 0);
        this.quiz = new Quiz("", 0);
    }

    public void updateChapter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void deleteChapter() {
        this.assignment = null;
        this.quiz = null;
    }

    public void viewChapter() {
        System.out.println("Chapter: " + name + "\nDescription: " + description);
    }
}
