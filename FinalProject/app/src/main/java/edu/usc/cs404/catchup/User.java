package edu.usc.cs404.catchup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eshitamathur on 5/4/17.
 */


public class User {
    String username;
    String UID;
    HashMap<String, String> friends = new HashMap<>();

    // default constructor is needed by Firebase!
    public User() {

        friends.put(username, "Confirmed");
    }

    public void setUID(String UID) {
        this.UID = UID;

    }

    public String getUID ( ) {
        return UID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFriends()    {

        ArrayList<String> myFriends = new ArrayList<>();
        for (Map.Entry<String, String> e : friends.entrySet()) {
            if (e.getValue().contains("Confirmed")) {
                myFriends.add(e.getKey());
            }
        }

        return myFriends;

    }

    public void createPendingFriend(String friendUsername) {

        friends.put(friendUsername, "Pending");
    }

    public void deletePendingFriend(String username) {
        friends.remove(username);
    }

    public void confirmedFriend(String username ) {
        for (Map.Entry<String, String> e : friends.entrySet()) {
            if (e.getKey().startsWith("username")) {
                friends.put(username, "Confirmed");
            }
        }
    }


}