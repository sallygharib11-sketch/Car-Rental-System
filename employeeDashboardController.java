package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

public class employeeDashboardController {

    @FXML
    private MenuButton btnDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnCars;

    @FXML
    private Button btnRentals;

    @FXML
    private Button btnPayments;


    @FXML
    private void logout(ActionEvent event) {
        loadView(event, "/views/LoginView.fxml", "Car Rental System - Login");
    }


    @FXML
    private void openCustomers(ActionEvent event) {
        loadView(event, "/views/CustomersView.fxml", "Car Rental System - Customers");
    }


    @FXML
    private void openCars(ActionEvent event) {
        loadView(event, "/views/CarsView.fxml", "Car Rental System - Cars");
    }


    @FXML
    private void openRentals(ActionEvent event) {
        loadView(event, "/views/RentalsView.fxml", "Car Rental System - Rentals");
    }


    @FXML
    private void openPayments(ActionEvent event) {
        loadView(event, "/views/PaymentsView.fxml", "Car Rental System - Payments");
    }


    /**
     * Loads the given FXML into the current window.
     * If the FXML doesn't exist yet, shows an alert instead of crashing.
     * Works whether the event source is a Button or a MenuItem (both are Nodes
     * once attached to the scene, except MenuItem, which is handled separately below).
     */
    private void loadView(ActionEvent event, String fxmlPath, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
            );

            Parent root = loader.load();

            Stage stage = resolveStage(event);

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(
                Alert.AlertType.ERROR,
                "Page Not Available",
                "Could not open this page yet: " + fxmlPath
            );
        }
    }


    /**
     * MenuItem is not a Node, so it doesn't have getScene() directly.
     * This resolves the current Stage whether the click came from a
     * Button (a Node) or a MenuItem (attached to the MenuButton instead).
     */
    private Stage resolveStage(ActionEvent event) {

        Object source = event.getSource();

        if (source instanceof Node) {
            return (Stage) ((Node) source).getScene().getWindow();
        }

        // Fallback: MenuItem clicks route through the MenuButton in the FXML.
        return (Stage) btnDashboard.getScene().getWindow();
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