package waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class MyWaypoint extends DefaultWaypoint {
    private int id;
    private JButton button;
    private PointType pointType;
    private String name;
    private UserType type;

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public UserType getType() {
        return type;
    }
    public void setType(UserType type) {
        this.type = type;
    }
    
    //Kalli
    public MyWaypoint(UserType type, int id, PointType pointType, EventWaypoint event, GeoPosition coord) {
        super(coord);
        this.id = id;
        this.type = type;
        this.pointType = pointType;
        initButton(event, type, id);
    }
    
    //Spyros
    public MyWaypoint(String name, EventWaypoint event, GeoPosition coord, String iconPath) {
        super(coord);
        this.name = name;
        initButton(event, iconPath);
    }
    
    public MyWaypoint() {
    }
    
    // Spyros
    private void initButton(EventWaypoint event,String iconPath) {
        button = new ButtonWaypoint(iconPath);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.selected(MyWaypoint.this);
            }
        });
    }
    
    // Kalli
    private void initButton(EventWaypoint event, UserType type, int id) {
        if (type == UserType.driver) {
            button = new ButtonWaypoint("src\\main\\java\\icons\\pin_icon\\driver.png");
        } else if (type == UserType.passenger) {
            button = new ButtonWaypoint("src\\main\\java\\icons\\pin_icon\\user" + id + ".png");
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.selected(MyWaypoint.this);
            }
        });
    }

    public static enum PointType {
        START, END
    }
    
    public static enum UserType {
        driver, passenger
    }
}
