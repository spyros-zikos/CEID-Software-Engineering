package com.mycompany.casheri;

import org.jxmapviewer.viewer.GeoPosition;

public class User {
    
//    public String getUserType() {
//        return userType;
//    }
//
//    public void setUserType(String userType) {
//        this.userType = userType;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setButton(String uname) {
        this.uname = uname;
    }
    
    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
    
    public GeoPosition getCoord() {
        return coord;
    }

    public void setCoord(GeoPosition coord) {
        this.coord = coord;
    }

    public User(int id, String uname, int phone, GeoPosition coord) {
        this.id = id;
//        this.userType = userType;
        this.uname = uname;
        this.phone = phone;
        this.coord = coord;
    }

    private int id;
//    private String userType;
    private String uname;
    private int phone;
    private GeoPosition coord;

}
