package edu.usc.cs404.catchup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.support.v4.app.Fragment;

/**
 * Created by eshitamathur on 5/7/17.
 */

public class ProfileActivity extends Fragment{

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private Button save;
    private TextView textViewUserEmail;
    private EditText name;
    private EditText address;

    private DatabaseReference databaseReference;


    public ProfileActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_profile, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        textViewUserEmail = (TextView)v.findViewById(R.id.textViewProfile);
        name = (EditText)v.findViewById(R.id.profileName);
        address = (EditText)v.findViewById(R.id.profileAddress);
        buttonLogout = (Button)v.findViewById(R.id.logout_button);
        save = (Button)v.findViewById(R.id.save_button_profile);
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserEmail.setText("Welcome " + user.getEmail());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(getActivity(),"Logged Out", Toast.LENGTH_LONG).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

        return v;

    }

    private void saveUserInformation() {
        String nameString = name.getText().toString();
        String addressString = address.getText().toString();

        User newUser = new User();


        //firebase getting user to store info
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(newUser);

        Toast.makeText(getActivity(),"Information Saved...", Toast.LENGTH_LONG).show();
    }


}
