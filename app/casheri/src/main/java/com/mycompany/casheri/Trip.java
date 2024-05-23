package com.mycompany.casheri;

import java.sql.Connection;
import org.jxmapviewer.viewer.GeoPosition;

public class Trip {

    private int id;
    private int driverId;
    private String datetime;
    private String duration;
    private GeoPosition coordStart;
    private GeoPosition coordEnd;
    private int passengerCapacity;
    private int repeatTrip;
    private float cost;
    private int passengers;
    private float profit;
    
    public Trip(int id, int driver_id, String datetime, float cost) {
        this.id = id;
        this.driverId = driver_id;
        this.datetime = datetime;
        this.cost = cost;
    }
    
    public Trip(int id, int driver_id, String datetime, String duration, int passengers, float profit) {
        this.id = id;
        this.driverId = driver_id;
        this.datetime = datetime;
        this.duration = duration;
        this.passengers = passengers;
        this.profit = profit;
    }
    
    public Trip() {
        
    }
    
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getRepeatTrip() {
        return repeatTrip;
    }

    public void setRepeatTrip(int repeatTrip) {
        this.repeatTrip = repeatTrip;
    }    
    
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
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
    
    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public void storeTrip() {
        Connection con = (new Database()).con();
 
        String query = "INSERT INTO trip(id,driver_id,date_time,duration,start_latitude,start_longitude,end_latitude,end_longitude,passenger_capacity,repeat_trip,cost)" +
                        "VALUES(NULL,"+driverId+",'" +datetime+"', '"+duration+"'," +coordStart.getLatitude()+","+coordStart.getLongitude()+"," +
                        coordEnd.getLatitude()+","+ coordEnd.getLongitude()+","+ passengerCapacity+","+repeatTrip+","+cost+")";

        try{
            con.createStatement().executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public String getTripInfo() {
        String[] str = getDatetime().split("\\s+");
        
        return "<html>Date: " + str[0] +
                "<br>Time: " + str[1] +
                "<br>Trip Cost: " + getCost() + "<html>";
    }
}
