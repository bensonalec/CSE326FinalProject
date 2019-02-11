import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;

public class testsend {
    public static void main(String[] args){
        try{
            Socket a = new Socket("localhost", 5000);
            PrintWriter b =  new PrintWriter(a.getOutputStream(), true);
            b.write("Hello");
        }
        catch (Exception e){
            System.out.println("error");
        }

    } 



}