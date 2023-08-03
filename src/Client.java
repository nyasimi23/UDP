import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    private static InetAddress host;
    private static final int PORT = 1234;
    private static DatagramSocket datagramSocket;
    private static DatagramPacket inPacket ,outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (Exception e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        accessServer();
    }

    private static void accessServer(){
        
        try {
            //  Create DatagramSocket object [STEP 1]
            datagramSocket = new DatagramSocket();

            // Set up a stream for keyboard entry
            try (Scanner userEntry = new Scanner(System.in)) {
				String message = "", response = "";


				do {
				    System.out.println("Enter a message");
				    message = userEntry.nextLine();

				    if(!message.equals("***CLOSE***")){
                        // Create the outgoing datagram [STEP 2]
				        outPacket = new DatagramPacket(message.getBytes(),message.length(),host,PORT);
                        // Send the datagram message [STEP 3]
                        datagramSocket.send(outPacket);
                        // Create a buffer for incoming data [STEP 4]
				        buffer = new byte[256];
                        // Create DatagramPacket object for incoming datagrams [STEP 5]
				        inPacket = new DatagramPacket(buffer,buffer.length);
                        // Accept an incoming datagram [STEP 6]
				        datagramSocket.receive(inPacket);
                        // Retreive the data from the buffer [STEP 7]
				        response = new String(inPacket.getData(),0,inPacket.getLength());
				        System.out.println("\nSERVER>" + response);
				    }
				} while (true);
			}
            
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            // Close the connection [STEP 9]
            System.out.println("\n CLosing connection ..* ");
            datagramSocket.close();
        }
    }
}
