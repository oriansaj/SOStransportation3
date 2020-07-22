package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReservationThanks extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_thanks);
        super.displayToolbar(true);
    }

    /** Called when the user taps the logout button. Clears all data and returns to main screen **/
    public void backToMain(View view) {
        Settings.setLoginStatus(false);
        Settings.setFirstname("");
        Settings.setLastname("");
        Settings.setEmail("");
        Settings.setPhone("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}