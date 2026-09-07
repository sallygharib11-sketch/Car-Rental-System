/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseOperations;
import database.DatabaseConnection;
import models.Rental;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salley
 */
public class RentalDAO {
public boolean addRental(Rental rental) {

        String sql = "INSERT INTO Rentals "
                + "(Customer_id, Car_id, Employee_id, Checkout_date, "
                + "Return_date, Total_price, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rental.getCustomerId());
            ps.setInt(2, rental.getCarId());
            ps.setInt(3, rental.getEmployeeId());
           ps.setDate(4, (Date)rental.getCheckoutDate());
            ps.setDate(5, (Date)rental.getReturnDate());
            ps.setDouble(6, rental.getTotalPrice());
            ps.setString(7, rental.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Rental> getAllRentals() {

        List<Rental> rentals = new ArrayList<>();

        String sql = "SELECT * FROM Rentals";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                rentals.add(new Rental(
                        rs.getInt("Rental_id"),
                        rs.getInt("Customer_id"),
                        rs.getInt("Car_id"),
                        rs.getInt("Employee_id"),
                        rs.getDate("Checkout_date"),
                        rs.getDate("Return_date"),
                        rs.getDouble("Total_price"),
                        rs.getString("Status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentals;
    }

    public boolean updateRental(Rental rental) {

        String sql = "UPDATE Rentals SET "
                + "Customer_id=?, Car_id=?, Employee_id=?, "
                + "Checkout_date=?, Return_date=?, Total_price=?, Status=? "
                + "WHERE Rental_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rental.getCustomerId());
            ps.setInt(2, rental.getCarId());
            ps.setInt(3, rental.getEmployeeId());
            ps.setDate(4, (Date)rental.getCheckoutDate());
            ps.setDate(5, (Date)rental.getReturnDate());
            ps.setDouble(6, rental.getTotalPrice());
            ps.setString(7, rental.getStatus());
            ps.setInt(8, rental.getRentalId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRental(int rentalId) {

        String sql = "DELETE FROM Rentals WHERE Rental_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rentalId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
