package com.example.indygoreservation;

import android.os.Bundle;

public class Settings extends ToolbarActivity {

    private static boolean loginStatus;
    private static boolean settingsActive;

    /**
     * Setter/getter functions for logged in info
     */
    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setLoginStatus(boolean loggedIn) {
        loginStatus = loggedIn;
    }

    public static boolean getSettingsPageActive()
    {
        return settingsActive;
    }

    public static void setSettingsPageActivity(boolean active)
    {
        settingsActive = active;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSettingsPageActivity(true);
        super.displayToolbar(true);
    }

    protected void onDestroy() {
        setSettingsPageActivity(false);
        super.onDestroy();
    }
}

