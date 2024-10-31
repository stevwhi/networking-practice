package tcpLabPack;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program demonstrates a TCP client
 * @author jl922223
 * @version 1.0
 * @since 2020-12-12
 */

public class TCPClient{
    private Socket tcpSocket;
    private InetAddress serverAddress;
    private int serverPort;
    private Scanner scanner;

    /**
     * @param serverAddress
     * @param serverPort
     * @throws Exception
     */
    private TCPClient(InetAddress serverAddress, int serverPort) throws Exception {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        
        //Initiate the connection with the server using Socket. 
        //For this, creates a stream socket and connects it to the specified port number at the specified IP address. 
        //add your code here
        this.tcpSocket = new Socket(this.serverAddress, this.serverPort);
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * The start method connect to the server and datagrams 
     * @throws IOException
     */
    private void start() throws IOException {
        String input;
        //create a new PrintWriter from an existing OutputStream (i.e., tcpSocket). 
        //This convenience constructor creates the necessary intermediateOutputStreamWriter, which will convert characters into bytes using the default character encoding
        //You may add your code in a loop so that client can keep send datagrams to server
        //add your code here
        do {
            input = scanner.nextLine();
            PrintWriter output = new PrintWriter(this.tcpSocket.getOutputStream(), true);
            output.println(input);
            output.flush();
        } while(!input.equals("exit"));
        this.tcpSocket.close();
    }
    
    public static void main(String[] args) throws Exception {
		// set the server address (IP) and port number
    	//add your code here
    	InetAddress serverIP = InetAddress.getByName("192.168.56.1"); // local IP address
    	int port = 7077;
		
		if (args.length > 0) {
			serverIP = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
		}
		
		// call the constructor and pass the IP and port
		//add your code here
		TCPClient client = new TCPClient(serverIP, port);     
        System.out.println("\r\n Connected to Server: " + client.tcpSocket.getInetAddress());
        client.start();                
    }
}