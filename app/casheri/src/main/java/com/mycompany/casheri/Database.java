package com.mycompany.casheri;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private ResultSet dummyRs;
    public Connection con;
    
    public Database() {
        con = con();
        try { dummyRs = con.createStatement().executeQuery("select id from user"); }
        catch (Exception ex) { ex.printStackTrace(); }
    }
    
    public Connection con() {
        String url = "jdbc:mysql://localhost:3306/casheri";
        String username = "root";
        String password = "root";
        
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
    
    public ResultSet executeQuery(String query) {
        try {
            return con.createStatement().executeQuery(query);
            //rs.getFloat("reviews_rank") //rs.getInt("total_trips");
        } catch (Exception ex) {
            ex.printStackTrace();
            return dummyRs;
        }
    }
        
    public void executeUpdate(String query) {
        try{ con.createStatement().executeUpdate(query); }
        catch(Exception ex){ ex.printStackTrace(); }
    }
}
