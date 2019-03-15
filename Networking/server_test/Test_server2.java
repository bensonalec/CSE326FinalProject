package com;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test_server2 {
	public static void main(String[] args)  {
		Socket soc = null;
		Boolean a = true;
		DataOutputStream out = null;
		DataInputStream in = null;
		while(a) {
			try {
				System.out.println("attemping connection");
				soc = new Socket("localhost",5000);
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
			out.writeUTF("test2@group1\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//out.flush();
		try {
			out.writeUTF("test recieved\n");
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
		while(true) {
			try {
				if(in.available() != 0) {
					System.out.println(in.readUTF());
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		//socket connect
	}
}
