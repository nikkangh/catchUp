package edu.usc.cs404.catchup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoadingActivity extends AppCompatActivity {
    public static final String EXTRA_LOADFAILED = "edu.usc.cs404.catchup.loadfailed";

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Intent i = getIntent();
        email = i.getStringExtra(LogInActivity.EXTRA_EMAIL);
        password = i.getStringExtra(LogInActivity.EXTRA_PASSWORD);
        Boolean newAccount = i.getBooleanExtra(LogInActivity.EXTRA_NEWACCOUNT, true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (newAccount) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(), SurveyActivity.class);
                                startActivity(i);
                            }
                            else {
                                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                                i.putExtra(LogInActivity.EXTRA_EMAIL, email);
                                i.putExtra(LogInActivity.EXTRA_PASSWORD, password);
                                i.putExtra(EXTRA_LOADFAILED, true);
                                startActivity(i);
                            }
                        }
                    });
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //If user cuisine preference from data base is empty, then go to SURVEY:
                            //Intent i = new Intent(getApplicationContext(), SurveyActivity.class);
                            Intent i = new Intent(getApplicationContext(), FoodBankActivity.class);
                            startActivity(i);

                            //ELSE go to SEARCH
//                            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
//                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(getApplicationContext(), LogInActivity.class);
                            i.putExtra(LogInActivity.EXTRA_EMAIL, email);
                            i.putExtra(LogInActivity.EXTRA_PASSWORD, password);
                            i.putExtra(EXTRA_LOADFAILED, true);
                            startActivity(i);
                        }
                    }
                });

        }
    }
}
