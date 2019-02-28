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

public class MyServer2 {
    private static ServerSocket sock;
    private static Socket recSock;
    public static void main(String[] args) {
        try {
            sock = new ServerSocket(5000);
        
            while(true) {
                
                recSock = sock.accept();
                System.out.println("New conncetion!");
                PrintWriter out = new PrintWriter(recSock.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(recSock.getInputStream()));
                String received;
                while ((received = in.readLine()) != null) {
                    System.out.println(received);
                    out.println("TEST LINE");
                    out.flush();
                }
                in.close();
                recSock.close();
                
                
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
}