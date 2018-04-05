package edu.usc.cs404.catchup;

import java.util.ArrayList;

/**
 * Created by eshitamathur on 5/4/17.
 */


public class User {
    String name;
    String username;
    String password;
    String address;
    boolean userExists = true;
    ArrayList<String> friends = new ArrayList<String>();
    ArrayList<SurveyItem> preferences = new ArrayList<SurveyItem>();



    // default constructor is needed by Firebase!
    public User() {
    }

    public User(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> myFriends()    {
        return(friends);
    }

    public void emailDoesNotExist() {
        userExists = false;
    }

    public boolean emailExist() {
        return userExists;
    }

    public void addFriend(String username) {
        friends.add(username);
    }

    public void setSurveyResults(ArrayList<SurveyItem> surveyResults) {
        preferences = surveyResults;
    }

    public ArrayList<SurveyItem> getSurveyResults() {
        return(preferences);
    }


    public void setPassword(String password) {
        this.password = password;
    }


}