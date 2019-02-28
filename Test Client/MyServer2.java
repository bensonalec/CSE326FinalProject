import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class MyServer2 {
    private static ServerSocket sock;
    private static Socket recSock;
    public static void main(String[] args) {
        try {
            sock = new ServerSocket(5000);
            int i = 0;
            //Map for the devicename, socket
            Map<String,ConnectionSock> connectionsList = new HashMap <String,ConnectionSock>();
            //Map for the IP, socket
            Map<String,ConnectionSock> connectionsListIP = new HashMap <String,ConnectionSock>();

            //This should end up (modified of course) being the accept Connections Socket
            //Creates socket object for 2 connections, as its not threaded it is blocked until it receives a connection, then a bytestream, then a second connection
            for(i=0;i<2;i++) {
                //Accepts the connection
                recSock = sock.accept();
                
                //Creates a ConnectionSock object and makes it the accepted socket
                ConnectionSock newSock = new ConnectionSock();
                newSock.setSock(recSock);
                newSock.name = "Unknown";
                newSock.receiveFrom();
                
                connectionsList.put(newSock.name,newSock);
                connectionsListIP.put(newSock.ip,newSock);
                
                System.out.println(newSock.name + ": " + newSock.ip);
                
            }
            //This should end up being the routing and sending thread
            //Iterates through all  of the connected sockets
            for(String key: connectionsList.keySet()) {
                ConnectionSock temp = connectionsList.get(key);
                //Case for the android device, right now its mapped only to my personal phone
                if(key.equals("bensonalec@ONEPLUS A5010")) {
                    temp.sendTo("This should go to phone");
                    temp.closeSock();
                }
                //case for anyhting not my phone, aka the desktop client
                else {
                    temp.sendTo(connectionsList.get("bensonalec@ONEPLUS A5010").notif);
                    temp.closeSock();
                }
            }            

            System.out.println("Finished Routing");
              
            
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
        private String ip = null;

        //getter for name
        private String getName() {
            return this.name;
        }
        //setter for name
        private void setName(String name) {
            this.name = name;
        }
        //getter for ip
        private String getIP() {
            return this.ip;
        }
        //setter for ip
        private void setIP(String IP) {
            this.ip = IP;
        }
        //setter for socket, NOTE THIS IS NEEDED TO INITIALIZE
        private void setSock(Socket socket) {
            sock = socket;
            this.ip = sock.getRemoteSocketAddress().toString();
        }
        //getter for socket
        private Socket getSock() {
            return this.sock;
        }

        //Receives a packet
        private String receiveFrom() {
            try {
                if(!sock.equals(null)) {
                    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String received = in.readLine();
                    String[] notification = received.split("\t");
                    if(notification[0].contains("@")) {
                        name = notification[0];
                    }
                    else {
                        name = "Unkown";
                    }
                    notif = received;
                    return received;
                }
                else {
                    System.out.println("Must initialize wiht a socket object!");
                }

            } catch(IOException e) {
                return "Exception";
            }
        }

        //Sends a packet
        private void sendTo(String message) {
            try {
                if(!sock.equals(null)) {
                    out = new PrintWriter(sock.getOutputStream(), true);
                    out.println(message);
                    out.flush();
                } else {
                    System.out.println("Must initialize with a socket object!");
                }
            } catch(IOException e) {
                System.out.println("Exception");
            }
        
        }

        //Closes the socket
        private void closeSock() {
            try {
                in.close();
                sock.close();
            } catch(IOException e) {

            }
        }
    }
}