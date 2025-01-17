package Learning.Management.System.ClientSide.Interfaces;
import javax.swing.*;
import java.awt.*;

public class Splashscreen extends JFrame {
    private JLabel label1;
    private JProgressBar progressBar;
    private JLabel percentageLabel;

    Splashscreen() {
        super("LMS");



        // Setup background image
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image ii2 = ii1.getImage().getScaledInstance(900, 530, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel image1 = new JLabel(ii3);
        image1.setBounds(0, 0, 900, 530);
        add(image1);

        // Setup progress bar
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setBounds(30, 450, 820, 20);
        add(progressBar);

        // Setup percentage label
        percentageLabel = new JLabel("0%");
        percentageLabel.setForeground(Color.RED);
        percentageLabel.setBounds(375, 430, 70, 20);
        add(percentageLabel);

        setLayout(null);
        setSize(900, 530);
        setLocation(380, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load in a new thread
        load();
    }

    private void load() {
        // Create a new thread to update the progress bar and percentage
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                progressBar.setValue(i);
                percentageLabel.setText(i + "%");
                try {
                    Thread.sleep(80); // Simulate time for loading
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // After loading, open the next frame
            SwingUtilities.invokeLater(() -> {
                new Starting(); // Your next frame class
                dispose(); // Close the splash screen
            });
        }).start();
    }

    public static void main(String[] args) {
        new Splashscreen();
    }
}