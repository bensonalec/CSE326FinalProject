package com;

import java.io.IOException;
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
		try {
			out = new PrintWriter(soc.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write("test1@group1");
		out.write("snapchat recieved");
		//socket connect
	}
}
