package edu.usc.cs404.catchup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MARKERTAG = "edu.usc.cs404.catchup.markertag";

    private ArrayList<LocationObject> markerPoints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int index = getIntent().getExtras().getInt(EXTRA_MARKERTAG);
        markerPoints = DataModel.getInstance().getData();

        TextView restaurantName = (TextView)findViewById(R.id.name);
        restaurantName.setText(markerPoints.get(index).getRestaurantName());
        //TextView category = (TextView)findViewById(R.id.category);
        TextView address = (TextView)findViewById(R.id.location);
        address.setText(markerPoints.get(index).getLocation());

        TextView price = (TextView)findViewById(R.id.price);
        price.setText(markerPoints.get(index).getPrice());

//        TextView reviewCount = (TextView)findViewById(R.id.numReviews);
//        reviewCount.setText(markerPoints.get(index).getReviewCount());

    }



}
