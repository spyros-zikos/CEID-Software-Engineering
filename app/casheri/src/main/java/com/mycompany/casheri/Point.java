
package com.mycompany.casheri;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class Point extends DefaultWaypoint {
    
    private String name;
    private GeoPosition coord;
    private PointType pointType;

    
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
    
    public PointType getPointType() {
        return pointType;
    }
    
    public void setPointType(PointType type) {
        this.pointType = type;
    }
 
    public Point(String name, GeoPosition coord) {
        this.coord = coord;
        this.name = name;
    }
     
    public Point(String name, GeoPosition coord, PointType type) {
        this.coord = coord;
        this.name = name;
        this.pointType = type;
    }
    
      public static enum PointType {
        START, END
    }
}
