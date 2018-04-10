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

/**
 * Created by eshitamathur on 4/9/18.
 */

public class FriendsDBHandler {
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    public FriendsDBHandler() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

    }


    public void pendingFriends(String friendemail) {
        final String result = friendemail;
        Log.d("entered", "made it inside");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d("User Email", user.getEmail());
        Log.d("User UID", user.getUid());


        Query query = reference.orderByChild("username").equalTo(friendemail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {



                        Log.d("dataSnapshot", issue.child("uid").getValue().toString());
                        Log.d("dataSnapshot", issue.child("username").getValue().toString());



                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void deletePendingFriends(String friendemail) {

        final String result = friendemail;
        ValueEventListener userListener = new ValueEventListener() { //get latest info from database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //get the current database snap (hehe like snapchat SO COOL)

                firebaseAuth = FirebaseAuth.getInstance(); //get instance of authentication
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                Query query = reference.orderByChild("username").equalTo(result);
                query.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot people : dataSnapshot.getChildren()) {
                                User user2 = dataSnapshot.getValue(User.class);
                                User me = dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(User.class);
                                me.deletePendingFriend(result); // I add friend
                                user2.deletePendingFriend(me.getUsername()); //friend adds me

                                databaseReference.setValue(me);
                                databaseReference.setValue(user2);


                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("hi", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(userListener);

    }

    public void confirmFriends(String friendemail) {

        final String result = friendemail;
        ValueEventListener userListener = new ValueEventListener() { //get latest info from database
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { //get the current database snap (hehe like snapchat SO COOL)

                firebaseAuth = FirebaseAuth.getInstance(); //get instance of authentication
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                Query query = reference.orderByChild("username").equalTo(result);
                query.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot people : dataSnapshot.getChildren()) {
                                User user2 = dataSnapshot.getValue(User.class);
                                User me = dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).getValue(User.class);
                                me.confirmedFriend(result); // I add friend
                                user2.confirmedFriend(me.getUsername()); //friend adds me

                                databaseReference.setValue(me);
                                databaseReference.setValue(user2);


                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("hi", "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(userListener);

    }



}

