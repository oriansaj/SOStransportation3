package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OneTimePurchaseScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_purchase);
        super.displayToolbar(true);

        if(!Settings.getShowAds()) {
            TextView ad = (TextView) findViewById(R.id.textView4);
            ad.setVisibility(View.INVISIBLE);
            ad.setHeight(0);
        }
    }

    /** Called when the user taps the No button */
    public void no(View view) {
        Settings.setLoginStatus(false);
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Yes button */
    public void yes(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}