
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
}
