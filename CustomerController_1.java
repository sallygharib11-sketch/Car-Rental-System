package controllers;

import DatabaseOperations.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Customer;

import java.io.IOException;
import java.util.List;

public class CustomerController {

    // =========================
    // FORM FIELDS
    // =========================

    @javafx.fxml.FXML
    private TextField txtfirstname;

    @javafx.fxml.FXML
    private TextField txtlastname;

    @javafx.fxml.FXML
    private TextField txtphone;

    @javafx.fxml.FXML
    private TextField txtemail;

    // =========================
    // SEARCH
    // =========================

    @javafx.fxml.FXML
    private TextField txtSearch;

    // =========================
    // BUTTONS
    // =========================

    @javafx.fxml.FXML
    private Button btnsearch;

    @javafx.fxml.FXML
    private Button btnAdd;

    @javafx.fxml.FXML
    private Button btnEdit;

    @javafx.fxml.FXML
    private Button btnDelete;

    // =========================
    // TABLE
    // =========================

    @javafx.fxml.FXML
    private TableView<Customer> tblMovies;

    @javafx.fxml.FXML
    private TableColumn<Customer, Integer> colId;

    @javafx.fxml.FXML
    private TableColumn<Customer, String> colTitle;

    @javafx.fxml.FXML
    private TableColumn<Customer, String> colDescription;

    @javafx.fxml.FXML
    private TableColumn<Customer, String> colGenre;

    @javafx.fxml.FXML
    private TableColumn<Customer, String> colLanguage;

    // =========================
    // DAO
    // =========================

    private final CustomerDAO customerDAO = new CustomerDAO();

    private final ObservableList<Customer> customerList =
            FXCollections.observableArrayList();

    // =========================
    // INITIALIZE
    // =========================

    @javafx.fxml.FXML
    public void initialize() {

        // Connect table columns to Customer model
        colId.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));

        colTitle.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        colDescription.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        colGenre.setCellValueFactory(
                new PropertyValueFactory<>("phone"));

        colLanguage.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        // Load customers
        loadCustomers();

        // Select customer from table
        tblMovies.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if (newValue != null) {

                        txtfirstname.setText(newValue.getFirstName());
                        txtlastname.setText(newValue.getLastName());
                        txtphone.setText(newValue.getPhone());
                        txtemail.setText(newValue.getEmail());
                    }
                });
    }

    // =========================
    // LOAD CUSTOMERS
    // =========================

    private void loadCustomers() {

        customerList.clear();

        List<Customer> customers =
                customerDAO.getAllCustomers();

        customerList.addAll(customers);

        tblMovies.setItems(customerList);
    }

    // =========================
    // ADD CUSTOMER
    // =========================

    @javafx.fxml.FXML
    private void handleAdd() {

        if (!validateFields()) {
            return;
        }

        Customer customer = new Customer(
                0,
                txtfirstname.getText().trim(),
                txtlastname.getText().trim(),
                txtphone.getText().trim(),
                txtemail.getText().trim()
        );

        boolean success = customerDAO.addCustomer(customer);

        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer added successfully."
            );

            clearFields();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Failed to add customer."
            );
        }
    }

    // =========================
    // SEARCH
    // =========================

    @javafx.fxml.FXML
    private void handleSearch() {

        String search = txtSearch.getText()
                .trim()
                .toLowerCase();

        if (search.isEmpty()) {

            loadCustomers();
            return;
        }

        ObservableList<Customer> filtered =
                FXCollections.observableArrayList();

        for (Customer customer : customerDAO.getAllCustomers()) {

            if (
                String.valueOf(customer.getCustomerId())
                        .contains(search)
                ||
                customer.getFirstName()
                        .toLowerCase()
                        .contains(search)
                ||
                customer.getLastName()
                        .toLowerCase()
                        .contains(search)
                ||
                customer.getPhone()
                        .toLowerCase()
                        .contains(search)
                ||
                customer.getEmail()
                        .toLowerCase()
                        .contains(search)
            ) {

                filtered.add(customer);
            }
        }

        tblMovies.setItems(filtered);
    }

    // =========================
    // UPDATE CUSTOMER
    // =========================

    @javafx.fxml.FXML
    private void handleUpdate() {

        Customer selected =
                tblMovies.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Customer Selected",
                    "Please select a customer from the table."
            );

            return;
        }

        if (!validateFields()) {
            return;
        }

        Customer updatedCustomer = new Customer(
                selected.getCustomerId(),
                txtfirstname.getText().trim(),
                txtlastname.getText().trim(),
                txtphone.getText().trim(),
                txtemail.getText().trim()
        );

        boolean success =
                customerDAO.updateCustomer(updatedCustomer);

        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer updated successfully."
            );

            clearFields();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Failed to update customer."
            );
        }
    }

    // =========================
    // DELETE CUSTOMER
    // =========================

    @javafx.fxml.FXML
    private void handleDelete() {

        Customer selected =
                tblMovies.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Customer Selected",
                    "Please select a customer from the table."
            );

            return;
        }

        boolean success =
                customerDAO.deleteCustomer(
                        selected.getCustomerId()
                );

        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer deleted successfully."
            );

            clearFields();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Failed to delete customer."
            );
        }
    }

    // =========================
    // VALIDATION
    // =========================

    private boolean validateFields() {

        String firstName =
                txtfirstname.getText().trim();

        String lastName =
                txtlastname.getText().trim();

        String phone =
                txtphone.getText().trim();

        String email =
                txtemail.getText().trim();

        if (firstName.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please enter the first name."
            );

            txtfirstname.requestFocus();
            return false;
        }

        if (lastName.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please enter the last name."
            );

            txtlastname.requestFocus();
            return false;
        }

        if (phone.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please enter the phone number."
            );

            txtphone.requestFocus();
            return false;
        }

        if (email.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please enter the email."
            );

            txtemail.requestFocus();
            return false;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Validation Error",
                    "Please enter a valid email address."
            );

            txtemail.requestFocus();
            return false;
        }

        return true;
    }

    // =========================
    // CLEAR FIELDS
    // =========================

    private void clearFields() {

        txtfirstname.clear();
        txtlastname.clear();
        txtphone.clear();
        txtemail.clear();

        tblMovies.getSelectionModel().clearSelection();
    }

    // =========================
    // ALERT
    // =========================

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

    // =========================================================
    // NAVIGATION
    // =========================================================

    @javafx.fxml.FXML
    private void openCustomers() {
        // Already on Customer page
    }

    @javafx.fxml.FXML
    private void openPayments() {
        openPage("/views/PaymentView.fxml");
    }

    @javafx.fxml.FXML
    private void openCars() {
        openPage("/views/CarView.fxml");
    }

    @javafx.fxml.FXML
    private void openEmployees() {
        openPage("/views/EmployeeView.fxml");
    }

    @javafx.fxml.FXML
    private void openRentals() {
        openPage("/views/Rental.fxml");
    }

    // =========================
    // OPEN PAGE
    // =========================

    private void openPage(String fxmlPath) {

        try {

            Parent root =
                    FXMLLoader.load(
                            getClass().getResource(fxmlPath)
                    );

            Stage stage =
                    (Stage) tblMovies.getScene().getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);
            stage.show();

        } catch (IOException | NullPointerException e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Navigation Error",
                    "Could not open:\n" + fxmlPath
            );
        }
    }
}