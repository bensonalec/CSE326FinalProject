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


public class MyServer {
    private static ServerSocket ss;
    private static Socket s;
    private static BufferedReader br;
    private static InputStreamReader isr;
    private static String message = "";
    public static void main(String[] args) {
        try {
            while(true) {
                //accept connection from incoming notification
                    //ss = new ServerSocket(5000);
                
                DatagramSocket socket = new DatagramSocket(5000);
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                
                /*
                s = ss.accept();
                //set up I/O on socket 
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                //read incoming notification
                message = br.readLine();
                */
                //Split the notification which is tab delimited
                String[] notification = received.split("\t");
                
                //Check if the device is new, if it's new will register it in the DB, then will print "New Device Registered!"
                if(!checkDevice(notification[0])) {
                    System.out.println("New Device Registered!");
                }

                //Parses individual notifications by notification type
                System.out.println(message);
                //Closes all sockets and I/O
                /*
                    //isr.close();
                    //br.close();
                    //ss.close();
                    //s.close();
                */
                socket.close();
            }
        } catch (IOException e) {
            
        }
    }

    public static Boolean checkDevice(String name) {
        
        File db = new File("sampleDB.csv");
        //Split device:useranme combo
        String[] toCheck = name.split(":");
        //Open the db, check whether device is already present 
        try {
            BufferedReader reader = new BufferedReader(new FileReader(db));
            String entry = null;
            
            while(((entry = reader.readLine()) != null)) {
                String[] data = entry.split(",");
                
                if(data[0].equals(toCheck[1]) && data[1].equals(toCheck[0])) {
                    return true;
                }
            }
            reader.close();
            
        } catch (IOException e) {

        }
        //Attempt to register an unrecognized device
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("sampleDB.csv", true));
            writer.append(toCheck[1] + "," + toCheck[0] + "," + s.getRemoteSocketAddress().toString());
            writer.close();
        } catch(IOException e) {
            System.out.println("IOException when writing");
        }
        return false;
    }
}