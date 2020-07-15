
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Reservation implements Comparable<Reservation> {

//	public static final int MAX_CAPACITY = 5; // arbitrary number
//
//	private static HashMap<String, PriorityQueue<Reservation>> reservations = new HashMap<String, PriorityQueue<Reservation>>();
	private String firstname;
	private String lastname;
	private String email;
	private Date start;
	private Date end;
	private String route;

	public Reservation(String firstname, String lastname, String email, String date, String startTime, String endTime,
			String route) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		// Make a date object to represent the start time, and change time accordingly
		this.start = new Date(date);
		start.setSeconds(0);
		start.setMinutes(Integer.parseInt(startTime.substring(3, 5)));
		start.setHours(Integer.parseInt(startTime.substring(0, 2)));
		// The reservation should end on the same day, so get the date from the start
		// Date and change the time
		this.end = (Date) this.start.clone();
		end.setSeconds(0);
		end.setMinutes(Integer.parseInt(endTime.substring(3, 5)));
		end.setHours(Integer.parseInt(endTime.substring(0, 2)));
		this.route = route;

		System.out.println("Name: " + firstname + " " + lastname + "\nEmail: " + email + "\nDate: "
				+ this.start.toString() + "\nEnd: " + this.end.toString() + "\nRoute: " + route);
		//System.out.println(add(this));
		//if(!reservations.isEmpty()) {System.out.println("test");}
	}

	@Override
	public int compareTo(Reservation arg0) {
		return this.start.compareTo(arg0.getStart());
	}

	public Date getStart() {
		return this.start;
	}

	public Date getEnd() {
		return this.end;
	}
	
	public String getRoute() {
		return this.route;
	}

	@Override
	public String toString() {
		return this.start.toString();
	}

	/**
	 * Checks if two reservations overlap (i.e. start or end time falls within the
	 * other, or if start/end time equals the other start/end time)
	 * 
	 * @param other reservation to check against
	 * @return if the two overlap
	 */
	public boolean overlaps(Reservation other) {
		return this.start.equals(other.getStart()) || this.end.equals(other.getEnd())
				|| (this.start.after(other.getStart()) && this.start.before(other.getEnd()))
				|| (this.end.after(other.getStart()) && this.end.before(other.getEnd()));
	}

	/**
	 * Adds a reservation to the list if there is room
	 * 
	 * @param reservation to add
	 * @return whether it could be added
	 */
	public static boolean add(Reservation toAdd) {
//		if (!reservations.containsKey(toAdd.route)) {
//			// There are no other reservations, so capacity is no issue. Just add it
//			PriorityQueue<Reservation> temp = new PriorityQueue<Reservation>();
//			temp.add(toAdd);
//			reservations.put(toAdd.route, temp);
//			return true;
//		} else {
//			// Check is there is enough room. Add and return true is there is, return false
//			// and do nothing otherwise
//			PriorityQueue<Reservation> line = reservations.get(toAdd.route);
//			if (line.size() < MAX_CAPACITY) {
//				// There are fewer reservations than the allowed maximum at a time. Capacity is
//				// no issue, so add it
//				reservations.get(toAdd.route).add(toAdd);
//				return true;
//			} else {
//				// Check how many reservations will be in use at the requested time. If it's
//				// more than the limit, deny it, otherwise add
//				int overlaps = 0;
//				for (Reservation r : line) {
//					if (toAdd.overlaps(r)) {
//						overlaps++;
//					}
//				}
//				if (overlaps < MAX_CAPACITY) {
//					reservations.get(toAdd.route).add(toAdd);
//					return true;
//				} else {
//					//System.out.println(reservations.get(toAdd.route).toString());
//					return false;
//				}
//			}
//		}
		return false;
	}

//	public static void main(String[] args) {
//		while (true) {
//			remove();
//		}
//	}

	/**
	 * Checks for any active or past reservations and removes them from memory
	 */
	private static void remove() {
//		Date now = new Date();
//		if(!reservations.isEmpty()) {System.out.println("yay!");}
//		for (String route : reservations.keySet()) {
//			Reservation next = reservations.get(route).peek();
//			if (next != null && (next.getStart().equals(now) || next.getStart().before(now))) {
//				System.out.println(reservations.get(route).toString());
//				reservations.get(route).poll();
//				System.out.println(reservations.get(route).toString());
//			}
//		}
	}
}
