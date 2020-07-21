package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;

public class PurchaseScreen extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_screen);
        super.displayToolbar(true);
    }
}