package itp341.mathur.eshita.finalproject;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
    private ArrayAdapter<String> adapter;
    private ArrayList<String> countries;
    private RelativeLayout layout;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_list);
        layout = (RelativeLayout) findViewById(R.id.layout);
        //layout.setBackgroundColor(Color.parseColor("#d6fffc"));
        String newString;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        url = initialURL + newString; // + "&term=foodbank";


        textLanguage = (TextView) findViewById(R.id.text_language);
        listCountries = (ListView) findViewById(R.id.list_countries);

        //Intent i = getIntent();
        //String language = i.getStringExtra(EXTRA_LANGUAGE);

        textLanguage.setText(newString);

        countries  = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                countries);
        listCountries.setAdapter(adapter);


        queue = Volley.newRequestQueue(this);
        requestJSONParse(url);

        listCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // we know a row was clicked but we need to know WHERE specifically
                // is that data stored in the database
                Intent i = new Intent(getApplicationContext(), BankInfoActivity.class);
                startActivity(i);
            }
        });



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
                            Log.d(TAG, "Response is: "+ response.substring(0,500));

                            JSONObject mainObject = new JSONObject(response);

                            JSONArray results = mainObject.getJSONArray("businesses");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject a = results.getJSONObject(i);
                                JSONObject shipper = a.getJSONObject("location");
                                String b = a.getString("name");
                                countries.add(b);
                                String c = a.getString("display_phone");
                                String d = shipper.getString("display_address");
                                System.out.println(b);
                                System.out.println(c);
                                System.out.println(d);
                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        }){
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


        /*JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        //Log.d("Response I GOT IT", response.toString());
                        try {
                            String resp = response.toString();

                            //JSONObject object = (JSONObject) new JSONTokener(resp).nextValue();
                            JSONArray results = response.getJSONArray("businesses");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject a = results.getJSONObject(i);
                                JSONObject shipper = a.getJSONObject("location");
                                String b = a.getString("name");
                                countries.add(b);
                                String c = a.getString("display_phone");
                                String d = shipper.getString("display_address");
                                System.out.println(b);
                                System.out.println(c);
                                System.out.println(d);
                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }


        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };*/

        queue.add(stringRequest);
    }
}

