package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewAccount extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        super.displayToolbar(true);
    }

    /** Called when the user taps the Create Account button */
    public void create(View view) {
        Settings.setLoginStatus(true);
        EditText firstnameBox = (EditText) findViewById(R.id.editTextTextPersonName4);
        Settings.setFirstname(firstnameBox.getText().toString());
        EditText lastnameBox = (EditText) findViewById(R.id.editTextTextPersonName5);
        Settings.setLastname(lastnameBox.getText().toString());
        EditText emailBox = (EditText) findViewById(R.id.editTextTextEmailAddress);
        Settings.setEmail(emailBox.getText().toString());
        EditText phoneBox = (EditText) findViewById(R.id.editTextPhone);
        Settings.setPhone(phoneBox.getText().toString());
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }
}