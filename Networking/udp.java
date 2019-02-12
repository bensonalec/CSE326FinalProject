import java.net.*;
import java.io.*;

public class udp {
        public static void main (String[] args){
                try {
                        Socket s = new Socket();

                        while (true){
                                try {
                                        s.connect(new InetSocketAddress("localhost", 1234));

                                        DataOutputStream d = new DataOutputStream(s.getOutputStream());

                                        d.writeUTF("Hello from Java");

                                } catch (IOException e){
                                        //System.out.println("IOException occurred");
                                        e.printStackTrace();
                                }
                        }



                } catch( Exception e) {
                        e.printStackTrace();
                }
        }
}