package waypoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class MyWaypoint extends DefaultWaypoint {

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public JButton getButton() {
        return button;
    }
    public void setButton(JButton button) {
        this.button = button;
    }

    public MyWaypoint(String name, EventWaypoint event, GeoPosition coord, String iconPath) {
        super(coord);
        this.name = name;
        initButton(event, iconPath);
    }
    
    private String name;
    private JButton button;
    
    private void initButton(EventWaypoint event, String iconPath) {
        button = new ButtonWaypoint(iconPath);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Click : " + name);
            }
        });
    }
}
