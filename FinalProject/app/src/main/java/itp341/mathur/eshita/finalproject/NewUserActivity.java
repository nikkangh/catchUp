package itp341.mathur.eshita.finalproject;

/**
 * Created by eshitamathur on 5/4/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.io.Serializable;


public class NewUserActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button submit;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        username = (EditText) findViewById(R.id.new_user_username);
        password = (EditText) findViewById(R.id.new_user_password);
        submit = (Button) findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = username.getText().toString();
                String passwordString = password.getText().toString();
                registerUser(nameString, passwordString);
            }
        });
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

        progressDialog.setMessage("Signing you in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            //start activity
                        }
                        else {
                            Toast.makeText(NewUserActivity.this, "You entered incorrect info, try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




}
