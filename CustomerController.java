package controllers;

import DatabaseOperations.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.net.URL;
import java.util.List;

public class CustomerController {

    // =========================================================
    // FORM FIELDS
    // =========================================================

    @FXML
    private TextField txtfirstname;

    @FXML
    private TextField txtlastname;

    @FXML
    private TextField txtphone;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtSearch;


    // =========================================================
    // BUTTONS
    // =========================================================

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;


    // =========================================================
    // TABLE
    // =========================================================

    @FXML
    private TableView<Customer> tblMovies;

    @FXML
    private TableColumn<Customer, Integer> colId;

    @FXML
    private TableColumn<Customer, String> colTitle;

    @FXML
    private TableColumn<Customer, String> colDescription;

    @FXML
    private TableColumn<Customer, String> colGenre;

    @FXML
    private TableColumn<Customer, String> colLanguage;


    // =========================================================
    // DAO
    // =========================================================

    private final CustomerDAO customerDAO = new CustomerDAO();

    private final ObservableList<Customer> customerList =
            FXCollections.observableArrayList();


    // =========================================================
    // INITIALIZE
    // =========================================================

    @FXML
    public void initialize() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("customerId")
        );

        colTitle.setCellValueFactory(
                new PropertyValueFactory<>("firstName")
        );

        colDescription.setCellValueFactory(
                new PropertyValueFactory<>("lastName")
        );

        colGenre.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        colLanguage.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        loadCustomers();

        // Search while typing
        txtSearch.textProperty().addListener(
                (observable, oldValue, newValue) -> searchCustomers()
        );

        // Select customer from table
        tblMovies.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, selectedCustomer) -> {

                            if (selectedCustomer != null) {
                                fillForm(selectedCustomer);
                            }
                        }
                );
    }


    // =========================================================
    // LOAD CUSTOMERS
    // =========================================================

    private void loadCustomers() {

        List<Customer> customers =
                customerDAO.getAllCustomers();

        customerList.clear();

        if (customers != null) {
            customerList.addAll(customers);
        }

        tblMovies.setItems(customerList);
    }


    // =========================================================
    // ADD CUSTOMER
    // =========================================================

    @FXML
    private void handleAdd() {

        String firstName =
                txtfirstname.getText().trim();

        String lastName =
                txtlastname.getText().trim();

        String phone =
                txtphone.getText().trim();

        String email =
                txtemail.getText().trim();


        // -----------------------------------------------------
        // VALIDATION
        // -----------------------------------------------------

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please fill in all customer fields."
            );

            return;
        }


        if (!email.contains("@")
                || !email.contains(".")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Email",
                    "Please enter a valid email address."
            );

            return;
        }


        // -----------------------------------------------------
        // CREATE CUSTOMER
        // -----------------------------------------------------

        Customer customer = new Customer(
                0,
                firstName,
                lastName,
                phone,
                email
        );


        // -----------------------------------------------------
        // INSERT
        // -----------------------------------------------------

        boolean success =
                customerDAO.addCustomer(customer);


        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer added successfully."
            );

            clearForm();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Add Customer Failed",
                    "Customer could not be added."
            );
        }
    }


    // =========================================================
    // UPDATE CUSTOMER
    // =========================================================

    @FXML
    private void handleEdit() {

        Customer selectedCustomer =
                tblMovies.getSelectionModel()
                        .getSelectedItem();


        if (selectedCustomer == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Customer Selected",
                    "Please select a customer from the table."
            );

            return;
        }


        String firstName =
                txtfirstname.getText().trim();

        String lastName =
                txtlastname.getText().trim();

        String phone =
                txtphone.getText().trim();

        String email =
                txtemail.getText().trim();


        // -----------------------------------------------------
        // VALIDATION
        // -----------------------------------------------------

        if (firstName.isEmpty()
                || lastName.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Missing Information",
                    "Please fill in all customer fields."
            );

            return;
        }


        if (!email.contains("@")
                || !email.contains(".")) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Email",
                    "Please enter a valid email address."
            );

            return;
        }


        // -----------------------------------------------------
        // UPDATE OBJECT
        // -----------------------------------------------------

        selectedCustomer.setFirstName(firstName);
        selectedCustomer.setLastName(lastName);
        selectedCustomer.setPhone(phone);
        selectedCustomer.setEmail(email);


        // -----------------------------------------------------
        // UPDATE DATABASE
        // -----------------------------------------------------

        boolean success =
                customerDAO.updateCustomer(selectedCustomer);


        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer updated successfully."
            );

            tblMovies.refresh();

            clearForm();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Update Failed",
                    "Customer could not be updated."
            );
        }
    }


    // =========================================================
    // DELETE CUSTOMER
    // =========================================================

    @FXML
    private void handleDelete() {

        Customer selectedCustomer =
                tblMovies.getSelectionModel()
                        .getSelectedItem();


        if (selectedCustomer == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "No Customer Selected",
                    "Please select a customer from the table."
            );

            return;
        }


        boolean success =
                customerDAO.deleteCustomer(
                        selectedCustomer.getCustomerId()
                );


        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Success",
                    "Customer deleted successfully."
            );

            clearForm();

            loadCustomers();

        } else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Delete Failed",
                    "Customer could not be deleted."
            );
        }
    }


    // =========================================================
    // SEARCH BUTTON
    // =========================================================

    @FXML
    private void handleSearch() {
        searchCustomers();
    }


    // =========================================================
    // SEARCH
    // =========================================================

    private void searchCustomers() {

        String search =
                txtSearch.getText()
                        .trim()
                        .toLowerCase();


        if (search.isEmpty()) {

            tblMovies.setItems(customerList);

            return;
        }


        ObservableList<Customer> filtered =
                FXCollections.observableArrayList();


        for (Customer customer : customerList) {

            if (
                    String.valueOf(
                            customer.getCustomerId()
                    ).contains(search)

                    || customer.getFirstName()
                            .toLowerCase()
                            .contains(search)

                    || customer.getLastName()
                            .toLowerCase()
                            .contains(search)

                    || customer.getPhone()
                            .toLowerCase()
                            .contains(search)

                    || customer.getEmail()
                            .toLowerCase()
                            .contains(search)
            ) {

                filtered.add(customer);
            }
        }


        tblMovies.setItems(filtered);
    }


    // =========================================================
    // FILL FORM WHEN CUSTOMER IS SELECTED
    // =========================================================

    private void fillForm(Customer customer) {

        txtfirstname.setText(
                customer.getFirstName()
        );

        txtlastname.setText(
                customer.getLastName()
        );

        txtphone.setText(
                customer.getPhone()
        );

        txtemail.setText(
                customer.getEmail()
        );
    }


    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        txtfirstname.clear();
        txtlastname.clear();
        txtphone.clear();
        txtemail.clear();

        tblMovies.getSelectionModel()
                .clearSelection();
    }


    // =========================================================
    // NAVIGATION
    // =========================================================

    @FXML
    private void openCustomers() {

        loadCustomers();
    }


    @FXML
    private void openPayments() {

        openPage(
                "/views/PaymentView.fxml",
                "Car Rental System - Payments"
        );
    }


    @FXML
    private void openCars() {

        openPage(
                "/views/CarView.fxml",
                "Car Rental System - Cars"
        );
    }


    @FXML
    private void openEmployees() {

        openPage(
                "/views/EmployeeView.fxml",
                "Car Rental System - Employees"
        );
    }


    @FXML
    private void openRentals() {

        openPage(
                "/views/Rental.fxml",
                "Car Rental System - Rentals"
        );
    }


    // =========================================================
    // OPEN PAGE
    // =========================================================

    private void openPage(
            String fxmlPath,
            String title) {

        try {

            URL url =
                    getClass().getResource(fxmlPath);


            if (url == null) {

                showAlert(
                        Alert.AlertType.ERROR,
                        "FXML Error",
                        "Could not find:\n"
                                + fxmlPath
                );

                return;
            }


            FXMLLoader loader =
                    new FXMLLoader(url);

            Parent root =
                    loader.load();


            Stage stage =
                    (Stage) tblMovies
                            .getScene()
                            .getWindow();


            Scene scene =
                    new Scene(root);


            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();


        } catch (IOException e) {

            e.printStackTrace();

            showAlert(
                    Alert.AlertType.ERROR,
                    "Navigation Error",
                    "Could not open:\n"
                            + fxmlPath
                            + "\n\n"
                            + e.getMessage()
            );
        }
    }


    // =========================================================
    // ALERT
    // =========================================================

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message) {

        Alert alert =
                new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}