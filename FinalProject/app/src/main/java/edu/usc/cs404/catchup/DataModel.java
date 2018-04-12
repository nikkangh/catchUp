package edu.usc.cs404.catchup;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataModel implements Serializable {
    private static final DataModel ourInstance = new DataModel();

    private final static String TAG = DataModel.class.getSimpleName();
    private final String token = "Bearer tWAipkg4xbWWob7UL4FOB1WCpYjULYS8YTZE4UATW60-wHE5SuAbCrrQj_EBc0x5OhujYfLqnVQZ6pfi2itHglfkPv4kCn6ObEJxgUSmP0qbVJV7gcNAFlCp5tOhWnYx";
    private final String initialURL = "https://api.yelp.com/v3/businesses/search?location=losangeles&latitude=34.022564&longitude=-118.290867&term=food";

    Context context;
    ArrayList<LocationObject> finalList;
    ArrayList<SurveyItem> currItems;
    ArrayList<SurveyItem> newItems;
    HashMap<SurveyItem, ArrayList<LocationObject>> itemToLocation;

    private RequestQueue queue;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public static DataModel getInstance() {
        return ourInstance;
    }

    public void setContext(Context context) { this.context = context; }

    private DataModel() {
        finalList = new ArrayList<LocationObject>();
        currItems = new ArrayList<SurveyItem>();
        newItems = new ArrayList<SurveyItem>();
        itemToLocation = new HashMap<SurveyItem, ArrayList<LocationObject>>();

        databaseReference = FirebaseDatabase.getInstance().getReference(); //get instance of database
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); //get your information
        databaseReference = databaseReference.child(firebaseUser.getUid()).child("surveyItems");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    SurveyItem item = itemSnapshot.getValue(SurveyItem.class);
                    if (!currItems.contains(item)) {
                        newItems.add(item);
                    }
                }
                if (!newItems.isEmpty()) {
                    reloadData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void reloadData() {
        for (int i = 0; i < newItems.size(); i++) {
            //find if item exists already
            int foundIndex = -1;
            for (int j = 0; j < currItems.size() && foundIndex == -1; j++) {
                if (currItems.get(j).getDesc().equals(newItems.get(i).getDesc())) {
                    foundIndex = j;
                }
            }

            //modify map as necessary
            if (newItems.get(i).isSelected()) {
                getDataForTerm(newItems.get(i));
                //itemToLocation.put(newItems.get(i), getDataForTerm(newItems.get(i).getDesc()));
            } else {
                if (foundIndex != -1) {
                    itemToLocation.remove(currItems.get(foundIndex));
                    update();
                }
            }

            //modify currItems list
            if (foundIndex != -1) {
                currItems.remove(foundIndex);
            }

            currItems.add(newItems.get(i));
        }
        newItems.clear();
    }

    public void getDataForTerm (final SurveyItem item) {
        String url = initialURL + "+" + item.getDesc();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.trim(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            //Log.d(TAG, "Response is: " + response.substring(0, 500));

                            JSONObject mainObject = new JSONObject(response);

                            JSONArray results = mainObject.getJSONArray("businesses");

                            ArrayList<LocationObject> newLocs = new ArrayList<LocationObject>();

                            Log.d(TAG, "response length is: " + results.length());
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject a = results.getJSONObject(i);

                                String id = "";
                                String restaurantName = "";
                                String imageUrl = "";
                                boolean isClosed = false;
                                int reviewCount = 0;
                                ArrayList<String> categories = new ArrayList<String>();
                                double rating = 0.0;
                                ArrayList<String> transactions = new ArrayList<String>();
                                String price = "";
                                String location = "";
                                double latitude = 0.0;
                                double longitude = 0.0;
                                String phone = "";
                                double distance = 0.0;


                                if (a.has("id")) {
                                    id = a.getString("id");
                                }
                                if (a.has("name")) {
                                    restaurantName = a.getString("name");
                                }
                                if (a.has("image_url")) {
                                    imageUrl = a.getString("image_url");
                                }
                                if (a.has("is_closed")) {
                                    isClosed = a.getBoolean("is_closed");
                                }
                                if (a.has("review_count")) {
                                    reviewCount = a.getInt("review_count");
                                }
                                if (a.has("categories")) {
                                    JSONArray b = a.getJSONArray("categories");
                                    for (int j = 0; j < b.length(); j++) {
                                        JSONObject c = b.getJSONObject(j);
                                        categories.add(c.getString("title"));
                                    }
                                }
                                if (a.has("rating")) {
                                    rating = a.getDouble("rating");
                                }
                                if (a.has("transactions")) {
                                    JSONArray b = a.getJSONArray("transactions");
                                    for (int j = 0; j < b.length(); j++) {
                                        transactions.add(b.getString(j));
                                    }
                                }
                                if (a.has("price")) {
                                    price = a.getString("price");
                                }

                                if (a.has("location")) {
                                    JSONObject b = a.getJSONObject("location");
                                    if (b.has("display_address")) {
                                        JSONArray d = b.getJSONArray("display_address");
                                        StringBuilder sb = new StringBuilder(d.getString(0));
                                        for (int j = 1; j < d.length(); j++) {
                                            sb.append("\n");
                                            sb.append(d.getString(j));
                                        }
                                        location = sb.toString();
                                    }
                                }
                                //Grabs coordinates for maps to place markers
                                if (a.has("coordinates")) {
                                    JSONObject cords = a.getJSONObject("coordinates");
                                    if(cords.has("latitude") && cords.has("longitude")) {
                                        latitude = cords.getDouble("latitude");
                                        longitude = cords.getDouble("longitude");
                                    }

                                }
                                if (a.has("display_phone")) {
                                    phone = a.getString("display_phone");
                                }
                                if (a.has("distance")) {
                                    distance = a.getDouble("distance");
                                    distance *= 0.000621371;
                                }

                                LocationObject locObj= new LocationObject(id, restaurantName, imageUrl,
                                isClosed, reviewCount, categories, rating, transactions, price,
                                        location, latitude, longitude, phone, distance);

                                newLocs.add(locObj);
                                /*if (latitude != 0 && longitude !=0 && restaurantName != "") {
                                    markerInfo.add(locObj);
                                }*/
                            }

                            newLocs.sort(new LocComparator());
                            addData(item, newLocs);
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

        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    private void addData(SurveyItem item, ArrayList<LocationObject> newLocs) {
        itemToLocation.put(item, newLocs);

        update();
    }

    public void update() {
        //remake final list
        finalList.clear();
        Set<LocationObject> removeDuplicates = new HashSet<LocationObject>();
        for (ArrayList<LocationObject> curr: itemToLocation.values()) {
            //finalList.addAll(curr);
            removeDuplicates.addAll(curr);
        }
        finalList.addAll(removeDuplicates);
        finalList.sort(new LocComparator());

        MainActivity.update();
    }


    public ArrayList<LocationObject> getData() {
        return finalList;
    }

    static class LocComparator implements Comparator<LocationObject>
    {
        public int compare(LocationObject l1, LocationObject l2)
        {
            return Double.compare(l1.getDistance(), l2.getDistance());
        }
    }

    public int getDataCount() {
        return finalList.size();
    }

    public LocationObject getLocationObjectAtIndex (int index) {
        return finalList.get(index);
    }

}
