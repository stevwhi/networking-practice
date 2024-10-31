package WWWServer;

import java.io.* ;
import java.net.* ;
import java.util.* ;


/**
 * This program fetches an HTTP request
 * @author jl922223 * @author jl922223
 * @version 1.0
 * @since 2020-12-12
 * */

final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;

	// Constructor
	public HttpRequest(Socket socket) throws Exception {
		this.socket = socket;
	}

	// The run() method of the Runnable interface.
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception {
		// Get a reference to the socket's input stream.
		// add your code here
		InputStream is = socket.getInputStream();
		// Get a reference to the socket's output stream.
		// add your code here
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());

		// Set up input stream filters using BufferedReader.
		// add your code here
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// Get the request line of the HTTP request message.
		// add your code here
		String requestLine = br.readLine();

		// Extract the filename from the requestLine using StringTokenizer.
		// add your code here
		StringTokenizer tokens = new StringTokenizer(requestLine);
		
		// Try to run to check what the tokens object contain 
		//System.out.println(tokens.countTokens());
		//System.out.println(tokens.nextToken());
		//System.out.println(tokens.nextToken());
		// Note tokens.nextToken(); skip over the method, which should be "GET"
		// add your code here
		tokens.nextToken();
		// Now you need to extract the requested file-name as string from the the tokens object
		// add your code here
		String fileName = tokens.nextToken();

		// Append a "public-html" to the file-name so that file request is within the web file directory which is public-html.
		// add your code here
		fileName = "public_html" + fileName; //the fileName will be in the format of /+fileName (e.g., /index.html)
		
		// Optional set index.html as default page when fileName is empty
		// add your code here
		if(fileName.equals("public_html/")) {
			fileName = fileName+"index.html";	
		}

		// Open the requested file using FileInputStream.
		// add your code here
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}

		// Debug info printed in the console for private use
		System.out.println("Incoming!!!");
		System.out.println(requestLine);
		System.out.println(fileName);
		String headerLine = null;
		while ((headerLine = br.readLine()).length() != 0) {
			System.out.println(headerLine);
		}
		

		// construct the response message.
		String statusLine = null; // use for status e.g. "HTTP/1.0 200 OK" or   HTTP/1.0 404 Not Found"
		String contentTypeLine = null; // use for content type e.g. "Content-Type: text/html"
		String entityBody = null; // use for entityBody in case file not found 
		
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-Type: " + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "Content-Type: text/html" + CRLF;
			entityBody = "<HTML>" + "<HEAD><TITLE>Not found 404</TITLE></HEAD>" + "<BODY>This file not found</BODY></HTML>";
		}
		
		// Send the status line using output stream -> writeBytes(status line).
		// add your code here
		os.writeBytes(statusLine);

		// Send the content type line using output stream -> writeBytes(content Type Line).
		// add your code here
		os.writeBytes(contentTypeLine);

		// Send a blank line to indicate the end of the header lines using output stream.
		// add your code here
		os.writeBytes(CRLF);

		// If fileExists - Send the entity body using the sendBytes method. If not fileExists - use output stream to writeBytes(entityBody);
		// add your code here
		if (fileExists) {
			sendBytes(fis, os);
			fis.close();
		} else {
			os.writeBytes(entityBody);
		}

		
		// Close streams and socket.
		is.close();
		os.close();
		br.close();
		socket.close();
	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;

		// Copy requested file into the socket's output stream.
		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		// Check if the file name end with ".html" or ".htm"
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		return "application/octet-stream";
	}
}