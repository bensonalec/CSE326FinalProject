package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;



public class Server3 {
	int port = 5000;
	Map<String, Socket> connected_users = new ConcurrentHashMap<String, Socket>();
	ConcurrentLinkedQueue<Frame> stack = new ConcurrentLinkedQueue<Frame>();
	public static void main(String[] arg) {
		Server3 serv = new Server3();
		//start server
		serv.start_server();
	}
	void start_server() {
		//start each thread.
		Connector a = new Connector();
		Listener b = new Listener();
		Grouper c = new Grouper();
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
		BufferedReader in;
		public void run() {
			//set up server socket
			try {
				serv = new ServerSocket(port);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//continue to listen for new connection to the server.
			while(true) {
				try {
					//accept new user.
					soc = serv.accept();
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
				//----------------------------------------------------------
				//Verify login, if not close connection
				//and add device to table. 
				//---------------------------------------------------------
				
				//push users name and socket to hashmap that contains connected users.
				try {
					connected_users.put(in.readLine(), soc);
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
		BufferedReader in;
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */
			  for (Entry<String, Socket> entry : connected_users.entrySet()) {
			      temp = entry.getValue();
			      try {
					in = new BufferedReader(new InputStreamReader(temp.getInputStream()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			      try {
			    	//if string has line push notification on stack.
					if(in.ready()) {
						  while(in.ready()) {
							 buf = buf.concat(in.readLine());
						  }
						  stack.add(new Frame(entry.getKey(),buf));
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			while(true) {
				if(!stack.isEmpty()) {
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
		PrintWriter out;
		Sender(Frame a){
			current = a;
			//get destination socket
			destination = connected_users.get(current.name);
		}
		public void run() {
			//create output using printwriter
			 try {
				out = new PrintWriter(destination.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.write(current.packet);
			out.flush();
			/*send to all members in group*/
		}
	}

}
