import java.io.*; 
import java.util.*; 
import javax.net.ssl.*;
import java.util.regex.Pattern;
  
/**
 * Server class 
 */
public class testServer { 
    static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    static int i = 0; // client counter 
  
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
              
            ClientHandler mtch = new ClientHandler(sslClientSocket,"client " + i, dis, dos); // Create a new handler object for handling this request
            Thread t = new Thread(mtch); // Create a new Thread with this handler               
            System.out.println("Adding this client to active client list"); 
            clients.add(mtch); // add this client to active clients list
   
            t.start(); 
            i++; 
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
    SSLSocket s; 
    boolean isloggedin; 
      
    public ClientHandler(SSLSocket s, String name, DataInputStream dis, DataOutputStream dos) { 
        this.dis = dis; 
        this.dos = dos;
        this.name = name; 
        this.s = s; 
        this.isloggedin=true; 
    } 
  
    @Override
    public void run() { 
        String received; 
        while (true)  { 
            try { 
                received = dis.readUTF(); // receive the string
                  
                System.out.println(received); 
                  
                if (received.equals("logout")) { 
                    this.isloggedin = false; 
                    this.s.close(); 
                    break; 
                }
                
                if (received.equals("whois")) {
                    for (ClientHandler client : testServer.clients) {
                        if (!client.getName().equals(this.name)) {
                            this.dos.writeUTF(client.getName());
                        }
                        else {
                            this.dos.writeUTF(client.getName() + " [YOU]");
                        }
                    }
                }
                else {
                    // break the string into message and recipient part, delimiter = ->
                    String[] splitMessage = received.split(Pattern.quote("->"));
                    String sentMessage = splitMessage[0]; 
                    String param = splitMessage[1];
      
                    if (sentMessage.equals("chusr")) {
                        String[] names = param.split(Pattern.quote(","));
                        String orignalName = names[0]; 
                        String newName = names[1];

                        // search for the recipient in client list
                        for (ClientHandler client : testServer.clients) { 
                            // if the recipient is found, write on its output stream 
                            if (client.getName().equals(orignalName) && client.isloggedin == true) { 
                                client.setName(newName);
                                break; 
                            } 
                        }
                    }
                    else {
                        // search for the recipient in client list
                        for (ClientHandler client : testServer.clients) { 
                            // if the recipient is found, write on its output stream 
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

    public String getName() {
        return this.name;
    } 

    public void setName(String name) {
        this.name = name;
    } 
} 
