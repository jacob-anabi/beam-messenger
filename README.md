# Beam Messenger
Beam is a secure chat application that ensures private communications between parties. The purpose of Beam Messenger is to provide a free and open source blueprint for anyone who is interested with working with encrypted communication. Using SSL/TLS, messages are able to be securely sent between two separate parties ensuring private communications. Currently only non-local communication is possible over the same network.

# How to run (SERVER)
1. Open a terminal window and 'cd' to the appropiate directory
2. Compile the Server.java file ('javac SSLServer.java')
3. Run the following command 'java -Djavax.net.ssl.keyStore=keystore -Djavax.net.ssl.keyStorePassword=password SSLServer'
4. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'SSLServer' in the previous command to see some nifty stuff
5. The server should now be running using your machine's address

# How to run (CLIENT)
1. Open a terminal window and 'cd' to the appropiate directory
2. Compile the Client.java file ('javac SSLClient.java')
3. Run the following command 'java -Djavax.net.ssl.trustStore=truststore -Djavax.net.ssl.trustStorePassword=trustword SSLClient'
4. (OPTIONAL) You can add java '-Djavax.net.debug=ssl' before 'SSLClient' in the previous command to see some nifty stuff
5. Now you can use the client commands listed below to communicate

## Client Commands
1. whois - list all users connected to server
2. chusr->oldName,newName - change your username from oldName to newName
3. 'message'->reciepientName - send a message to a recipient with username recipientName
4. exit - disconnect from server (can now safely use CTRL+C to terminate program without interfering with the server)

# Example
## Server Example
![Server Example Picture](resources/server-example.png?raw=true "Server Example")

## Client 0 - Edward Example
![Client 0 - Edward Picture](resources/client-0-edward-example.png?raw=true "Client 0 - Edward Example")

## Client 1 - Alphonse Example
![Client 1 - Alphonse Picture](resources/client-0-edward-example.png?raw=true "Client 1 - Alphonse Example")

# NOTICE
BEAM MESSENGER IS NOT SECURELY RIGOROUS ENOUGH TO BE USED IN COMMERCIAL APPLICATIONS. IT IS MEANT FOR DEMONSTRATION AND LEARNING PURPOSES ONLY.
