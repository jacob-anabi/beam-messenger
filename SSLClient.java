import java.io.*; 
import javax.net.ssl.*; 
import java.util.Scanner; 
  
public class SSLClient { 
    final static int serverPort = 9999;
  
    public static void main(String args[]) throws IOException { 
        Scanner scanner = new Scanner(System.in); 
        String hostName = "localhost"; // getting hostname 
          
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