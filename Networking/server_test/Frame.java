package com;



/*
 * The Frame class is used to hold the Client and data packet that was either
 * sent from or is the destination depending on which thread it is in.
 */


public class Frame {
	byte[] packet; //data sent from clients
	Client user; //client information
	Frame(Client name, byte[] buf){
		this.packet = buf;
		this.user = name;
	}
}
