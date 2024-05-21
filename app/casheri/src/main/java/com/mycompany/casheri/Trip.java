/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.casheri;

import org.jxmapviewer.viewer.GeoPosition;

public class Trip {
        
    private String name;
    private GeoPosition coord_start;
    private GeoPosition coord_end;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public GeoPosition getCoordStart() {
        return coord_start;
    }

    public void setCoordStart(GeoPosition coord_start) {
        this.coord_start = coord_start;
    }
    
    public GeoPosition getCoordEnd() {
        return coord_end;
    }

    public void setCoordEnd(GeoPosition coord_end) {
        this.coord_end = coord_end;
    }
    

    public Trip(String name, GeoPosition coord_start, GeoPosition coord_end) {
        this.coord_end = coord_end;
        this.coord_start = coord_start;
        this.name = name;
    }
}
