import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
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
	public static final int MAX_CAPACITY = 5; // arbitrary number
	private HashMap<String, PriorityQueue<Reservation>> reservations = new HashMap<String, PriorityQueue<Reservation>>();

	// constructor with port
	public Server(int port) {
		// starts server and waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	private void acceptConnection() {
		try {
			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			try {
				Reservation r = new Reservation(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
						in.readUTF(), in.readUTF());
				add(r);
				System.out.println(reservations.toString());
			} catch (IOException i) {
				System.out.println(i);
			}

			System.out.println("Closing connection");

			// close connection
			socket.close();
			in.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	private boolean add(Reservation toAdd) {
		if (!reservations.containsKey(toAdd.getRoute())) {
			// There are no other reservations, so capacity is no issue. Just add it
			PriorityQueue<Reservation> temp = new PriorityQueue<Reservation>();
			temp.add(toAdd);
			reservations.put(toAdd.getRoute(), temp);
			return true;
		} else {
			// Check is there is enough room. Add and return true is there is, return false
			// and do nothing otherwise
			PriorityQueue<Reservation> line = reservations.get(toAdd.getRoute());
			if (line.size() < MAX_CAPACITY) {
				// There are fewer reservations than the allowed maximum at a time. Capacity is
				// no issue, so add it
				reservations.get(toAdd.getRoute()).add(toAdd);
				return true;
			} else {
				// Check how many reservations will be in use at the requested time. If it's
				// more than the limit, deny it, otherwise add
				int overlaps = 0;
				for (Reservation r : line) {
					if (toAdd.overlaps(r)) {
						overlaps++;
					}
				}
				if (overlaps < MAX_CAPACITY) {
					reservations.get(toAdd.getRoute()).add(toAdd);
					return true;
				} else {
					// System.out.println(reservations.get(toAdd.route).toString());
					return false;
				}
			}
		}

	}

	private void remove() {
		Date now = new Date();
		if (!reservations.isEmpty()) {
			System.out.println("yay!");
		}
		for (String route : reservations.keySet()) {
			Reservation next = reservations.get(route).peek();
			if (next != null && (next.getStart().equals(now) || next.getStart().before(now))) {
				System.out.println(reservations.get(route).toString());
				reservations.get(route).poll();
				System.out.println(reservations.get(route).toString());
			}
		}
	}

	public static void main(String args[]) {
		Server server = new Server(5000);
		while (true) {
			server.acceptConnection();
			server.remove();
		}
	}
}