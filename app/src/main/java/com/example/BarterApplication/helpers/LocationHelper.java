package com.example.BarterApplication.helpers;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

public class LocationHelper extends Thread{

    private static Location location;
    private final int REFRESH_INTERVAL = 5000;

    private FusedLocationProviderClient fusedLocationClient;

    public LocationHelper(FusedLocationProviderClient client){
        fusedLocationClient = client;
        this.start();
    }

    @Override
    public void run() {
        while(true){
            try {
                updateLocation();
                Log.i("Location", location +"");
                Thread.sleep(REFRESH_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateLocation(){

//        Task<Location> x = fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new cancel(){
//
//        });
//        x.addOnCanceledListener()
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener( location-> {

                    if (location != null) {
                        setLocation(location);
                        // Logic to handle location object
                    }

                });

    }

    public static Location getLocation() { return location; }

    public static void setLocation(Location loc) { location = loc; }
}
