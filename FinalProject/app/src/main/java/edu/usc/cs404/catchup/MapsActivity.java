package edu.usc.cs404.catchup;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LocationObject> markerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        markerPoints = (ArrayList<LocationObject>) intent.getSerializableExtra("loc_key");

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
            mMap.addMarker(new MarkerOptions().position(point).title(name));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fll));
    }

}
