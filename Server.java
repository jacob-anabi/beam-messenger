import java.io.*;
import javax.net.ssl.*;

public class Server implements Runnable{
  int portNumber = 9999;
  String clientSentence = "";
  String serverSentence = "";
  SSLServerSocket sslSrvSocket;
  SSLServerSocketFactory sslSrvFact;
  SSLSocket sslConnSocket;
  BufferedReader inFromClient;
  BufferedReader outFromServer;
  DataOutputStream outToClient;
  Thread thread_one;
  Thread thread_two;

  public Server(){
    try{
      thread_one = new Thread(this);
      thread_two = new Thread(this);
      sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      sslSrvSocket = (SSLServerSocket) sslSrvFact.createServerSocket(portNumber);
      sslConnSocket = (SSLSocket) sslSrvSocket.accept();
      thread_one.start();
      thread_two.start();
    }
    catch(Exception e){}
  }

  public void run(){
    try{
      if (Thread.currentThread() == thread_one){
        do{
           outFromServer = new BufferedReader(new InputStreamReader(System.in));
           serverSentence = outFromServer.readLine();

           outToClient = new DataOutputStream(sslConnSocket.getOutputStream());
           outToClient.writeBytes(serverSentence + '\n');
        }while(!serverSentence.equals("END"));
      }
      else{
        do{
          inFromClient = new BufferedReader(new InputStreamReader(sslConnSocket.getInputStream()));
          clientSentence = inFromClient.readLine();
          System.out.println(clientSentence);
        }while(!clientSentence.equals("END"));
      }
    }
    catch(Exception e){}
  }





  public static void main(String [] arstring) {
    new Server();
  }
}
