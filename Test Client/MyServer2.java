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
                        

            //This should end up (modified of course) being the accept Connections threads
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
                
                System.out.println(newSock.type + "\t" + newSock.name + ": " + newSock.ip);
                
            }
            //This should end up being the routing and sending thread
            //Iterates through all  of the connected sockets
            for(String key: connectionsList.keySet()) {
                ConnectionSock temp = connectionsList.get(key);
                //Case for the android device, right now its mapped only to my personal phone
                if(temp.type.equals("phone")) {
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

    public static class notification {
        String title = null;
        String user = null;
        String device = null;
        Socket socket = null;
        String content = null;
        String app = null;
        String raw = null;

        private String getTitle() {
            return title;
        }
        private void setTitle(String title) {
            this.title = title;
        }
        private String getUser() {
            return user;
        }
        private void setUser(String user) {
            this.user = user;
        }
        private String getDevice() {
            return device;
        }
        private void setDevice(String device) {
            this.device = device;
        }
        private Socket getSocket() {
            return socket;
        }
        private void setSocket(Socket socket) {
            this.socket = socket;
        }
        private String getContent() {
            return content;
        }
        private void setContent(String content) {
            this.content = content;
        }
        private String getApp() {
            return this.app;
        }
        private void setApp(String app) {
            this.app = app;
        }
        private String getRaw() {
            return raw;
        }
        private void setRaw(String raw) {
            String[] notif = raw.split("#");
            this.title = notif[2];
            this.content = notif[3];
            String[] userDev = notif[0].split("@");
            this.user = userDev[0];
            this.device = userDev[1];
            this.app = notif[1];
            this.raw = raw;
        }

        private String toBytes() {
            return raw;
        }
        private void fromBytes(String input) {
            String[] notif = input.split("#");
            this.raw = input;
            this.title = notif[2];
            this.content = notif[3];
            String[] userDev = notif[0].split("@");
            this.user = userDev[0];
            this.device = userDev[1];
            this.app = notif[1];
        }

    }

    public static class ConnectionSock {
        private Socket sock = null;
        private BufferedReader in = null;
        private PrintWriter out = null;
        private String name = null;
        private String notif = null;
        private String ip = null;
        private String type = null;

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
        //getter for socket
        private Socket getSock() {
            return this.sock;
        }
        //setter for socket, NOTE THIS IS NEEDED TO INITIALIZE
        private void setSock(Socket socket) {
            sock = socket;
            this.ip = sock.getRemoteSocketAddress().toString();
        }   
        private String getType() {
            return this.type;
        }
        private void setType(String type) {
            this.type = type;
        }

        //Receives a packet
        private String receiveFrom() {
            try {
                if(!sock.equals(null)) {
                    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String received = in.readLine();
                    String[] notification = received.split("#");
                    if(notification[0].contains("@")) {
                        if(notification[0].contains("PHONE:")) {
                            type = "phone";
                            notification[0] = notification[0].substring(6);
                        }
                        else {
                            type = "desktop";
                        }
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
                    return "0";
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