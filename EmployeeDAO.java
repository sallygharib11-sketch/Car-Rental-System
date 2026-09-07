package DatabaseOperations;

import database.DatabaseConnection;
import models.Employe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // =========================
    // Safe conversion: works whether Employe.getlastlogin()
    // returns java.util.Date or java.sql.Date.
    // Casting java.util.Date -> java.sql.Date directly throws
    // ClassCastException, so convert via getTime() instead.
    // =========================
    private Date toSqlDate(java.util.Date date) {

        if (date == null) {
            return null;
        }

        if (date instanceof Date) {
            return (Date) date;
        }

        return new Date(date.getTime());
    }


    // =========================
    // ADD EMPLOYEE
    // =========================
    public boolean addEmployee(Employe employee) {

        String sql = "INSERT INTO Employees "
                + "(Username, Password, Role, First_name, Last_name, Phone, Last_login) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, employee.getusername());
            ps.setString(2, employee.getpassword());
            ps.setString(3, employee.getrole());
            ps.setString(4, employee.getFirstName());
            ps.setString(5, employee.getLastName());
            ps.setString(6, employee.getPhone());

            ps.setDate(7, toSqlDate(employee.getlastlogin()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding employee:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // GET ALL EMPLOYEES
    // =========================
    public List<Employe> getAllEmployees() {

        List<Employe> employees = new ArrayList<>();

        String sql = "SELECT * FROM Employees";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Employe employee = new Employe(

                        rs.getInt("Employee_id"),

                        rs.getString("Username"),

                        rs.getString("Password"),

                        rs.getString("Role"),

                        rs.getString("First_name"),

                        rs.getString("Last_name"),

                        rs.getString("Phone"),

                        rs.getDate("Last_login")
                );

                employees.add(employee);
            }

        } catch (SQLException e) {

            System.out.println("Error loading employees:");
            e.printStackTrace();
        }

        return employees;
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================
    public boolean updateEmployee(Employe employee) {

        String sql = "UPDATE Employees SET "
                + "Username=?, "
                + "Password=?, "
                + "Role=?, "
                + "First_name=?, "
                + "Last_name=?, "
                + "Phone=?, "
                + "Last_login=? "
                + "WHERE Employee_id=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, employee.getusername());
            ps.setString(2, employee.getpassword());
            ps.setString(3, employee.getrole());
            ps.setString(4, employee.getFirstName());
            ps.setString(5, employee.getLastName());
            ps.setString(6, employee.getPhone());
            ps.setDate(7, toSqlDate(employee.getlastlogin()));

            // Employee ID for WHERE clause
            ps.setInt(8, employee.getEmployeeid());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error updating employee:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // DELETE EMPLOYEE
    // =========================
    public boolean deleteEmployee(int employeeId) {

        String sql = "DELETE FROM Employees "
                + "WHERE Employee_id=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, employeeId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting employee:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================
    // COUNT EMPLOYEES
    // =========================
    public int countEmployees() {

        String sql = "SELECT COUNT(*) FROM Employees";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Error counting employees:");
            e.printStackTrace();
        }

        return 0;
    }
}