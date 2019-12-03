# Beam Messenger
Beam is an open-source chat application that uses SSL to secure communications between parties.

# How to run
Currently only local machine communication is possible. To test the communication, do the following:
1. Open one terminal window and 'cd' to the appropiate directory
2. Compile the Server.java file ('javac SSLServer.java')
3. Run the following command 'java -Djavax.net.ssl.keyStore=keystore -Djavax.net.ssl.keyStorePassword=password SSLServer'
4. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'SSLServer' in the previous command to see some nifty stuff
5. Open another terminal window and 'cd' to the appropiate directory
6. Compile the Client.java file ('javac SSLClient.java')
7. Run the following command 'java -Djavax.net.ssl.trustStore=truststore -Djavax.net.ssl.trustStorePassword=trustword SSLClient'
8. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'SSLClient' in the previous command to see some nifty stuff
9. Repeat steps 5-8 for as many clients as you want
9. Type a message to send in one of your Client windows

# Client Commands
1. whois - list all users connected to server
2. chusr->oldName,newName - change your username from oldName to newName
3. message->reciepientName - send a message to a recipient with username recipientName