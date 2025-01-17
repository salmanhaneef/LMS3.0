package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.UserManagement.Admin;
import Learning.Management.System.ApplicationLayer.UserManagement.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField textField1;
    JPasswordField passwordField2;
    JButton button1, button2, button3;

    public Login() {
        super("LMS");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(370, 20, 120, 120);
        add(image);
        label1 = new JLabel("WELCOME LOGIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(255, 150, 450, 40);
        add(label1);

        label2 = new JLabel("Email :");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Ralway", Font.BOLD, 28));
        label2.setBounds(195, 213, 375, 30);
        add(label2);

        textField1 = new JTextField(15);
        textField1.setBounds(325, 215, 230, 30);
        textField1.setFont(new Font("Arial", Font.BOLD, 14));
        add(textField1);

        label3 = new JLabel("Password :");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Ralway", Font.BOLD, 28));
        label3.setBounds(195, 263, 375, 30);
        add(label3);

        passwordField2 = new JPasswordField(15);
        passwordField2.setBounds(325, 270, 230, 30);
        passwordField2.setFont(new Font("Arial", Font.BOLD, 14));
        add(passwordField2);

        button1 = new JButton("<- Login");
        button1.setForeground(Color.WHITE);
        button1.setBackground(Color.BLACK);
        button1.setBounds(330, 340, 100, 30);
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("Register");
        button2.setForeground(Color.WHITE);
        button2.setBackground(Color.BLACK);
        button2.setBounds(450, 340, 100, 30);
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        button2.addActionListener(this);
        add(button2);

        button3 = new JButton(" <-Back");
        button3.setForeground(Color.WHITE);
        button3.setBackground(Color.BLACK);
        button3.setBounds(330, 390, 220, 30);
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.addActionListener(this);
        add(button3);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) { // Login attempt
            String email = textField1.getText();
            String password = new String(passwordField2.getPassword());

            // Check for admin credentials
            if (Admin.ADMIN_EMAIL.equals(email) && Admin.ADMIN_PASSWORD.equals(password)) {
                JOptionPane.showMessageDialog(this, "Admin login successful! Welcome!");
                dispose(); // Close the login frame
                new AdminDashboard(); // Open admin dashboard
                return; // Exit the method
            }

            // Proceed to check regular user login (this part assumes implementation for normal user logins is present)
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required for login.", "Login Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create an instance of StudentB (or regular User class) if login functionality is available.
            Student student = new Student("", email, password); // Pass email and password for login attempt

            boolean loginSuccessful = student.loginStudent(email, password);

            if (loginSuccessful) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome!");
                dispose();
                new Starting(); // Close the login frame (or open the main application)
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == button2) { // Navigate to Signup
            new Signup();
            setVisible(false);
        } else if (e.getSource() == button3) { // Back button action
            new Starting();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Login();
    }

}