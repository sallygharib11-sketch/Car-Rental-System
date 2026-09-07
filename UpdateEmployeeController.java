package controllers;

import DatabaseOperations.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employe;

public class UpdateEmployeeController {

    // =========================
    // FXML COMPONENTS
    // =========================

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> cmbRole;


    // =========================
    // DAO
    // =========================

    private final EmployeeDAO employeeDAO = new EmployeeDAO();


    // =========================
    // CURRENT EMPLOYEE
    // =========================

    private Employe currentEmployee;


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
    // RECEIVE SELECTED EMPLOYEE
    // =========================

    public void setEmployee(Employe employee) {

        this.currentEmployee = employee;

        String firstName = employee.getFirstName();

        String lastName = employee.getLastName();

        txtFullName.setText(
                (firstName == null ? "" : firstName)
                + " "
                + (lastName == null ? "" : lastName)
        );

        txtUsername.setText(
                employee.getusername()
        );

        cmbRole.setValue(
                employee.getrole()
        );

        // Never display the old password
        txtPassword.clear();
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================

    @FXML
    private void handleUpdate() {

        if (currentEmployee == null) {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No employee was selected."
            );

            return;
        }


        // Get form values

        String fullName =
                txtFullName.getText().trim();

        String username =
                txtUsername.getText().trim();

        String newPassword =
                txtPassword.getText().trim();

        String role =
                cmbRole.getValue();


        // Validation

        if (fullName.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please enter the employee's full name."
            );

            return;
        }

        if (username.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please enter a username."
            );

            return;
        }

        if (role == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please select a role."
            );

            return;
        }


        // =========================
        // SPLIT FULL NAME
        // =========================

        String[] nameParts =
                fullName.split("\\s+", 2);

        String firstName =
                nameParts[0];

        String lastName = "";

        if (nameParts.length > 1) {

            lastName = nameParts[1];
        }


        // =========================
        // PASSWORD
        // =========================

        String finalPassword;

        if (newPassword.isEmpty()) {

            // Keep old password
            finalPassword =
                    currentEmployee.getpassword();

        } else {

            // Use new password
            finalPassword =
                    newPassword;
        }


        // =========================
        // CREATE UPDATED EMPLOYEE
        // =========================

        Employe updatedEmployee =
                new Employe(

                        currentEmployee.getEmployeeid(),

                        username,

                        finalPassword,

                        role,

                        firstName,

                        lastName,

                        currentEmployee.getPhone(),

                        currentEmployee.getlastlogin()
                );


        // =========================
        // UPDATE DATABASE
        // =========================

        boolean updated =
                employeeDAO.updateEmployee(updatedEmployee);


        if (updated) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Employee updated successfully."
            );

            closeWindow();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Update Failed",
                    "Could not update employee."
            );
        }
    }


    // =========================
    // CANCEL
    // =========================

    @FXML
    private void handleCancel() {

        closeWindow();
    }


    // =========================
    // CLOSE WINDOW
    // =========================

    private void closeWindow() {

        Stage stage =
                (Stage) txtUsername
                        .getScene()
                        .getWindow();

        stage.close();
    }


    // =========================
    // SHOW ALERT
    // =========================

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }
}