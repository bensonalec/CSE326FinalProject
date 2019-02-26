import java.io.*;
import java.net.*;
public class testsend {
    public static void main(String[] args){
        try{
            DatagramSocket a = new DatagramSocket(5000);
            PrintWriter b =  new PrintWriter(a.getOutputStream(), true);
            b.write("bensonalec@tmp\tWhatevs");

	    byte[] buf = new byte[1024];
	    Datagram da = new DatagramPacket(buf, buf.length);

	    a.receive(da);

	    System.out.println(da.getAddress());

            a.close();
        }
        catch (Exception e){
            e.printStackTrace();
            //System.out.println("error");
        }


    } 



}
