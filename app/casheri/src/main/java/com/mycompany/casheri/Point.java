
package com.mycompany.casheri;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class Point extends DefaultWaypoint {
    
    private String name;
    private GeoPosition coord;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public GeoPosition getCoord() {
        return coord;
    }

    public void setCoord(GeoPosition coord) {
        this.coord = coord;
    }
 
    public Point(String name, GeoPosition coord) {
        this.coord = coord;
        this.name = name;
    }
}
