/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.casheri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {
    public Connection getConnection(){
        String DB_URL = "jdbc:mysql://localhost:3306/casheri";
        String USERNAME = "root";
        String PASSWORD = "root";        
              
        Connection connection;
        try{
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return connection;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
