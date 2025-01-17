package Learning.Management.System.ClientSide.Interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTable todoTable;
    DefaultTableModel tableModel;
    JButton button1, button2, button3, button4, button5,button6;
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
        todoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(todoTable);
        scrollPane.setBounds(20, 100, 700, 350);
        add(scrollPane);

        // Add buttons with action listeners
        button1 = createButton("Course", 750, 140);
        button2 = createButton("Student", 750, 190);
        button3 = createButton("Assignment", 750, 240);
        button4 = createButton("Quiz", 750, 290);
        button5 = createButton("Detail", 750, 340);
        button5 = createButton("Logout", 750, 390);

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
//        refreshTable();

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
//    private void refreshTable() {
//        tableModel.setRowCount(0); // Clear table
//        try {
//            for (Todo todo : AuthService.getInstance().getTodosForLoggedInUser()) {
//                tableModel.addRow(new Object[]{
//                        todo.getTask(),
//                        todo.getReminderTime(),
//                        todo.isCompleted() ? "Yes" : "No"
//                });
//            }
//        } catch (IllegalStateException e) {
//            JOptionPane.showMessageDialog(this, "Please log in to view todos.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    public static void main(String[] args) {
        new AdminDashboard();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
