package edu.usc.cs404.catchup;

/**
 * Created by eshitamathur on 5/7/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by eshitamathur on 3/6/17.
 */

public class RatingFragment extends Fragment{


    Button submit;


    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_rating, container, false);

        submit = (Button)v.findViewById(R.id.rating_save_button);
        //inspect the parent View and get references to our widgets

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Thanks!", Toast.LENGTH_SHORT).show();

            }
        });

        return v;

    }



}
