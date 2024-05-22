package com.mycompany.casheri;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
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
}
