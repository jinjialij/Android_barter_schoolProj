package com.example.BarterApplication;

import java.io.Serializable;

public class SimpleLocation implements Serializable {
    public double Longitude;

    public double Latitude;

    public SimpleLocation(){
    }

    public SimpleLocation(double x, double y){
        Longitude = x;
        Latitude = y;
    }

}
