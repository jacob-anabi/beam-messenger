import java.io.*;
import javax.net.ssl.*;

public class Client implements Runnable {
  int portNumber = 9999;
  String hostName = "localhost";
  String inSentence = "";
  String outSentence = "";
  BufferedReader inFromUser;
  BufferedReader outFromUser;
  DataOutputStream outToServer;
  Thread thread_one;
  Thread thread_two;
  SSLSocketFactory sslFact;
  SSLSocket sslClientSocket;

  public Client() {
    try {
      thread_one = new Thread(this);
      thread_two = new Thread(this);
      sslFact = (SSLSocketFactory) SSLSocketFactory.getDefault();
      sslClientSocket = (SSLSocket) sslFact.createSocket(hostName, portNumber);
      thread_one.start();
      thread_two.start();
    } catch(Exception exception) {
    	exception.printStackTrace();
    }
  }

  public void run() {
    try {
      if (Thread.currentThread() == thread_two) {
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
    new Client();
  }
}