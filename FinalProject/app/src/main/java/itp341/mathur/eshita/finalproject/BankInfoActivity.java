package itp341.mathur.eshita.finalproject;

/**
 * Created by eshitamathur on 5/4/17.
 */
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BankInfoActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "itp341.firebase.EXTRA_URL";
    Button profile;
    Button rating;
    Button volunteer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = (Button)findViewById(R.id.profileButton);
        rating = (Button)findViewById(R.id.rateButton);
        volunteer = (Button)findViewById(R.id.volunteerButton);



        Intent i = getIntent();
        String reference = i.getStringExtra(EXTRA_URL);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        //Create the fragment if it doesn't exist
        if (f == null) {
            //f = TipFragment.newInstance();
            //FragmentTransaction ft = fm.beginTransaction();
            //ft.replace(R.id.fragment_container, f);
            //ft.commit();
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm1 = getSupportFragmentManager();
                Fragment a = fm1.findFragmentById(R.id.fragment_container);
                a = new ProfileActivity();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.fragment_container, a);
                ft1.commit();
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm2 = getSupportFragmentManager();
                Fragment b = fm2.findFragmentById(R.id.fragment_container);
                b = new RatingFragment();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragment_container, b);
                ft2.commit();

            }
        });

        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm3 = getSupportFragmentManager();
                Fragment c = fm3.findFragmentById(R.id.fragment_container);
                c = new VolunteerFragment();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.fragment_container, c);
                ft3.commit();

            }
        });
    }
}

