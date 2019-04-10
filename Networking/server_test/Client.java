package com;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This will hold client information such as username, password, and device type
 * Along with flag that may be used in registration.
 * I will also add the functions in here and make them private to controll database access and add security.
 */


public class Client {
		//This is used for connection to jdbc
		final static String dbURL = "jdbc:mysql://localhost:3306/cse326"
		+"?verifyServerCertificate=false"
		+"&useSSL=false"
		+"&requireSSL=false"
		+"&serverTimezone=UTC";
		final static String username = "server";
		final static String password = "2468135790";
	
	
		String client;
		String user_password;
		String device;
		String type;
		String register;
		
		//Constructor for client which parses LOGIN frame to get information. 
		//May need to be changed at some point to work with registration frame.
		
		Client(String login){
			//example frame format LOGIN(ascii 31)username@device(ascii 31)password
			
			//split on ascii 31
			System.out.println(login);
			String[] temp = login.split(Character.toString((char) 31), 3);
			user_password = temp[2].replace("\n", "");
			
			//type is used to hold either LOGIN or REGISTRATION 
			type = temp[0];
			String[] temp2 = temp[1].split("@");
			client = temp2[0];
			device = temp2[1];
			
		}
		
		/*
		 * Registration constructor.
		 */
		Client(byte[] a){
			try {
				register = new String(a, "UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean verify() {
			//Clean this up eventually didn't want to mess with it until it was fully tested
			String[] temp = {client, user_password};
			System.out.println("username = " + temp[0]);
			System.out.println("password = " + temp[1]);

			/*
			if (!temp[0].equals("testUser"))
				System.out.println("Username wont match");

			if (!temp[1].equals("testPassword"))
				System.out.println("Password wont match");
				*/

			try {
			    //LOAD PROPER DRIVERS
				Class.forName("com.mysql.jdbc.Driver");
			    //MAKE A CONNECTION
				Connection connectionconnection = DriverManager.getConnection(dbURL, username, password);
				if(connectionconnection != null) {
					
					PreparedStatement tmp = connectionconnection.prepareStatement("SELECT UserPassword FROM Users WHERE UserName=?");
					tmp.setString(1, temp[0]);
					ResultSet tmpRs = tmp.executeQuery();
					
					tmpRs.next();
					if (tmpRs.getString(1).equals(temp[1]))
						System.out.println("Passwords dont match");
					
			        //THE QUERY TO BE EXECUTED
					String userQuery = "SELECT * FROM Users WHERE UserName=? AND UserPassword=?;";
			        //CREATE  A NEW STATEMENT
					PreparedStatement ps = connectionconnection.prepareStatement(userQuery);
					ps.setString(1, temp[0]);
					ps.setString(2, temp[1]);
					
					ResultSet rs = ps.executeQuery();
			/*
			        rs.afterLast();
			
			        if (rs.getRow() > 0){
			        	// CORRECT LOGIN INFO
			        	System.out.println("Password and Username Correct");
			        	return true;
			        }
			*/
			        //CHECKS IF THE QUERY WAS NULL OR NOT
			        if(rs.next()) {
			            //GETS THE USERNAME OF FIRST RETURNED ROW
			        	System.out.println(rs.getString("UserName"));
			        	System.out.println("Succesful query!");
			        	return true;
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
					return false;
				}
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Cannot find the driver in the classpath!", e);
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return false;
		}
		
		boolean register() {
			//Clean this up eventually didn't want to mess with it until it was fully tested
			
			//registration variable.
			String user;
			String email;
			String pass;
			String []parse = register.split(Character.toString((char) 31));
			
			
			//parse for registration below
			//Frame = REGISTER(ASCII 31)UserName@DeviceName(ASCII 31)UserPassword(ASCII 31)UserEmail
			user = parse[1].split("@")[0];
			pass = parse[2];
			email = parse[3];
			System.out.println("User = " + user);
			System.out.println("pass = " + pass);
			System.out.println("email =" + email);
			
			
			
			//-----------------------------

			try {
			    //LOAD PROPER DRIVERS
				Class.forName("com.mysql.jdbc.Driver");
			    //MAKE A CONNECTION
				Connection connectionconnection = DriverManager.getConnection(dbURL, username, password);
				if(connectionconnection != null) {
					
			        //THE QUERY TO BE EXECUTED
					//String userQuery = "SELECT * FROM Users WHERE UserName=? AND UserPassword=?;";
			        //CREATE  A NEW STATEMENT
					//ps.setString(1, temp[0]);
					//ps.setString(2, temp[1]);
					
					PreparedStatement ps = connectionconnection.prepareStatement("insert into cse326.Users (UserName, UserEmail, UserPassword) values (?, ?, ?);");
					ps.setString(1, user);
					ps.setString(2, email);
					ps.setString(3, pass);
					
					//ps1 find the uid.
					//ps2 insert the device the device table. ps1 then ps2.
					
					/*
					PreparedStatement ps1 = connectionconnection.prepareStatement("Select UID from cse326.Users where UserName=?;");
					ps1.setString(1, username);
					PreparedStatement ps2 = connectionconnection.prepareStatement("insert into cse326.Devices (UID, DID, Device Name) values (?, ?, ?);");
					*/
					//ResultSet rs;
					if(ps.executeUpdate() != 0) {
						ps.close();
						return true;
					}
					else {
						ps.close();
						return false;
					}
			}
			/*
			 * Database interaction failed.
			 */
			else {
				System.out.println("Failure!");
				return false;
			}
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException("Cannot find the driver in the classpath!", e);
			} catch (SQLException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
		
		
			/*	
			 * 
			 * will be used to check pre logged in settings at some point, but for now just to see if the device is registered
			boolean device_login() {
				
			}
			
			
			//used to register a device/
			boolean device_register() {
				
			}
			*/
		
	}
