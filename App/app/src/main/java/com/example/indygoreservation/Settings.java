package com.example.indygoreservation;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends ToolbarActivity {

    private static boolean loginStatus;
    private static boolean settingsActive;
    private static String firstname;
    private static String lastname;
    private static String email;
    private static String phone;
    private static boolean showAds = true;

    /**
     * Setter/getter functions for logged in info
     */
    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setLoginStatus(boolean loggedIn) {
        loginStatus = loggedIn;
    }

    public static String getFirstname() {return firstname;}

    public static void  setFirstname(String newName) {firstname = newName;}

    public static String getLastname() {return lastname;}

    public static void  setLastname(String newName) {lastname = newName;}

    public static String getEmail() {return email;}

    public static void  setEmail(String newEmail) {email = newEmail;}

    public static String getPhone() {return phone;}

    public static void  setPhone(String newPhone) {phone = newPhone;}

    public static boolean getSettingsPageActive()
    {
        return settingsActive;
    }

    public static void setSettingsPageActivity(boolean active)
    {
        settingsActive = active;
    }

    public static boolean getShowAds() {return showAds;}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSettingsPageActivity(true);
        super.displayToolbar(true);

        Switch ads = (Switch) findViewById(R.id.automatedCouponRecommendationSwitch);
        ads.setChecked(showAds);
        ads.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showAds = true;
                } else {
                    showAds = false;
                }
            }
        });
    }

    protected void onDestroy() {
        setSettingsPageActivity(false);
        super.onDestroy();
    }
}

