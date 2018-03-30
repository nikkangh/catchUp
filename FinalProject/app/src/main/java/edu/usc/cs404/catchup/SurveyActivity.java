package edu.usc.cs404.catchup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class SurveyActivity extends Activity {

    int preSelectedIndex = -1;

    //database stuff
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    ArrayList<String> preferences = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Button save = (Button) findViewById(R.id.save);
        ListView listView = (ListView) findViewById(R.id.listview);
        String selectedCuisine = "";
        final String[] results = new String[getResources().getStringArray(R.array.cuisineList).length];

        //final Bundle surveyResults = new Bundle();


        final List<UserModel> users = new ArrayList<>();


        //TO add cuisines, go to res/values/strings.xml and add items in the cuisineList array

        for (int i = 0; i < getResources().getStringArray(R.array.cuisineList).length; i++)
            users.add(new UserModel(false,getResources().getStringArray(R.array.cuisineList)[i]));


        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //println("test");
                int counter = 0;
                for(UserModel user: users){

                    if(user.isSelected()) {
                        //System.out.println(user.getUserName());
                        results[counter] = user.getUserName();
                        counter++;
                    }
                    else {
                        results[counter] = "";
                        counter++;
                    }
                    //user.isSelected();
                }
                //surveyResults.putStringArray("surveyKey",results);
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);

                //database stuff
                databaseReference = FirebaseDatabase.getInstance().getReference(); //get instance of database
                firebaseAuth = FirebaseAuth.getInstance(); //get instance of authentication
                FirebaseUser user = firebaseAuth.getCurrentUser(); //get your information
                User userProperties = new User(); //create object of user properties
                userProperties.setUsername(user.getEmail()); //add your email to your properties

                for (int x = 0 ; x < results.length; x++) {
                    preferences.add(results[x]); //convert to ArrayList

                }

                userProperties.setSurveyResults(preferences); //add survey results to your properties
                databaseReference.child(user.getUid()).setValue(userProperties); //set surveyresults



                i.putExtra("key", results);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserModel model = users.get(i);

                if (model.isSelected())
                    model.setSelected(false);

                else
                    model.setSelected(true);

                users.set(i, model);

                //now update adapter
                adapter.updateRecords(users);
            }
        });

    }
}