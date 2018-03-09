package itp341.mathur.eshita.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//ANALYTICS
import com.google.firebase.analytics.FirebaseAnalytics;
//HOCKEY
import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;
import net.hockeyapp.android.metrics.MetricsManager;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button newUserButton;
    private EditText email;
    private EditText password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    //ANALYTICS
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //ANALYTICS
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        loginButton = (Button)findViewById(R.id.login_button);
        newUserButton = (Button)findViewById(R.id.new_user_button);
        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ANALYTICS
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginClick1");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LoginActivity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                Intent i = new Intent(getApplicationContext(), NewUserActivity.class);
                startActivity(i);
            }
        });

        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ANALYTICS
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginClick1");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LoginActivity");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                if (!emailString.isEmpty() && !passwordString.isEmpty()) {
                    registerUser(emailString, passwordString);
                    Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(i);
                }
            }
        });
        //HOCKEY
        checkForUpdates();
        MetricsManager.register(getApplication());
    }

    private void registerUser(String email1, String password1) {
        String emailString = email1;
        String passwordString = password1;

        if (TextUtils.isEmpty(emailString)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        //ANALYTICS
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginClick2");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "LoginActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        MetricsManager.trackEvent("LoginClick2");


        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Not Successful, Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
