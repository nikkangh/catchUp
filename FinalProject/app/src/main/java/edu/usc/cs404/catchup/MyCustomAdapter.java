package edu.usc.cs404.catchup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by zacharyg on 3/29/18.
 * https://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    public ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;



    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.add_friends_list_box, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //database stuff
                firebaseAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();
                user = firebaseAuth.getCurrentUser();
                ref = FirebaseDatabase.getInstance().getReference();
                final String email = list.get(position);

                ref = ref.child(user.getUid());
                Log.d("List Position", list.get(position));
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            // dataSnapshot is the "issue" node with all children with id 0
                            Log.d("User UID", dataSnapshot.getValue().toString());
                                HashMap<String, ArrayList<String>> friends = (HashMap<String, ArrayList<String>>)dataSnapshot.child("Friends").getValue();
                                Log.d("Friend Exist", Collections.singletonList(friends).toString());

                                ArrayList<String> pending = friends.get("Pending");
                                if (pending.size() == 0) {
                                    Log.d("Pending Empty", "Empty");
                                }
                                else {
                                    for (int i = 0; i < pending.size(); i++) {
                                        Log.d("Pending: ", pending.get(i));
                                    }
                                }
                                ArrayList<String> delete;
                                if (friends.get("Delete") == null) {
                                    delete = new ArrayList<>();
                                }
                                else {
                                    delete = friends.get("Delete");
                                }


                                pending.remove(email);
                                delete.add(email);

                                if (pending.size() == 0) {}
                                else {
                                    friends.put("Pending", pending); //works up until here
                                }
                                friends.put("Delete", delete);

                                Log.d("Inserting new friends", Collections.singletonList(friends).toString());

                                ref = ref.child("Friends");
                                ref.setValue(friends);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                //dealing with the friend

                FriendsDBHandler DBHandler = new FriendsDBHandler();
                DBHandler.deletePendingFriends(list.get(position));


                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //database stuff
                firebaseAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();
                user = firebaseAuth.getCurrentUser();
                ref = FirebaseDatabase.getInstance().getReference();
                final String email = list.get(position);

                ref = ref.child(user.getUid());
                Log.d("List Position", list.get(position));
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            // dataSnapshot is the "issue" node with all children with id 0
                            Log.d("User UID", dataSnapshot.getValue().toString());
                            HashMap<String, ArrayList<String>> friends = (HashMap<String, ArrayList<String>>)dataSnapshot.child("Friends").getValue();
                            Log.d("Friend Exist", Collections.singletonList(friends).toString());

                            ArrayList<String> pending = friends.get("Pending");
                            if (pending.size() == 0) {
                                Log.d("Pending Empty", "Empty");
                            }
                            else {
                                for (int i = 0; i < pending.size(); i++) {
                                    Log.d("Pending: ", pending.get(i));
                                }
                            }
                            ArrayList<String> confirmed;
                            if (friends.get("Confirm") == null) {
                                confirmed = new ArrayList<>();
                            }
                            else {
                                confirmed = friends.get("Confirm");
                            }


                            pending.remove(email);
                            confirmed.add(email);

                            if (pending.size() == 0) {}
                            else {
                                friends.put("Pending", pending); //works up until here
                            }
                            friends.put("Confirm", confirmed);

                            Log.d("Inserting new friends", Collections.singletonList(friends).toString());

                            ref = ref.child("Friends");
                            ref.setValue(friends);

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                //dealing with the friend

                FriendsDBHandler DBHandler = new FriendsDBHandler();
                DBHandler.confirmFriends(list.get(position));
                //do something
                notifyDataSetChanged();
            }
        });

        return view;
    }
}