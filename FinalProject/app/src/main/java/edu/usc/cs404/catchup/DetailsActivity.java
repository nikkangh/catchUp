package edu.usc.cs404.catchup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView restaurantName;
    private TextView category;
    private TextView rating;
    private TextView reviews;
    private TextView location;
    private TextView distance;
    private TextView price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int index = getIntent().getExtras().getInt(ListFragment.EXTRA_INDEX);
        LocationObject loc = DataModel.getInstance().getLocationObjectAtIndex(index);

        image = (ImageView) findViewById(R.id.image);
        Picasso.with(DetailsActivity.this).load(loc.getImageUrl()).fit().centerCrop().into(image);


        restaurantName = (TextView) findViewById(R.id.name);
        restaurantName.setText(loc.getRestaurantName());

        category = (TextView) findViewById(R.id.category);
        category.setText(loc.getCategoryString());

        rating = (TextView) findViewById(R.id.rating);
        rating.setText(loc.getRatingString());

        reviews = (TextView) findViewById(R.id.numReviews);
        reviews.setText(String.format(getString(R.string.num_reviews), loc.getReviewCount()));

        location = (TextView) findViewById(R.id.location);
        location.setText(loc.getLocation());

        distance = (TextView) findViewById(R.id.distance);
        distance.setText(String.format(getString(R.string.num_distance), loc.getDistance()));

        price = (TextView) findViewById(R.id.price);
        price.setText(loc.getPrice());

    }



}
