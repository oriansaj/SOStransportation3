package com.example.indygoreservation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PurchaseScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);
        super.displayToolbar(true);

        //Hides ad based on settings
        if(!Settings.getShowAds()) {
            TextView ad = (TextView) findViewById(R.id.textView5);
            ad.setVisibility(View.INVISIBLE);
            ad.setHeight(0);
        }
    }

    public void purchase(View view) {
        String emptyField = "";
        final String fullName;
        final String email;
        final String cardNum;
        final String cvc;
        final String expDate;
        final String ticketType;

        EditText cardNumBox = (EditText) findViewById(R.id.editTextNumberPassword);
        EditText cvcNumBox = (EditText) findViewById(R.id.editTextNumberPassword2);
        EditText expDataBox = (EditText) findViewById(R.id.editTextDate2);
        EditText fullNameBox = (EditText) findViewById(R.id.editTextTextPersonName6);

        cardNum = cardNumBox.getText().toString();
        cvc = cvcNumBox.getText().toString();
        expDate = expDataBox.getText().toString();
        fullName = fullNameBox.getText().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButton radioButton;

        if (cardNum.isEmpty())
        {
            emptyField = "Card number has not been entered";
        } else if (cvc.isEmpty())
        {
            emptyField = "CVC has not been entered";
        } else if (expDate.isEmpty())
        {
            emptyField = "Expiration date has not been entered";
        } else if (fullName.isEmpty())
        {
            emptyField = "Name has not been entered";
        }

        if (radioGroup.getCheckedRadioButtonId() == -1)
        {
            emptyField = "Ticket has not been selected";
            ticketType = "";
        }
        else {
            if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton) {
                radioButton = (RadioButton) findViewById(R.id.radioButton);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton2) {
                radioButton = (RadioButton) findViewById(R.id.radioButton2);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton3) {
                radioButton = (RadioButton) findViewById(R.id.radioButton3);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton4) {
                radioButton = (RadioButton) findViewById(R.id.radioButton4);
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton5) {
                radioButton = (RadioButton) findViewById(R.id.radioButton5);
            } else {
                radioButton = (RadioButton) findViewById(R.id.radioButton6);
            }
            ticketType = radioButton.getText().toString();
        }

        if (!emptyField.isEmpty())
        {
            Snackbar snackbar = Snackbar.make(view, emptyField, Snackbar.LENGTH_LONG);
            snackbar.show();
            return;
        }

        if(Settings.getLoginStatus()) {
            email = Settings.getEmail();
        } else {
            email = "";
        }
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    sendRequest("10.0.2.2", 5000, fullName, email, ticketType, view);
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendRequest(String address, int port, String fullname, String email, String ticketType, View view) throws IOException {
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
            dataOut.writeUTF("Purchase");
            dataOut.writeUTF(fullname);
            dataOut.writeUTF(email);
            dataOut.writeUTF(ticketType);
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
            downloadTicket(fullname, ticketType, view);
        }
    }

    /*
     *  Generates PDF with ticket if email confirmation is unvailable - STILL IN PROGRESS
     *  Code adapted from BlueApp Software Tutorial: https://www.blueappsoftware.com/how-to-create-pdf-file-in-android/
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void downloadTicket(String fullName, String ticketType, View view)
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        PdfDocument ticket = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 300, 1).create();
        PdfDocument.Page page = ticket.startPage(pageInfo);

        Canvas pageAppearance = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        pageAppearance.drawText("Hi " + fullName + ",", 15, 25, paint);
        pageAppearance.drawText("Thank you for purchasing a ticket ", 15, 55, paint);
        pageAppearance.drawText("with IndyGo! You purchased a ", 15, 75, paint);
        pageAppearance.drawText(ticketType + " Pass.", 15, 95, paint);
        pageAppearance.drawText("Information on how to track your bus ", 15, 115, paint);
        pageAppearance.drawText("can be found HERE, or plan your trip", 15, 135, paint);
        pageAppearance.drawText("HERE.", 15, 155, paint);
        pageAppearance.drawText("If you did not purchase this ticket, ", 15, 195, paint);
        pageAppearance.drawText("our contact information can be found HERE. ", 15, 215, paint);
        pageAppearance.drawText("Have a question? See if it's answered ", 15, 235, paint);
        pageAppearance.drawText("on our Frequently Asked Questions page.", 15, 255, paint);
        pageAppearance.drawText("We look forward to seeing you!", 15, 275, paint);

        ticket.finishPage(page);

        File file = new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/IndyGo-PurchaseConfirmation.pdf"));
        try {
            ticket.writeTo(new FileOutputStream(file));
        } catch (IOException e)
        {
            System.out.println(e);
        }
        ticket.close();

        Snackbar snackbar = Snackbar.make(view, "Purchase Confirmation Downloaded", Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}