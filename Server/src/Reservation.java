
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
	private String notes;

	public Reservation(String firstname, String lastname, String email, String date, String startTime, String endTime,
			String route, String notes) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		// Make a date object to represent the start time, and change time accordingly
		this.start = new Date(date);
		start.setSeconds(0);
		// The reservation should end on the same day, so get the date from the start
		// Date and change the time
		this.end = (Date) this.start.clone();
		end.setSeconds(0);
		//accounts for format X:XX (Non miltary time)
		if (startTime.length() == 4)
		{
			start.setMinutes(Integer.parseInt(startTime.substring(2, 4)));
			start.setHours(Integer.parseInt(startTime.substring(0, 1)));
			end.setMinutes(Integer.parseInt(endTime.substring(2, 4)));
			end.setHours(Integer.parseInt(endTime.substring(0, 1)));
		}
		//accounts for format XX:XX
		else
		{
			start.setMinutes(Integer.parseInt(startTime.substring(3, 5)));
			start.setHours(Integer.parseInt(startTime.substring(0, 2)));
			end.setMinutes(Integer.parseInt(endTime.substring(3, 5)));
			end.setHours(Integer.parseInt(endTime.substring(0, 2)));
		}
		this.route = route;
		this.notes = notes;
		
		System.out.println("Name: " + firstname + " " + lastname + "\nEmail: " + email + "\nDate: "
				+ this.start.toString() + "\nEnd: " + this.end.toString() + "\nRoute: " + route + "\nNotes: " + this.notes);
		//System.out.println(add(this));
		//if(!reservations.isEmpty()) {System.out.println("test");}
	}

	@Override
	public int compareTo(Reservation arg0) {
		return this.end.compareTo(arg0.getEnd());
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

	public String getEmail()
	{
		return email;
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
