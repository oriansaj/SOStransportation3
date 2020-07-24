package com.example.indygoreservation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.net.*;
import java.io.*;
import java.util.Date;

public class ReserveScreen extends ToolbarActivity implements AdapterView.OnItemSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve_screen);
		super.displayToolbar(true);

		/*
		 *  Route drop-down functionality added on reservation screen
		 *  Basis of implementation and explanation on dropdown functionality can be found here:
		 *  https://developer.android.com/guide/topics/ui/controls/spinner#java
		 */
		Spinner routeDropdown = findViewById(R.id.route);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.route_dropdown, R.layout.support_simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
		routeDropdown.setAdapter(adapter);
		routeDropdown.setOnItemSelectedListener(this);

		//Set date and name boxes visible/invisible based on login status
		if(!Settings.getLoginStatus()) {
			EditText dateBox = (EditText) findViewById(R.id.editTextDate);
			dateBox.setVisibility(View.INVISIBLE);
			dateBox.setHeight(0);
		} else {
			EditText firstnameBox = (EditText) findViewById(R.id.editTextTextPersonName);
			EditText lastnameBox = (EditText) findViewById(R.id.editTextTextPersonName2);
			firstnameBox.setVisibility(View.INVISIBLE);
			lastnameBox.setVisibility(View.INVISIBLE);
			firstnameBox.setHeight(0);
			lastnameBox.setHeight(0);
		}

		//Show or hide ad based on settings
		if(!Settings.getShowAds()) {
			TextView ad = (TextView) findViewById(R.id.textView6);
			ad.setVisibility(View.INVISIBLE);
			ad.setHeight(0);
		}
	}

	/*
	 *  Sets selected item from dropdown to the one that has been clicked.
	 */
	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		adapterView.getItemAtPosition(i);

	}

	/*
	 *  If no dropdown item has been selected, sets the selection to the first item in list,
	 *  which is not a route.
	 */
	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		adapterView.getItemAtPosition(1);
	}

	/** Called when the user taps the Reserve button. Sends entered data to the server */
	public void reserve(View view) {
		final String firstname;
		final String lastname;
		final String email;
		final String date;
		if(Settings.getLoginStatus()) {
			firstname = Settings.getFirstname();
			lastname = Settings.getLastname();
			email = Settings.getEmail();
			EditText dateBox = (EditText) findViewById(R.id.editTextDate);
			date = dateBox.getText().toString();
		} else {
			EditText firstnameBox = (EditText) findViewById(R.id.editTextTextPersonName);
			firstname = firstnameBox.getText().toString();
			EditText lastnameBox = (EditText) findViewById(R.id.editTextTextPersonName2);
			lastname = lastnameBox.getText().toString();
			email = "";

			Date now = new Date();
			date = now.toString();
		}
		EditText startEnter = (EditText) findViewById(R.id.editTextTime);
		final String startTime = startEnter.getText().toString(); //There's probably an issue with making this final, but it works

		EditText stopEnter = (EditText) findViewById(R.id.editTextTime2);
		final String stopTime = stopEnter.getText().toString();

		EditText notesEnter = (EditText) findViewById(R.id.editTextTextMultiLine);
		final String notes = notesEnter.getText().toString();

		Spinner routeDropdown = findViewById(R.id.route);
		final String route = routeDropdown.getSelectedItem().toString();

		Thread thread = new Thread(new Runnable() {
			@RequiresApi(api = Build.VERSION_CODES.KITKAT)
			@Override
			public void run() {
				try  {
					sendRequest("10.0.2.2", 5000, firstname, lastname, email, date, startTime, stopTime, route, notes, view);
				} catch (Exception e) {
					System.out.println("In button: " + e);
				}
			}
		});
		thread.start();
		Intent intent = new Intent(this, ReservationThanks.class);
		startActivity(intent);
	}

	//Server stuff below here. Code adapted from geeksforgeeks.org (https://www.geeksforgeeks.org/socket-programming-in-java/)
	/**
	 * Sends the data of the reservation to the server, and determines whether or not to download the confirmation as a PDF
	 * @param address
	 * @param port
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void sendRequest(String address, int port, String firstname, String lastname, String email, String date, String startTime, String endTime, String route, String notes, View view) throws IOException {
		Socket socket = null;
		DataOutputStream dataOut = null;
		DataInputStream dataIn = null;
		boolean downloadPDF = false;

		// establish a connection
		try {
			socket = new Socket(address, port);

			// sends output to the socket
			dataOut = new DataOutputStream(socket.getOutputStream());
			// gets data back from server
			dataIn = new DataInputStream(socket.getInputStream());
		} catch(Exception e) {System.out.println("In connect: " + e);}

		try {
			//Send data
			dataOut.writeUTF("Reservation");
			dataOut.writeUTF(firstname);
			dataOut.writeUTF(lastname);
			dataOut.writeUTF(email);
			dataOut.writeUTF(date);
			dataOut.writeUTF(startTime);
			dataOut.writeUTF(endTime);
			dataOut.writeUTF(route);
			dataOut.writeUTF(notes);
		} catch(Exception e) {System.out.println("Failed to send");}

		do
		{
			try {
				//Read server response and act accordingly
				String input = dataIn.readUTF();
				if (input == null)
				{
					continue;
				}
				else if (input.compareTo("Download") == 0)
				{
					downloadPDF = true;
					break;
				}
				else if (input.compareTo("TicketProcessed") != 0)
				{
					break;
				}
			} catch (EOFException e)
			{
				System.out.println(e);
				break;
			} catch (IOException ex)
			{
				System.out.println(ex);
				break;
			}

		} while (true);

		// close the connection
		try {
			dataOut.close();
			dataIn.close();
			socket.close();
		} catch(Exception e) {System.out.println("Failed to close");}

		if (downloadPDF == true)
		{
			downloadTicket(firstname, date, route, view);
		}
	}

	/*
	 *  Generates PDF with ticket if email confirmation is unavailable
	 *  Code adapted from BlueApp Software Tutorial: https://www.blueappsoftware.com/how-to-create-pdf-file-in-android/
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void downloadTicket(String firstName, String date, String route, View view)
	{
		ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

		PdfDocument ticket = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 300, 1).create();
		PdfDocument.Page page = ticket.startPage(pageInfo);

		Canvas pageAppearance = page.getCanvas();
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		pageAppearance.drawText("Hi " + firstName + ",", 15, 25, paint);
		pageAppearance.drawText("Your IndyGo bus seat reservation has been booked!", 15, 55, paint);
		pageAppearance.drawText("We'll save you a seat on the: ", 15, 75, paint);
		pageAppearance.drawText(route, 15, 95, paint);
		pageAppearance.drawText("on " + date, 15, 115, paint);
		pageAppearance.drawText("Have a question? See if it's answered on our ", 15, 135, paint);
		pageAppearance.drawText("Frequently Asked Questions page.", 15, 155, paint);
		pageAppearance.drawText("We look forward to seeing you!", 15, 175, paint);

		ticket.finishPage(page);

		File file = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/IndyGo-ReservationTicket.pdf"));
		try {
			ticket.writeTo(new FileOutputStream(file));
		} catch (IOException e)
		{
			System.out.println(e);
		}
		ticket.close();

		Snackbar snackbar = Snackbar.make(view, "Reservation Ticket Downloaded", Snackbar.LENGTH_LONG);
		snackbar.show();
	}

}