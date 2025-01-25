package Learning.Management.System.ApplicationLayer.Payment_Certification;

import Learning.Management.System.DB.DbCon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Payment {
    private int paymentId;
    private String studentId;
    private String courseId;
    private double amount;
    private String paymentDate; // You can switch to LocalDateTime if preferred
    private String paymentMethod;
    private String status;

    // Constructor
    public Payment(String studentId, String courseId, double amount, String paymentMethod) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "Pending"; // Default status
        System.out.println("Payment object created: " + this.toString());
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    // Method to insert payment into the database
    public void insertPayment(String studentId, String courseId, double amount, String paymentMethod) {
        String sql = "INSERT INTO payments (student_id, course_id, amount, payment_method, status) VALUES (?, ?, ?, ?, ?)";
        String status = "Pending"; // Set a default status for the payment

        System.out.println("Attempting to connect to the database...");

        // Use try-with-resources to ensure DbCon is closed automatically
        try (DbCon dbCon = new DbCon(); // Automatically closes the connection at the end
             PreparedStatement pstmt = dbCon.getConnection().prepareStatement(sql)) {

            System.out.println("Database connection established successfully.");

            pstmt.setString(1, studentId);
            pstmt.setString(2, courseId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, paymentMethod);
            pstmt.setString(5, status);

            System.out.println("Prepared statement created: " + pstmt.toString());

            // Execute the insert operation
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment inserted successfully.");
            } else {
                System.out.println("No rows affected. Payment insertion failed.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", amount=" + amount +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}