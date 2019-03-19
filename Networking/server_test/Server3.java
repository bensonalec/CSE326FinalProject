package com;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.sql.*;


public class Server3 {
    final static String dbURL = "jdbc:mysql://localhost:3306/cse326";
    final static String username = "server";
    final static String password = "2468135790";
	static int port = 5000;
	Map<String, Socket> connected_users = new ConcurrentHashMap<String, Socket>();
	ConcurrentLinkedQueue<Frame> stack = new ConcurrentLinkedQueue<Frame>();
	volatile boolean turnoff = true;
	public static void main(String[] arg) {
		ServerSocket s = null;
		try {
			//add this in super
			s = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Server3 serv = new Server3();
		//start server
		serv.start_server(s);
		
		//need to catch sig, and close threads based upon this.
		//take user input to cause a shutdown.
		
	}
	boolean verify(String username, String password) {
		//Clean this up eventually didn't want to mess with it until it was fully tested
		String[] temp = {username, password};
		System.out.println("username = " + temp[0]);
		System.out.println("password = " + temp[1]);

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
		        	return true;
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
	void start_server(ServerSocket serv) {

		//start each thread.
		Connector a = new Connector(serv);
		Listener b = new Listener();
		Grouper c = new Grouper();
		Runtime.getRuntime().addShutdownHook(new Thread() 
	    { 
	      public void run() 
	      { 
	        try {
				serv.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        turnoff = false;
	      } 
	    }); 
		//Runtime.getRuntime().addShutdownHook(b);
		//Runtime.getRuntime().addShutdownHook(c);
		System.out.println("Starting threads");
		a.start();
		b.start();
		c.start();
	}
	public class Frame {
		String packet;
		String name;
		Frame(String name, String packet){
			this.packet = packet;
			this.name = name;
		}
	}
	public class Connector extends Thread{
		ServerSocket serv;
		Socket soc;
		DataInputStream in;
		Connector(ServerSocket a){
			serv = a;
		}
		public void run() {
			//set up server socket

			//continue to listen for new connection to the server.
			while(turnoff) {
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
					in = new DataInputStream(soc.getInputStream());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//----------------------------------------------------------
				//Verify login, if not close connection
				//and add device to table. 
				//---------------------------------------------------------
				
				//push users name and socket to hashmap that contains connected users.
				try {
					String temp = in.readUTF();
					String[] verify = temp.split(" ");
					String[] temp2 = verify[0].split("@");
					//verify here
					String username = temp2[1];
					String password = verify[1];
					DataOutputStream ack = new DataOutputStream(soc.getOutputStream());
					if(verify(username, password)) { //passed verification
						connected_users.put(verify[0], soc);
						System.out.println("new user = " + temp);
						
						//send accept frame
						ack.writeUTF("SUCCESS" + Character.toString((char) 31) + verify[0] + Character.toString((char) 31) + "EncrytedPasswordSererRecieved");

					}
					else { //failed verification
						//send failure frame.
						ack.writeUTF("FAILURE" + Character.toString((char) 31) + verify[0] + Character.toString((char) 31) + "EncrytedPasswordSererRecieved");
					}


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
	}
	public class Listener extends Thread{
		Socket temp;
		String username;
		String buf;
		DataInputStream in;
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */

			while(turnoff) {
				  for (Entry<String, Socket> entry : connected_users.entrySet()) {
					  temp = entry.getValue();
				     // if(!temp.isInputShutdown()) {
					      try {
					    	  in = new DataInputStream(temp.getInputStream());
					      } catch (IOException e1) {
					    	  // TODO Auto-generated catch block
					    	  e1.printStackTrace();
					      }
					      try {
					    	  //if string has line push notification on stack.
					    	  if(in.available() != 0) {
							      	System.out.println("Reading");
							      	buf = in.readUTF();
							      	System.out.println(buf);
							      	stack.add(new Frame(entry.getKey(),buf));
					    	  }
					      } catch (IOException e) {
							// TODO Auto-generated catch block
					    	  e.printStackTrace();
					      }
				      //}
				 }
			}

		}
	}
	public class Grouper extends Thread{
		public void run() {
			/*find other devices in groups*/
			Frame current;
			String []parse;
			//Checks if there are frames to be sent
			while(turnoff) {
				if(!stack.isEmpty()) {
					System.out.println("Grouping");
					//pop from stack
					current = stack.remove();
					parse = current.name.split("@");
					//search from group
					for (Entry<String, Socket> entry : connected_users.entrySet()) {
					      if(entry.getKey().endsWith(parse[1])) {
					    	  //create thread to send.
					    	  new Sender(new Frame(entry.getKey(), current.packet)).start();
					      }
					 }

				}
			}
			
			

			
			//create 4th threads
		}
	}
	public class Sender extends Thread{
		Frame current;
		Socket destination;
		DataOutputStream out;
		Sender(Frame a){
			current = a;
			//get destination socket
			destination = connected_users.get(current.name);
		}
		public void run() {
			//create output using printwriter
			 try {
				out = new DataOutputStream(destination.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			try {
				out.writeUTF(current.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*send to all members in group*/
		}
	}

}
