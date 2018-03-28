package edu.usc.cs404.catchup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.println;

public class SurveyActivity extends Activity {

    int preSelectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Button save = (Button) findViewById(R.id.save);
        ListView listView = (ListView) findViewById(R.id.listview);
        String selectedCuisine = "";


        final List<UserModel> users = new ArrayList<>();
        users.add(new UserModel(false, "American"));
        users.add(new UserModel(false, "Chinese"));
        users.add(new UserModel(false, "Ethiopian"));
        users.add(new UserModel(false, "French"));
        users.add(new UserModel(false, "Indian"));
        users.add(new UserModel(false, "Italian"));
        users.add(new UserModel(false, "Japanese"));
        users.add(new UserModel(false, "Korean"));
        users.add(new UserModel(false, "Mediterranean"));
        users.add(new UserModel(false, "Mexican"));
        users.add(new UserModel(false, "Peruvian"));
        users.add(new UserModel(false, "Spanish"));
        users.add(new UserModel(false, "Thai"));

        final CustomAdapter adapter = new CustomAdapter(this, users);
        listView.setAdapter(adapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //println("test");
                for(UserModel user: users){
                    if(user.isSelected()) {
                        System.out.println(user.getUserName());
                    }
                    //user.isSelected();
                }
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
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