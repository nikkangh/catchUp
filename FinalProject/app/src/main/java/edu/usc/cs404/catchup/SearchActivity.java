package edu.usc.cs404.catchup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;


public class SearchActivity extends AppCompatActivity {
    private EditText editTextSearchTerm;

    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextSearchTerm = (EditText)findViewById(R.id.editTextSearchTerm);
        buttonSearch = (Button)findViewById(R.id.ButtonSearch);


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextSearchTerm.getText().toString().isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), FoodBankActivity.class);
                    String loc = editTextSearchTerm.getText().toString().trim();
                    i.putExtra("STRING_I_NEED", loc);
                    startActivity(i);
                }
            }
        });
    }
}