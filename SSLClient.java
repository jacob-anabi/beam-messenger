import java.io.*;
import javax.net.ssl.*;

public class SSLClient implements Runnable {
  int portNumber = 9999;
  String hostName = "localhost";
  String inSentence = "";
  String outSentence = "";

  BufferedReader inFromUser;
  BufferedReader outFromUser;
  DataOutputStream outToServer;

  Thread t;

  SSLSocketFactory sslFact;
  SSLSocket sslClientSocket;

  public SSLClient() {
    try {
      t = new Thread(this);

      sslFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
      sslClientSocket = (SSLSocket) sslFact.createSocket(hostName, portNumber);
      
      t.start();
    } catch(Exception exception) {
    	exception.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      if (Thread.currentThread() == t) {
        do {
          inFromUser = new BufferedReader(new InputStreamReader(System.in));
          inSentence = inFromUser.readLine();

          outToServer = new DataOutputStream(sslClientSocket.getOutputStream());
          outToServer.writeBytes(inSentence + '\n');
        } while(!inSentence.equals("END"));
      }
      else {
        do {
          outFromUser = new BufferedReader(new InputStreamReader(sslClientSocket.getInputStream()));
          outSentence = outFromUser.readLine();
          System.out.println(outSentence);
        } while(!outSentence.equals("END"));
      }
    } catch(Exception exception) {
    	exception.printStackTrace();
    }
  }

public static void main(String [] arstring) throws Exception {
    new SSLClient();
  }
}