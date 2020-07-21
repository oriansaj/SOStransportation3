package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SelectionScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
        super.displayToolbar(true);

        if(!Settings.getShowAds()) {
            TextView ad = (TextView) findViewById(R.id.textView3);
            ad.setVisibility(View.INVISIBLE);
            ad.setHeight(0);
        }
    }

     /** Called when the user taps the Reserve button **/
    public void reserve(View view) {
        Intent intent = new Intent(this, ReserveScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Logout button */
    public void logout(View view) {
        Settings.setLoginStatus(false);
        Settings.setFirstname("");
        Settings.setLastname("");
        Settings.setEmail("");
        Settings.setPhone("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Purchase button */
    public void purchase(View view) {
        Intent intent = new Intent(this, PurchaseScreen.class);
        startActivity(intent);
    }
}