package edu.usc.cs404.catchup;

import java.io.Serializable;
import java.util.ArrayList;

public class LocationObject implements Serializable {

    String id;
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


    public LocationObject(String id, String restaurantName, String imageUrl, boolean isClosed, int reviewCount,
                          ArrayList<String> categories, double rating, ArrayList<String> transactions,
                          String price, String location, double latitude, double longitude,
                          String phone, double distance) {
        this.id = id;
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

    public String getId() {return id;}
    public String getRestaurantName() {return restaurantName; }
    public String getImageUrl() { return imageUrl; }
    public boolean getIsClosed() { return isClosed; }
    public int getReviewCount() { return reviewCount; }
    public ArrayList<String> getCategories() { return categories; }
    public String getCategoryString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < categories.size(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(categories.get(i));
        }
        return sb.toString();
    }
    public double getRating() { return rating; }
    public String getRatingString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < rating; j++) {
            sb.append("*");
        }
        return sb.toString();
    }
    public ArrayList<String> getTransactions() { return transactions; }
    public String getPrice() { return price; }
    public String getLocation() { return location; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getPhone() { return phone; }
    public double getDistance() { return distance; }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o){

        if(o instanceof LocationObject){

            LocationObject l = (LocationObject) o;
            if(l.id.equals(this.id))
            {
                return true;
            }
            else return false;

        }

        return false;
    }
}
