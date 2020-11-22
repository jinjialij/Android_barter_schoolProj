package com.example.BarterApplication;


import android.location.Location;

import com.example.BarterApplication.helpers.LocationHelper;

import java.io.Serializable;

public class SimpleLocation implements Serializable{

    public double Longitude;
    public double Latitude;
    public SimpleLocation(){

    }

    public SimpleLocation(double lon, double lat) {
        Longitude = lon;
        Latitude = lat;
    }
}
