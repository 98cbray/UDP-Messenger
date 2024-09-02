/*
Colby Bray

Networking 331-001
This class sets up the Client side of the message chatroom
*/
import java.io.*;
import java.net.*;
import java.util.*;

class ClientSetup implements User {
    
    private OutputStream output;
    private InputStream input; 
    private ChatFrame clientChatFrame;

    public ClientSetup() {
        clientChatFrame = new ChatFrame(this, "Client", "Server", true);
        
    }

    public void run() throws IOException {
            Socket socket = new Socket("10.103.58.45", 4000);
            System.out.println("Client:- Connected to Server!");

            input = socket.getInputStream();
            output = socket.getOutputStream(); 
            
            try {
        		InetAddress addr = InetAddress.getLocalHost();
        		String hostName = addr.getHostName();
        		String hostAddress = addr.getHostAddress();
        		System.out.println("Connection IP Address: "+hostAddress);
        		System.out.println("Port # & Socket : "+socket);
        		System.out.println("HostName: "+hostName);
        		
        	}
        	catch (UnknownHostException e) {
        		e.printStackTrace();
        	}
    }

    @Override
    public void sendMessage() throws IOException {
        Scanner sc = new Scanner(System.in);
        String send = clientChatFrame.getMessage();
        if (send != null && !send.equals("")) {
            output.write(send.getBytes());
            clientChatFrame.addMessage("You", send);
            System.out.println("Client:- Message sent to Server: " + send);
        }
    }

    @Override
    public int receiveMessage() throws IOException {
        byte[] response = new byte[1000];
        int status = input.read(response);
        String received = new String(response).trim();

        if (received != null && !received.equals("")) {
            clientChatFrame.addMessage("Server", received);
            System.out.println("Client:- Received message from server: " + received);
        }
        return status;
    }

}

public class Client {
    
    public static void main(String[] args) throws IOException {
        ClientSetup client = new ClientSetup();
        client.run();

        while (true) {
            int status = client.receiveMessage();
            if (status == -1) {
                System.exit(0);
            }
        }
    }
}