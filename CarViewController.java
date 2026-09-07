package controllers;

import DatabaseOperations.CarDAO;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.Optional;
import models.Car;

public class CarViewController {

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Car> tblCars;

    @FXML
    private TableColumn<Car, Integer> colId;

    @FXML
    private TableColumn<Car, String> colNumber;

    @FXML
    private TableColumn<Car, String> colBrand;

    @FXML
    private TableColumn<Car, String> colModel;

    @FXML
    private TableColumn<Car, Integer> colYear;

    @FXML
    private TableColumn<Car, String> colColor;

    @FXML
    private TableColumn<Car, Double> colPrice;

    @FXML
    private TableColumn<Car, String> colStatus;

    private final CarDAO carDAO = new CarDAO();

    private ObservableList<Car> carList = FXCollections.observableArrayList();

    private FilteredList<Car> filteredCars;


    @FXML
    private void initialize() {

        // Set the column resize policy in code rather than via fx:constant in FXML.
        // CONSTRAINED_RESIZE_POLICY has existed since JavaFX's earliest versions,
        // so it avoids any ambiguity with newer constants that some tooling
        // may not recognize (e.g. CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN).
        tblCars.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Bind columns to Car getters (getCarId, getCarNumber, getBrand, ...)
        colId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("dailyPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadCars();

        // Live search filter
        filteredCars = new FilteredList<>(carList, c -> true);

        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {

            String filter = newVal == null ? "" : newVal.trim().toLowerCase();

            filteredCars.setPredicate(car -> {

                if (filter.isEmpty()) {
                    return true;
                }

                return (car.getCarNumber() != null && car.getCarNumber().toLowerCase().contains(filter))
                        || (car.getBrand() != null && car.getBrand().toLowerCase().contains(filter))
                        || (car.getModel() != null && car.getModel().toLowerCase().contains(filter))
                        || (car.getStatus() != null && car.getStatus().toLowerCase().contains(filter));
            });
        });

        tblCars.setItems(filteredCars);
    }


    private void loadCars() {

        carList.setAll(carDAO.getAllCars());
    }


    // ============================================================
    // DASHBOARD MENU NAVIGATION
    // ============================================================

    @FXML
    private void openCustomers(ActionEvent event) {
        navigateTo(event, "/views/Customer.fxml", "Car Rental System - Customers");
    }


    @FXML
    private void openPayments(ActionEvent event) {
        navigateTo(event, "/views/PaymentsView.fxml", "Car Rental System - Payments");
    }


    @FXML
    private void openCars(ActionEvent event) {
        navigateTo(event, "/views/CarsView.fxml", "Car Rental System - Cars");
    }


    @FXML
    private void openEmployees(ActionEvent event) {
        navigateTo(event, "/views/EmployeeView.fxml", "Car Rental System - Employees");
    }


    @FXML
    private void openRentals(ActionEvent event) {
        navigateTo(event, "/views/RentalsView.fxml", "Car Rental System - Rentals");
    }


    /**
     * Navigates from the Dashboard MenuButton. Handles both Button and MenuItem
     * event sources, since MenuItem is not a Node and has no getScene() of its own.
     */
    private void navigateTo(ActionEvent event, String fxmlPath, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
            );

            Parent root = loader.load();

            Stage stage;

            if (event.getSource() instanceof Node) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } else {
                stage = (Stage) tblCars.getScene().getWindow();
            }

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(Alert.AlertType.ERROR, "Page Not Available", "Could not open this page yet: " + fxmlPath);
        }
    }


    // ============================================================
    // ADD
    // ============================================================

    @FXML
    private void handleAdd(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/Caradd.fxml")
            );

            Parent root = loader.load();

            Stage stage =
                (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Car Rental System - Add Car");
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(Alert.AlertType.ERROR, "Page Error", "Could not open the Add Car page.");
        }
    }


    // ============================================================
    // UPDATE
    // ============================================================

    @FXML
    private void handleUpdate(ActionEvent event) {

        Car selected = tblCars.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a car to update.");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/Carupdate.fxml")
            );

            Parent root = loader.load();

            CarupdateController controller = loader.getController();
            controller.setCar(selected);

            Stage stage =
                (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Car Rental System - Update Car");
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(Alert.AlertType.ERROR, "Page Error", "Could not open the Update Car page.");
        }
    }


    // ============================================================
    // DELETE
    // ============================================================

    @FXML
    private void handleDelete(ActionEvent event) {

        Car selected = tblCars.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a car to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete car " + selected.getCarNumber() + "?");

        Optional<ButtonType> choice = confirm.showAndWait();

        if (choice.isPresent() && choice.get() == ButtonType.OK) {

            if (carDAO.deleteCar(selected.getCarId())) {
                loadCars();
            } else {
                showAlert(Alert.AlertType.ERROR, "Delete Failed", "Could not delete the car. Check the console for details.");
            }
        }
    }


    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}