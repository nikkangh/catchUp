package edu.usc.cs404.catchup;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class FoodBankActivity extends AppCompatActivity {
    public final static String EXTRA_LANGUAGE = "extra_language";
    private final static String TAG = FoodBankActivity.class.getSimpleName();
    private final String token = "Bearer tWAipkg4xbWWob7UL4FOB1WCpYjULYS8YTZE4UATW60-wHE5SuAbCrrQj_EBc0x5OhujYfLqnVQZ6pfi2itHglfkPv4kCn6ObEJxgUSmP0qbVJV7gcNAFlCp5tOhWnYx";
    private final String initialURL = "https://api.yelp.com/v3/businesses/search?location=losangeles&term=";


    //my added
    //private final String token = "Bearer 2AoB-QZdrxqCUkWLkISbsJP-YwoKP4SrSfswvJg9YYbllhDfJpUEpedhHhgyPUMM25W4rMy" +
    //"YZHOgO1EJ9mAnjHUNeaK6R1-sJrpqd1oSEcP_YcU0dw5YOBOTz14OWXYx";
    // private final String URL = "https://api.yelp.com/v3/businesses/search?location=94087&term=foodbank";


    private ListView listCountries;

    private TextView textLanguage;

    private RequestQueue queue;
    private CustomAdapter adapter;
    private ArrayList<String> names;
    private ArrayList<String> locations;
    private ArrayList<String> prices;

    private RelativeLayout layout;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_list);
        layout = (RelativeLayout) findViewById(R.id.layout);
        String newString;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("STRING_I_NEED");
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        url = initialURL + newString; // + "&term=foodbank";


        textLanguage = (TextView) findViewById(R.id.text_language);
        listCountries = (ListView) findViewById(R.id.list_countries);

        //Intent i = getIntent();
        //String language = i.getStringExtra(EXTRA_LANGUAGE);

        textLanguage.setText(newString);

        names = new ArrayList<>();
        locations = new ArrayList<>();
        prices = new ArrayList<>();
        adapter = new CustomAdapter(this,names);
        listCountries.setAdapter(adapter);



        queue = Volley.newRequestQueue(this);
        requestJSONParse(url);

        /*listCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // we know a row was clicked but we need to know WHERE specifically
                // is that data stored in the database
                Intent i = new Intent(getApplicationContext(), BankInfoActivity.class);
                startActivity(i);
            }
        });*/


    }

    //IMPORTANT NOTE: unlike the HTTP version, this JSON is already returned
    //as a JSONArray
    public void requestJSONParse(String reqURL) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Log.d(TAG, "Response is: " + response.substring(0, 500));

                            JSONObject mainObject = new JSONObject(response);

                            JSONArray results = mainObject.getJSONArray("businesses");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject a = results.getJSONObject(i);
                                if (a.has("name")) {
                                    String b = a.getString("name");
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

                                if (a.has("price")) {
                                    String c = a.getString("price");
                                    prices.add(c);
                                } else {
                                    prices.add("");
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
                //params.put("Host", "api.yelp.com");
                params.put("Authorization", token);
                //params.put("Cache-Control", "no-cache");
                //..add other headers
                return params;
            }
        };

        queue.add(stringRequest);
    }

    class CustomAdapter extends ArrayAdapter<String>
    {

        protected Context mContext;
        protected ArrayList<String> mNames;

        public CustomAdapter(Context context, ArrayList<String> names) {
            super(context, R.layout.list_item, names); // Use a custom layout file
            mContext = context;
            mNames = names;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            System.out.println("enters");
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
            }

            // You'll need to use the mItems array to populate these...
            ((TextView) convertView.findViewById(R.id.name)).setText(names.get(position));
            ((TextView) convertView.findViewById(R.id.location)).setText(locations.get(position));
            //((TextView) convertView.findViewById(R.id.rating)).setText(ratings(position));
            ((TextView) convertView.findViewById(R.id.price)).setText(prices.get(position));
            return convertView;
        }
    }
}

