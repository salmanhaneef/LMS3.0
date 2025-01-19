package Learning.Management.System.ClientSide.Interfaces;

import Learning.Management.System.ApplicationLayer.UserManagement.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends JFrame implements ActionListener {
    JLabel label1, label2, label3, label4;
    JTextField textField1, textField2;
    JPasswordField passwordField3;
    JButton button1, button2, button3;


    Signup() {
        super("Todo App");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(370, 5, 120, 120);
        add(image);

        label1 = new JLabel("WELCOME SIGNUP");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label1.setBounds(255, 125, 450, 40);
        add(label1);

        label2 = new JLabel("Name :");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Ralway", Font.BOLD, 28));
        label2.setBounds(150, 190, 375, 30);
        add(label2);

        textField1 = new JTextField(15);
        textField1.setBounds(325, 190, 230, 30);
        textField1.setFont(new Font("Arial", Font.BOLD, 14));
        add(textField1);

        label3 = new JLabel("Email :");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Ralway", Font.BOLD, 28));
        label3.setBounds(150, 245, 375, 30);
        add(label3);

        textField2 = new JTextField(15);
        textField2.setBounds(325, 245, 230, 30);
        textField2.setFont(new Font("Arial", Font.BOLD, 14));
        add(textField2);

        label4 = new JLabel("Password :");
        label4.setForeground(Color.WHITE);
        label4.setFont(new Font("Ralway", Font.BOLD, 28));
        label4.setBounds(150, 300, 375, 30);
        add(label4);

        passwordField3 = new JPasswordField(15);
        passwordField3.setBounds(325, 300, 230, 30);
        passwordField3.setFont(new Font("Arial", Font.BOLD, 14));
        add(passwordField3);

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
        if (e.getSource() == button2) { // Register
            registration(); // Close Login page
        } else if (e.getSource() == button3) {
            setVisible(false);// Exit button
            new Starting();
        }

    }
    public void registration() {
        // Retrieve input values from text fields
        String name = textField1.getText();
        String email = textField2.getText();
        String password = new String(passwordField3.getPassword());

        // Validate input fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required for registration.", "Registration Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 7 characters long, contain an uppercase letter, a lowercase letter, a number, and a special character!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email address. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create a new Student instance
        Student student = new Student(name, email, password);

        // Attempt to register the student
        boolean registrationSuccessful = student.registerStudent(name, email, password);

        // Handle registration success or failure
        if (registrationSuccessful) {
            JOptionPane.showMessageDialog(this, "Registration successful! You can now log in.");
            // Clear the input fields
            textField1.setText("");
            textField2.setText("");
            passwordField3.setText("");
            // Optionally hide the registration form
            setVisible(false);
            // Launch the login form
            new Login();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean isValidPassword(String password) {
        if (password.length() < 7) return false;

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;

            // Early exit if all conditions are met
            if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) return true;
        }

        return false;
    }
    // Email validation method
    private boolean isValidEmail(String email) {
        String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }


    public static void main(String[] args) {
        new Signup();
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}