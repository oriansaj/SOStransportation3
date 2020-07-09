
import java.time.LocalTime;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Reservation implements Comparable<Reservation> {

	public static final int MAX_CAPACITY = 5; // arbitrary number

	private static HashMap<String, PriorityQueue<Reservation>> reservations = new HashMap<String, PriorityQueue<Reservation>>();

	private String firstname;
	private String lastname;
	private String email;
	private LocalTime startTime;
	private LocalTime endTime;
	private String route;

	public Reservation(String firstname, String lastname, String email, String startTime, String endTime,
			String route) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.startTime = LocalTime.parse(startTime).minusHours(-4);
		this.endTime = LocalTime.parse(endTime).minusHours(-4);
		this.route = route;

		System.out.println("Name: " + firstname + " " + lastname + "\nEmail: " + email + "\nStart: "
				+ this.startTime.toString() + "\nEnd: " + this.endTime.toString() + "\nRoute: " + route);
		System.out.println(add(this));
	}

	@Override
	public int compareTo(Reservation arg0) {
		return this.startTime.compareTo(arg0.getStartTime());
	}

	public LocalTime getStartTime() {
		return this.startTime;
	}

	public LocalTime getEndTime() {
		return this.endTime;
	}

	@Override
	public String toString() {
		return this.startTime.toString();
	}

	/**
	 * Checks if two reservations overlap (i.e. start or end time falls within the
	 * other, or if start/end time equals the other start/end time)
	 * 
	 * @param other reservation to check against
	 * @return if the two overlap
	 */
	public boolean overlaps(Reservation other) {
		return this.startTime.equals(other.startTime) || this.endTime.equals(other.endTime)
				|| (this.startTime.isAfter(other.getStartTime()) && this.startTime.isBefore(other.getEndTime()))
				|| (this.endTime.isAfter(other.getStartTime()) && this.endTime.isBefore(other.getEndTime()));
	}

	/**
	 * Adds a reservation to the list if there is room
	 * 
	 * @param reservation to add
	 * @return whether it could be added
	 */
	private static boolean add(Reservation toAdd) {
		if (!reservations.containsKey(toAdd.route)) {
			// There are no other reservations, so capacity is no issue. Just add it
			PriorityQueue<Reservation> temp = new PriorityQueue<Reservation>();
			temp.add(toAdd);
			reservations.put(toAdd.route, temp);
			return true;
		} else {
			// Check is there is enough room. Add and return true is there is, return false
			// and do nothing otherwise
			PriorityQueue<Reservation> line = reservations.get(toAdd.route);
			if (line.size() < MAX_CAPACITY) {
				// There are fewer reservations than the allowed maximum at a time. Capacity is
				// no issue, so add it
				reservations.get(toAdd.route).add(toAdd);
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
					reservations.get(toAdd.route).add(toAdd);
					return true;
				} else {
					return false;
				}
			}
			// System.out.println(reservations.get(route).toString());
		}
	}
}
