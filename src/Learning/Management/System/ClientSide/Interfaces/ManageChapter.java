package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.ContentManagement.Chapter;
import Learning.Management.System.ApplicationLayer.ContentManagement.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ManageChapter extends JFrame implements ActionListener {
    JLabel label1;
    JTable todoTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6,button7;
    ManageChapter(){
        super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Chapter");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(315, 35, 200, 30);
        add(label1);

        tableModel = new DefaultTableModel(new String[]{"CourseCode","CourseName","ChapterId", "ChapterName", "Description"}, 0);
        todoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(todoTable);
        scrollPane.setBounds(20, 100, 700, 350);
        add(scrollPane);

        // Add buttons with action listeners
        button1 = createButton("Add", 750, 160);
        button2 = createButton("Update", 750, 210);
        button3 = createButton("Delete", 750, 260);
        button4 = createButton("Back", 750, 310);


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
        viewChapter();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==button1){
            addChapter();
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
    public void addChapter() {
        JPanel AddCourse = new JPanel(new GridLayout(4, 2, 5, 5));

        // Labels and text fields for course information
        JLabel CourseLabel = new JLabel("Course Id:");
        JTextField CourseField = new JTextField();
        AddCourse.add(CourseLabel);
        AddCourse.add(CourseField);
        JLabel ChapterNoLabel = new JLabel("Chapter No:");
        JTextField ChapterNoField = new JTextField();
        AddCourse.add(ChapterNoLabel);
        AddCourse.add(ChapterNoField);
        JLabel ChapterLabel = new JLabel("Name:");
        JTextField ChapterField = new JTextField();
        AddCourse.add(ChapterLabel);
        AddCourse.add(ChapterField);
        JLabel DescriptionLabel = new JLabel("Description:");
        JTextField DescriptionField = new JTextField();
        AddCourse.add(DescriptionLabel);
        AddCourse.add(DescriptionField);

        // Show input dialog
        int result = JOptionPane.showConfirmDialog(this, AddCourse, "Add New Chapter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Capture user input
            String id = CourseField.getText().trim();
            String cid = ChapterNoField.getText().trim();
            String course = ChapterField.getText().trim();
            String description = DescriptionField.getText().trim();
            boolean iscompelete = false;

            // Validate inputs
            if (!id.isEmpty()&&!cid.isEmpty() && !course.isEmpty() && !description.isEmpty()) {
                // Create a new Chapter object
                Chapter newChapter = new Chapter(cid,course, description,id,"","","",iscompelete);

                // Add the chapter to the database
                if (newChapter.addChapter(cid, course, description,id)) {
                    JOptionPane.showMessageDialog(this, "Course added successfully.");
                     viewChapter(); // Refresh the course list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }
    private void viewChapter() {
        tableModel.setRowCount(0); // Clear existing rows in the table

        try {
            // Fetch all chapters with course details
            List<Chapter> chapters = Chapter.showAllChapters();

            // Check if there are chapters available
            if (chapters.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No chapters available.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return; // Exit if no chapters exist
            }

            // Populate the JTable with chapter data
            for (Chapter chapter : chapters) {
                tableModel.addRow(new Object[]{
                        chapter.getCourseId(),     // Course ID
                        chapter.getCourseName(),
                        chapter.getCno(),// Course Name
                        chapter.getName(),         // Chapter Name
                        chapter.getDescription()   // Chapter Description
                });
            }

            System.out.println("Table updated with " + chapters.size() + " chapters.");
        } catch (Exception e) {
            // Handle exceptions gracefully
            JOptionPane.showMessageDialog(this, "Error retrieving chapters: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        new ManageChapter();
    }
}
