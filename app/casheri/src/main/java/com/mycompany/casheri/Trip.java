package com.mycompany.casheri;

import java.sql.Connection;
import org.jxmapviewer.viewer.GeoPosition;

public class Trip {

    private int id;
    private int driverId;

    private String datetime;
    private GeoPosition coordStart;
    private GeoPosition coordEnd;
    private float cost;
    
    
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

    public void setCoordStart(GeoPosition coord_start) {
        this.coordStart = coord_start;
    }
    
    public GeoPosition getCoordEnd() {
        return coordEnd;
    }

    public void setCoordEnd(GeoPosition coord_end) {
        this.coordEnd = coord_end;
    }

    public void storeTrip() {
        Connection con = (new Database()).con();
        String query = String.format("INSERT INTO TRIP VALUES (NULL, %d, '%s', %f, %f, %f, %f, %f) ",
            driverId, datetime, coordStart.getLatitude(), coordStart.getLongitude(),
            coordStart.getLatitude(), coordStart.getLongitude(), cost);
        try{
            con.createStatement().executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
