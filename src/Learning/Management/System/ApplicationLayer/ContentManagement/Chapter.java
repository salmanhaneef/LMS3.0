package Learning.Management.System.ApplicationLayer.ContentManagement;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Assignment;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;
import Learning.Management.System.DB.ChapterDB;
import Learning.Management.System.DB.CourseDB;

import java.util.List;

public class Chapter {
    private  String cno;
    private String name;
    private String description;
    private boolean isComplete;
    private Assignment assignment;
    private Quiz quiz;
    private String courseId;    // Associated Course ID
    private String courseName;  // Associated Course Name

    public Chapter(String cno, String name, String description, String courseId, String courseName) {
        this.cno = cno;
        this.name = name;
        this.description = description;
        this.courseId = courseId;
        this.courseName = courseName;
    }
    public boolean addChapter(String cno,String name, String description, String courseId){
        System.out.println("Attempting to adding course:");
        System.out.println("chapter no :" + cno + "Name: " + name + ", Description: " + description +",Course Id:" + courseId);

        ChapterDB chapterDB = new ChapterDB();
        Course course = new Course(cno, name,description,courseId);
        boolean result =chapterDB.addChapter(this, course);
//        boolean result =true;
        System.out.println("Registration " + (result ? "successful!" : "failed."));
        return result ;

    }
    public static List<Chapter> showAllChapters() {
        ChapterDB chapterDB = new ChapterDB(); // Instance of ChapterDB
        List<Chapter> chapters = chapterDB.getAllChapters(); // Fetch chapters from DB

        if (chapters.isEmpty()) {
            System.out.println("No chapters available.");
        } else {
            System.out.println("Available Chapters:");
            for (Chapter chapter : chapters) {
                System.out.printf("Chapter Name: %s, Description: %s, Course ID: %s%n",
                        chapter.getName(), chapter.getDescription(), chapter.getCno());
            }
        }

        return chapters;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getCno(){
        return cno;
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
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
