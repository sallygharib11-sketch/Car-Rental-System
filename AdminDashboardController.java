package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnPayments;

    @FXML
    private Button btnCars;

    @FXML
    private Button btnEmployees;

    @FXML
    private Button btnRentals;


    @FXML
    private void openDashboard(ActionEvent event) {
        // Already on the dashboard; nothing to do for now.
    }


    @FXML
    private void logout(ActionEvent event) {
        loadView(event, "/views/LoginView.fxml", "Car Rental System - Login");
    }


    @FXML
    private void openCustomers(ActionEvent event) {
        loadView(event, "/views/Customer.fxml", "Car Rental System - Customers");
    }


    @FXML
    private void openPayments(ActionEvent event) {
        loadView(event, "/views/PaymentsView.fxml", "Car Rental System - Payments");
    }


    @FXML
    private void openCars(ActionEvent event) {
        loadView(event, "/views/CarView.fxml", "Car Rental System - Cars");
    }


    @FXML
    private void openEmployees(ActionEvent event) {
        loadView(event, "/views/EmployeeView.fxml", "Car Rental System - Employees");
    }


    @FXML
    private void openRentals(ActionEvent event) {
        loadView(event, "/views/RentalsView.fxml", "Car Rental System - Rentals");
    }


    /**
     * Loads the given FXML into the current window.
     * If the FXML doesn't exist yet, shows an alert instead of crashing.
     */
    private void loadView(ActionEvent event, String fxmlPath, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
            );

            Parent root = loader.load();

            Stage stage =
                (Stage) ((Button) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException | NullPointerException e) {

            e.printStackTrace();

            showAlert(
                Alert.AlertType.ERROR,
                "Page Not Available",
                "Could not open this page yet: " + fxmlPath
            );
        }
    }


    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}