package edu.usc.cs404.catchup;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SurveyActivity extends Activity {
    /*public static final String PREFERENCE_FILENAME = "edu.usc.cs404.catchup.pref_file";
    public static final String PREFERENCE_FOODPREFS = "edu.usc.cs404.catchup.pref_food";*/
    private EditText editText;
    private ListView listView;
    private Button saveButton;
    private SurveyAdapter adapter;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    ArrayList<String> preferences = new ArrayList<String>();

    ArrayList<SurveyItem> items = new ArrayList<SurveyItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        /*
        SharedPreferences prefs = getSharedPreferences(
                SurveyActivity.PREFERENCE_FILENAME, MODE_PRIVATE);
        Set<String> s = prefs.getStringSet(
                SurveyActivity.PREFERENCE_FOODPREFS, new HashSet<String>());*/

        listView = (ListView) findViewById(R.id.listview);
        adapter = new SurveyAdapter(this, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SurveyItem model = items.get(i);

                if (model.isSelected()) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }

                items.set(i, model);

                adapter.notifyDataSetChanged();
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    List<String> newItems = Arrays.asList(editText.getText().toString().split(","));
                    for (int i = 0; i < newItems.size(); i++) {
                        String newItem = newItems.get(i).trim();
                        if (!newItem.isEmpty()) {
                            items.add(new SurveyItem(true, newItem));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    listView.smoothScrollToPosition(items.size()-1);
                    editText.setText("");
                    return true;
                }
                return false;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference(); //get instance of database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //get your information
        databaseReference = databaseReference.child(firebaseUser.getUid()).child("surveyItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    SurveyItem item = itemSnapshot.getValue(SurveyItem.class);
                    items.add(item);
                }

                if (items.size() == 0) {
                    String[] initialItems = getResources().getStringArray(R.array.cuisineList);
                    for (int i = 0; i < initialItems.length; i++) {
                        items.add(new SurveyItem(false, initialItems[i]));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> newItems = Arrays.asList(editText.getText().toString().split(","));
                for (int i = 0; i < newItems.size(); i++) {
                    String newItem = newItems.get(i).trim();
                    if (!newItem.isEmpty()) {
                        items.add(new SurveyItem(true, newItem));
                    }
                }
                editText.setText("");

                databaseReference.setValue(items); //set surveyresults

                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(i);
                finish();
            }
        });

        //User userProperties = new User(); //create object of user properties
        //userProperties.setUsername(firebaseUser.getEmail()); //add your email to your properties

        /*for (int x = 0 ; x < results.length; x++) {
            if (results[x] != "") {
                preferences.add(results[x]); //convert to ArrayList
                Log.d("TAG", "putting into preferences: ");
                //Log.d("TAG", preferences.get(x));
            }

        }*/

        /*
        final String[] results = new String[getResources().getStringArray(R.array.cuisineList).length];

        final List<SurveyItem> users = new ArrayList<>();*/

        //TO add cuisines, go to res/values/strings.xml and add items in the cuisineList array

        /*for (int i = 0; i < getResources().getStringArray(R.array.cuisineList).length; i++)
            users.add(new SurveyItem(false,getResources().getStringArray(R.array.cuisineList)[i]));*/



        /*

        for (int i = 0; i < users.size(); i++) {
            if (s.contains(users.get(i).getUserName())) {
                users.get(i).setSelected(true);
            }
        }





        /*invite = (Button) findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InviteActivity.class);
                startActivity(i);
            }
        });*/
    }
}