package com.example.BarterApplication.helpers;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

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
        Task<Location> locTask = fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        });
        locTask.addOnSuccessListener(task ->{
            location = task;
        });


    }

    public static Location getLocation() { return location; }

    public static void setLocation(Location loc) { location = loc; }
}
