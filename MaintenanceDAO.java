/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DatabaseOperations;
import database.DatabaseConnection;
import models.Maintenance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author salley
 */
public class MaintenanceDAO {


    public boolean addMaintenance(Maintenance maintenance) {

        String sql = "INSERT INTO Maintenance "
                + "(Car_id, Maintenance_date, Description, Cost, Status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maintenance.getCarId());
             ps.setDate(2, (Date) maintenance.getMaintenanceDate());
            ps.setString(3, maintenance.getDescription());
            ps.setDouble(4,(Double) maintenance.getCost());
            ps.setString(5, maintenance.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Maintenance> getAllMaintenance() {

        List<Maintenance> maintenanceList = new ArrayList<>();

        String sql = "SELECT * FROM Maintenance";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                maintenanceList.add(new Maintenance(
                        rs.getInt("Maintenance_id"),
                        rs.getInt("Car_id"),
                        rs.getDate("Maintenance_date"),
                        rs.getString("Description"),
                        rs.getDouble("Cost"),
                        rs.getString("Status")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maintenanceList;
    }

    public boolean updateMaintenance(Maintenance maintenance) {

        String sql = "UPDATE Maintenance SET "
                + "Car_id=?, Maintenance_date=?, Description=?, "
                + "Cost=?, Status=? "
                + "WHERE Maintenance_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maintenance.getCarId());
            ps.setDate(2, (Date)maintenance.getMaintenanceDate());
            ps.setString(3, maintenance.getDescription());
            ps.setDouble(4, maintenance.getCost());
            ps.setString(5, maintenance.getStatus());
            ps.setInt(6, maintenance.getMaintenanceId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMaintenance(int maintenanceId) {

        String sql = "DELETE FROM Maintenance WHERE Maintenance_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maintenanceId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
