package edu.usc.cs404.catchup;

import java.io.Serializable;

public class LocationObject implements Serializable {

    double latitude;
    double longitude;
    String restaurantName;

    public LocationObject(double latitude, double longitude, String restaurantName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.restaurantName = restaurantName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
