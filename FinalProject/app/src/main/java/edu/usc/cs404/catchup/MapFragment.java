package edu.usc.cs404.catchup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private Button pick;
    private ArrayList<LocationObject> markerPoints;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        markerPoints = DataModel.getInstance().getData();

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pick = (Button) v.findViewById(R.id.pick);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = rand.nextInt(DataModel.getInstance().getDataCount());

                Intent i = new Intent(getContext(), DetailsActivity.class);
                i.putExtra(ListFragment.EXTRA_INDEX, randomNum);
                startActivity(i);
            }
        });

        //Intent intent = getIntent();
        return v;
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

        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        Marker melbourne = mMap.addMarker(new MarkerOptions()
//                .position(MELBOURNE)
//                .title("Melbourne")
//                .snippet("Population: 4,137,400"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //String firstLoc = markerPoints.get(0).restaurantName;
        LatLng fll = new LatLng(34.022564, -118.290867);
        for (int i = 0; i < markerPoints.size(); i++) {
            String name = markerPoints.get(i).restaurantName;
            LatLng point = new LatLng(markerPoints.get(i).latitude,markerPoints.get(i).longitude);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(point)
                    .title(name)
                    .snippet("Click for more info")
            );
            marker.setTag(i);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fll, 11));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(getContext(), DetailsActivity.class);
        i.putExtra(ListFragment.EXTRA_INDEX, (int)(marker.getTag()));
        startActivity(i);
    }

    static public void update() {
        //possibly set marker vals?
    }
}
