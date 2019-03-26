
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.sql.*;


public class Server3 {
	
	//This is used for connection to jdbc
	final static String dbURL = "jdbc:mysql://localhost:3306/cse326"
	+"?verifyServerCertificate=false"
	+"&useSSL=false"
	+"&requireSSL=false";
	final static String username = "server";
	final static String password = "2468135790";
	
    //Port number that the server will listen on
	static int port = 5000;
	
	//This will map the client to its tcp  socket
	Map<Client, Socket> connected_users = new ConcurrentHashMap<Client, Socket>();
	
	//This stack will hold frame that need to be sent to group of users
	ConcurrentLinkedQueue<Frame> stack = new ConcurrentLinkedQueue<Frame>();
	
	//Used in shutdown hook to turn off while look in threads
	volatile boolean turnoff = true;
	
	
	public static void main(String[] arg) {
		ServerSocket s = null;
		
		//create server socket location of this may change
		try {
			s = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Server3 serv = new Server3();
		//start server
		serv.start_server(s);

		
	}
	boolean verify(String use, String pass) {
		//Clean this up eventually didn't want to mess with it until it was fully tested
		String[] temp = {use, pass};
		System.out.println("username = " + temp[0]);
		System.out.println("password = " + temp[1]);

		try {
		    //LOAD PROPER DRIVERS
			Class.forName("com.mysql.cj.jdbc.Driver");
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
	
	boolean register(String reg) {
		//Clean this up eventually didn't want to mess with it until it was fully tested
		
		//registration variable.
		String username;
		String email;
		String password;
		String []parse = reg.split(Character.toString((char) 31));
		
		
		//parse for registration below
		//Frame = REGISTER(ASCII 31)UserName@DeviceName(ASCII 31)UserPassword(ASCII 31)UserEmail
		username = parse[1].split("@")[0];
		password = parse[2];
		email = parse[3];
		
		
		
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
				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, password);
				
				//ps1 find the uid.
				//ps2 insert the device the device table. ps1 then ps2.
				
				/*
				PreparedStatement ps1 = connectionconnection.prepareStatement("Select UID from cse326.Users where UserName=?;");
				ps1.setString(1, username);
				PreparedStatement ps2 = connectionconnection.prepareStatement("insert into cse326.Devices (UID, DID, Device Name) values (?, ?, ?);");
				*/
				ResultSet rs = ps.executeQuery();
				//ResultSet rs1 = ps1.executeQuery();
				//ResultSet rs2 = ps2.executeQuery();
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
		        	System.out.println("Registration query!");
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
	
/*	
boolean device_login() {
	
}

boolean device_register() {
	
}
*/	
	
/*
 * Below will start the server and all threads.	
 */
	
	
void start_server(ServerSocket serv) {

		//start each thread.
	
		//Creates the 3 threads that will be always running in the server
	Connector a = new Connector(serv);
	Listener b = new Listener();
	Grouper c = new Grouper();
	
	
		//This shutdown hook will close the servers socket and all for while loops in the threads to break
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

		//Start the threads
	System.out.println("Starting threads");
	a.start();
	b.start();
	c.start();
}

	/*
	 * The Frame class is used to hold the Client and data packet that was either
	 * sent from or is the destination depending on which thread it is in.
	 */
	public class Frame {
		byte[] packet;
		Client user;
		Frame(Client name, byte[] buf){
			this.packet = buf;
			this.user = name;
		}
	}
	
	/**
	 * This will hold client information such as username, password, and device type
	 * Along with flag that may be used in registration.
	 */
	
	public class Client {
		String client;
		String user_password;
		String device;
		String type;
		
		//Constructor for client which parses LOGIN frame to get information. 
		//May need to be changed at some point to work with registration frame.
		
		Client(String login){
			//example frame format LOGIN(ascii 31)username@device(ascii 31)password
			
			//split on ascii 31
			System.out.println(login);
			String[] temp = login.split(Character.toString((char) 31), 3);
			user_password = temp[2];
			
			//type is used to hold either LOGIN or REGISTRATION 
			type = temp[0];
			String[] temp2 = temp[1].split("@");
			client = temp2[0];
			device = temp2[1];
			
		}
	}
	
	/*
	 * This thread will wait for new connections and then from there create local
	 * client for the instance, along with verifying credentials on the server
	 */
	
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

					in = new DataInputStream(soc.getInputStream());
					
					//login format 
					//"LOGIN" + Character.toString((char) 31) + "username@device" + Character.toString((char) 31) + "passwords");
					//also eventually use .startswith if statement to choose between LOGIN and REGISTER.
					
					int size;
					size = in.readInt();
					byte[] temp = new byte[size];
					in.read(temp); //reads in frame
					
					System.out.println(temp); //prints login frame.
					
					
					DataOutputStream ack = new DataOutputStream(soc.getOutputStream());
					
					String temp2 = new String(temp, "UTF8");
					
					if(temp2.startsWith("REGISTER")){
						register(temp.toString());
						//close connection.
						soc.close();
					}				
					else {
						Client current = new Client(temp2);

					     if(verify(current.client, current.user_password)) { 
							//passed verification
						
									//Add new user to list of current connected users
								connected_users.put(current, soc);
								
									//send accept frame
								
								String success = "SUCCESS" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password;
								
								System.out.println(success);
								ack.writeInt(success.length());
								ack.write(success.getBytes());
					     }
					     else { 
									//failed verification
								
									//send failure frame.
								String fail = "FAILURE" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password;
								
								System.out.println(fail);
								ack.writeInt(fail.length());
								ack.write(fail.getBytes());
								soc.close();
							}
				}
					
				} catch(UTFDataFormatException a){
						//send request back because broken frame
						//a.printStackTrace();
					System.out.println("UTF data exception.");
					continue;
				}
				catch (IOException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
					
				}

			}
			
			
		}
	}
	
	
	/*
	 * Listen checks all connected to user to see if a new frame has arrived
	 */
	
	
	public class Listener extends Thread{
		Socket temp;
		String username;
		byte[] buf;
		DataInputStream in;
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */

			while(turnoff) {
				for (Entry<Client, Socket> entry : connected_users.entrySet()) {
					temp = entry.getValue();
					
					
					  //remove socket if it is closed
					if(entry.getValue().isClosed()) {
						System.out.println("Removing device = " + entry.getKey().device);
						connected_users.remove(entry.getKey());
					}
					
					  //create new data input stream
					else {
						try {
							in = new DataInputStream(temp.getInputStream());
						} catch (IOException e1) {
					    	  // TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					      //Check if socket has info and if so push to stack
						
						try {
					    	  //if string has line push notification on stack.
							if(in.available() != 0) {
								System.out.println("Reading");
								
							      	//read in data
								int size = in.readInt();
								
								buf = new byte[size];
								
								in.read(buf);
							      	//push on stack to be sent
								stack.add(new Frame(entry.getKey() ,buf));
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
	}
	
	/*
	 * This thread will take frames from the stack. Then it will figure out which 
	 * client the frame will need to be sent and then make threads to send the frame
	 */
	
	
	public class Grouper extends Thread{
		public void run() {
			/*find other devices in groups*/
			Frame current;
			
			//Checks if there are frames to be sent
			while(turnoff) {
				if(!stack.isEmpty()) {
					
					System.out.println("Grouping");
					//pop from stack
					current = stack.remove();
					
					//search for group member
					for (Entry<Client, Socket> entry : connected_users.entrySet()) {
						
						//this if state check to see if usernames are equal but device name are different.
						
						if(entry.getKey().client.equals(current.user.client) && !entry.getKey().device.equals(current.user.device)) {
							
							//create thread to send make new frame for destination user
							System.out.println("Sending frame");
							//if we have weird issues were fail
							if(!entry.getValue().isClosed()) {
								new Sender(new Frame(entry.getKey(), current.packet)).start();
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * This class will take a packet and send it to this client listed within the frame
	 */
	public class Sender extends Thread{
		Frame current;
		Socket destination;
		DataOutputStream out;
		
		
		//Constructor just take the frame.
		Sender(Frame a){
			
			//save current client that pack is being sent to used later aswell
			current = a;
			//get destination socket
			destination = connected_users.get(current.user);
		}
		
		
		
		public void run() {
			
			//create output stream
			try {
				out = new DataOutputStream(destination.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			//Write packet to client
			try {
				out.writeInt(current.packet.length);
				out.write(current.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
