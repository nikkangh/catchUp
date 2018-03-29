package edu.usc.cs404.catchup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        //CHECK database to see if email exists. if so, add friends!
        try {
            //Code for checking DB
            //code
            Toast.makeText(getApplicationContext(), "Friend-Name Added!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Here we can display a fail message and invite the friend to the app
            Toast.makeText(getApplicationContext(),
                    "Invite to Catchup sent!",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
