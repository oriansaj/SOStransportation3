
import java.time.LocalTime;

public class Reservation {
	private String firstname;
	private String lastname;
	private String email;
	private LocalTime startTime;
	private LocalTime endTime;

	public Reservation(String firstname, String lastname, String email, String startTime, String endTime) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.startTime = LocalTime.parse(startTime);
		this.endTime = LocalTime.parse(endTime);

		System.out.println("Name: " + firstname + " " + lastname + "\nEmail: " + email + "\nStart: "
				+ this.startTime.toString() + "\nEnd: " + this.endTime.toString());
	}
}
