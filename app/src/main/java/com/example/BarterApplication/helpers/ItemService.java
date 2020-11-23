package com.example.BarterApplication.helpers;
//https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.SimpleLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemService {
    private static ArrayList<Item> itemList = new ArrayList<Item>();
    private static String dbItemListKeyName = "Items";

    public static boolean hasItem(Item i){
        return itemList.contains(i);
    }

    public static String getItemKeyName(){
        return dbItemListKeyName;
    }
    private static boolean initialized = false;
    private static boolean lastInsertSucceed = false;

    /**
     * @brief add an item to the item list
     * @param i item to add
     * @note also adds to database and triggers on-data-change event listener for client device
     */
    public static void addItem(Item i){
        lastInsertSucceed = false;
        insertIfNotExist(i);
        initDbListener();
    }

    /**
     * @brief remove an item from the item list
     * @param i item to remove
     */
    public static void removeItem(Item i){
        /* Synchronize with database */

        /** @todo NEEDED FOR ITERATION 3 */
    }

    /** @todo REFACTOR SO WE AREN'T LITERALLY GOING THROUGH THE ENTIRE ITEM LIST */
    public static ArrayList<Item> getUserItems(FirebaseUser u){
        if(u != null){
            // filter the entire database item list for just the user's items
            ArrayList<Item> userItems = new ArrayList<Item>();
            String my_uid = u.getUid();
            for(int i = 0; i < getItemList().size(); i++){
                Item current = getItemList().get(i);
                if(current.getOwnerId().equals(my_uid)){
                    userItems.add(current);
                }
            }
            return userItems;
        }
        else
        {
            /* return empty list instead of crashing */
            return new ArrayList<Item>();
        }
    }

    /**
     * @getter method for the item service's item list
     * @return the item list
     */
    public static ArrayList<Item> getItemList(){
        return itemList;
    }

    /**
     * @brief initialize the item service for the firsrt time
     */
    public static void init(){
        if(!initialized){
            initDbListener();
            initialized = true;
        }
    }


    /**
     * @brief access an item from the item list by it's UID
     * @param uid the UID of the item to search
     * @return item with id == UID if in list, else null
     */
    public static Item findItemByUid(String uid){
        return UidService.findItemByItemUid(uid, getItemList());
    }

    /**
     * @brief the the subset of the item list that is within a given radius of the current user
     * @param radiusKm the radius in kilometers
     * @return subset of item list within the radius being searched
     */
    public static ArrayList<Item> getItemsInRadius(int radiusKm){
        ArrayList<Item> itemsInRadius = new ArrayList<Item>();
        if(radiusKm > 0) {

            for(Item i : itemList){
                double distanceKm = getDistanceToItemKm(i);
                int distanceKmInt = (int)distanceKm;
                if(distanceKmInt < radiusKm){
                    if(!itemsInRadius.contains(i)){
                        itemsInRadius.add(i);
                    }
                }
            }
        }
        return itemsInRadius;
    }

    /**
     * @brief private method to check if the last item DB insertion was successful
     * @return
     */
    public static boolean isLastInsertSucceed() {
        return lastInsertSucceed;
    }

    /**
     * @brief helper method to access the database child node for the item list
     * @return database reference to the item node
     */
    private static DatabaseReference getKeyNode(){
        return FirebaseDatabase.getInstance().getReference().child(dbItemListKeyName);
    }

    /**
     * @brief helper method to access the database root reference
     * @return database reference to the db root
     */
    private static DatabaseReference getDbNode(){
        return FirebaseDatabase.getInstance().getReference();
    }


    /**
     * @brief get the distance to an item (from the current user) in kilometers
      * @param i the item to seek
     * @return the ditsance to the item in kilometers
     */
    private static double getDistanceToItemKm(Item i){
        Location myLocation = LocationHelper.getLocation();
        SimpleLocation SimpleItemLocation = i.getLocation();
        Location itemLocation = new Location(LocationManager.GPS_PROVIDER);
        itemLocation.setLatitude(SimpleItemLocation.Latitude);
        itemLocation.setLongitude(SimpleItemLocation.Longitude);
        double distance = myLocation.distanceTo(itemLocation);
        distance = distance/1000.0d; /* convert to Km because distanceTo returns metres */

        /* Account for epsilon rounding */
        if(Math.abs(distance) < 1e-6d){
            distance = 0.0d;
        }
        return distance;
    }


    /**
     * @todo JIALI JIN : need you to write javadoc, I'm not certain what this function does - Carl
     *
     * @todo Currently only check uid, need to check other attribute? - Jiali
     * @param i the item to insert if not exist
     */
    private static void insertIfNotExist(Item i) {
        Query queryToGetData = getKeyNode().orderByChild("uid").equalTo(i.getUid());
        queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    getKeyNode().child(i.getUid()).setValue(i).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            lastInsertSucceed = true;
                            Log.d("IS_insertIfNotExist","insertIfNotExist item " + i.getName() + " is successful!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            lastInsertSucceed = false;
                            Log.d("IS_insertIfNotExist","Fail to insertIfNotExist item " + i.getName());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @brief helper method to launc the database datachange event listener for item service
     */
    private static void initDbListener() {
        getKeyNode().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                for(DataSnapshot itDataSnapshot : dataSnapshot.getChildren()){
                    Item item = itDataSnapshot.getValue(Item.class);
                    itemList.add(item);
                    Log.i("DEBUG", "onDataChange: " + item);
                }
                Log.i("IS_initDbListener", "Operation is successful!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("IS_initDbListener", "Failed to read value.", error.toException());
            }
        });
    }
}
