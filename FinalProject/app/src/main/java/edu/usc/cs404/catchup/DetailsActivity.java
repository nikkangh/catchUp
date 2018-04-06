package edu.usc.cs404.catchup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MARKERTAG = "edu.usc.cs404.catchup.markertag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int index = getIntent().getExtras().getInt(EXTRA_MARKERTAG);
    }


}
