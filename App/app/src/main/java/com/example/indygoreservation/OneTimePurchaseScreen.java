package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OneTimePurchaseScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_purchase);
        super.displayToolbar(true);
    }

    /** Called when the user taps the No button */
    public void no(View view) {
        GlobalData.setLoginStatus(false);
        Intent intent = new Intent(this, PurchaseScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Yes button */
    public void yes(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}