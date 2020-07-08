
import java.time.LocalTime;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Reservation implements Comparable<Reservation> {

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

		if (!reservations.containsKey(route)) {
			PriorityQueue<Reservation> temp = new PriorityQueue<Reservation>();
			temp.add(this);
			reservations.put(route, temp);
		} else {
			reservations.get(route).add(this);
			// System.out.println(reservations.get(route).toString());
		}
	}

	@Override
	public int compareTo(Reservation arg0) {
		return this.startTime.compareTo(arg0.getStartTime());
	}

	public LocalTime getStartTime() {
		return this.startTime;
	}

	@Override
	public String toString() {
		return this.startTime.toString();
	}
}
