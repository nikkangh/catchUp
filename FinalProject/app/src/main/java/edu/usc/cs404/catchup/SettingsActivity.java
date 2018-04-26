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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        int childCount = grid.getChildCount();
        for (int i= 0; i < childCount; i++){
            Button btn = (Button) grid.getChildAt(i);
            btn.setTag(i);
            btn.setText(DataModel.getInstance().getString(i));
            btn.setSelected(DataModel.getInstance().isSelected(i));
            btn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    if (view.isSelected()) {
                        view.setSelected(false);
                        DataModel.getInstance().unSelectTerm((Integer) view.getTag());
                    } else {
                        view.setSelected(true);
                        DataModel.getInstance().selectTerm((Integer) view.getTag());
                    }
                }
            });
        }
    }
}
