import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class MyServer2 {
    private static ServerSocket sock;
    private static Socket recSock;
    public static void main(String[] args) {
        try {
            sock = new ServerSocket(5000);
            int i = 0;
            ConnectionSock[] sockArray = new ConnectionSock[2];
            Map<String,ConnectionSock> connectionsList = new HashMap <String,ConnectionSock>();

            String[] received = new String[2];

            while(i < 2) {
                recSock = sock.accept();
                ConnectionSock newSock = new ConnectionSock();
//                sockArray[i] = new ConnectionSock();
//                sockArray[i].setSock(recSock, "Tester");
                newSock.setSock(recSock,"Tester");                                  
                //received[i] = sockArray[i].receiveFrom();
                newSock.receiveFrom();
                connectionsList.put(newSock.name,newSock);
                i++;
                
            }
            /*
            String received = sockArray[0].receiveFrom();
            sockArray[0].sendTo("HI");
            sockArray[0].closeSock();
            sockArray[1].receiveFrom();
            sockArray[1].sendTo(received);
            sockArray[1].closeSock();
            System.out.println(sockArray[0].name);
            System.out.println(sockArray[1].name);

            System.out.println(received);  
            */
            String out = null;
            //has to receive to know it's name
            /*
            for(i = 0; i < 2; i++) {
                if(sockArray[i].name.equals("bensonalec@ONEPLUS A5010")) {
                    sockArray[i].sendTo("HI");
                    sockArray[i].closeSock();

                }
                if(sockArray[i].name.equals("bensonalec@PythonTest")) {
                    sockArray[i].sendTo("Sample");
                    sockArray[i].closeSock();

                }
            }
            */
            for(String key: connectionsList.keySet()) {
                ConnectionSock temp = connectionsList.get(key);
                if(key.equals("bensonalec@ONEPLUS A5010")) {
                    temp.sendTo("This should go to phone");
                    temp.closeSock();
                }
                if(key.equals("bensonalec@PythonTest")) {
                    temp.sendTo(connectionsList.get("bensonalec@ONEPLUS A5010").notif);
                    temp.closeSock();
                }
            }            

            System.out.println(out);
              
            
        } catch (IOException e) {
            System.out.println("IOException");
        }

    }

    private static class ConnectionSock {
        private Socket sock = null;
        private BufferedReader in = null;
        private PrintWriter out = null;
        private String name = null;
        private String notif = null;

        private void setSock(Socket socket, String name) {
            sock = socket;
            this.name = name;
        }

        private String receiveFrom() {
            try {
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                String received = in.readLine();
                String[] notification = received.split("\t");
                name = notification[0];
                notif = received;
                return received;

            } catch(IOException e) {
                return "Exception";
            }
        }

        private void sendTo(String message) {
            try {
                out = new PrintWriter(sock.getOutputStream(), true);
                out.println(message);
                out.flush();
            } catch(IOException e) {
                System.out.println("Exception");
            }
        
        }

        private void closeSock() {
            try {
                in.close();
                sock.close();
            } catch(IOException e) {

            }
        }
    }
}