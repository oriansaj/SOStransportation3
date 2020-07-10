package com.example.indygoreservation;

import androidx.appcompat.app.AppCompatActivity;

public class GlobalData extends AppCompatActivity {

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
}

