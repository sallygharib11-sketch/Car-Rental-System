package controllers;

import DatabaseOperations.CarDAO;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Car;

public class CarupdateController {
private void loadPage(String fxmlPath, String title) {

    try {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
        );

        Parent root = loader.load();

        Stage stage = (Stage) btnDashboard
                .getScene()
                .getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Car Rental System - " + title);
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
    @FXML
    private MenuButton btnDashboard;
     @FXML
    private void openCustomers() {
        loadPage("/views/CustomerView.fxml", "Customers");
    }

    @FXML
    private void openPayments() {
        loadPage("/views/PaymentView.fxml", "Payments");
    }

    @FXML
    private void openCars() {
        loadPage("/views/CarView.fxml", "Cars");
    }

    @FXML
    private void openEmployees() {
        loadPage("/views/EmployeeView.fxml", "Employees");
    }

    @FXML
    private void openRentals() {
        loadPage("/views/RentalView.fxml", "Rentals");
    }


    @FXML
    private ComboBox<String> cmbBrand;

    @FXML
    private ComboBox<Integer> cmbYear;

    @FXML
    private TextField txtNewYear;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtCarNumber;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Spinner<Double> spnPrice;

    @FXML
    private ComboBox<String> cmbStatus;

    private final CarDAO carDAO = new CarDAO();

    private Car currentCar;


    @FXML
    private void initialize() {

        cmbBrand.setItems(FXCollections.observableArrayList(
                "Toyota", "Honda", "Ford", "Chevrolet", "BMW",
                "Mercedes-Benz", "Nissan", "Hyundai", "Kia", "Volkswagen"
        ));

        int currentYear = java.time.Year.now().getValue();
        java.util.List<Integer> years = new java.util.ArrayList<>();

        for (int y = currentYear + 1; y >= currentYear - 20; y--) {
            years.add(y);
        }

        cmbYear.setItems(FXCollections.observableArrayList(years));

        cmbStatus.setItems(FXCollections.observableArrayList(
                "Available", "Rented", "Under Maintenance"
        ));

        SpinnerValueFactory<Double> priceFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000.0, 0.0, 5.0);

        spnPrice.setValueFactory(priceFactory);
    }


    /**
     * Called by CarsController right after loading this FXML,
     * to pre-fill the form with the car being edited.
     */
    public void setCar(Car car) {

        this.currentCar = car;

        cmbBrand.getEditor().setText(car.getBrand());

        if (!cmbYear.getItems().contains(car.getYear())) {
            cmbYear.getItems().add(0, car.getYear());
        }
        cmbYear.getSelectionModel().select(Integer.valueOf(car.getYear()));

        txtModel.setText(car.getModel());
        txtCarNumber.setText(car.getCarNumber());

        try {
            colorPicker.setValue(Color.web(car.getColor()));
        } catch (Exception e) {
            colorPicker.setValue(Color.WHITE);
        }

        SpinnerValueFactory<Double> priceFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000.0, car.getDailyPrice(), 5.0);
        spnPrice.setValueFactory(priceFactory);

        if (!cmbStatus.getItems().contains(car.getStatus())) {
            cmbStatus.getItems().add(car.getStatus());
        }
        cmbStatus.getSelectionModel().select(car.getStatus());
    }


    @FXML
    private void handleAddYear(ActionEvent event) {

        String text = txtNewYear.getText().trim();

        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Year", "Please enter a year to add.");
            return;
        }

        int year;

        try {
            year = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Year", "Year must be a whole number, e.g. 2022.");
            return;
        }

        if (!cmbYear.getItems().contains(year)) {
            cmbYear.getItems().add(0, year);
        }

        cmbYear.getSelectionModel().select(Integer.valueOf(year));
        txtNewYear.clear();
    }


    @FXML
    private void handleSave(ActionEvent event) {

        if (currentCar == null) {
            showAlert(Alert.AlertType.ERROR, "No Car Loaded", "No car was loaded to update.");
            return;
        }

        String brand = cmbBrand.getEditor().getText().trim();
        Integer year = cmbYear.getValue();
        String model = txtModel.getText().trim();
        String carNumber = txtCarNumber.getText().trim();
        Color color = colorPicker.getValue();
        Double price = spnPrice.getValue();
        String status = cmbStatus.getValue();

        if (brand.isEmpty() || year == null || model.isEmpty()
                || carNumber.isEmpty() || color == null
                || price == null || status == null) {

            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in every field before saving.");
            return;
        }

        String colorHex = toHex(color);

        Car updatedCar = new Car(
                currentCar.getCarId(),
                carNumber,
                brand,
                model,
                year,
                colorHex,
                price,
                status
        );

        if (carDAO.updateCar(updatedCar)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Updated");
            alert.setHeaderText(null);
            alert.setContentText("Car updated successfully.");
            alert.showAndWait();

            goToCarsView(event);

        } else {

            showAlert(Alert.AlertType.ERROR, "Update Failed",
                    "Could not update the car. It may conflict with an existing car number, "
                    + "or check the console for details.");
        }
    }


    @FXML
    private void handleBack(ActionEvent event) {

        goToCarsView(event);
    }


    @FXML
    private void handleLogout(ActionEvent event) {

        loadView(event, "/views/LoginView.fxml", "Car Rental System - Login");
    }


    @FXML
    private void handleDashboard(ActionEvent event) {

        loadView(event, "/views/AdminDashboard.fxml", "Car Rental System - Admin Dashboard");
    }


    private void goToCarsView(ActionEvent event) {

        loadView(event, "/views/CarsView.fxml", "Car Rental System - Cars");
    }


    private void loadView(ActionEvent event, String fxmlPath, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(fxmlPath)
            );

            Parent root = loader.load();

            Stage stage =
                (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException | IllegalStateException e) {

            e.printStackTrace();

            showAlert(Alert.AlertType.ERROR, "Page Error", "Could not open: " + fxmlPath);
        }
    }


    private String toHex(Color color) {

        return String.format(
                "#%02X%02X%02X",
                (int) Math.round(color.getRed() * 255),
                (int) Math.round(color.getGreen() * 255),
                (int) Math.round(color.getBlue() * 255)
        );
    }


    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}