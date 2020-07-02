package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuestScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_screen);
    }

    /** Called when the user taps the No button */
    public void no(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}