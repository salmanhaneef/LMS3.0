package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.ContentManagement.Course;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ManageCourse extends JFrame implements ActionListener {
    JLabel label1;
    JTable courseTable;
    DefaultTableModel tableModel;

    JButton button1, button2, button3, button4, button5,button6,button7;
    ManageCourse(){
        super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Course");
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
        button1 = createButton("Add", 750, 160);
        button2 = createButton("Update", 750, 210);
        button3 = createButton("Delete", 750, 260);
        button4 = createButton("View", 750, 310);
        button5 = createButton("Back", 750, 360);

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
        refreshTable();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            JPanel AddChapter = new JPanel(new GridLayout(3, 2, 5, 5));

            // Labels and text fields for course information
            JLabel ChapterLabel = new JLabel("Name:");
            JTextField ChapterField = new JTextField();
            AddChapter.add(ChapterLabel);
            AddChapter.add(ChapterField);

            JLabel DescriptionLabel = new JLabel("Description:");
            JTextField DescriptionField = new JTextField();
            AddChapter.add(DescriptionLabel);
            AddChapter.add(DescriptionField);

            JLabel PriceLabel = new JLabel("Price:");
            JTextField PriceField = new JTextField();
            AddChapter.add(PriceLabel);
            AddChapter.add(PriceField);

            // Show input dialog
            int result = JOptionPane.showConfirmDialog(this, AddChapter, "Add New Course", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                // Capture user input
                String chapter = ChapterField.getText().trim();
                String description = DescriptionField.getText().trim(); // Fixed from ChapterField to DescriptionField
                String price = PriceField.getText().trim(); // Fixed from ChapterField to PriceField

                // Validate inputs
                if (!chapter.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                    // Validate price is a valid decimal before creating a Course
                    if (isValidPrice(price)) {
                        // Create a new Course object
                        Course newCourse = new Course(chapter, description, price);

                        // Add the course to the database
                        if (newCourse.addCourse(chapter,description,price)) {
                            JOptionPane.showMessageDialog(this, "Course added successfully.");
                            // Optionally refresh the table or any UI components
                            refreshTable(); // Uncomment if this method is meant to refresh your course list
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to add course.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Price must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
    private void refreshTable() {
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

    private boolean isValidPrice(String price) {
        try {
            Double.parseDouble(price); // Attempting to parse the price to double
            return true; // If successful, it's a valid price
        } catch (NumberFormatException e) {
            return false; // If it fails, it's not a valid price
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
    public static void main(String[] args) {new ManageCourse();}
}
