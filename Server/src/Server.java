import java.net.*;
import java.io.*;

/**
 * A simple server to process reservations. Code adapted from geeksforgeeks.org
 * (https://www.geeksforgeeks.org/socket-programming-in-java/)
 * 
 * @author Andrew Orians, geeksforgeeks.org
 *
 */
public class Server {
	// initialize socket and input stream
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;

	// constructor with port
	public Server(int port) {
		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");

			while (true) {
				System.out.println("Waiting for a client ...");

				socket = server.accept();
				System.out.println("Client accepted");

				// takes input from the client socket
				in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

				try {
					Reservation r = new Reservation(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
							in.readUTF(), in.readUTF());
				} catch (IOException i) {
					System.out.println(i);
				}

				System.out.println("Closing connection");

				// close connection
				socket.close();
				in.close();
			}
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		Server server = new Server(5000);
	}
}