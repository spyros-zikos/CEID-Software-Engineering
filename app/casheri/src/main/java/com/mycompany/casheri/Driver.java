package com.mycompany.casheri;

import java.util.ArrayList;
import org.jxmapviewer.viewer.GeoPosition;

public class Driver extends User {
    private String carModel;
    private String carId;
    private String carColor;
    private String carPhoto;
    private ArrayList<Post> posts;

    // Constructor
    public Driver(int id, String name, String password, GeoPosition location, String userPhoto,
                  String carModel, String carId, String carColor, String carPhoto) {
        super(id, name, password, location, userPhoto);
        this.carModel = carModel;
        this.carId = carId;
        this.carColor = carColor;
        this.carPhoto = carPhoto;
    }
        public Driver(int id, String name, String password, GeoPosition location, String userPhoto,
                  String carModel, String carId, String carColor) {
        super(id, name, password, location, userPhoto);
        this.carModel = carModel;
        this.carId = carId;
        this.carColor = carColor;
        
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
    
    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}

