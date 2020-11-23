package com.example.BarterApplication;


import android.location.Location;

import com.example.BarterApplication.helpers.LocationHelper;

import java.io.Serializable;

public class SimpleLocation implements Serializable{

    public double Longitude;
    public double Latitude;
    public SimpleLocation(){

    }

    /**
     * @brief SimepleLocation constructor
     * @param lon longitude in RADIANS
     * @param lat lattitude in RADIANS
     * @apiNote IF YOU DON'T USE RADIANS YOUR SUFFERING WILL BE GREATER THAN MINE - CARL
     */
    public SimpleLocation(double lon, double lat) {
        Longitude = lon;
        Latitude = lat;
    }


    /**
     * @brief constructor from Android.Location
     * @param l the android.location object
     */
    public SimpleLocation(Location l){
        Longitude = l.getLongitude();
        Latitude = l.getLatitude();
    }

    /**
     * @brief Stringify a SimpleLocation
     * @return the stringified simpleLocation
     */
    public String toString(){
        String s = new String();
        s += "Lat: " + this.Latitude + " ";
        s += "Lon: " + this.Longitude + " ";
        return s;
    }
}
