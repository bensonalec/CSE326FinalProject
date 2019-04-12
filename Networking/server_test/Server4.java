package com;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Server4 {
	
    //Port number that the server will listen on
	static int port = 5000;
	
	//This will map the client to its tcp  socket
	Map<String, ConcurrentHashMap<Client, Socket>> connected_users = new ConcurrentHashMap<String, ConcurrentHashMap<Client, Socket>>();
	
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
		
		
		Server4 serv = new Server4();
		//start server
		serv.start_server(s);

		
	}

	
/*
 * Below will start the server and all threads.	
 */
	
	
void start_server(ServerSocket serv) {

		//start each thread.
	
		//Creates the 3 threads that will be always running in the server
	Connector a = new Connector(serv);
	//Listener b = new Listener();
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
	//b.start();
	c.start();
}



	/*
	 * Thread code begins here---------------------------------------------------------------------------------------------
	 */

	
	/*
	 * This thread will wait for new connections and then from there create local
	 * client for the instance, along with verifying credentials on the server
	 */
	
	private class Connector extends Thread{
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
					
					String temp_UTF = new String(temp, "UTF8");
					
					
					
					if(temp_UTF.startsWith("REGISTER")){
						Client reg_user = new Client(temp);
						//close connection.
						if(reg_user.register()) {
							System.out.println("Registration complete");
							String sec2 = "SUCCESS";
							ack.writeInt(sec2.length());
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ack.write(sec2.getBytes(StandardCharsets.UTF_8));
						}
						else {
							System.out.println("Registration failed");
							String fal2 = "FAILURE";
							ack.writeInt(fal2.length());
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ack.write(fal2.getBytes(StandardCharsets.UTF_8));
						}
						
						soc.close();
					}				
					else if(temp_UTF.startsWith("LOGIN")){
						Client current = new Client(temp_UTF);

					     if(current.verify()) { 
							//passed verification
						
									//Add new user to list of current connected users
								if(connected_users.containsKey(current.client)) {
									connected_users.get(current.client).put(current, soc);
								}
								else {
									connected_users.put(current.client, new ConcurrentHashMap<Client, Socket>());
									new Listener(current.client).start();
								}
								
									//send accept frame
								String success = "SUCCESS" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password;

								
								System.out.println(success);
								ack.writeInt(success.length());
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								ack.write(success.getBytes(StandardCharsets.UTF_8));
					     }
					     else { 
									//failed verification
								
									//send failure frame.
								String fail = "FAILURE" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password;
								
								System.out.println(fail);
								ack.writeInt(fail.length());
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								ack.write(fail.getBytes(StandardCharsets.UTF_8));
								soc.close();
						}
				}
				else {
					System.out.println("Frame did not contain LOGIN or REGISTER");
					String fail2 = "FAILURE";
					
					System.out.println(fail2);
					ack.writeInt(fail2.length());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ack.write(fail2.getBytes(StandardCharsets.UTF_8));
					soc.close();
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
	
	
	private class Listener extends Thread{
		Socket temp;
		String user;
		byte[] buf;
		DataInputStream in;
		Listener(String a){
			user = a;
		}
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */

			while(turnoff) {
				//for(Entry<String, ConcurrentHashMap<Client, Socket>> entry : connected_users.entrySet()) {
					for (Entry<Client, Socket> entry2 : connected_users.get(user).entrySet()) {
						temp = entry2.getValue();
						
						
						  //remove socket if it is closed
						if(entry2.getValue().isClosed()) {
							System.out.println("Removing device = " + entry2.getKey().device);
							connected_users.get(entry2.getKey().client).remove(entry2.getKey());
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
									stack.add(new Frame(entry2.getKey() ,buf));
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				//}
			}

		}
	}
	
	/*
	 * This thread will take frames from the stack. Then it will figure out which 
	 * client the frame will need to be sent and then make threads to send the frame
	 */
	
	
	private class Grouper extends Thread{

		public void run() {
			/*find other devices in groups*/
			Frame current;
			Client u = null;			
			//Checks if there are frames to be sent
			while(turnoff) {
				if(!stack.isEmpty()) {
					
					System.out.println("Grouping");
					//pop from stack
					current = stack.remove();
					u = current.user;
					
					//search for group member
					for (Entry<Client, Socket> entry : connected_users.get(u.client).entrySet()) {
						
						//this if state check to see if usernames are equal but device name are different.
						
						if(entry.getKey().client.equals(u.client) && !entry.getKey().device.equals(u.device)) {
							
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
	private class Sender extends Thread{
		Frame current;
		Socket destination;
		DataOutputStream out;
		
		
		//Constructor just take the frame.
		Sender(Frame a){
			
			//save current client that pack is being sent to used later aswell
			current = a;
			//get destination socket
			destination = connected_users.get(current.user.client).get(current.user);
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
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.write(current.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

