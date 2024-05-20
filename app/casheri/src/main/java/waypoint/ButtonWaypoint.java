package waypoint;

import java.awt.Cursor;
import java.awt.Dimension;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ButtonWaypoint extends JButton {
     public ButtonWaypoint() {
        setContentAreaFilled(false);
        setIcon(new ImageIcon("src\\main\\java\\icons\\pin_icon\\pin1.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(24, 24));
    }
}
