/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package my.casheri;
import waypoint.MyWaypoint;
import org.jxmapviewer.viewer.DefaultTileFactory;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import com.mycompany.casheri.Database;
import waypoint.EventWaypoint;
import com.mycompany.casheri.RoutingData;
import com.mycompany.casheri.RoutingService;
import com.mycompany.casheri.Trip;
import com.mycompany.casheri.User;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.ImageIcon;
import waypoint.WaypointRender;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ViewLiveTripRouteUI extends javax.swing.JFrame {

    private Integer passengerId;
    private Integer rideId;
    private final Set<MyWaypoint> driverPoints = new HashSet<>();
    private final Set<MyWaypoint> ridePoints = new HashSet<>();
    private final Set<MyWaypoint> endPoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Trip scheduledTrip;
    public Integer flag=0;
    
    
    public ViewLiveTripRouteUI(Integer rideId,Integer passengerId) {
        this.passengerId = passengerId;
        this.rideId = rideId;
        initComponents();
        this.setSize(296,455);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.decode("#FFFFBA"));
        jButton2.setVisible(false);
        jLabel3.setVisible(false);
        initMap();  
        Set<MyWaypoint> points = getScheduledTrip();
        
        
        
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        String path = "src\\main\\java\\icons\\user_icon\\user" + 1 + ".png"; 
                jLabel1.setIcon(new ImageIcon(path));
        if (points == null) {
            jLabel2.setText("<html><br>NO SCEDULED TRIP FOUND!<html>");  
            jButton1.setVisible(false);
        } else {            
            jLabel2.setText(getUser(1) + "<html><br>Start Point<html>");
            addPins(points);
            addRoute(driverPoints);  
        } 
    }
   
    private Set<MyWaypoint> getScheduledTrip() {
        Set<MyWaypoint> points = new HashSet<>();
        Connection con = (new Database()).con();    
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        String query = "SELECT trip.*, ride.* " +
               "FROM trip " +
               "INNER JOIN ride ON trip.id = ride.trip_id " +
               "WHERE trip.driver_id = 1 AND " +
               "trip.status = 'inprogress' " +
               "ORDER BY trip.date_time ASC";

        int trip_id = 0;
        MyWaypoint point_start_trip;
        MyWaypoint point_end_trip;
        MyWaypoint point_start_ride;
        MyWaypoint point_end_ride;

        Statement st;
        ResultSet rs;
        boolean hasResult = false;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                hasResult = true;
                scheduledTrip = new Trip(rs.getInt("id"), rs.getInt("driver_id"), rs.getString("date_time"));
                point_start_trip = new MyWaypoint(MyWaypoint.UserType.car, rs.getInt("driver_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("trip.start_latitude"), rs.getDouble("trip.start_longitude")));                
                point_end_trip = new MyWaypoint(MyWaypoint.UserType.driver, rs.getInt("driver_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("ride.start_latitude"), rs.getDouble("ride.start_longitude")));
                point_start_ride = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("ride.start_latitude"), rs.getDouble("ride.start_longitude")));                
                point_end_ride = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("ride.end_latitude"), rs.getDouble("ride.end_longitude")));
                
                if(flag==0)points.add(point_start_trip);
                else if(flag==1)points.add(point_start_ride);
                else points.add(point_end_ride);
                    
               
                driverPoints.add(point_start_trip);
                driverPoints.add(point_end_trip);
                ridePoints.add(point_start_ride);
                ridePoints.add(point_end_ride);
                endPoints.add(point_end_ride);
                trip_id = rs.getInt("id");

                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        if (!hasResult)
            return null;
            
        query = "SELECT * FROM ride WHERE trip_id = " + trip_id + " AND passenger_id = " + passengerId;

        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                point_start_ride = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));                
                point_end_ride = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));
                if(flag==2)
                points.add(point_end_ride);
                else points.add(point_start_ride);
                
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
            }
        };
    }
    
    private static void showNotification(JDialog dialog) {
        dialog.setUndecorated(true); 
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Passengers will be notified", SwingConstants.CENTER);
        messageLabel.setOpaque(true);
        messageLabel.setBackground(new Color(255, 255, 224)); 
        messageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dialog.add(messageLabel, BorderLayout.CENTER);
        dialog.setSize(250, 100);
        dialog.setLocationRelativeTo(null); 
        dialog.setVisible(true);
    }

private void refreshMap() {
    // Increase flag state and cycle it if necessary
    flag = (flag + 1) % 3; // This will cycle flag values between 0, 1, and 2
    
    map1.removeAll();  // Clear the map for new data
    initMap();  // Reinitialize map settings

 
    
    // Decide what to show based on the flag
      // Show route of driver reaching to passenger
     if (flag == 1) {  // Show passenger's entire route
        addRoute(ridePoints);  // Show route of passenger from start to finish
        Set<MyWaypoint> points = getScheduledTrip();
        addPins(points);
    } else if (flag == 2) {
        // Show only passenger's end location
        addRoute(endPoints);
        Set<MyWaypoint> points = getScheduledTrip();
        points.add(new MyWaypoint(MyWaypoint.UserType.passenger, passengerId, MyWaypoint.PointType.End, event, getPassengerEndPosition()));
        addPins(points);
        
        jButton1.setVisible(false);
        jButton2.setVisible(true);
        jLabel3.setVisible(true);
    }

      // Add the relevant waypoints to the map
    map1.repaint();  // Refresh the map to display changes
}

private GeoPosition getPassengerEndPosition() {
    // This method assumes you fetch the last known or end position of the passenger's journey
    // You need to implement this based on how you fetch data, for example:
    return new GeoPosition(38.720, 21.350);  // Dummy coordinates
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        map1 = new com.mycompany.casheri.Map();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        map1.setPreferredSize(new java.awt.Dimension(260, 300));

        javax.swing.GroupLayout map1Layout = new javax.swing.GroupLayout(map1);
        map1.setLayout(map1Layout);
        map1Layout.setHorizontalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );
        map1Layout.setVerticalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
        );

        jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel2.setBackground(new java.awt.Color(255, 255, 0));
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 100));

        jLabel3.setText("You have arrived!");

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(map1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(map1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jButton2)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        refreshMap();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewLiveTripRouteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewLiveTripRouteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewLiveTripRouteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewLiveTripRouteUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewLiveTripRouteUI(0,0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private com.mycompany.casheri.Map map1;
    // End of variables declaration//GEN-END:variables
}
