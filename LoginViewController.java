package controllers;

import database.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML
    private TextField txtusername;

    @FXML
    private PasswordField txtpassword;
    @FXML
    private Button btnLogin;


    // =========================================================
    // LOGIN
    // =========================================================
    @FXML
    private void handleLogin(ActionEvent event) {

        String username = txtusername.getText().trim();
        String password = txtpassword.getText().trim();

        // Check empty fields
        if (username.isEmpty() || password.isEmpty()) {

            showAlert(
                Alert.AlertType.WARNING,
                "Missing Information",
                "Please enter your username and password."
            );

            return;
        }

        String sql =
            "SELECT Employee_id, Role " +
            "FROM Employees " +
            "WHERE Username = ? AND Password = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                // =================================================
                // USER FOUND
                // =================================================
                if (resultSet.next()) {

                    int employeeId =
                            resultSet.getInt("Employee_id");

                    String role =
                            resultSet.getString("Role");

                    System.out.println(
                        "Login successful."
                    );

                    System.out.println(
                        "Employee ID: " + employeeId
                    );

                    System.out.println(
                        "Role: " + role
                    );

                    // Update last login
                    updateLastLogin(employeeId);

                    // =================================================
                    // ADMIN
                    // =================================================
                    if (role != null &&
                        role.trim().equalsIgnoreCase("Admin")) {

                        System.out.println(
                            "Opening Admin Dashboard..."
                        );

                        openAdminPage();

                    }

                    // =================================================
                    // EMPLOYEE / NON-ADMIN
                    // =================================================
                    else {

                        System.out.println(
                            "Opening Employee Dashboard..."
                        );

                        openEmployeePage();
                    }

                }

                // =================================================
                // USER NOT FOUND
                // =================================================
                else {

                    showAlert(
                        Alert.AlertType.ERROR,
                        "Login Failed",
                        "Invalid username or password."
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            showAlert(
                Alert.AlertType.ERROR,
                "Database Error",
                "Error connecting to the database.\n\n"
                + e.getMessage()
            );
        }
    }


    // =========================================================
    // UPDATE LAST LOGIN
    // =========================================================
    private void updateLastLogin(int employeeId) {

        String sql =
            "UPDATE Employees " +
            "SET Last_login = NOW() " +
            "WHERE Employee_id = ?";

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setInt(1, employeeId);

            statement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // =========================================================
    // OPEN ADMIN DASHBOARD
    // =========================================================
    private void openAdminPage() {

        openPage(
            "/views/AdminDashboard.fxml",
            "Car Rental System - Admin Dashboard"
        );
    }


    // =========================================================
    // OPEN EMPLOYEE DASHBOARD
    // =========================================================
    private void openEmployeePage() {

        openPage(
            "/views/employeeDashboard.fxml",
            "Car Rental System - Employee Dashboard"
        );
    }


    // =========================================================
    // OPEN PAGE
    // =========================================================
    private void openPage(
            String fxmlPath,
            String title) {

        try {

            System.out.println(
                "Loading FXML: " + fxmlPath
            );

            URL fxmlUrl =
                    getClass().getResource(fxmlPath);

            // =====================================================
            // CHECK IF FXML EXISTS
            // =====================================================
            if (fxmlUrl == null) {

                System.err.println(
                    "FXML NOT FOUND: " + fxmlPath
                );

                showAlert(
                    Alert.AlertType.ERROR,
                    "FXML Error",
                    "Could not find:\n"
                    + fxmlPath
                    + "\n\nMake sure the file is inside:"
                    + "\n/src/views/"
                );

                return;
            }

            System.out.println(
                "FXML found: " + fxmlUrl
            );

            FXMLLoader loader =
                    new FXMLLoader(fxmlUrl);

            Parent root = loader.load();

            Stage stage =
                    (Stage) txtusername
                            .getScene()
                            .getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

            System.out.println(
                "Dashboard opened successfully."
            );

        } catch (IOException e) {

            e.printStackTrace();

            showAlert(
                Alert.AlertType.ERROR,
                "Page Loading Error",
                "Could not open:\n"
                + fxmlPath
                + "\n\nError:\n"
                + e.getMessage()
            );

        } catch (Exception e) {

            e.printStackTrace();

            showAlert(
                Alert.AlertType.ERROR,
                "Application Error",
                "An error occurred while opening:\n"
                + fxmlPath
                + "\n\nError:\n"
                + e.getMessage()
            );
        }
    }


    // =========================================================
    // SHOW ALERT
    // =========================================================
    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}