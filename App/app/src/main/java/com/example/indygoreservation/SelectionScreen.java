package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectionScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_screen);
    }

     /** Called when the user taps the Reserve button **/
    public void reserve(View view) {
        Intent intent = new Intent(this, ReserveScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Logout button */
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Purchase button */
    public void purchase(View view) {
        Intent intent = new Intent(this, PurchaseScreen.class);
        startActivity(intent);
    }
}