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

    Button searchSubmitButton;
    EditText location;
    RequestQueue queue;
    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        searchSubmitButton = (Button)findViewById(R.id.searchSubmitButton);
        location = (EditText)findViewById(R.id.address);




        searchSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FoodBankActivity.class);
                String loc = location.getText().toString();
                i.putExtra("STRING_I_NEED", loc);
                startActivity(i);




            }
        });



    }
}