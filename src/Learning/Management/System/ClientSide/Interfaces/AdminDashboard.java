package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.ContentManagement.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboard extends JFrame implements ActionListener {
    JLabel label1;
    JTable courseTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6,button7;
    AdminDashboard(){
        super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Admin  ");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(315, 35, 200, 30);
        add(label1);

        tableModel = new DefaultTableModel(new String[]{"Name", "Description", "Price"}, 0);
        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBounds(20, 100, 700, 350);
        add(scrollPane);

        // Add buttons with action listeners
        button1 = createButton("Detail", 750, 100);
        button2 = createButton("Course", 750, 150);
        button3 = createButton("Chapter", 750, 200);
        button4 = createButton("Assignment", 750, 250);
        button5 = createButton("Quiz", 750, 300);
        button6 = createButton("Student", 750, 350);
        button7 = createButton("Logout", 750, 400);

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
        viewCourse();
        //

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
    private void viewCourse() {
        tableModel.setRowCount(0); // Clear existing rows

        try {
            // ✅ Fetch courses from showAllCourses() (now returns a list)
            List<Course> courses = Course.showAllCourses();

            // ✅ Ensure list is not empty
            if (courses.isEmpty()) {
                System.out.println("No courses found.");
                return; // Exit if no courses exist
            }

            // ✅ Add each course to the JTable
            for (Course course : courses) {
                tableModel.addRow(new Object[]{
                        course.getName(),
                        course.getDescription(),
                        course.getPrice()
                });
            }

            System.out.println("Table updated with " + courses.size() + " courses.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error retrieving courses: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        new AdminDashboard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button2){
            new ManageCourse();
        }

    }


}
