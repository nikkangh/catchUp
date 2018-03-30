package edu.usc.cs404.catchup;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.sql.DriverManager.println;

public class SurveyActivity extends Activity {
    public static final String PREFERENCE_FILENAME = "edu.usc.cs404.catchup.pref_file";
    public static final String PREFERENCE_FOODPREFS = "edu.usc.cs404.catchup.pref_food";

    int preSelectedIndex = -1;
    private Button invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        SharedPreferences prefs = getSharedPreferences(
                SurveyActivity.PREFERENCE_FILENAME, MODE_PRIVATE);
        Set<String> s = prefs.getStringSet(
                SurveyActivity.PREFERENCE_FOODPREFS, new HashSet<String>());

        Button save = (Button) findViewById(R.id.save);
        ListView listView = (ListView) findViewById(R.id.listview);
        final String[] results = new String[getResources().getStringArray(R.array.cuisineList).length];
        //final Bundle surveyResults = new Bundle();


        final List<UserModel> users = new ArrayList<>();


        //TO add cuisines, go to res/values/strings.xml and add items in the cuisineList array

        for (int i = 0; i < getResources().getStringArray(R.array.cuisineList).length; i++)
            users.add(new UserModel(false,getResources().getStringArray(R.array.cuisineList)[i]));
//        users.add(new UserModel(false, "American"));
//        users.add(new UserModel(false, "Chinese"));
//        users.add(new UserModel(false, "Ethiopian"));
//        users.add(new UserModel(false, "French"));
//        users.add(new UserModel(false, "Indian"));
//        users.add(new UserModel(false, "Italian"));
//        users.add(new UserModel(false, "Japanese"));
//        users.add(new UserModel(false, "Korean"));
//        users.add(new UserModel(false, "Mediterranean"));
//        users.add(new UserModel(false, "Mexican"));
//        users.add(new UserModel(false, "Peruvian"));
//        users.add(new UserModel(false, "Spanish"));
//        users.add(new UserModel(false, "Thai"));

        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);

        for (int i = 0; i < users.size(); i++) {
            if (s.contains(users.get(i).getUserName())) {
                users.get(i).setSelected(true);
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //println("test");
                Set<String> terms = new HashSet<>();

                int counter = 0;
                for(UserModel user: users){

                    if(user.isSelected()) {
                        //System.out.println(user.getUserName());
                        results[counter] = user.getUserName();
                        terms.add(user.getUserName());
                        counter++;
                    }
                    else {
                        results[counter] = "";
                        counter++;
                    }
                    //user.isSelected();
                }
                //surveyResults.putStringArray("surveyKey",results);

                SharedPreferences prefs = getSharedPreferences(
                        PREFERENCE_FILENAME, MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefs.edit();
                prefEditor.putStringSet(PREFERENCE_FOODPREFS, terms);
                prefEditor.commit();

                Intent i = new Intent(getApplicationContext(), FoodBankActivity.class);
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

        invite = (Button) findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InviteActivity.class);
                startActivity(i);
            }
        });
    }
}