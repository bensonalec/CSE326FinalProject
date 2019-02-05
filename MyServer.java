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
                ss = new ServerSocket(5000);
                s = ss.accept();
                //set up I/O on socket 
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                //read incoming notification
                message = br.readLine();
                //Split the notification which is tab delimited
                String[] notification = message.split("\t");
                
                //Check if the device is new, if it's new will register it in the DB, then will print "New Device Registered!"
                if(!checkDevice(notification[0])) {
                    System.out.println("New Device Registered!");
                }

                //Parses individual notifications by notification type
                if(notification[1].equals("com.oneplus.screenshot")) {
                    System.out.println("Screenshot: " + notification[2]);
                } else if (notification[1].equals("com.snapchat.android")) {
                    System.out.println("Snapchat received from " + notification[2]);
                } else if (notification[1].equals("com.groupme.android")) {
                    System.out.println("GroupMe message in group message \"" + notification[2] +"\": " + notification[3]);
                } else if (notification[1].equals("com.textra")) {
                    System.out.println("Text received from " + notification[2] + ": " + notification[3]);
                }
                else {
                    System.out.println(message);
                }
                //Closes all sockets and I/O
                isr.close();
                br.close();
                ss.close();
                s.close();
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