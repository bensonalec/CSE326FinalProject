import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;

public class testsend {
    public static void main(String[] args){
        try{
            Socket a = new Socket("localhost", 6000);
            PrintWriter b =  new PrintWriter(a.getOutputStream(), true);
            b.write("Hello");
            a.close();
        }
        catch (Exception e){
            e.printStackTrace();
            //System.out.println("error");
        }


    } 



}