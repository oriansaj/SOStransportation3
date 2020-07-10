package com.example.indygoreservation;

import android.os.Bundle;

public class Settings extends ToolbarActivity {

    private static boolean loginStatus;

    /**
     * Setter/getter functions for logged in info
     */
    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setLoginStatus(boolean loggedIn) {
        loginStatus = loggedIn;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        super.displayToolbar(true);
    }
}

