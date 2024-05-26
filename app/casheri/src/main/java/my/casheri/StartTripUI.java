
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
import javax.swing.Timer;

public class StartTripUI extends javax.swing.JFrame {

    private final Set<MyWaypoint> driverPoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Trip scheduledTrip;

    public StartTripUI() { 
        initComponents();
        this.setSize(296,455);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.decode("#FFFFBA"));
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
        String query = "select * from trip where date_time >= NOW() and driver_id = 1 and status=\'incomplete\' order by date_time asc";
        int trip_id = 0;
        MyWaypoint point_start;
        MyWaypoint point_end;

        Statement st;
        ResultSet rs;
        boolean hasResult = false;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                hasResult = true;
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
        
        if (!hasResult)
            return null;
            
        query = "select * from ride where trip_id = " + trip_id;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                point_start = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.Start, event, new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));                
                point_end = new MyWaypoint(MyWaypoint.UserType.passenger, rs.getInt("passenger_id"), MyWaypoint.PointType.End, event, new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));
                points.add(point_start);
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
    
    private void startRides() {
        Connection con = (new Database()).con();
        String query = "UPDATE RIDE SET STATUS='waiting' WHERE trip_id=" + scheduledTrip.getId();

        try{ con.createStatement().executeUpdate(query); }
        catch(Exception ex){ ex.printStackTrace(); }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        map1 = new com.mycompany.casheri.Map();
        jOptionPane1 = new javax.swing.JOptionPane();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(296, 455));

        jButton1.setBackground(new java.awt.Color(236, 218, 61));
        jButton1.setText("Start Trip");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setPreferredSize(new java.awt.Dimension(100, 100));

        jLabel2.setBackground(new java.awt.Color(255, 255, 0));
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 100));

        map1.setPreferredSize(new java.awt.Dimension(260, 300));

        javax.swing.GroupLayout map1Layout = new javax.swing.GroupLayout(map1);
        map1.setLayout(map1Layout);
        map1Layout.setHorizontalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        map1Layout.setVerticalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(236, 218, 61));
        jButton2.setText("Menu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(map1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(39, 39, 39))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 17, Short.MAX_VALUE)
                    .addComponent(jOptionPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(map1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(15, 15, 15))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 182, Short.MAX_VALUE)
                    .addComponent(jOptionPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 183, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int response = jOptionPane1.showConfirmDialog(this, "Are you sure you want to start the trip?", "Confirm Start Trip", jOptionPane1.YES_NO_OPTION, jOptionPane1.QUESTION_MESSAGE);
        if (response == jOptionPane1.YES_OPTION) {
            JDialog dialog = new JDialog();
            
            try{
                (new Database()).con().createStatement().executeUpdate("UPDATE trip SET status = 'inprogress' WHERE id = " + scheduledTrip.getId() );
            }catch(Exception ex){
                ex.printStackTrace();
            }
            showNotification(dialog);
            
            startRides();
            
            Timer timer = new Timer(1500, e->{
                dialog.dispose();
                new NavigationUI().setVisible(true);
                this.setVisible(false);
            });
            timer.setRepeats(false); 
            timer.start();
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new DriverMenuUI().setVisible(true);
        this.setVisible(false);
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
            java.util.logging.Logger.getLogger(StartTripUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartTripUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartTripUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartTripUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartTripUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JOptionPane jOptionPane1;
    private com.mycompany.casheri.Map map1;
    // End of variables declaration//GEN-END:variables
}
