package com.mycompany.casheri;

import java.math.BigInteger;
import org.jxmapviewer.viewer.GeoPosition;

public class User {
    private int id;
    private String uname;
    private String password;
    private GeoPosition location;
    private String userPhoto;
    private String phone;

    // Simple Constructor
    public User(int id) {
        this.id = id;
    }
    
    // Constructor Greg
    public User(int id, String uname, String password, GeoPosition location, String userPhoto) {
        this.id = id;
        this.uname = uname;
        this.password = password;
        this.location = location;
        this.userPhoto = userPhoto;
    }
    
    //Constructor Kalli
    public User(int id, String uname, String phone, GeoPosition location) {
        this.id = id;
//        this.userType = userType;
        this.uname = uname;
        this.phone = phone;
        this.location = location;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return uname;
    }

    public void setName(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GeoPosition getLocation() {
        return location;
    }

    public void setLocation(GeoPosition location) {
        this.location = location;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPhone() {
        return phone;
    }
   
    public void setPhone(String phone) {
        this.phone = phone;
    }
}



