import java.io.*;
import javax.net.ssl.*;

public class Server {
  public static void main(String [] arstring) {
  	int portNumber = 9999;
    SSLServerSocket sslSrvSocket;
    String clientSentence;

    try {
      SSLServerSocketFactory sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      sslSrvSocket = (SSLServerSocket) sslSrvFact.createServerSocket(portNumber);
      SSLSocket sslConnSocket = (SSLSocket) sslSrvSocket.accept();

      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(sslConnSocket.getInputStream()));
      DataOutputStream outToClient = new DataOutputStream(sslConnSocket.getOutputStream());

      clientSentence = inFromClient.readLine();
      System.out.println(clientSentence);

    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}