package edu.usc.cs404.catchup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
//ANALYTICS
import com.google.firebase.analytics.FirebaseAnalytics;
//HOCKEY
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonLogIn;
    private Button buttonSignUp;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    //ANALYTICS
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent i = getIntent();
        String email = i.getStringExtra(LogInActivity.EXTRA_EMAIL);
        String password = i.getStringExtra(LogInActivity.EXTRA_PASSWORD);
        boolean loadFailed = i.getBooleanExtra(LoadingActivity.EXTRA_LOADFAILED, false);

        if (loadFailed) {
            Toast.makeText(SignUpActivity.this, "Sign Up Not Successful, Try Again!", Toast.LENGTH_LONG).show();
        }

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //ANALYTICS
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        if (email != null) {
            editTextEmail.setText(email);
        }
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        if (password != null) {
            editTextPassword.setText(password);
        }

        buttonLogIn = (Button)findViewById(R.id.buttonLogIn);
        buttonSignUp = (Button)findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ANALYTICS
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SignUpClick1");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SignUpActivity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                String emailString = editTextEmail.getText().toString();
                String passwordString = editTextPassword.getText().toString();
                signUpUser(emailString, passwordString);
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                i.putExtra(LogInActivity.EXTRA_EMAIL, editTextEmail.getText().toString());
                i.putExtra(LogInActivity.EXTRA_PASSWORD, editTextPassword.getText().toString());
                startActivity(i);
            }
        });


        //HOCKEY
        checkForUpdates();
        MetricsManager.register(getApplication());
    }

    private void signUpUser(String email, String password) {

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Please enter a password that is longer than 6 characters", Toast.LENGTH_LONG).show();
            return;
        }

        //ANALYTICS
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "SignUpClick2");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "SignUpActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        MetricsManager.trackEvent("LoginClick2");

        Intent i = new Intent(getApplicationContext(), LoadingActivity.class);
        i.putExtra(LogInActivity.EXTRA_EMAIL, editTextEmail.getText().toString());
        i.putExtra(LogInActivity.EXTRA_PASSWORD, editTextPassword.getText().toString());
        i.putExtra(LogInActivity.EXTRA_NEWACCOUNT, true);
        startActivity(i);
    }



    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
