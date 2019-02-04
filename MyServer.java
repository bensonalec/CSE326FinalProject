import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ServerSocket;


public class MyServer {
    private static ServerSocket ss;
    private static Socket s;
    private static BufferedReader br;
    private static InputStreamReader isr;
    private static String message = "";
    public static void main(String[] args) {
        try {
            while(true) {
                ss = new ServerSocket(5000);
                s = ss.accept();

                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                //PrintWriter toout = new PrintWriter(s.getOutputStream());
                //toout.write("Received!");
                message = br.readLine();
                //toout.flush();
                //toout.close();
                System.out.println(message);
                isr.close();
                br.close();
                ss.close();
                s.close();
            }
        } catch (IOException e) {
            
        }
    }
}