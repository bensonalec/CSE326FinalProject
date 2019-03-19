package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
	//Connect to the database, then start reading in socs and then verify login.
	
	//start connection for the database
	
	//-------------------------------------------
	
	
	//Send query to check username and password

    final static String dbURL = "jdbc:mysql://localhost:3306/cse326";
    final static String username = "server";
    final static String password = "2468135790";
	
	//--------------------------------------------
	//check return statement
	public static void main(String[] args) {

		//Socket soc;
		//ServerSocket serv;
		//BufferedReader in;=
			/*
			try {
				serv = new ServerSocket(5000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//while(true) { 
			//only takes one connection for test purposes
				try {
					//accept new user.
					soc = serv.accept();
					System.out.println("Socket accepted");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Get input stream
				try {
					in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] temp = null;
				try {
					temp = in.readLine().split(" ");
				*/	
					String[] temp = {"bensonalec", "sample"};
					System.out.println("username = " + temp[0]);
					System.out.println("password = " + temp[1]);
				//} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
		        try {
		            //LOAD PROPER DRIVERS
		            Class.forName("com.mysql.jdbc.Driver");
		            //MAKE A CONNECTION
		            Connection connectionconnection = DriverManager.getConnection(dbURL, username, password);
		            if(connectionconnection != null) {
		                
		                //THE QUERY TO BE EXECUTED
		                String userQuery = "SELECT * FROM Users WHERE UserName=? AND UserPassword=?;";
		                //CREATE  A NEW STATEMENT
		                PreparedStatement ps = connectionconnection.prepareStatement(userQuery);
		                ps.setString(1, temp[0]);
		                ps.setString(2, temp[1]);

		                ResultSet rs = ps.executeQuery();

		                rs.afterLast();

		                if (rs.getRow() > 0){
		                	// CORRECT LOGIN INFO
		                	System.out.println("Password and Username Correct");
		                }

		                //CHECKS IF THE QUERY WAS NULL OR NOT
		                if(rs.next()) {
		                    //GETS THE USERNAME OF FIRST RETURNED ROW
		                    System.out.println(rs.getString("UserName"));
		                    System.out.println("Succesful query!");
		                }
		                //CLOSES STATEMENT
		                ps.close();
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
		        } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			//}
		}

}