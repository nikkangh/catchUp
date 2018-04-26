package edu.usc.cs404.catchup;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFERENCE_FILENAME = "edu.usc.cs404.catchup.pref_file";
    public static final String PREFERENCE_PREFS = "edu.usc.cs404.catchup.search_pref";

    private static String options[] = {"Foodie1, Foodie2, Foodie3, Foodie4, Foodie5, Foodie6, Foodie7, Foodie8, Foodie9"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences prefs = getSharedPreferences(
                SettingsActivity.PREFERENCE_FILENAME, MODE_PRIVATE);
        Set<String> s = prefs.getStringSet(
                SettingsActivity.PREFERENCE_PREFS, new HashSet<String>());

        if (s == null) {
            for (int i = 0; i < 9; i++) {
                s.add(options[i]);
            }
        }

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        int childCount = grid.getChildCount();
        for (int i= 0; i < childCount; i++){
            Button btn = (Button) grid.getChildAt(i);
            btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){

                    if (view.isSelected()) {
                        view.setSelected(false);
                    } else {
                        view.setSelected(true);
                    }
                }
            });
        }

    }
}
