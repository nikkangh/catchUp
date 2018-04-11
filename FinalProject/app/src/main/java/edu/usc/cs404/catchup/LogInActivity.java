package edu.usc.cs404.catchup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;


public class LogInActivity extends AppCompatActivity {
    public static final String EXTRA_EMAIL = "edu.usc.cs404.catchup.email";
    public static final String EXTRA_PASSWORD = "edu.usc.cs404.catchup.password";
    public static final String EXTRA_NEWACCOUNT = "edu.usc.cs404.catchup.newaccount";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogIn;
    private Button buttonSignUp;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    //ANALYTICS
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent i = getIntent();
        String email = i.getStringExtra(EXTRA_EMAIL);
        String password = i.getStringExtra(EXTRA_PASSWORD);
        boolean loadFailed = i.getBooleanExtra(LoadingActivity.EXTRA_LOADFAILED, false);

        if (loadFailed) {
            Toast.makeText(LogInActivity.this, "Email/password combo incorrect, try again.", Toast.LENGTH_LONG).show();
        }

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //ANALYTICS
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        if (email != null) {
            editTextEmail.setText(email);
        }
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        if (password != null) {
            editTextPassword.setText(password);
        }

        buttonLogIn = (Button) findViewById(R.id.buttonLogIn);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ANALYTICS
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginClick1");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LogInActivity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                LogInUser(email, password);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                i.putExtra(EXTRA_EMAIL, editTextEmail.getText().toString());
                i.putExtra(EXTRA_PASSWORD, editTextPassword.getText().toString());
                startActivity(i);
            }
        });
    }

    private void LogInUser(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_LONG).show();
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter you password", Toast.LENGTH_LONG).show();
        }

        //ANALYTICS
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginClick2");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LogInActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        Intent i = new Intent(getApplicationContext(), FoodBankActivity.class);
        i.putExtra(EXTRA_EMAIL, editTextEmail.getText().toString());
        i.putExtra(EXTRA_PASSWORD, editTextPassword.getText().toString());
        i.putExtra(EXTRA_NEWACCOUNT, false);
        startActivity(i);
    }

}
