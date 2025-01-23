package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;
import Learning.Management.System.ApplicationLayer.ContentManagement.Course;
import Learning.Management.System.ApplicationLayer.ContentManagement.Enrollment;
import Learning.Management.System.ApplicationLayer.UserManagement.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentDashboard extends JFrame implements ActionListener {
    JLabel label1;
    JTable todoTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6,button7;
    StudentDashboard(){super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Student");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(315, 35, 200, 30);
        add(label1);

        tableModel = new DefaultTableModel(new String[]{"Id","Name", "Description", "Price","Enrollment"}, 0);
        todoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(todoTable);
        scrollPane.setBounds(20, 100, 700, 350);
        add(scrollPane);

        // Add buttons with action listeners

        button1 = createButton("View", 750, 310);
        button2 = createButton("Enroll", 750, 360);

        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/6.jpg"));
        Image ii2 = ii1.getImage().getScaledInstance(900, 530, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel image1 = new JLabel(ii3);
        image1.setBounds(0, 0, 900, 530);
        add(image1);

        setLayout(null);
        setSize(900, 530);
        setLocation(380, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewEnrollCourse();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button2){
            addEnrollment();
        }

    }
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setBounds(x, y, 100, 35);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(this);
        add(button);
        return button;
    }
    private void addEnrollment() {
        JPanel AddCourse = new JPanel(new GridLayout(1, 2, 5, 5));

        // Labels and text fields for course information
        JLabel CourseLabel = new JLabel("Course Id:");
        JTextField ChapterField = new JTextField();
        AddCourse.add(CourseLabel);
        AddCourse.add(ChapterField);
        int result = JOptionPane.showConfirmDialog(this, AddCourse, "Add New Q/A", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Capture user input
            String cid = ChapterField.getText().trim();

            // Validate inputs
            if (!cid.isEmpty()) {
                // Get the current student ID
                Student currentStudent = Student.getCurrentUser();
                if (currentStudent != null) {
                    String studentId = currentStudent.getId(); // Get the current student's ID
                    Enrollment enrollment =new Enrollment(cid,studentId);

                    // Call the enrollStudent method
                    if (enrollment.enrollStudent(cid, studentId)) {
                        JOptionPane.showMessageDialog(this, "Enrollment successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // Optionally refresh the course list or perform other actions
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to enroll in course.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No current student logged in.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void viewEnrollCourse() {
        tableModel.setRowCount(0); // Clear existing rows

        try {
            // Fetch the current student
            Student currentStudent = Student.getCurrentUser();
            if (currentStudent != null) {
                String studentId = currentStudent.getId();
                Enrollment enrollment = new Enrollment(studentId,"");

                // Fetch enrolled courses for the current student
                List<Course> enrolledCourses = enrollment.viewSpecificStudentEnrollment(studentId);

                // Ensure the list is not empty
                if (enrolledCourses.isEmpty()) {
                    System.out.println("No courses found for student ID " + studentId + ".");
                    return; // Exit if no courses exist
                }

                // Add each course to the JTable
                for (Course course : enrolledCourses) {
                    tableModel.addRow(new Object[]{
                            course.getId(),
                            course.getName(),
                            course.getDescription(),
                            course.getPrice(),
                            course.isEnrollmentDone() ? "No" : "Yes" // Display enrollment status
                    });
                }

                System.out.println("Table updated with " + enrolledCourses.size() + " courses for student ID " + studentId + ".");
            } else {
                JOptionPane.showMessageDialog(this, "No current student logged in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error retrieving courses: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {new StudentDashboard();}
}
