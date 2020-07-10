package com.example.indygoreservation;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class ToolbarActivity extends AppCompatActivity {

    /*
     * Toolbar implementation adapted from:
     * https://developer.android.com/training/appbar/setting-up
     * https://stackoverflow.com/questions/26651602/display-back-arrow-on-toolbar
     * https://stackoverflow.com/questions/38435138/set-toolbar-for-all-activities
     */
    public void displayToolbar(boolean displayBackArrow) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (displayBackArrow == true)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    /** Called when the user taps the Settings button */
    public void settings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
