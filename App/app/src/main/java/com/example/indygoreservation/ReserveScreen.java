package com.example.indygoreservation;

import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

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
		} else {
			EditText firstnameBox = (EditText) findViewById(R.id.editTextTextPersonName);
			EditText lastnameBox = (EditText) findViewById(R.id.editTextTextPersonName2);
			firstnameBox.setVisibility(View.INVISIBLE);
			lastnameBox.setVisibility(View.INVISIBLE);
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

	/** Called when the user taps the Reserve button */
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
			@Override
			public void run() {
				try  {
					sendRequest("10.0.2.2", 5000, firstname, lastname, email, date, startTime, stopTime, route, notes);
				} catch (Exception e) {
					System.out.println("In button: " + e);
				}
			}
		});
		thread.start();
	}

	//Server stuff below here. Code adapted from geeksforgeeks.org (https://www.geeksforgeeks.org/socket-programming-in-java/)

	/**
	 * Sends a string (eventually the data of the reservation) to the server
	 * @param address
	 * @param port
	 */
	public void sendRequest(String address, int port, String firstname, String lastname, String email, String date, String startTime, String endTime, String route, String notes) throws IOException {
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
			downloadTicket();
		}
	}

	/*
	 *  Generates PDF with ticket if email confirmation is unvailable - STILL IN PROGRESS
	 *  Adaptation from documentation found here: https://developer.android.com/reference/android/graphics/pdf/PdfDocument
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public void downloadTicket()
	{
		PdfDocument ticket = new PdfDocument();
		PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, 1).create();
		PdfDocument.Page page = ticket.startPage(pageInfo);

		ticket.finishPage(page);
		File file = new File(Environment.getExternalStorageState(), "/ticket.pdf");
		try {
			ticket.writeTo(new FileOutputStream(file));
		} catch (IOException e)
		{
			System.out.println(e);
		}
		ticket.close();
	}
}