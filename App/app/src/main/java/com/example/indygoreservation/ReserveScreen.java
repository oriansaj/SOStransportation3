package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ReserveScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_screen);

        /*
         *  Route drop-down functionality added on reservation screen
         *  Basis of implementation and explanation on dropdown functionality can be found here:
         *  https://developer.android.com/guide/topics/ui/controls/spinner#java
         */

        Spinner routeDropdown = findViewById(R.id.route);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.route_dropdown, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        routeDropdown.setAdapter(adapter);
        routeDropdown.setOnItemSelectedListener(this);
    }

    /*
     *  Sets selected item from dropdown to the one that has been clicked.
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        adapterView.getItemAtPosition(i);

    }

    /*
     *  If no dropdown item has been selected, sets the selection to the first item in list,
     *  which is not a route.
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        adapterView.getItemAtPosition(1);
    }
}