package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewAccount extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        super.displayToolbar(true);
    }

    /** Called when the user taps the Guest button */
    public void create(View view) {
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }
}