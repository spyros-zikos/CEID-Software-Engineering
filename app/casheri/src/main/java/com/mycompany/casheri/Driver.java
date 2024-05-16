/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casheri;

/**
 *
 * @author Kallinikos
 */
public class Driver extends User {
    private String carModel;
    private String carId;
    private String carColor;
    private String carPhoto;

    // Constructor
    public Driver(int id, String name, String password, Coordinates location, String userPhoto,
                  String carModel, String carId, String carColor, String carPhoto) {
        super(id, name, password, location, userPhoto);
        this.carModel = carModel;
        this.carId = carId;
        this.carColor = carColor;
        this.carPhoto = carPhoto;
    }

    // Getters and setters
    public String getCarModel() {
        return carModel;
    }

    public void setCarDriver(String carDriver) {
        this.carModel = carDriver;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(String carPhoto) {
        this.carPhoto = carPhoto;
    }

    // Override toString() method to provide a string representation of the Driver object
    @Override
    public String toString() {
        return "Driver{" +
                "carModel='" + carModel + '\'' +
                ", carId='" + carId + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carPhoto='" + carPhoto + '\'' +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", location=" + getLocation() +
                ", userPhoto='" + getUserPhoto() + '\'' +
                '}';
    }
}

