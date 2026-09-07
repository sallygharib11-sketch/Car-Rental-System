
package models;
public class Car {
    private int carId;
    private String carNumber;
    private String brand;
    private String model;
    private int year;
    private String color;
    private double dailyPrice;
    private String status;

   
    public Car() {
    }

    public Car(
            int carId,
            String carNumber,
            String brand,
            String model,
            int year,
            String color,
            double dailyPrice,
            String status
    ) {

        this.carId = carId;
        this.carNumber = carNumber;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.dailyPrice = dailyPrice;
        this.status = status;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


