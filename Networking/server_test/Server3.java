package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
	int port = 5000;
	public static void main(String[] arg) {
		
	
	
	}
	public class Connector extends Thread{
		ServerSocket serv = new ServerSocket(5000);
		public void run() {
			//continue to listen for new connection to the server.
			while(true) {
				try {
					Socket soc = serv.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			//figure out what to do with socket maybe read in first frame for setup.
			//----------------------------------------------------------
			//Verify login, if not close connection
			//and add device to table. 
			//-----------------------------------------------------------
		}
	}
	public class Listener extends Thread{
		public void run() {
			/* Need to go through sockets and check if new connection*/
			/* also clean up closed sockets */
		}
	}
	public class Grouper extends Thread{
		public void run() {
			/*find other devices in groups*/
			
			//create 4th threads
		}
	}
	public class Sender extends Thread{
		public void run() {
			/*send to all members in group*/
		}
	}

}
