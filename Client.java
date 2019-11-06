import java.io.*;
import javax.net.ssl.*;

public class Client {
  public static void main(String [] arstring) throws Exception {
  	int portNumber = 9999;
  	String hostName = "localhost";

  	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
  	String sentence = inFromUser.readLine();

    try {
      SSLSocketFactory sslFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
      SSLSocket sslClientSocket = (SSLSocket) sslFact.createSocket(hostName, portNumber);

      DataOutputStream outToServer = new DataOutputStream(sslClientSocket.getOutputStream());
      BufferedReader inFromServer =  new BufferedReader(new InputStreamReader(sslClientSocket.getInputStream()));
      outToServer.writeBytes(sentence + '\n');

    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}