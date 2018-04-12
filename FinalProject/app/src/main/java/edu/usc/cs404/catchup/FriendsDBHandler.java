package edu.usc.cs404.catchup;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by eshitamathur on 4/9/18.
 */

public class FriendsDBHandler {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    FirebaseDatabase database;




    public FriendsDBHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }




    public void pendingFriends(String friendemail) {
        final String result = friendemail;
        //Log.d("entered", "made it inside");
        // Log.d("User Email", user.getEmail());
        // Log.d("User UID", user.getUid());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.orderByChild("username").equalTo(friendemail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        //Log.d("User UID", issue.getValue().toString());

                        String uid = issue.child("uid").getValue().toString();


                        if (issue.child("Friends").exists()) {

                            HashMap<String, ArrayList<String>> friends = (HashMap<String, ArrayList<String>>)issue.child("Friends").getValue();
                            Log.d("Friend Exist", Collections.singletonList(friends).toString());

                            ArrayList<String> pending;

                            if (friends.get("Pending") == null) {
                                pending = new ArrayList<>();
                            }
                            else {
                                pending = friends.get("Pending");
                            }
                            pending.add(user.getEmail());
                            friends.put("Pending", pending); //works up until here

                           // Log.d("Inserting new friends", Collections.singletonList(friends).toString());

                            databaseReference = databaseReference.child(uid).child("Friends");
                            databaseReference.setValue(friends);




                        }
                        else {

                            //Log.d("New Friends", "You have none");
                            HashMap<String, ArrayList<String>> friends = new HashMap<String, ArrayList<String>>();
                            ArrayList<String> pending = new ArrayList<>();
                            pending.add(user.getEmail());
                            friends.put("Pending", pending);
                            databaseReference = databaseReference.child(uid).child("Friends");
                            databaseReference.setValue(friends);
                            //Log.d("Now You have", Collections.singletonList(friends).toString());

                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }



    public void deletePendingFriends(final String friendemail) {
        final String result = friendemail;
        //Log.d("entered", "made it inside");
        // Log.d("User Email", user.getEmail());
        // Log.d("User UID", user.getUid());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.orderByChild("username").equalTo(friendemail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        Log.d("FRIENDDBHANDLER User", issue.getValue().toString());

                        String uid = issue.child("uid").getValue().toString();


                        if (issue.child("Friends").exists()) {

                            HashMap<String, ArrayList<String>> friends = (HashMap<String, ArrayList<String>>)issue.child("Friends").getValue();
                            Log.d("FRIENDDBHANDLER List", Collections.singletonList(friends).toString());

                            ArrayList<String> pending = friends.get("Pending");
                            ArrayList<String> delete;
                            if (friends.get("Delete") == null) {
                                delete = new ArrayList<>();
                            }
                            else {
                                delete = friends.get("Delete");
                            }

                            pending.remove(user.getEmail());
                            delete.add(user.getEmail());

                            friends.put("Pending", pending); //works up until here
                            friends.put("Delete", delete);

                            Log.d("Insert FRIENDDBHANDLER", Collections.singletonList(friends).toString());

                            databaseReference = databaseReference.child(uid).child("Friends");
                            databaseReference.setValue(friends);



                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }




    public void confirmFriends(String friendemail) {

        final String result = friendemail;
        //Log.d("entered", "made it inside");
        // Log.d("User Email", user.getEmail());
        // Log.d("User UID", user.getUid());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.orderByChild("username").equalTo(friendemail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {

                        Log.d("FRIENDDBHANDLER User", issue.getValue().toString());

                        String uid = issue.child("uid").getValue().toString();


                        if (issue.child("Friends").exists()) {

                            HashMap<String, ArrayList<String>> friends = (HashMap<String, ArrayList<String>>)issue.child("Friends").getValue();
                            Log.d("FRIENDDBHANDLER List", Collections.singletonList(friends).toString());

                            ArrayList<String> pending = friends.get("Pending");
                            ArrayList<String> confirmed;
                            if (friends.get("Confirm") == null) {
                                confirmed = new ArrayList<>();
                            }
                            else {
                                confirmed = friends.get("Confirm");
                            }

                            pending.remove(user.getEmail());
                            confirmed.add(user.getEmail());

                            friends.put("Pending", pending); //works up until here
                            friends.put("Confirm", confirmed);

                            Log.d("Insert FRIENDDBHANDLER", Collections.singletonList(friends).toString());

                            databaseReference = databaseReference.child(uid).child("Friends");
                            databaseReference.setValue(friends);



                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


}


