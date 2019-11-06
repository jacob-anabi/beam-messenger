# Beam Messenger
Beam is an open-source chat application that uses SSL to secure communications between parties.

# How to run
Currently only local machine communication is possible. To test the communication, do the following:
1. Open one terminal window and 'cd' to the appropiate directory
2. Compile the Server.java file ('javac Server.java')
3. Run the following command 'java -Djavax.net.ssl.keyStore=keystore -Djavax.net.ssl.keyStorePassword=password Server'
4. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'Server' in the previous command to see some nifty stuff
5. Open another terminal window and 'cd' to the appropiate directory
6. Compile the Client.java file ('javac Client.java')
7. Run the following command 'java -Djavax.net.ssl.trustStore=truststore -Djavax.net.ssl.trustStorePassword=trustword Client'
8. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'Client' in the previous command to see some nifty stuff
9. Type a message to send in you Client or Server window and have comminications back and forth
10. Type 'END' in both windows to close, or ^C
