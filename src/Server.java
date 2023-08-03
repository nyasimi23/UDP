import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {
    private static final int PORT = 1234;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket,outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
        System.out.println("Opening port ... \n");

        try {
            // Create  a DatagramSocket object [STEP 1]
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException sockEx) {
            System.out.println("Unable to run port!");
            System.exit(1);
        }
        handleClient();
    }

    private static void handleClient(){
       
       try {
            String messageIn, messageOut;
            int numMessage = 0;
            InetAddress clientAddress = null;
            int clientPort;

            do{
                // Create a buffer for incoming datagrams [STEP 2]
                buffer = new byte[256];
                // Create DatagramPacket object for incoming datagrams [STEP 3]
                inPacket = new DatagramPacket(buffer, buffer.length);
                // Accept an incoming datagram [STEP 4]
                datagramSocket.receive(inPacket);
                // Accept the sender's address and port from the packet [STEP 5]
                clientAddress = inPacket.getAddress();
                clientPort = inPacket.getPort();
                // Retrieve the data from the buffer [STEP 6]
                messageIn = new String(inPacket.getData(),0, inPacket.getLength());
                System.out.println("Message received.");
                numMessage++;
                messageOut = "Message " + numMessage +": " + messageIn;
                // Create the response datagram [STEP 7]
                outPacket = new DatagramPacket(messageOut.getBytes(),messageOut.length(), clientAddress,clientPort);
                // Send the response datagram [STEP 8]
                datagramSocket.send(outPacket);
            }while(true);

       } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }finally{
        System.out.println("\n * Closing coonection... ");
        // Close the DatagramSocket [STEP 9]
        datagramSocket.close();

    }

    }
    
}
