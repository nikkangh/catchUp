package edu.usc.cs404.catchup;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationObject implements Serializable {

    String restaurantName;
    String imageUrl;
    boolean isClosed;
    int reviewCount;
    ArrayList<String> categories;
    double rating;
    ArrayList<String> transactions;
    String price;
    String location;
    double latitude;
    double longitude;
    String phone;
    double distance;


    public LocationObject(String restaurantName, String imageUrl, boolean isClosed, int reviewCount,
                          ArrayList<String> categories, double rating, ArrayList<String> transactions,
                          String price, String location, double latitude, double longitude,
                          String phone, double distance) {
        this.restaurantName = restaurantName;
        this.imageUrl = imageUrl;
        this.isClosed = isClosed;
        this.reviewCount = reviewCount;
        this.categories = categories;
        this.rating = rating;
        this.transactions = transactions;
        this.price = price;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.distance = distance;

    }

    public LocationObject(double latitude, double longitude, String restaurantName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {return restaurantName; }
    public String getImageUrl() { return imageUrl; }
    public boolean getIsClosed() { return isClosed; }
    public int getReviewCount() { return reviewCount; }
    public ArrayList<String> getCategories() { return categories; }
    public double getRating() { return rating; }
    public ArrayList<String> getTransactions() { return transactions; }
    public String getPrice() { return price; }
    public String getLocation() { return location; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getPhone() { return phone; }
    public double getDistance() { return distance; }

}
