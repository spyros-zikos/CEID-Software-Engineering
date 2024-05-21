/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casheri;

/**
 *
 * @author greg
 */
public class User {
    private int id;
    private String name;
    private String password;
    private Coordinates location;
    private String userPhoto;

    // Simple Constructor
    public User(int id) {
        this.id = id;
    }
    
    // Constructor
    public User(int id, String name, String password, Coordinates location, String userPhoto) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.location = location;
        this.userPhoto = userPhoto;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Coordinates getLocation() {
        return location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    // Override toString() method to provide a string representation of the User object
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", location=" + location +
                ", userPhoto='" + userPhoto + '\'' +
                '}';
    }
}



