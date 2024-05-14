/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package my.casheri;

import org.jxmapviewer.viewer.DefaultTileFactory;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import javax.swing.ImageIcon;

import com.mycompany.casheri.Database;
import com.mycompany.casheri.Point;
import com.mycompany.casheri.Trip;

public class StartTripUI extends javax.swing.JFrame {

    public StartTripUI() {
        initComponents();
        ArrayList<Point> points = getPoints();
        initMap();
        addPins(points);
    }

    private ArrayList<Point> getPoints(){
        
        ArrayList<Point> points = new ArrayList<>();
        Connection con = (new Database()).con();
        //here the driver is manually configured->driver_id=1
        String query = "select * from trip where date_time >= NOW()and driver_id = 1 order by date_time asc";
        Statement st;
        ResultSet rs;
        
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            Point point_start;
            Point point_end;
            while(rs.next()){
//                trip = new Trip(rs.getInt("id"), rs.getInt("driver_id"), rs.getString("date_time"), rs.getFloat("start_latitude"),
//                        rs.getFloat("start_longitude"), rs.getFloat("end_latitude"), rs.getFloat("end_longitude"), rs.getFloat("cost"));
                point_start = new Point("start", new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));
                point_end = new Point("end", new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));

                points.add(point_start);
                points.add(point_end);   
                
                break;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return points;
    }
    
    private void initMap() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer1.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(38.2483182,21.7532223);
        jXMapViewer1.setAddressLocation(geo);
        jXMapViewer1.setZoom(12);
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer1);
        jXMapViewer1.addMouseListener(mm);
        jXMapViewer1.addMouseMotionListener(mm);
        jXMapViewer1.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer1));
    }
    
    private void addPins(ArrayList<Point> points) {
        Set<Waypoint> waypoints = new HashSet<>();

        // Add coordinates for pins
        for (Point point : points) {
            GeoPosition position = point.getCoord(); 
            // Create waypoints for pins
            Waypoint waypoint = new DefaultWaypoint(position);
            // Add waypoints to the list
            waypoints.add(waypoint);
        }
        
        // Create a WaypointPainter to paint the waypoints on the map
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        jXMapViewer1.setOverlayPainter(waypointPainter);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapViewer1 = new org.jxmapviewer.JXMapViewer();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jXMapViewer1Layout = new javax.swing.GroupLayout(jXMapViewer1);
        jXMapViewer1.setLayout(jXMapViewer1Layout);
        jXMapViewer1Layout.setHorizontalGroup(
            jXMapViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );
        jXMapViewer1Layout.setVerticalGroup(
            jXMapViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );

        jButton1.setText("Start Trip");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(248, 248, 248))
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jXMapViewer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jXMapViewer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jButton1)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private org.jxmapviewer.JXMapViewer jXMapViewer1;
    // End of variables declaration//GEN-END:variables
}
