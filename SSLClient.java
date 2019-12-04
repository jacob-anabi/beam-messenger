import java.io.*; 
import javax.net.ssl.*; 
import java.util.Scanner; 

/**
 * @author Jacob Anabi <anabi@chapman.edu>
 * @author John Park <sanpark@chapman.edu>
 * @version 1.0
 *
 * SSLClient starts up a client and connects to a specified server.
 */  

/**
 * SSLCLient class 
 */  
public class SSLClient { 
    final static int serverPort = 9999;
  
    public static void main(String args[]) throws IOException { 
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Please enter a valid server address to connect to below:");
        String hostName = scanner.nextLine(); // getting hostname 
          
        // establish the connection 
        SSLSocketFactory sslFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslClientSocket = (SSLSocket) sslFactory.createSocket(hostName, serverPort);
          
        DataInputStream dis = new DataInputStream(sslClientSocket.getInputStream()); 
        DataOutputStream dos = new DataOutputStream(sslClientSocket.getOutputStream());

        /*
         * messageReader
         * Does the necessary work to read on the input stream for the client
         */
        Runnable messageReader = new Runnable() {
            @Override
            public void run() { 
                while (true) {
                    try { 
                        // read the message sent to this client 
                        String msg = dis.readUTF(); 
                        System.out.println(msg);
                    } catch (EOFException e) {
                        break; // there are no more bytes to read
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    }
                } 
            }
        };

        /*
         * messageSender
         * Does the necessary work to write on the output stream for the client
         */
        Runnable messageSender = new Runnable() {
            @Override
            public void run() { 
                while (true) {
                    String msg = scanner.nextLine(); 
                    try { 
                        dos.writeUTF(msg); 
                    } catch (IOException e) { 
                        e.printStackTrace(); 
                    } 
                } 
            }
        };
  
        // the thread for sending messages for the client
        Thread sendMessage = new Thread(messageSender); 
          
        // the thread for reading messages for the client
        Thread readMessage = new Thread(messageReader);
        
        sendMessage.start(); 
        readMessage.start();
    }
} 