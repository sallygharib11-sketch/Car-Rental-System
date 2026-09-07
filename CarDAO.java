/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseOperations;

import database.DatabaseConnection;
import models.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author salley
 */
public class CarDAO {
   



    public boolean addCar(Car car) {

        String sql = "INSERT INTO Cars "
                + "(Car_number, Brand, Model, Year, Color, Daily_price, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getCarNumber());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setString(5, car.getColor());
            ps.setDouble(6, car.getDailyPrice());
            ps.setString(7, car.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Car> getAllCars() {

        List<Car> cars = new ArrayList<>();

        String sql = "SELECT * FROM Cars";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                cars.add(new Car(
                        rs.getInt("Car_id"),
                        rs.getString("Car_number"),
                        rs.getString("Brand"),
                        rs.getString("Model"),
                        rs.getInt("Year"),
                        rs.getString("Color"),
                        rs.getDouble("Daily_price"),
                        rs.getString("Status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    public boolean updateCar(Car car) {

        String sql = "UPDATE Cars SET "
                + "Car_number=?, Brand=?, Model=?, Year=?, "
                + "Color=?, Daily_price=?, Status=? "
                + "WHERE Car_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, car.getCarNumber());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setInt(4, car.getYear());
            ps.setString(5, car.getColor());
            ps.setDouble(6, car.getDailyPrice());
            ps.setString(7, car.getStatus());
            ps.setInt(8, car.getCarId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCar(int carId) {

        String sql = "DELETE FROM Cars WHERE Car_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, carId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
