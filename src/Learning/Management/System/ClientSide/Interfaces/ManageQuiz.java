package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.AssesmentManagement.Assignment;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageQuiz extends JFrame implements ActionListener {
    JLabel label1;
    JTable todoTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6,button7;
    ManageQuiz(){
        super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Quiz");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(350, 35, 200, 30);
        add(label1);

        tableModel = new DefaultTableModel(new String[]{"Course Id","CourseName", "ChapterName","QuizId" ,"QuizTitle","QuizMarks"}, 0);
        todoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(todoTable);
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
        viewQuiz();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            addQuiz();
        }


    }
    public void addQuiz() {
        JPanel AddCourse = new JPanel(new GridLayout(4, 2, 5, 5));

        // Labels and text fields for course information
        JLabel CourseLabel = new JLabel("Chapter Id:");
        JTextField ChapterField = new JTextField();
        AddCourse.add(CourseLabel);
        AddCourse.add(ChapterField);
        JLabel ChapterNoLabel = new JLabel("Quiz No:");
        JTextField QuizNoField = new JTextField();
        AddCourse.add(ChapterNoLabel);
        AddCourse.add(QuizNoField);
        JLabel ChapterLabel = new JLabel("Title:");
        JTextField TitleField = new JTextField();
        AddCourse.add(ChapterLabel);
        AddCourse.add(TitleField);
        JLabel MarksLabel = new JLabel("Total Marks :");
        JTextField MarksField = new JTextField();
        AddCourse.add(MarksLabel);
        AddCourse.add(MarksField);

        // Show input dialog
        int result = JOptionPane.showConfirmDialog(this, AddCourse, "Add New Q/A", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Capture user input
            String cid = ChapterField.getText().trim();
            String qid = QuizNoField.getText().trim();
            String title = TitleField.getText().trim();
            String Marks = MarksField.getText().trim();

            // Validate inputs
            if (!cid.isEmpty()&&!qid.isEmpty() && !title.isEmpty() && !Marks.isEmpty()) {
                // Create a new Chapter object
                Quiz newQuiz = new Quiz(cid,"", "",qid,title,Marks);

                // Add the chapter to the database
                if (newQuiz.addAssignment(cid, qid, title,Marks)) {
                    JOptionPane.showMessageDialog(this, "Quiz added successfully.");
                    viewQuiz(); // Refresh the course list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }
    private void viewQuiz() {
        tableModel.setRowCount(0);

        try {
            List<Quiz> quizList = Quiz.showAllQuiz(); // Renamed from `quiz` to `quizList`

            if (quizList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No quiz available.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Quiz quiz : quizList) { // Using the original variable name `quiz` for the loop
                tableModel.addRow(new Object[]{
                        quiz.getChapterId(),
                        quiz.getCourseName(),
                        quiz.getChapterName(),
                        quiz.getId(),
                        quiz.getTitle(),
                        quiz.getTotalMarks()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error retrieving assignments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    public static void main(String[] args) {new ManageQuiz();}
}
