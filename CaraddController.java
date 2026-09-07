package controllers;

import DatabaseOperations.CarDAO;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Car;

public class CarAddController {

    @FXML
    private Button btnLogout;

    @FXML
    private MenuButton btnDashboard;

    @FXML
    private ComboBox<String> cmbBrand;

    @FXML
    private ComboBox<Integer> cmbYear;

    @FXML
    private TextField txtNewYear;

    @FXML
    private Button btnAddYear;

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

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    private final CarDAO carDAO = new CarDAO();


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

        colorPicker.setValue(Color.WHITE);
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

        Car car = new Car(0, carNumber, brand, model, year, colorHex, price, status);

        if (carDAO.addCar(car)) {

            showAlert(Alert.AlertType.INFORMATION, "Saved", "Car saved successfully.");
            clearForm();

        } else {

            showAlert(Alert.AlertType.ERROR, "Add Failed",
                    "Could not add the car. It may already exist (duplicate car number), "
                    + "or check the console for details.");
        }
    }


    /**
     * Resets the form back to its default state after a successful save,
     * so another car can be entered right away.
     */
    private void clearForm() {

        cmbBrand.getEditor().clear();
        cmbBrand.getSelectionModel().clearSelection();

        cmbYear.getSelectionModel().clearSelection();
        txtNewYear.clear();

        txtModel.clear();
        txtCarNumber.clear();

        colorPicker.setValue(Color.WHITE);

        SpinnerValueFactory<Double> priceFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000.0, 0.0, 5.0);
        spnPrice.setValueFactory(priceFactory);

        cmbStatus.getSelectionModel().clearSelection();
    }


    @FXML
    private void handleCancel(ActionEvent event) {

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
                (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {

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
