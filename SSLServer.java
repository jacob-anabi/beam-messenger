import java.io.*;
import javax.net.ssl.*;

public class SSLServer {
  int portNumber = 9999;
  String clientSentence = "";
  String serverSentence = "";
  String inSentence = "";
  String outSentence = "";

  SSLServerSocket sslSrvSocket;
  SSLServerSocketFactory sslSrvFact;
  SSLSocket sslConnSocket;

  BufferedReader inFromClient;
  BufferedReader outFromServer;
  DataOutputStream outToClient;

  BufferedReader inFromUser;
  BufferedReader outFromUser;
  DataOutputStream outToServer;

  Thread client;

  public SSLServer() {
    try {
      sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      sslSrvSocket = (SSLServerSocket) sslSrvFact.createServerSocket(portNumber);

      while (true) {
        sslConnSocket = (SSLSocket) sslSrvSocket.accept();
        Thread client = new ClientThread(sslConnSocket);
        client.start();
      }
    } catch(Exception exception) {
    	exception.printStackTrace();
    }
  }

class ClientThread extends Thread {
    private SSLSocket sslConnSocket;

    public ClientThread(SSLSocket sslConnSocket) {
        this.sslConnSocket = sslConnSocket;
    }

    @Override
    public void run() {
      try {
        if (Thread.currentThread() == client) {
          do {
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            inSentence = inFromUser.readLine();

            outToServer = new DataOutputStream(sslConnSocket.getOutputStream());
            outToServer.writeBytes(inSentence + '\n');
          } while(!inSentence.equals("END"));
        }
        else {
          do {
            outFromUser = new BufferedReader(new InputStreamReader(sslConnSocket.getInputStream()));
            outSentence = outFromUser.readLine();
            System.out.println(outSentence);
          } while(!outSentence.equals("END"));
        }
      } catch(Exception exception) {
        exception.printStackTrace();
      }
    }
  }
  public static void main(String [] arstring) {
    new SSLServer();
  }
}