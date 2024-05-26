package my.casheri;

import com.mycompany.casheri.Database;
import com.mycompany.casheri.RoutingData;
import com.mycompany.casheri.RoutingService;
import com.mycompany.casheri.Trip;
import com.mycompany.casheri.User;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;


public class Navigation extends javax.swing.JFrame {

    private final Set<MyWaypoint> driverPoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Trip scheduledTrip;
    private int passengerId = -1;
    private JOptionPane jOptionPane1 = new JOptionPane();
    private int jButton3Mode;

    public Navigation() {
        initComponents();
        
        jOptionPane1.setVisible(false);
        this.getContentPane().setBackground(Color.decode("#FFFFBA"));
        initMap();
        Set<MyWaypoint> points = getScheduledTrip();
        
        String path = "src\\main\\java\\icons\\user_icon\\user" + 1 + ".png";
        jLabel1.setIcon(new ImageIcon(path));
        jLabel2.setText(getUser(1) + "<html><br>Start Point<html>");
        
        addPins(points);
        addRoute(driverPoints);
        
        jButton3.setVisible(false);
    }
    
    private Set<MyWaypoint> getScheduledTrip() {
        Set<MyWaypoint> points = new HashSet<>();
        Connection con = (new Database()).con();    
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        String query = "select * from trip where status='inprogress'";
        int trip_id = 0;
        MyWaypoint point_start;
        MyWaypoint point_end;
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                scheduledTrip = new Trip(rs.getInt("id"), rs.getInt("driver_id"), rs.getString("date_time"));
                point_start = new MyWaypoint(MyWaypoint.UserType.driver, rs.getInt("driver_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));                
                point_end = new MyWaypoint(MyWaypoint.UserType.driver, rs.getInt("driver_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));
                points.add(point_start);
                points.add(point_end);
                driverPoints.add(point_start);
                driverPoints.add(point_end);
                trip_id = rs.getInt("id");
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        query = "select * from ride where trip_id = " + trip_id + " and (status='waiting' or status='inprogress')";
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                point_start = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));                
                point_end = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));
                if (rs.getString("status").equals("waiting"))
                    points.add(point_start);
                else
                    points.add(point_end);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return points;
    }
    
    private String getUser(int id) {
        Connection con = (new Database()).con();
        String query = "select * from user where id = " + id;
        String info = null;
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            User user;
            while (rs.next()) {
                user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("phone"), new GeoPosition(rs.getDouble("latitude"), rs.getDouble("longitude")));
                if (rs.getString("type").equals("passenger")) {
                    info = "<html>Name: " + user.getName() + 
                       "<br> Phone: " + user.getPhone() +
                       getPassengerInfo(rs.getInt("id"), con) + "<html>";
                } else {
                    info = "<html>Name: " + user.getName() + 
                           "<br>Phone: " + user.getPhone() +
                           "<br>" + scheduledTrip.getTripInfo() + "<html>";
                }                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
        return info;
    }
    
    private String getPassengerInfo(int id, Connection con) {
        String query = "select * from passenger where user_id = " + id;
        Statement st;
        ResultSet rs;
        String info = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                info = "<br> Rank: " + rs.getFloat("reviews_rank") +
                       "<br> Total Trips: " + rs.getInt("total_trips");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }
    
    private void initMap() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        map1.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(38.2483182, 21.7532223);//38.2483182, 21.7532223

        map1.setAddressLocation(geo);
        map1.setZoom(8);
        MouseInputListener mm = new PanMouseInputListener(map1);
        map1.addMouseListener(mm);
        map1.addMouseMotionListener(mm);
        map1.addMouseWheelListener(new ZoomMouseWheelListenerCenter(map1));
        
        event = getEvent();
    }
    
    private void addPins(Set<MyWaypoint> points) {
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointRender();
        waypointPainter.setWaypoints(points);
        map1.setOverlayPainter(waypointPainter);
        for (MyWaypoint d : points) {
            map1.add(d.getButton());
        }      
    }
    
    private void addRoute(Set<MyWaypoint> points) {
        if (points.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (MyWaypoint p : points) {
                if (p.getPointType() == MyWaypoint.PointType.Start) {
                    start = p.getPosition();
                } else if (p.getPointType() == MyWaypoint.PointType.End) {
                    end = p.getPosition();
                }
            }
            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());               
            } else {
                routingData.clear();
            }
            map1.setRoutingData(routingData);        
        }   
    }
    
    private EventWaypoint getEvent() {
        return new EventWaypoint() {
            @Override
            public void selected(MyWaypoint waypoint) {
                String path = "src\\main\\java\\icons\\user_icon\\user" + waypoint.getId() + ".png";
                jLabel1.setIcon(new ImageIcon(path));
                jLabel2.setText(getUser(waypoint.getId()) + "<html><br>" + waypoint.getPointType() + " Point<html>");
                if (waypoint.getType()==MyWaypoint.UserType.driver) {
                    passengerId = -1;
                    jButton3Mode = 0;
                } else {
                    passengerId = waypoint.getId();
                    if (waypoint.getPointType() == MyWaypoint.PointType.Start) {
                        jButton3Mode = 1;
                    } else if (waypoint.getPointType() == MyWaypoint.PointType.End) {
                        jButton3Mode = 2;
                    }
                }
                setUpButton(jButton3Mode);
            }
        };
    }
    
    private void endTrip() {
        Connection con = (new Database()).con();
        String query = "UPDATE TRIP SET STATUS='completed' WHERE STATUS='inprogress'";

        try{ con.createStatement().executeUpdate(query); }
        catch(Exception ex){ ex.printStackTrace(); }
        
        new casheriUI().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void pickUp(int id) {
        Connection con = (new Database()).con();
        String query = "UPDATE RIDE SET STATUS='inprogress' WHERE passenger_id=" + id + " and trip_id=" + scheduledTrip.getId();

        try{ con.createStatement().executeUpdate(query); }
        catch(Exception ex){ ex.printStackTrace(); }
        
        new Navigation().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void dropOff(int id) {
        Connection con = (new Database()).con();
        String query = "UPDATE RIDE SET STATUS='completed' WHERE passenger_id=" + id + " and trip_id=" + scheduledTrip.getId();
        try{ con.createStatement().executeUpdate(query); }
        catch(Exception ex){ ex.printStackTrace(); }
        
        new Navigation().setVisible(true);
        this.setVisible(false);
        dispose();
    }
    
    private void setUpButton(int mode) {
        if (mode==1) {
            jButton3.setText("<html>Pick<br>Up</html>");
            jButton3.setVisible(true);
        } else if (mode==2) {
            jButton3.setText("<html>Drop<br>Off</html>");
            jButton3.setVisible(true);
        } else {
            jButton3.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        map1 = new com.mycompany.casheri.Map();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(296, 455));
        setSize(new java.awt.Dimension(296, 455));

        jLabel1.setText("jLabel1");
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel2.setText("jLabel2");
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 100));

        jButton1.setText("End Trip");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Menu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        map1.setPreferredSize(new java.awt.Dimension(260, 300));

        javax.swing.GroupLayout map1Layout = new javax.swing.GroupLayout(map1);
        map1.setLayout(map1Layout);
        map1Layout.setHorizontalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
        );
        map1Layout.setVerticalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );

        jButton3.setText("<html>Pick<br> Up</html>");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(map1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(314, 314, 314)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(114, 114, 114)
                    .addComponent(map1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(44, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int response = jOptionPane1.showConfirmDialog(this, "Are you sure you want to end this trip?", "Confirm", jOptionPane1.YES_NO_OPTION, jOptionPane1.QUESTION_MESSAGE);
        if (response == jOptionPane1.YES_OPTION) {
            endTrip();
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new casheriUI().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jButton3Mode==1)
            pickUp(passengerId);
        else if (jButton3Mode==2)
            dropOff(passengerId);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Navigation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Navigation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Navigation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Navigation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Navigation().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.mycompany.casheri.Map map1;
    // End of variables declaration//GEN-END:variables
}
