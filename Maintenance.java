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
public class Maintenance {
    private int maintenanceId;
    private int carId;
    private Date maintenanceDate;
    private String description;
    private double cost;
    private String status;

    public Maintenance() {
    }

    public Maintenance(int carId, Date maintenanceDate,
                       String description, double cost,
                       String status) {

        this.carId = carId;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.status = status;
    }

    public Maintenance(int maintenanceId, int carId,
                       Date  maintenanceDate,
                       String description, double cost,
                       String status) {

        this.maintenanceId = maintenanceId;
        this.carId = carId;
        this.maintenanceDate = maintenanceDate;
        this.description = description;
        this.cost = cost;
        this.status = status;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date  maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

