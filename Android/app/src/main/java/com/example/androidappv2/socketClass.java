package com.example.androidappv2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

class socketClass extends AsyncTask<Void,Void,Void> {

    private static Socket socket = null;
    private static DataOutputStream out = null;
    private static DataInputStream in = null;

    public static synchronized Socket getSocket() {
        return socket;
    }
    public static synchronized void setSocket(Socket socket) {
        socketClass.socket = socket;
    }
    public static synchronized DataOutputStream getDOS() {
        return out;
    }
    public static synchronized void setDOS(DataOutputStream dos) {
        socketClass.out = dos;
    }
    public static synchronized DataInputStream getDIS() {
        return in;
    }
    public static synchronized void setDIS(DataInputStream dis) {
        socketClass.in = dis;
    }


    @Override
    protected Void doInBackground(Void... Params) {
        String receivedMessage = "not what i want";
        try {
            Socket s = null;
            s = new Socket("jerry.cs.nmt.edu",5000);
            socket = s;
            out = new DataOutputStream(s.getOutputStream());
            in = new DataInputStream(s.getInputStream());

        } catch (UnknownHostException e) {

        } catch (IOException e) {

        }


        return null;

    }



}
