
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
import com.mycompany.casheri.MyPoint;
import com.mycompany.casheri.RoutingData;
import com.mycompany.casheri.RoutingService;
import java.util.List;
import waypoint.WaypointRender;


public class StartTripUI extends javax.swing.JFrame {

    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;


    public StartTripUI() {
        initComponents();
        initMap();  
//delete it        ArrayList<MyPoint> driverPoints = getTrip();
//delete it        ArrayList<MyPoint> passengerPoints = getRide(Integer.valueOf(driverPoints.get(0).getName()));
        Set<MyWaypoint> driverPoints = getTrip();
        Set<MyWaypoint> passengerPoints = getRide();//TODO: find a way to pass trip_id
//        addRoute(driverPoints);
//        driverPoints.addAll(passengerPoints);
        addPins(driverPoints);
    }
   
    private Set<MyWaypoint> getTrip() {
        Set<MyWaypoint> points = new HashSet<>();
        Connection con = (new Database()).con();
        String query = null;
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        query = "select * from trip where date_time >= NOW() and driver_id = 1 order by date_time asc";
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            MyWaypoint point_start;
            MyWaypoint point_end;
            while (rs.next()) {
                System.out.print("One More Time 1");
                point_start = new MyWaypoint(rs.getString("id"), MyWaypoint.PointType.START, event, new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")));                
                point_end = new MyWaypoint(rs.getString("id"), MyWaypoint.PointType.END, event, new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")));
               
                points.add(point_start);
                points.add(point_end);
                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return points;
    }

    private Set<MyWaypoint> getRide(int trip_id) {
        ArrayList<MyPoint> points = new ArrayList<>();
        Connection con = (new Database()).con();
        String query = null;
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        query = "select * from ride where trip_id=" + trip_id;
        
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            MyPoint point_start;
            MyPoint point_end;
            while (rs.next()) {
                System.out.print("One More Time");
                point_start = new MyPoint(rs.getString("passenger_id"), new GeoPosition(rs.getDouble("start_latitude"), rs.getDouble("start_longitude")), MyPoint.PointType.START);                
                point_end = new MyPoint(rs.getString("passenger_id"), new GeoPosition(rs.getDouble("end_latitude"), rs.getDouble("end_longitude")), MyPoint.PointType.END);
                points.add(point_start);
                points.add(point_end);
                jTextPane1.setText(point_start.getName() + point_start.getPointType());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return points;
    }  
    
    private void initMap() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        map1.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(38.2483182, 21.7532223);

        map1.setAddressLocation(geo);
        map1.setZoom(7);
        MouseInputListener mm = new PanMouseInputListener(map1);
        map1.addMouseListener(mm);
        map1.addMouseMotionListener(mm);
        map1.addMouseWheelListener(new ZoomMouseWheelListenerCenter(map1));
        event = getEvent();
    }
    
    private void addPins(Set<MyWaypoint> points) {//ArrayList<MyPoint>
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointRender();
        waypointPainter.setWaypoints(points);//waypoints
        map1.setOverlayPainter(waypointPainter);
        for (MyWaypoint d : points) {
            map1.add(d.getButton());
        }      
    }
    
    private void addRoute(ArrayList<MyPoint> points) {
        if (points.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (MyPoint p : points) {
                if (p.getPointType() == MyPoint.PointType.START) {
                    start = p.getCoord();
                } else if (p.getPointType() == MyPoint.PointType.END) {
                    end = p.getCoord();
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
                jTextPane1.setText(waypoint.getName());
            }
        };
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        map1 = new com.mycompany.casheri.Map();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout map1Layout = new javax.swing.GroupLayout(map1);
        map1.setLayout(map1Layout);
        map1Layout.setHorizontalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        map1Layout.setVerticalGroup(
            map1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );

        jButton1.setText("Start Trip");

        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(map1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(map1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(15, 15, 15))
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private com.mycompany.casheri.Map map1;
    // End of variables declaration//GEN-END:variables
}
