package com.example.indygoreservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;

public class NewAccount extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        super.displayToolbar(true);
    }

    /** Called when the user taps the Create Account button. Stores entered data and sends to selection screen */
    public void create(View view) {
        Settings.setLoginStatus(true);
        EditText usernameBox = (EditText) findViewById(R.id.editTextTextPersonName3);
        String username = usernameBox.getText().toString();
        EditText passBox = (EditText) findViewById(R.id.editTextTextPassword2);
        String pass = passBox.getText().toString();
        EditText confirmPassBox = (EditText) findViewById(R.id.editTextTextPassword3);
        String confirmPass = confirmPassBox.getText().toString();
        EditText firstnameBox = (EditText) findViewById(R.id.editTextTextPersonName4);
        String firstname = firstnameBox.getText().toString();
        Settings.setFirstname(firstname);
        EditText lastnameBox = (EditText) findViewById(R.id.editTextTextPersonName5);
        String lastname = lastnameBox.getText().toString();
        Settings.setLastname(lastname);
        EditText emailBox = (EditText) findViewById(R.id.editTextTextEmailAddress);
        String email = emailBox.getText().toString();
        Settings.setEmail(email);
        EditText phoneBox = (EditText) findViewById(R.id.editTextPhone);
        String phone = phoneBox.getText().toString();
        Settings.setPhone(phone);
        //Make sure password equals confirm password, then update database
        if(pass.equals(confirmPass)) {
            updateDB(username,pass,firstname,lastname,email,phone);
        }
        Intent intent = new Intent(this, SelectionScreen.class);
        startActivity(intent);
    }

    /**
     * Connects to and updates a mySQL database. We bean implementing this, but not fully. This would be a feature for a full release
     * @param username
     * @param password
     * @param first
     * @param last
     * @param email
     * @param phone
     */
    private void updateDB(String username, String password, String first, String last, String email, String phone) {
//        try {
//            String driver = "com.mysql.cj.jdbc.Driver";
//            String url = "jdbc:mysql://10.0.2.2:3306/accounts";
//            String uname = "root";
//            String pass = "transportation3";
//            Class.forName(driver);
//
//            Connection con = DriverManager.getConnection(url, uname, pass);
//            Statement stmt = con.createStatement();
//            stmt.execute("INSERT INTO users VALUES ('" +username+ "', '" +password+ "', '" +first+ "', '" +last+ "', '" +email + "', '" + phone + "')");
//            con.close();
//        } catch (Exception e) {e.printStackTrace();}
    }
}