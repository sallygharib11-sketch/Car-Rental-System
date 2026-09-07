package controllers;

import DatabaseOperations.EmployeeDAO;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import models.Employe;

public class EmployeeViewController {

    // =========================
    // NAVIGATION
    // =========================

    @FXML
    private MenuButton btnDashboard;

    // =========================
    // SEARCH
    // =========================

    @FXML
    private TextField txtSearch;

    // =========================
    // TABLE
    // =========================

    @FXML
    private TableView<Employe> tblEmployees;

    @FXML
    private TableColumn<Employe, Integer> colid;

    @FXML
    private TableColumn<Employe, String> colusename;

    @FXML
    private TableColumn<Employe, String> colrole;

    @FXML
    private TableColumn<Employe, String> colfirstname;

    @FXML
    private TableColumn<Employe, String> collastname;

    @FXML
    private TableColumn<Employe, String> colphone;

    @FXML
    private TableColumn<Employe, Date> collastlogin;

    // =========================
    // DAO AND LIST
    // =========================

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    private final ObservableList<Employe> employeeList =
            FXCollections.observableArrayList();


    // =========================
    // INITIALIZE
    // =========================

    @FXML
    public void initialize() {

        colid.setCellValueFactory(
                new PropertyValueFactory<>("employeeid")
        );

        colusename.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );

        colrole.setCellValueFactory(
                new PropertyValueFactory<>("role")
        );

        colfirstname.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        collastname.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        colphone.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        collastlogin.setCellValueFactory(
                new PropertyValueFactory<>("lastlogin")
        );

        loadEmployees();

        txtSearch.textProperty().addListener(
                (observable, oldValue, newValue) -> searchEmployees()
        );
    }


    // =========================
    // LOAD EMPLOYEES
    // =========================

    private void loadEmployees() {

        employeeList.clear();

        employeeList.addAll(
                employeeDAO.getAllEmployees()
        );

        tblEmployees.setItems(employeeList);
    }


    // =========================
    // SEARCH EMPLOYEES
    // =========================

    private void searchEmployees() {

        String searchText =
                txtSearch.getText().trim().toLowerCase();

        if (searchText.isEmpty()) {

            tblEmployees.setItems(employeeList);
            return;
        }

        ObservableList<Employe> filteredList =
                FXCollections.observableArrayList();

        for (Employe employee : employeeList) {

            boolean matches =

                    String.valueOf(employee.getEmployeeid())
                            .contains(searchText)

                    || (employee.getusername() != null
                    && employee.getusername()
                            .toLowerCase()
                            .contains(searchText))

                    || (employee.getrole() != null
                    && employee.getrole()
                            .toLowerCase()
                            .contains(searchText))

                    || (employee.getFirstName() != null
                    && employee.getFirstName()
                            .toLowerCase()
                            .contains(searchText))

                    || (employee.getLastName() != null
                    && employee.getLastName()
                            .toLowerCase()
                            .contains(searchText))

                    || (employee.getPhone() != null
                    && employee.getPhone()
                            .toLowerCase()
                            .contains(searchText));

            if (matches) {
                filteredList.add(employee);
            }
        }

        tblEmployees.setItems(filteredList);
    }


    // =========================
    // ADD EMPLOYEE
    // =========================

    @FXML
    private void handleAdd() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/views/AddEmployee.fxml"
                    )
            );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle("Add Employee");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            loadEmployees();

        } catch (IOException e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Page Error",
                    "Could not open Add Employee page."
            );
        }
    }


    // =========================
    // UPDATE EMPLOYEE
    // =========================

    @FXML
    private void handleUpdate() {

        Employe selectedEmployee =
                tblEmployees.getSelectionModel()
                        .getSelectedItem();

        if (selectedEmployee == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Employee Selected",
                    "Please select an employee to update."
            );

            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/views/EmployeeUpdate.fxml"
                    )
            );

            Parent root = loader.load();

           UpdateEmployeeController controller =
                    loader.getController();

            controller.setEmployee(selectedEmployee);

            Stage stage = new Stage();

            stage.setTitle("Update Employee");

            stage.setScene(new Scene(root));

            stage.showAndWait();

            loadEmployees();

        } catch (IOException e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Page Error",
                    "Could not open Update Employee page."
            );
        }
    }


    // =========================
    // DELETE EMPLOYEE
    // =========================

    @FXML
    private void handleDelete() {

        Employe selectedEmployee =
                tblEmployees.getSelectionModel()
                        .getSelectedItem();

        if (selectedEmployee == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Employee Selected",
                    "Please select an employee to delete."
            );

            return;
        }

        Alert confirmation =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmation.setTitle("Delete Employee");

        confirmation.setHeaderText(null);

        confirmation.setContentText(
                "Are you sure you want to delete employee: "
                + selectedEmployee.getusername()
                + "?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            boolean deleted =
                    employeeDAO.deleteEmployee(
                            selectedEmployee.getEmployeeid()
                    );

            if (deleted) {

                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Success",
                        "Employee deleted successfully."
                );

                loadEmployees();

            } else {

                showAlert(
                        Alert.AlertType.ERROR,
                        "Delete Failed",
                        "Could not delete employee."
                );
            }
        }
    }


    // =========================
    // NAVIGATION
    // =========================

    @FXML
    private void openCustomers() {

        loadPage(
                "/views/CustomerView.fxml",
                "Customers"
        );
    }


    @FXML
    private void openPayments() {

        loadPage(
                "/views/PaymentView.fxml",
                "Payments"
        );
    }


    @FXML
    private void openCars() {

        loadPage(
                "/views/CarView.fxml",
                "Cars"
        );
    }


    @FXML
    private void openEmployees() {

        loadPage(
                "/views/EmployeeView.fxml",
                "Employees"
        );
    }


    @FXML
    private void openRentals() {

        loadPage(
                "/views/RentalView.fxml",
                "Rentals"
        );
    }


    // =========================
    // LOGOUT
    // =========================

    @FXML
    private void handleLogout() {

        loadPage(
                "/views/LoginView.fxml",
                "Login"
        );
    }


    // =========================
    // LOAD PAGE
    // =========================

    private void loadPage(
            String fxmlPath,
            String title
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(fxmlPath)
                    );

            Parent root = loader.load();

            Stage stage =
                    (Stage) btnDashboard
                            .getScene()
                            .getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle(
                    "Car Rental System - " + title
            );

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