import java.io.*; 
import java.util.*; 
import javax.net.ssl.*;
import java.util.regex.Pattern;

/**
 * @author Jacob Anabi <anabi@chapman.edu>
 * @author John Park <sanpark@chapman.edu>
 * @version 1.0
 */
  
/**
 * SSLServer class 
 */
public class SSLServer { 
    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    static int clientNumber = 0; // client counter 
  
    public static void main(String[] args) throws IOException { 
        int portNumber = 9999;
        SSLServerSocketFactory sslServerFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerFactory.createServerSocket(portNumber);

        SSLSocket sslClientSocket;
          
        // running infinite loop for getting client request 
        while (true) { 
            sslClientSocket = (SSLSocket) sslServerSocket.accept();

            System.out.println("New client request received : " + sslClientSocket); 
              
            // obtain input and output streams 
            DataInputStream dis = new DataInputStream(sslClientSocket.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(sslClientSocket.getOutputStream()); 
              
            ClientHandler client = new ClientHandler(sslClientSocket, "client " + clientNumber, dis, dos); // Create a new handler object for handling this request
            Thread t = new Thread(client); // Create a new Thread with this handler               
            System.out.println("Adding client " + clientNumber + " to active client list"); 
            clients.add(client); // add this client to active clients list
   
            t.start(); 
            clientNumber++; 
        } 
    } 
} 
  
/**
 * ClientHandler class 
 */
class ClientHandler implements Runnable { 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    SSLSocket clientSocket; 
    boolean isloggedin; 
      
    public ClientHandler(SSLSocket clientSocket, String name, DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos;
        this.name = name; 
        this.clientSocket = clientSocket; 
        this.isloggedin = true; 
    } 
  
    @Override
    public void run() { 
        String received; 
        while (true)  { 
            try { 
                received = dis.readUTF(); // received message from client
                
                String[] splitMessage = received.split(Pattern.quote("->")); // break the string into message and recipient part, delimiter = ->
                String sentMessage = splitMessage[0]; 
                String param = splitMessage[splitMessage.length - 1];
                  
                System.out.println(received); 
                  
                if (sentMessage.equals("exit")) { 
                    this.isloggedin = false;
                    System.out.println("Removing " + this.name + " from the active client list"); 
                    SSLServer.clients.remove(this);
                    this.clientSocket.close();
                    break;
                }
                
                else if (sentMessage.equals("whois")) {
                    for (ClientHandler client : SSLServer.clients) {
                        if (!client.getName().equals(this.name)) {
                            this.dos.writeUTF(client.getName()); // print out all names not the client who typed 'whois'
                        }
                        else {
                            this.dos.writeUTF(client.getName() + " [YOU]"); // printing out the name of the client who typed 'whois'
                        }
                    }
                }
                else {
                    if (sentMessage.equals("chusr")) {
                        String[] names = param.split(Pattern.quote(",")); // break the param into original name and the new name, delimiter = ,
                        String orignalName = names[0]; 
                        String newName = names[1];

                        // search for the recipient in client list
                        for (ClientHandler client : SSLServer.clients) { 
                            // making sure that only the client can change their name and also updating the name in the clients list
                            if (client.getName().equals(orignalName) && client.isloggedin == true && this.name.equals(orignalName)) { 
                                client.setName(newName);
                                break; 
                            } 
                        }
                    }
                    else {
                        // search for the recipient in client list
                        for (ClientHandler client : SSLServer.clients) { 
                            // if found the recipient, write to its output stream 
                            if (client.getName().equals(param) && client.isloggedin == true) { 
                                client.dos.writeUTF(this.name + " : " + sentMessage); 
                                break; 
                            } 
                        }
                    }
                }
            } catch (IOException e) {             
                e.printStackTrace(); 
            } 
              
        } 
        try { 
            // closing resources 
            this.dis.close(); 
            this.dos.close(); 
        } catch(IOException e){ 
            e.printStackTrace(); 
        } 
    }

    // getters and setters
    public String getName() {
        return this.name;
    } 

    public void setName(String name) {
        this.name = name;
    } 
} 
