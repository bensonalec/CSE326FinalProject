package com;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class Server5 {
	
    //Port number that the server will listen on
	static int port = 5000;
	
	//This will map the client to its tcp  socket
	Map<String, ConcurrentHashMap<Client, Socket>> connected_users = new ConcurrentHashMap<String, ConcurrentHashMap<Client, Socket>>();
	
	//This stack will hold frame that need to be sent to group of users
	//ConcurrentLinkedQueue<Frame> stack = new ConcurrentLinkedQueue<Frame>();
	
	//Used in shutdown hook to turn off while look in threads
	volatile boolean turnoff = true;
	
	
	public static void main(String[] arg) throws NoSuchAlgorithmException {
		ServerSocket s = null;
		
		//Create server socket
		try {
			s = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Server5 serv = new Server5();
		//start server
		serv.start_server(s);

		
	}

	
/*
 * Below will start the server and all threads.	
 */
	
	
	void start_server(ServerSocket serv) {

	//creates thread that will handle new connections to server
	Connector a = new Connector(serv);

	
	
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
					byte[] temp;
					
					//read in initial login/register frame for new user.
					
					try {
						size = in.readInt();
						temp = new byte[size];
						in.read(temp); //reads in frame
					}
					catch(EOFException e) {
						continue;
					}
					
					System.out.println(temp); //prints login frame.
					
					
					DataOutputStream ack = new DataOutputStream(soc.getOutputStream());
					
					String temp_UTF = new String(temp, "UTF8");
					
					
					
					if(temp_UTF.startsWith("REGISTER")){
						Client reg_user = new Client(temp);

						//attempt to register user.
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
							//send success frame
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
							//send failure frame
							ack.write(fal2.getBytes(StandardCharsets.UTF_8));

						}
						//close socket after registration so that the user can login. No auto login on registration.
						soc.close();
					}				
					else if(temp_UTF.startsWith("LOGIN")){
						Client current = new Client(temp_UTF);
						 //check if usernamea and password are true.
					     if(current.verify()) { 
							//passed verification
						
									//Add new user to list of current connected users
								if(connected_users.containsKey(current.client)) {
									//if username thread/entry in hashmap already created then just add to hashmap
									connected_users.get(current.client).put(current, soc);
								}
								else {
									//Create new map for this username
									connected_users.put(current.client, new ConcurrentHashMap<Client, Socket>());
									connected_users.get(current.client).put(current, soc);
									//start listener thread for this username
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
								//send success frame
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
								//send failure frame
								ack.write(fail.getBytes(StandardCharsets.UTF_8));
								//close connection
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
		
		private void send_users(byte[] a, String b) {
			System.out.print("Grouping");
			
			//creates sending thread for all users in group other that itself
			for (Entry<Client, Socket> entry2 : connected_users.get(user).entrySet()) {
				if(!b.equals(entry2.getKey().device)) {	
					System.out.println("Starting sender");
					//thread to send frame
					new Sender(new Frame(entry2.getKey(), a)).start();					
				}
			}
		}
		
		
		
		public void run() {
			/* Goes through sockets and check if new connection*/
			/* also cleans up closed sockets */
			System.out.println("Starting listener for user = " + user);

			while(turnoff) {
				
				//this handles cleanup if all clients signed out from this username.
				if(connected_users.get(user).isEmpty()) {
					connected_users.remove(user);
					System.out.println("remover listener for user = " + user);
					break;
				}
				
				for (Entry<Client, Socket> entry2 : connected_users.get(user).entrySet()) {
					temp = entry2.getValue();
				
					
					  //create new data input stream
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
							System.out.println("size to read" + size);
							buf = new byte[size];
							
							in.read(buf);
						
							System.out.println("Size on input = " + buf.length);
							
							//send to function to create sending threads to other clients logged in with this username
							send_users(buf, entry2.getKey().device);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				//return;
			}
			//Write packet to client
			try {
				System.out.println( "Time = " + java.time.LocalTime.now() + "Packet =" + current.packet);
				System.out.println(current.packet.length);
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
				
				//This means that the connection is closed. Then we must remove client from its usernamehashmap
				//part of cleanup process
				
				System.out.println("Removing device = " + current.user.device);
				connected_users.get(current.user.client).remove(current.user);
			}
		}
	}

}

