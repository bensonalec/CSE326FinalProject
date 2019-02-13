import java.net.*;

public class EchoClient {
        public static void main(String[] arg){
                EchoClient a = new EchoClient();
                a.sendEcho("Told you it works");
                a.close();
        }
        private DatagramSocket socket;
        private InetAddress address;
     
        private byte[] buf;
     
        public EchoClient(){
          try{
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
          }        catch (Exception e){
            e.printStackTrace();
            //System.out.println("error");
          }
        }
     
        public String sendEcho(String msg) {
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 1234);
            try {socket.send(packet);
            }        catch (Exception e){
              e.printStackTrace();
              //System.out.println("error");
          }
            packet = new DatagramPacket(buf, buf.length);
            try{

              socket.receive(packet);
            }        catch (Exception e){
              e.printStackTrace();
              //System.out.println("error");
          }
            String received = new String(
              packet.getData(), 0, packet.getLength());
            return received;
        }
     
        public void close() {
            socket.close();
        }
}