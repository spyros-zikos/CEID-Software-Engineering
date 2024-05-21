/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casheri;

import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * @author greg
 */
public class Passenger extends User {
    // Default Constructor
    public Passenger(int id){
        super(id);
    }
    // Constructor
    public Passenger(int id, String name, String password, GeoPosition location, String userPhoto) {
        super(id, name, password, location, userPhoto);
    }

    // Additional methods
    public void returnPassengerInfo() {
        System.out.println("Passenger Information:");
        System.out.println("Name: " + getName());
        System.out.println("ID: " + getId());
        System.out.println("Location: " + getLocation());
    }

    public void saveLocOk() {
        // Method implementation to indicate that the location is saved successfully
        System.out.println("Location saved successfully for passenger: " + getName());
    }

    public void chargeOk() {
        // Method implementation to indicate that the passenger's payment is successful
        System.out.println("Payment charged successfully for passenger: " + getName());
    }
}

