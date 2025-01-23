package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.AssesmentManagement.QuestionAnswer;
import Learning.Management.System.ApplicationLayer.AssesmentManagement.Quiz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageQuestionAnswer extends JFrame implements ActionListener {
    JLabel label1;
    JTable todoTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6,button7;
    ManageQuestionAnswer(){super("LMS");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(70,10 , 70, 70);
        add(image);
        label1 = new JLabel("Q/A");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(350, 35, 200, 30);
        add(label1);

        tableModel = new DefaultTableModel(new String[]{"CourseName", "ChapterName", "Quiz Id","Assignment Id","Id","Question","Answer","Marks"}, 0);
        todoTable = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable all cell editing
            }
        };

        JScrollPane scrollPane = new JScrollPane(todoTable);

        scrollPane.setBounds(10, 100, 720, 350);
        add(scrollPane);

        // Add buttons with action listeners
        button1 = createButton("Add", 750, 160);
        button2 = createButton("Update", 750, 210);
        button3 = createButton("Delete", 750, 260);
        button5 = createButton("Back", 750, 310);

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
        viewQA();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            addQA();
        }

    }
    public void addQA() {
        JPanel AddCourse = new JPanel(new GridLayout(6, 2, 7, 7));

        // Labels and text fields for course information
        JLabel CourseLabel = new JLabel("Assignment Id:");
        JTextField AssignmentField = new JTextField();
        AddCourse.add(CourseLabel);
        AddCourse.add(AssignmentField);
        JLabel QuizLabel = new JLabel("Quiz Id:");
        JTextField QuizField = new JTextField();
        AddCourse.add(QuizLabel);
        AddCourse.add(QuizField);
        JLabel ChapterNoLabel = new JLabel("Q/A No:");
        JTextField QANoField = new JTextField();
        AddCourse.add(ChapterNoLabel);
        AddCourse.add(QANoField);
        JLabel ChapterLabel = new JLabel("Question:");
        JTextField QuestionField = new JTextField();
        AddCourse.add(ChapterLabel);
        AddCourse.add(QuestionField);
        JLabel AnswerLabel = new JLabel("Answer:");
        JTextField AnswerField = new JTextField();
        AddCourse.add(AnswerLabel);
        AddCourse.add(AnswerField);
        JLabel MarksLabel = new JLabel("Marks :");
        JTextField MarksField = new JTextField();
        AddCourse.add(MarksLabel);
        AddCourse.add(MarksField);

        // Show input dialog
        int result = JOptionPane.showConfirmDialog(this, AddCourse, "Add New Assignment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Capture user input
            String AssignmentId = AssignmentField.getText().trim();
            String QuizId = QuizField.getText().trim();
            String QAid = QANoField.getText().trim();
            String question = QuestionField.getText().trim();
            String Answer = AnswerField.getText().trim();
            String Marks = MarksField.getText().trim();

            // Validate inputs
            if (!QAid.isEmpty() && !question.isEmpty() && !Marks.isEmpty()&&!Answer.isEmpty()) {
                // Create a new Chapter object
                QuestionAnswer newQA = new QuestionAnswer("","", AssignmentId,QuizId,QAid,question,Answer,Marks);

                // Add the chapter to the database
                if (newQA.addAssignment( QAid, question,Answer,Marks,AssignmentId,QuizId)) {
                    JOptionPane.showMessageDialog(this, "Quiz added successfully.");
                    viewQA();// Refresh the course list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }


        }
    }
    private void viewQA() {
        tableModel.setRowCount(0);

        try {
            List<QuestionAnswer> qaList = QuestionAnswer.showAllQA(); // Renamed from `quiz` to `quizList`

            if (qaList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No quiz available.", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (QuestionAnswer qa : qaList) { // Using the original variable name `quiz` for the loop
                tableModel.addRow(new Object[]{
                        qa.getCourseName(),
                        qa.getChapterName(),
                        qa.getAssignmentId(),
                        qa.getQuizId(),
                        qa.getId(),
                        qa.getQuestion(),
                        qa.getAnswer(),
                        qa.getMarks()
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
    public static void main(String[] args) {new ManageQuestionAnswer();}

}
