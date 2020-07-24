package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PurchaseThanks extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_thanks);
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