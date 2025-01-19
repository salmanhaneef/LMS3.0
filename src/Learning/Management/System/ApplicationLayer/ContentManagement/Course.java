package Learning.Management.System.ApplicationLayer.ContentManagement;
import Learning.Management.System.DB.CourseDB;
import Learning.Management.System.DB.StudentDB;

import java.util.ArrayList;
import java.util.List;
public class Course {
    private String name;
    private String description;
    private String price;
    private boolean isComplete;
    private List<Chapter> chapters;

    public Course(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isComplete = false;
        this.chapters = new ArrayList<>();
    }

    public void addChapter(Chapter chapter) {
        chapters.add(chapter);
    }
    public boolean addCourse(String name, String description, String price){
        System.out.println("Attempting to adding course:");
        System.out.println("Name: " + name + ", Description: " + description +",Price:" + price);
        CourseDB courseDB = new CourseDB();
        boolean result =courseDB.addCourse(this);
        System.out.println("Registration " + (result ? "successful!" : "failed."));
        return result ;

    }

    public void updateCourse(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void deleteCourse() {
        chapters.clear();
    }

    public static List<Course> showAllCourses() {
        CourseDB courseDB = new CourseDB();
//        List<Course> courses = CourseDB.getInstance().getAllCourses();
        List<Course> courses = courseDB.getAllCourses();


        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("Available Courses:");
            for (Course course : courses) {
                System.out.printf("Name: %s, Description: %s, Price: %s%n",
                        course.getName(), course.getDescription(), course.getPrice());
            }
        }
        return courses;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
}
