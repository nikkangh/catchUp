package edu.usc.cs404.catchup;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import com.appsee.Appsee;


public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{
    public static final String EXTRA_MARKERTAG = "edu.usc.cs404.catchup.markertag";
    private final static String TAG = HomeActivity.class.getSimpleName();

    private Button locName;
    private Button distance;
    private GoogleMap mMap;

    private ArrayList<LocationObject> markerPoints;

    private ImageButton settingsBtn;
    private ImageButton retryBtn;

    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Appsee.setDebugToLogcat(true);
        Appsee.start();

        DataModel.getInstance().setContext(HomeActivity.this);

        locName = (Button) findViewById(R.id.name);
        locName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&origin=34.022564,-118.290867");

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

        distance = (Button) findViewById(R.id.distance);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Intent intent = getIntent();
        //markerPoints = DataModel.getInstance().getData();

        settingsBtn = (ImageButton) findViewById(R.id.settings);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        retryBtn = (ImageButton) findViewById(R.id.retry);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();

                Random rand = new Random();
                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = rand.nextInt(DataModel.getInstance().getDataCount());
                LocationObject newLoc = DataModel.getInstance().getLocationObjectAtIndex(randomNum);
                latitude = newLoc.latitude;
                longitude = newLoc.longitude;
                LatLng point = new LatLng(latitude, longitude);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(point)
                );
                locName.setText(newLoc.restaurantName);
                distance.setText(String.format(getString(R.string.num_distance), newLoc.getDistance()));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17));
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        LatLng fll = new LatLng(34.022564, -118.290867);

        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt(DataModel.getInstance().getDataCount());
        LocationObject newLoc = DataModel.getInstance().getLocationObjectAtIndex(randomNum);
        latitude = newLoc.latitude;
        longitude = newLoc.longitude;
        LatLng point = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(point)
        );

        locName.setText(newLoc.restaurantName);
        distance.setText(String.format(getString(R.string.num_distance), newLoc.getDistance()));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 17));
        //LatLng fll = new LatLng(markerPoints.get(0).latitude,markerPoints.get(0).longitude);

        /*mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        Marker melbourne = mMap.addMarker(new MarkerOptions()
//                .position(MELBOURNE)
//                .title("Melbourne")
//                .snippet("Population: 4,137,400"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        String firstLoc = markerPoints.get(0).restaurantName;
        LatLng fll = new LatLng(markerPoints.get(0).latitude,markerPoints.get(0).longitude);
        for (int i = 0; i < markerPoints.size(); i++) {
            String name = markerPoints.get(i).restaurantName;
            LatLng point = new LatLng(markerPoints.get(i).latitude,markerPoints.get(i).longitude);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(name)
                    .snippet("Click for more info")
            );
            marker.setTag(i);
        }*/
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fll, 11));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //System.out.println("Infowindow being clicked");
        Intent i = new Intent(HomeActivity.this, DetailsActivity.class);

        i.putExtra(EXTRA_MARKERTAG, (int)(marker.getTag()));
        //TODO: Add functionality to go to Activity page
        startActivity(i);

    }
}
