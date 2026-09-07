package controllers;

import DatabaseOperations.EmployeeDAO;
import java.io.IOException;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import models.Employe;

public class AddEmployeeController {

    // =========================
    // FXML COMPONENTS
    // =========================

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TextField txtPhone;


    // =========================
    // DAO
    // =========================

    private final EmployeeDAO employeeDAO = new EmployeeDAO();


    // =========================
    // INITIALIZE
    // =========================

    @FXML
    public void initialize() {

        cmbRole.setItems(
                FXCollections.observableArrayList(
                        "Admin",
                        "Employee"
                )
        );
    }


    // =========================
    // SAVE EMPLOYEE
    // =========================

    @FXML
    private void handleSave(ActionEvent event) {

        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String role = cmbRole.getValue();
        String phone = txtPhone.getText().trim();


        // =========================
        // VALIDATION
        // =========================

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || username.isEmpty()
                || password.isEmpty()
                || role == null
                || phone.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please fill in all fields."
            );

            return;
        }


        // =========================
        // CREATE EMPLOYEE
        // =========================

        Employe newEmployee = new Employe(

                username,
                password,
                role,
                firstName,
                lastName,
                phone,
                null
        );


        // =========================
        // SAVE TO DATABASE
        // =========================

        boolean success = employeeDAO.addEmployee(newEmployee);


        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Employee added successfully."
            );

            // Go back to employee page
            loadView(
                    event,
                    "/views/EmployeeView.fxml",
                    "Car Rental System - Employees"
            );

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Save Failed",
                    "Could not add the employee. The username may already exist."
            );
        }
    }


    // =========================
    // CANCEL
    // =========================

    @FXML
    private void handleCancel(ActionEvent event) {

        loadView(
                event,
                "/views/EmployeeView.fxml",
                "Car Rental System - Employees"
        );
    }


    // =========================
    // NAVIGATION
    // =========================

    @FXML
    private void openCustomers(ActionEvent event) {

        loadView(
                event,
                "/views/CustomerView.fxml",
                "Car Rental System - Customers"
        );
    }


    @FXML
    private void openCars(ActionEvent event) {

        loadView(
                event,
                "/views/CarView.fxml",
                "Car Rental System - Cars"
        );
    }


    @FXML
    private void openEmployees(ActionEvent event) {

        loadView(
                event,
                "/views/EmployeeView.fxml",
                "Car Rental System - Employees"
        );
    }


    @FXML
    private void openRentals(ActionEvent event) {

        loadView(
                event,
                "/views/RentalView.fxml",
                "Car Rental System - Rentals"
        );
    }


    @FXML
    private void openPayments(ActionEvent event) {

        loadView(
                event,
                "/views/PaymentView.fxml",
                "Car Rental System - Payments"
        );
    }


    // =========================
    // LOGOUT
    // =========================

    @FXML
    private void handleLogout(ActionEvent event) {

        loadView(
                event,
                "/views/LoginView.fxml",
                "Car Rental System - Login"
        );
    }


    // =========================
    // LOAD PAGE
    // =========================

    private void loadView(
            ActionEvent event,
            String fxmlPath,
            String title
    ) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxmlPath)
            );

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Page Error",
                    "Could not open: " + fxmlPath
            );
        }
    }


    // =========================
    // ALERT
    // =========================

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