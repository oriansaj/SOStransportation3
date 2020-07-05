package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.net.*;
import java.io.*;

public class ReserveScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_screen);

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    sendRequest("10.0.0.159", 5000);
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
    public void sendRequest(String address, int port) {
        Socket socket = null;
        DataOutputStream dataOut = null;

        // establish a connection
        try {
            socket = new Socket(address, port);

            // sends output to the socket
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch(Exception e) {System.out.println("In connect: " + e);}

        try {
            dataOut.writeUTF("test");
            System.out.println("Sent");
        } catch(Exception e) {System.out.println("Failed to send");}

        // close the connection
        try {
            dataOut.close();
            socket.close();
        } catch(Exception e) {System.out.println("Failed to close");}
    }
}