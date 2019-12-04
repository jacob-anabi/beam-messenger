import java.io.*; 
import javax.net.ssl.*; 
import java.util.Scanner; 

/**
 * @author Jacob Anabi <anabi@chapman.edu>
 * @author John Park <sanpark@chapman.edu>
 * @version 1.0
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
  
        // thread for sending messages 
        Thread sendMessage = new Thread(messageSender); 
          
        // thread for reading messages 
        Thread readMessage = new Thread(messageReader);
        
        sendMessage.start(); 
        readMessage.start();
    }
} 