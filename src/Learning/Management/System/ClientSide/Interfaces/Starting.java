package Learning.Management.System.ClientSide.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Starting extends JFrame implements ActionListener {
    JLabel label1;
    JButton button1, button2;

    Starting(){
        super("LMS");
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo2.png"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(390, 18, 100, 100);
        add(image);

//        label1 = new JLabel("L M S");
//        label1.setForeground(Color.WHITE);
//        label1.setFont(new Font("AvantGarde", Font.BOLD, 38));
//        label1.setBounds(375, 180, 500, 60);
//        add(label1);

        button1 = new JButton("SignIn");
        button1.setForeground(Color.BLACK);
        button1.setBackground(Color.WHITE);
        button1.setBounds(750, 25, 100, 30);
        button1.setFont(new Font("Arial", Font.BOLD, 18));
        button1.addActionListener(this);
        add(button1);

        button2 = new JButton("SignUp");
        button2.setForeground(Color.BLACK);
        button2.setBackground(Color.WHITE);
        button2.setBounds(750, 75, 100, 30);
        button2.setFont(new Font("Arial", Font.BOLD, 18));
        button2.addActionListener(this);
        add(button2);

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
        try {
            if (e.getSource() == button1) {
                System.out.println("Move to Login Page!");

                // Pass AuthService instance to Login
                new Login();
//                new Login(authService);
                setVisible(false);

            } else if (e.getSource() == button2) {
                System.out.println("Move to Register Page!");

                // Pass AuthService instance to Signup
                new Signup();
//                new Signup(authService);
                setVisible(false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Starting();

    }


}
