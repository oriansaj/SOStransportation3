package com.example.indygoreservation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends ToolbarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.displayToolbar(false);
    }

    /** Called when the user taps the Login button. Goes to selection screen and stores dummy data. */
    public void login(View view) {
        Settings.setLoginStatus(true);
        Settings.setFirstname("Example");
        Settings.setLastname("User");
        Settings.setEmail("user@example.com");
        Settings.setPhone("3175554639");
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the Guest button. Sends to screen explaining guest status */
    public void oneTimePurchase(View view) {
        Intent intent = new Intent(this, OneTimePurchaseScreen.class);
        startActivity(intent);
    }

    /** Called when the user taps the New Account button. Sends to new account page */
    public void create(View view) {
        Intent intent = new Intent(this, NewAccount.class);
        startActivity(intent);
    }
}