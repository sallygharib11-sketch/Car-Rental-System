/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author salley
 */
public class Rental {
    private int rentalId;
    private int customerId;
    private int carId;
    private int employeeId;
    private Date checkoutDate;
    private Date returnDate;
    private double totalPrice;
    private String status;

    public Rental() {
    }

    public Rental(int customerId, int carId, int employeeId,
                  Date checkoutDate, Date returnDate,
                  double totalPrice, String status) {

        this.customerId = customerId;
        this.carId = carId;
        this.employeeId = employeeId;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Rental(int rentalId, int customerId, int carId,
                  int employeeId, Date checkoutDate,
                  Date returnDate, double totalPrice,
                  String status) {

        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
        this.employeeId = employeeId;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

