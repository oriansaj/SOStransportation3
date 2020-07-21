package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PurchaseScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);
        super.displayToolbar(true);

        if(!Settings.getShowAds()) {
            TextView ad = (TextView) findViewById(R.id.textView5);
            ad.setVisibility(View.INVISIBLE);
            ad.setHeight(0);
        }
    }
}