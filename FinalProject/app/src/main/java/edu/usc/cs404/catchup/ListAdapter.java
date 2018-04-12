package edu.usc.cs404.catchup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;


public class ListAdapter extends ArrayAdapter {
    private ImageView image;
    private TextView restaurantName;
    private TextView category;
    private TextView rating;
    private TextView reviews;
    private TextView distance;
    private TextView price;

    public ListAdapter(Context c, int resId, ArrayList<LocationObject> _content) {
        super(c, resId, _content); // Use a custom layout file
    }

    public int getCount() {

        return DataModel.getInstance().getDataCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
        }

        LocationObject loc = DataModel.getInstance().getLocationObjectAtIndex(position);

        image = (ImageView) convertView.findViewById(R.id.image);
        Picasso.with(getContext()).load(loc.getImageUrl()).fit().centerCrop().into(image);


        restaurantName = (TextView) convertView.findViewById(R.id.name);
        restaurantName.setText(loc.getRestaurantName());

        category = (TextView) convertView.findViewById(R.id.category);
        category.setText(loc.getCategoryString());

        rating = (TextView) convertView.findViewById(R.id.rating);
        rating.setText(loc.getRatingString());

        reviews = (TextView) convertView.findViewById(R.id.numReviews);
        reviews.setText(String.format(getContext().getResources().getString(R.string.num_reviews), loc.getReviewCount()));

        distance = (TextView) convertView.findViewById(R.id.distance);
        distance.setText(String.format(getContext().getResources().getString(R.string.num_distance), loc.getDistance()));

        price = (TextView) convertView.findViewById(R.id.price);
        price.setText(loc.getPrice());

        return convertView;
    }
}

