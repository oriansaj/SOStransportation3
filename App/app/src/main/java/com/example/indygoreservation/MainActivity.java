package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Login button */
    public void login(View view) {
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Guest button */
    public void oneTimePurchase(View view) {
        Intent intent = new Intent(this, OneTimePurchaseScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the New Account button */
    public void create(View view) {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }
}