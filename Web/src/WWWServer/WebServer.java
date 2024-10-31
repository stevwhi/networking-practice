package WWWServer;

import java.net.* ;


/**
 * This program demonstrates a WWW server
 * @author jl922223
 * @version 1.0
 * @since 2020-12-12
 */


public final class WebServer {
	public static void main(String argv[]) throws Exception {
		// Get the port number from the command line.
		// add your code here
		int pp = 80;
		int port = pp != 0 ? pp : Integer.parseInt(argv[0]);

		// Establish the listen socket using the port number.
		// add your code here
		ServerSocket socket = new ServerSocket(port);

		// Process HTTP service requests in an infinite loop.
		while (true) {
			// Listen for a TCP connection request.
			// add your code here
			Socket connection = socket.accept();

			// Construct an object to process the HTTP request message.
			// add your code here
			HttpRequest request = new HttpRequest(connection);

			// Create a new thread to process the request and starting the thread. No need to update this.
			Thread thread = new Thread(request);
			// Start the thread.
			thread.start();
		}
	}
}