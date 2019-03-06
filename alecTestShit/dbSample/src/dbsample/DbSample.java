/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbsample;

import java.sql.*;

/**
 *
 * @author bensonalec
 */
public class DbSample {

    final static String dbURL = "jdbc:mysql://localhost:3306/cse326";
    final static String username = "server";
    final static String password = "2468135790";

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        initialConnection();
    }

    public static void initialConnection() throws SQLException, ClassNotFoundException{
        try {
            //LOAD PROPER DRIVERS
            Class.forName("com.mysql.jdbc.Driver");
            //MAKE A CONNECTION
            Connection connectionconnection = DriverManager.getConnection(dbURL, username, password);
            if(connectionconnection != null) {
                
                //CREATE  A NEW STATEMENT
                Statement s = connectionconnection.createStatement();
                //THE QUERY TO BE EXECUTED
                String userQuery = "SELECT * FROM Users;";
                //EXECUTES THE QUERY DEFINED ABOVE
                ResultSet rs = s.executeQuery(userQuery);
                //CHECKS IF THE QUERY WAS NULL OR NOT
                if(rs.next()) {
                    //GETS THE USERNAME OF FIRST RETURNED ROW
                    System.out.println(rs.getString("UserName"));
                    System.out.println("Succesful query!");
                }
                //CLOSES STATEMENT
                s.close();
                //CLOSES RESULTSET
                rs.close();
                //CLOSES CONNECTION
                connectionconnection.close();
            }
            else {
                System.out.println("Failure!");
            }
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }
    /*
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(dbURL);
        Connection conn = dataSource.getConnection();
        conn.close();
        */
    }
    
}
