package edu.usc.cs404.catchup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
    }

    public void get(View v){
        EditText edit = (EditText)findViewById(R.id.emailText);
        String result = edit.getText().toString();

        //print email for debugging purposes
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        //sendEmail(result);
        //CHECK database to see if email exists. if so, add friends!
        try {
            //Code for checking DB
            //code
            Toast.makeText(getApplicationContext(), "Friend-Name Added!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Here we can display a fail message and invite the friend to the app
            sendEmail(result);
            Toast.makeText(getApplicationContext(),
                    "Invite to Catchup sent!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void sendEmail(String email) {

        String[] TO = {email};
//        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your Friend has invited you to Catchup!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EmailActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
