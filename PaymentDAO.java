/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseOperations;

import database.DatabaseConnection;
import models.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salley
 */
public class PaymentDAO {


    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO Payments "
                + "(Rental_id, Payment_date, Amount, Payment_method, Payment_status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payment.getRentalId());
             ps.setDate(2, (Date) payment.getPaymentDate());
       
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getPaymentStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM Payments";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                payments.add(new Payment(
                        rs.getInt("Payment_id"),
                        rs.getInt("Rental_id"),
                        rs.getDate("Payment_date"),
                        rs.getDouble("Amount"),
                        rs.getString("Payment_method"),
                        rs.getString("Payment_status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public boolean updatePayment(Payment payment) {

        String sql = "UPDATE Payments SET "
                + "Rental_id=?, Payment_date=?, Amount=?, "
                + "Payment_method=?, Payment_status=? "
                + "WHERE Payment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, payment.getRentalId());
               ps.setDate(2, (Date) payment.getPaymentDate());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getPaymentStatus());
            ps.setInt(6, payment.getPaymentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePayment(int paymentId) {

        String sql = "DELETE FROM Payments WHERE Payment_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, paymentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
