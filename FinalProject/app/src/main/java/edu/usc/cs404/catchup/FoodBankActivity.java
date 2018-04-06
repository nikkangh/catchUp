package edu.usc.cs404.catchup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FoodBankActivity extends AppCompatActivity {
    public final static String EXTRA_LANGUAGE = "extra_language";
    private final static String TAG = FoodBankActivity.class.getSimpleName();
    private final String token = "Bearer tWAipkg4xbWWob7UL4FOB1WCpYjULYS8YTZE4UATW60-wHE5SuAbCrrQj_EBc0x5OhujYfLqnVQZ6pfi2itHglfkPv4kCn6ObEJxgUSmP0qbVJV7gcNAFlCp5tOhWnYx";
    private final String initialURL = "https://api.yelp.com/v3/businesses/search?location=losangeles&latitude=34.022564&longitude=-118.290867&term=food";
            //&term=food+";

    private Button settings;
    private Button pick;
    private Button viewMap;

    //Database Addition
    private ArrayList<String> surveyPreferences;
    private ArrayList<LocationObject> markerInfo = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    //my added
    //private final String token = "Bearer 2AoB-QZdrxqCUkWLkISbsJP-YwoKP4SrSfswvJg9YYbllhDfJpUEpedhHhgyPUMM25W4rMy" +
    //"YZHOgO1EJ9mAnjHUNeaK6R1-sJrpqd1oSEcP_YcU0dw5YOBOTz14OWXYx";
    // private final String URL = "https://api.yelp.com/v3/businesses/search?location=94087&term=foodbank";

    static private ListAdapter adapter;
    private ListView listCountries;

    private TextView textViewResultsHeader;
    private RelativeLayout layout;

    /*private RequestQueue queue;
    private ListAdapter adapter;
    private ArrayList<String> urls;
    private ArrayList<String> names;
    private ArrayList<String> locations;
    private ArrayList<String> prices;
    private ArrayList<String> ratings;

    private String url;
    private String term;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_list);
        layout = (RelativeLayout) findViewById(R.id.layout);
        DataModel.getInstance().setContext(FoodBankActivity.this);

        String latlong = "";
        /*try {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            latlong = "&latitude=" + Double.toString(latitude) + "&longitude=" + Double.toString(longitude);
        } catch (SecurityException ex){}
*/

        Intent intent = getIntent();
        String[] surveyData = intent.getStringArrayExtra("key");

        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(i);
            }
        });

        viewMap = (Button) findViewById(R.id.mapbtn);
        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                i.putExtra("loc_key", markerInfo);
                startActivity(i);
            }
        });



        /*SharedPreferences prefs = getSharedPreferences(
                SurveyActivity.PREFERENCE_FILENAME, MODE_PRIVATE);
        Set<String> s = prefs.getStringSet(
                SurveyActivity.PREFERENCE_FOODPREFS, new HashSet<String>());*/
        /*if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                term = "";
            } else {
                term = extras.getString("STRING_I_NEED");
            }
        } else {
            term = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }*/

        //url = initialURL + term; // + "&term=foodbank";

        listCountries = (ListView) findViewById(R.id.list_countries);
        adapter = new ListAdapter(FoodBankActivity.this, R.id.list_countries, new ArrayList<LocationObject>());
        listCountries.setAdapter(adapter);

        textViewResultsHeader = (TextView) findViewById(R.id.textViewResultsHeader);
        //Intent i = getIntent();
        //String language = i.getStringExtra(EXTRA_LANGUAGE);

        //url = initialURL;
        //urls = new ArrayList<>();
        /*for (String curr: s) {
            if (!curr.isEmpty()) {
                url += "+" + curr;
                //urls.add(initialURL + curr);
                System.out.println(curr);
            }
        }*/

        /*names = new ArrayList<>();
        locations = new ArrayList<>();
        prices = new ArrayList<>();
        ratings = new ArrayList<>();



        queue = Volley.newRequestQueue(this);
        //for (int i = 0; i < urls.size(); i++) {
                requestJSONParse(url);
                //System.out.println(surveyData[i])
        //}*/

        /*listCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // we know a row was clicked but we need to know WHERE specifically
                // is that data stored in the database
                Intent i = new Intent(getApplicationContext(), BankInfoActivity.class);
                startActivity(i);
            }
        });*/
        /*pick = (Button) findViewById(R.id.pick);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();

                // nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int randomNum = rand.nextInt(20);

                Toast.makeText(FoodBankActivity.this,
                        names.get(randomNum).toString(), Toast.LENGTH_LONG).show();
            }
        });*/

    }

    //database retrieving info
    @Override
    public void onStart() {
        super.onStart();

        //database stuff dont touch
        /*databaseReference = FirebaseDatabase.getInstance().getReference(); //get instance of database

        Log.d("TAG", "GOING INTO DATABASE");

        ValueEventListener userListener = new ValueEventListener() { //get latest info from database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //get the current database snap (hehe like snapchat SO COOL)


                /*firebaseAuth = FirebaseAuth.getInstance(); //get instance of authentication
                FirebaseUser user = firebaseAuth.getCurrentUser(); //get your information

                User sample;
                sample = dataSnapshot.child(user.getUid()).getValue(User.class); //get object associated with ur info

                if (sample != null) {
                   //surveyPreferences = sample.getSurveyResults(); //this should be your survey preferences list

                    Log.d("TAG", "I FUCKING DID IT FUCK DATABASES");
                    for (int i = 0; i < surveyPreferences.size(); i++) {

                        Log.d("TAG", surveyPreferences.get(i).toString());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("hi", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(userListener);*/
    }

    //IMPORTANT NOTE: unlike the HTTP version, this JSON is already returned
    //as a JSONArray
    /*public void requestJSONParse(String reqURL) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, reqURL.trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            //Log.d(TAG, "Response is: " + response.substring(0, 500));

                            JSONObject mainObject = new JSONObject(response);

                            JSONArray results = mainObject.getJSONArray("businesses");

                            Log.d(TAG, "response length is: " + results.length());
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject a = results.getJSONObject(i);
                                double lat = 0;
                                double longi = 0;
                                String rName = "";
                                if (a.has("name")) {
                                    String b = a.getString("name");
                                    rName = b;
                                    names.add(b);
                                } else {
                                    names.add("");
                                }
                                if (a.has("location")) {
                                    JSONObject shipper = a.getJSONObject("location");
                                    if (shipper.has("display_address")) {
                                        JSONArray d = shipper.getJSONArray("display_address");
                                        StringBuilder sb = new StringBuilder(d.getString(0));
                                        for (int j = 1; j < d.length(); j++) {
                                            sb.append("\n");
                                            sb.append(d.getString(j));
                                        }
                                        locations.add(sb.toString());
                                    }
                                } else {
                                    locations.add("");
                                }

                                //Grabs coordinates for maps to place markers

                                if (a.has("coordinates")) {
                                    JSONObject cords = a.getJSONObject("coordinates");
                                    if(cords.has("latitude") && cords.has("longitude")) {
                                        lat = cords.getDouble("latitude");
                                        longi = cords.getDouble("longitude");
                                        //System.out.println("cords got");

                                    }

                                }

                                if (a.has("price")) {
                                    String c = a.getString("price");
                                    prices.add(c);
                                } else {
                                    prices.add("");
                                }

                                if (a.has("rating")) {
                                    float rating = Float.valueOf(a.getString("rating"));
                                    StringBuilder sb = new StringBuilder();
                                    for (int j = 0; j < rating; j++) {
                                        sb.append("*");
                                    }
                                    ratings.add(sb.toString());
                                } else {
                                    ratings.add("");
                                }
                                LocationObject locObj= new LocationObject(lat, longi, rName);
                                if (lat != 0 && longi !=0 && rName != "") {
                                    markerInfo.add(locObj);
                                    //System.out.println("added");
                                }
                            }
                            System.out.println("STOP");

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Log.d(TAG, "onErrorResponse: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", token);
                return params;
            }
        };

        queue.add(stringRequest);
    }*/

    class ListAdapter extends ArrayAdapter {
        private TextView restaurantName;

        public ListAdapter(Context c, int resId, ArrayList<LocationObject> _content) {
            super(c, resId, _content); // Use a custom layout file
        }

        public int getCount() {
            return DataModel.getInstance().getDataCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item,null);
            }

            LocationObject loc = DataModel.getInstance().getLocationObjectAtIndex(position);

            restaurantName = (TextView) convertView.findViewById(R.id.name);
            restaurantName.setText(loc.getRestaurantName());


            textViewResultsHeader.setText(String.format(getString(R.string.search_results_header), DataModel.getInstance().getDataCount()));
            return convertView;
        }
    }


    static public void update() {
        adapter.notifyDataSetChanged();
    }
}

