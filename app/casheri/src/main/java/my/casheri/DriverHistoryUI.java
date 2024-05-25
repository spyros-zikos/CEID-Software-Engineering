package my.casheri;

import com.mycompany.casheri.Filters;
import com.mycompany.casheri.Database;
import com.mycompany.casheri.Trip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.round;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class DriverHistoryUI extends javax.swing.JFrame {

    private List<Trip> completedTrips = new ArrayList<>();
    private Filters selectedFilters = null; 
    private boolean filter = false;
    
    public DriverHistoryUI() {
        init();
    }
    
    public DriverHistoryUI(Filters filters) {
        selectedFilters = filters;
        filter = true;
        init();
    }
    
    private void init() {
        initComponents();
        //Design
        this.setSize(296,455);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.decode("#FFFFBA"));
        //End
        getCompletedTrips();
        displayTable();
    }
    
    private void getCompletedTrips() {
        Connection con = (new Database()).con();    
        //the value of driver_id is configured manually, TODO: modify it based on login (retrieve id from login form)
        String query = null;
        if (filter) {
            query = "select * from trip where driver_id = 1 and status = 'completed'" +
            " and date_time between '" + selectedFilters.getStartDateTime() + "' and '" + selectedFilters.getEndDateTime() + 
            "' and duration between '" + selectedFilters.getMinDuration() + "' and '" + selectedFilters.getMaxDuration() +
            "' order by date_time asc";
        } else {
            query = "select * from trip where driver_id = 1 and status = 'completed' order by date_time asc";
        }
      
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            while (rs.next()) {
                List<Float> temp = getTripProfit(rs.getInt("id"),con);
                int passengers = round(temp.get(0));
                float profit = temp.get(1);
                boolean val = false;
                if (filter) {
                    boolean val1 = (selectedFilters.getMaxProfit() >= profit && profit >= selectedFilters.getMinProfit());
                    boolean val2 = (selectedFilters.getMaxPassengers() >= passengers && passengers >= selectedFilters.getMinPassengers());
                    val = val1 && val2;
                    if (val) {
                        completedTrips.add(new Trip(rs.getInt("id"), rs.getInt("driver_id"), rs.getString("date_time"),
                                                                        rs.getString("duration"), passengers, profit));
                    }
                } else {
                    completedTrips.add(new Trip(rs.getInt("id"), rs.getInt("driver_id"), rs.getString("date_time"),
                                                                        rs.getString("duration"), passengers, profit));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    private List<Float> getTripProfit(int id, Connection con) {
        String query = "select SUM(cost), COUNT(id) from ride where trip_id = " + id;
        List<Float> values = new ArrayList<>();
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            values.add(rs.getFloat("COUNT(id)"));
            values.add(rs.getFloat("SUM(cost)"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      return values;
    }
    
    private void displayTable(){
        if(completedTrips.size() < 1) {
            jLabel1.setVisible(true);
            jLabel1.setText("NO TRIP FOUND!");
        } else {
            jLabel1.setVisible(false);
            DefaultTableModel model = (DefaultTableModel)statsTable.getModel();
            Object []  row = new Object [5];
            for(int i=0; i<completedTrips.size(); i++){
                row[0]= completedTrips.get(i).getDatetime();
                row[1]= completedTrips.get(i).getDuration();
                row[2]= completedTrips.get(i).getPassengers();  
                row[3]= completedTrips.get(i).getProfit();

                model.addRow(row);
            }
        }
    }
    
    private String calculateTotalDuration() {
        List<String> timeStrings = new ArrayList<>();
        for(int i=0; i<completedTrips.size(); i++) {
            timeStrings.add(completedTrips.get(i).getDuration());
        }
        
        Duration totalDuration = Duration.ZERO;

        for (String timeString : timeStrings) {
            LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
            Duration duration = Duration.between(LocalTime.MIN, time);
            totalDuration = totalDuration.plus(duration);
        }

        long totalSeconds = totalDuration.getSeconds();
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calculateButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        filtersButton = new javax.swing.JButton();
        barChartPane = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        statsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 245, 245));

        calculateButton.setBackground(new java.awt.Color(236, 218, 61));
        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        downloadButton.setBackground(new java.awt.Color(236, 218, 61));
        downloadButton.setText("Download");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        menuButton.setBackground(new java.awt.Color(236, 218, 61));
        menuButton.setText("Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        filtersButton.setBackground(new java.awt.Color(236, 218, 61));
        filtersButton.setText("Filters");
        filtersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtersButtonActionPerformed(evt);
            }
        });

        barChartPane.setBackground(new java.awt.Color(255, 250, 228));

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout barChartPaneLayout = new javax.swing.GroupLayout(barChartPane);
        barChartPane.setLayout(barChartPaneLayout);
        barChartPaneLayout.setHorizontalGroup(
            barChartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barChartPaneLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        barChartPaneLayout.setVerticalGroup(
            barChartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barChartPaneLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jScrollPane1.setPreferredSize(new java.awt.Dimension(280, 200));

        statsTable.setBackground(new java.awt.Color(255, 250, 228));
        statsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DateTime", "Duration", "Passengers", "Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        statsTable.setPreferredSize(new java.awt.Dimension(290, 100));
        statsTable.setSelectionBackground(new java.awt.Color(153, 153, 0));
        statsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(statsTable);
        if (statsTable.getColumnModel().getColumnCount() > 0) {
            statsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(menuButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filtersButton)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(calculateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(barChartPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtersButton)
                    .addComponent(menuButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(barChartPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(calculateButton)
                    .addComponent(downloadButton))
                .addContainerGap(81, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filtersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtersButtonActionPerformed
        // TODO add your handling code here:
        if(filter) 
            new FiltersUI(selectedFilters).setVisible(true);
        else
            new FiltersUI().setVisible(true);
        
        this.setVisible(false);
    }//GEN-LAST:event_filtersButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        // TODO add your handling code here:
        new casheriUI().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_menuButtonActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        // TODO add your handling code here:
        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        float sumProfit = 0;
        int sumPassengers = 0;
                
        for(int i=0; i<completedTrips.size(); i++) {
            sumProfit += completedTrips.get(i).getProfit();
        }
        for(int i=0; i<completedTrips.size(); i++) {
            sumPassengers += completedTrips.get(i).getPassengers();
        }
        
        barChartData.setValue(sumProfit,"","Profit");
        barChartData.setValue(sumPassengers,"","Passengers");
        barChartData.setValue(completedTrips.size(),"","Trips");

        String range = null;
        if (filter)
            range = selectedFilters.getStartDateTime()+" - "+ selectedFilters.getEndDateTime();
        else
            range = "All Time";
        
        JFreeChart barChart = ChartFactory.createBarChart(range, "Duration: " + calculateTotalDuration(), "", barChartData,PlotOrientation.VERTICAL, false,true,false);
        
        barChart.getTitle().setFont(new Font("SansSerif", Font.PLAIN, 12));
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, new Color(159, 159, 81));
        barChart.getCategoryPlot().setRenderer(renderer);
        
        CategoryPlot barchrt = barChart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.BLACK);
  
        ChartPanel chartPanel  = new ChartPanel(barChart);
        chartPanel.setSize(260,135);
        barChartPane.removeAll();
        barChartPane.add(chartPanel);//, BorderLayout.CENTER
        barChartPane.updateUI();
    }//GEN-LAST:event_calculateButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        // TODO add your handling code here:      
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as");
        fileChooser.setPreferredSize(new Dimension(290,420));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fileWriter = new FileWriter(fileChooser.getSelectedFile())) {
                TableModel model = statsTable.getModel();

                for (int i = 0; i < model.getColumnCount(); i++) {
                    fileWriter.write(model.getColumnName(i) + "\t");
                }
                fileWriter.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        fileWriter.write(model.getValueAt(i, j).toString() + "\t");
                    }
                    fileWriter.write("\n");
                }
                
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_downloadButtonActionPerformed

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
            java.util.logging.Logger.getLogger(DriverHistoryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DriverHistoryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DriverHistoryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DriverHistoryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DriverHistoryUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barChartPane;
    private javax.swing.JButton calculateButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JButton filtersButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton menuButton;
    private javax.swing.JTable statsTable;
    // End of variables declaration//GEN-END:variables
}
