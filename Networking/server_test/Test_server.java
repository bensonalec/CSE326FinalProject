package com;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test_server {
	public static void main(String[] args)  {
		Socket soc = null;
		Boolean a = true;
		DataOutputStream out = null;
		DataInputStream in = null;
		while(a) {
			try {
				System.out.println("attemping connection");
				soc = new Socket("jerry.cs.nmt.edu",5000);
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
		try {
			out = new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String login = "LOGIN" + Character.toString((char) 31) + "alecbenson@device1" + Character.toString((char) 31) + "pass";
			out.writeInt(login.length());
			out.write(login.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String login = "Test test test test test";
			out.writeInt(login.length());
			out.write(login.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try {
			in = new DataInputStream(soc.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		while(i < 5) {
			try {
				if(in.available() != 0) {
					System.out.println(in.readUTF());
					i = i + 1;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		try {
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//socket connect
	}


}
