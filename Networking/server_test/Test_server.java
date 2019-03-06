package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test_server {
	public static void main(String[] args)  {
		Socket soc = null;
		Boolean a = true;
		PrintWriter out = null;
		while(a) {
			try {
				System.out.println("attemping connection");
				soc = new Socket("localhost",5050);
				a = false;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("connected.");
		Test_server serv = new Test_server();
		serv.start_listen(soc);
		try {
			out = new PrintWriter(soc.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write("test1@group1");
		out.flush();
		out.write("snapchat recieved");
		out.flush();
		
		//socket connect
	}
	void start_listen(Socket soc) {
		new Listen(soc).start();
	}

	public class Listen extends Thread {
		Socket soc;
		BufferedReader in;
		Listen(Socket a){
			soc = a;
		}
		public void run(){
			try {
				in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(true) {
				try {
					if(in.ready()) {
						System.out.println(in.readLine());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
