package com.mycompany.casheri;

import java.sql.Connection;
import org.jxmapviewer.viewer.GeoPosition;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ride {
    private int id;
    private int tripId;
    private int driverId;
    private int passengerId;
    private String datetime;
    private String duration;
    private GeoPosition coordStart;
    private GeoPosition coordEnd;
    private float cost;
     private String status;
    
     public Ride(int id,int trip_id, int driver_id, int passenger_id, String datetime, float cost) {
        this.id = id;
        this.tripId = trip_id;
        this.driverId = driver_id;
        this.passengerId = passenger_id;
        this.datetime = datetime;
        this.cost = cost;
    }
     
         public Ride() {
        
    }
         
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
  
    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }
    
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    
    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
    

    public GeoPosition getCoordStart() {
        return coordStart;
    }

    public void setCoordStart(GeoPosition coordStart) {
        this.coordStart = coordStart;
    }
    
    public GeoPosition getCoordEnd() {
        return coordEnd;
    }

    public void setCoordEnd(GeoPosition coordEnd) {
        this.coordEnd = coordEnd;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
        public void storeRide() {
        Connection con = (new Database()).con();
        datetime="2024-06-14 13:00:00";
        duration="00:32:00";
        String query = "INSERT INTO ride(id,trip_id,driver_id,passenger_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,cost)" +
                        "VALUES(NULL,"+tripId+","+driverId+",'"+passengerId+"','"+datetime+"','"+duration+"'," +coordStart.getLatitude()+","+coordStart.getLongitude()+"," +
                        coordEnd.getLatitude()+","+ coordEnd.getLongitude()+","+cost+")";

        try{
            con.createStatement().executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
        
 
        
    public String getRideInfo() {
        String[] str = getDatetime().split("\\s+");
        
        return "<html>Date: " + str[0] +
                "<br>Time: " + str[1] +
                "<br>Ride Cost: " + getCost() + "<html>";
    }
}

   