package edu.usc.cs404.catchup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_chinese:
                //if (checked)

                break;
            case R.id.checkbox_french:
                //if (checked)

                break;
            case R.id.checkbox_indian:
                //if (checked)

                break;
            case R.id.checkbox_italian:
                //if (checked)

                break;
            case R.id.checkbox_japanese:
                //if (checked)
                break;


        }
    }
}