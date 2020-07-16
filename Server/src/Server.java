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

	// Inner class to simultaneously listen for new connections and check for
	// current reservations. Code based on example from geeksforgeeks.org
	// (https://www.geeksforgeeks.org/killing-threads-in-java/#:~:text=Modern%20ways%20to%20suspend%2Fstop,will%20be%20set%20to%20true.)
	class RemovalThread implements Runnable {

		Thread t;

		RemovalThread() {
			t = new Thread(this);
			t.start(); // Starting the thread
		}

		// execution of thread starts from run() method
		public void run() {
			while (!Thread.interrupted()) {
				remove();
			}
		}
	}

	
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

	/**
	 * Accepts a connection from a client, and creates a reservation based on the request
	 */
	private void acceptConnection() {
		try {
			System.out.println("Waiting for a client ...");
			//While waiting on a client, set up a thread to check if any existing reservations are outdated (are or were in use)
			RemovalThread thread = new RemovalThread();
			socket = server.accept();
			thread.t.interrupt();
			//CLose the thread to modify the HashMap it is iterating over
			System.out.println("Client accepted");

			// takes input from the client socket
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			try {
				Reservation r = new Reservation(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(),
						in.readUTF(), in.readUTF());
				System.out.println(add(r));
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

	/**
	 * Adds a reservation to the list if there is room
	 * 
	 * @param reservation to add
	 * @return whether it could be added
	 */
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

	/**
	 * Checks for any active or past reservations and removes them from memory
	 */
	private void remove() {
		Date now = new Date();
		for (String route : reservations.keySet()) {
			Reservation next = reservations.get(route).peek();
			if (next != null && (next.getStart().equals(now) || next.getStart().before(now))) {
				System.out.println(reservations.get(route).toString());
				reservations.get(route).poll();
				System.out.println(reservations.get(route).toString());
			}
		}
	}

	/**
	 * Set up a server and continually accept connections
	 */
	public static void main(String args[]) {
		Server server = new Server(5000);
		while (true) {
			server.acceptConnection();
			server.remove();
		}
	}
}