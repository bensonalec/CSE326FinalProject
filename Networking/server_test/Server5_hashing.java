package com;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Server5_hashing {
	
    //Port number that the server will listen on
	static int port = 5000;
	
	//public static KeyGenerator gen;

	public static SecretKey sec;
	
	//Encryption key
	
	//This will map the client to its tcp  socket
	Map<String, ConcurrentHashMap<Client, Socket>> connected_users = new ConcurrentHashMap<String, ConcurrentHashMap<Client, Socket>>();
	
	//This stack will hold frame that need to be sent to group of users
	//ConcurrentLinkedQueue<Frame> stack = new ConcurrentLinkedQueue<Frame>();
	
	//Used in shutdown hook to turn off while look in threads
	volatile boolean turnoff = true;
	
	
	public static void main(String[] arg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ServerSocket s = null;
		//gen = KeyGenerator.getInstance("AES");
		
		//key valiue stored before
		byte [] key = new String("this is the key").getBytes("UTF-8");
		sec =  new SecretKeySpec(key, "AES");
		
		//create server socket location of this may change
		try {
			s = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Server5_hashing serv = new Server5_hashing();
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
	//Grouper c = new Grouper();
	
	
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
	//c.start();
}
	
	private static byte [] encrypt(byte [] data, SecretKey sec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Cipher ciph = Cipher.getInstance("AES");
		ciph.init(Cipher.ENCRYPT_MODE, sec);
		
		return ciph.doFinal(data);	
	}
	
	private static byte [] decrypt(byte [] data, SecretKey sec) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher ciph = Cipher.getInstance("AES");
		ciph.init(Cipher.DECRYPT_MODE, sec);
		
		return ciph.doFinal(data);
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
					
					String temp_UTF = null;
					try {
						temp_UTF = new String(decrypt(temp, sec), "UTF8");
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					if(temp_UTF.startsWith("REGISTER")){
						Client reg_user = new Client(temp);
						//close connection.
						if(reg_user.register()) {
							System.out.println("Registration complete");
							byte[] sec2 = null;
							try {
								sec2 = encrypt(new String("SUCCESS").getBytes("UTF-8"), sec);
							} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
									| IllegalBlockSizeException | BadPaddingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							ack.writeInt(sec2.length);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ack.write(sec2);
						}
						else {
							System.out.println("Registration failed");
							byte[] fal2 = null;
							try {
								fal2 = encrypt(new String("FAILURE").getBytes("UTF-8"), sec);
							} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
									| IllegalBlockSizeException | BadPaddingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							ack.writeInt(fal2.length);
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ack.write(fal2);

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
									connected_users.get(current.client).put(current, soc);
									new Listener(current.client).start();
								}
								
									//send accept frame
								byte[] success = null;
								try {
									success = encrypt(new String("SUCCESS" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password).getBytes("UTF-8"), sec);
								} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
										| IllegalBlockSizeException | BadPaddingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								
								//System.out.println(success);
								ack.writeInt(success.length);
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								ack.write(success);

					     }
					     else { 
									//failed verification
								
									//send failure frame.
								byte[] fail = null;
								try {
									fail = encrypt(new String("FAILURE" + Character.toString((char) 31) + current.client + "@" + current.device + Character.toString((char) 31) + current.user_password).getBytes(), sec);
								} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
										| IllegalBlockSizeException | BadPaddingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								System.out.println(fail);
								ack.writeInt(fail.length);
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								ack.write(fail);

								soc.close();
						}
				}
				else {
					System.out.println("Frame did not contain LOGIN or REGISTER");
					byte[] fail2 = null;
					try {
						fail2 = encrypt(new String("FAILURE").getBytes("UTF-8"), sec);
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					System.out.println(fail2);
					ack.writeInt(fail2.length);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ack.write(fail2);

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
			for (Entry<Client, Socket> entry2 : connected_users.get(user).entrySet()) {
				if(!b.equals(entry2.getKey().device)) {	
					System.out.println("Starting sender");
					new Sender(new Frame(entry2.getKey(), a)).start();					
				}
			}
		}
		
		
		
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */
			System.out.println("Starting listener for user = " + user);

			while(turnoff) {
					if(connected_users.get(user).isEmpty()) {
						connected_users.remove(user);
						System.out.println("remover listener for user = " + user);
						break;
					}
					
				//for(Entry<String, ConcurrentHashMap<Client, Socket>> entry : connected_users.entrySet()) {
					for (Entry<Client, Socket> entry2 : connected_users.get(user).entrySet()) {
						temp = entry2.getValue();
					
						
						  //create new data input stream
						//else {
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
								      	//push on stack to be sent
									//stack.add(new Frame(entry2.getKey() ,buf));
									System.out.println("Size on input = " + buf.length);
									send_users(buf, entry2.getKey().device);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						//}
					}
				//}
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
				System.out.println("Removing device = " + current.user.device);
				connected_users.get(current.user.client).remove(current.user);
			}
		}
	}

}

