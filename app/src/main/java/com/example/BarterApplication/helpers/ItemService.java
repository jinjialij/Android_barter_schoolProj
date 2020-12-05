package com.example.BarterApplication.helpers;
//https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.SimpleLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemService {
    private static ArrayList<Item> itemList = new ArrayList<Item>();
    private static final String dbItemListKeyName = "Items";

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
        removeIfExists(i);
    }

    /**
     * @brief update an item
     * @param old_item the old item
     * @param new_item
     */
    public static void updateItem(Item old_item, Item new_item){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(old_item.getOwnerId() == currentUser.getUid()){
            removeItem(old_item);
            addItem(new_item);
        }
        else {
            Log.e("[ITEM SERVICE]", "updateItem: user tried to update item ");
        }
    }

    /** @todo REFACTOR SO WE AREN'T LITERALLY GOING THROUGH THE ENTIRE ITEM LIST */
    public static ArrayList<Item> getUserItems(FirebaseUser u){
        ArrayList<Item> userItems = new ArrayList<Item>();

        /* Need to null check because sometimes callback
        functions occur after FirebaseUser.signout() */
        if(u != null){
            /** @note I know this is not ideal but we're working on a deadline here - carl */
            String my_uid = u.getUid();
            for(int i = 0; i < getItemList().size(); i++){
                Item current = getItemList().get(i);
                if(current.getOwnerId().equals(my_uid)){
                    userItems.add(current);
                }
            }
        }
        return userItems;
    }

    public static ArrayList<Item> getOtherUserItems(ArrayList<Item> currentUserItems){
        ArrayList<String> currentUserItemUids = new ArrayList<>();
        ArrayList<Item> otherUserItems = new ArrayList<>();

        for (Item item: currentUserItems) {
            currentUserItemUids.add(item.getUid());
        }

        for (Item item : itemList){
            if (!currentUserItemUids.contains(item.getUid())){
                otherUserItems.add(item);
            }
        }

        return otherUserItems;
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
     * @return subset of items THAT ARE NOT OWNED BY CURRENT USER within the radius being searched
     */
    public static ArrayList<Item> getOtherItemsInRadius(int radiusKm){
        ArrayList<Item> itemsInRadius = new ArrayList<Item>();
        if(radiusKm > 0) {
            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
            if(u != null) {
                /*
                FOR each item in the list,
                IF it is within radius and NOT owned by current user
                THEN add it to the list of nearby items
                */
                for(Item i : itemList){
                    if(i.getUid() != u.getUid()) {
                        double distanceKm = getDistanceToItemKm(i);
                        int distanceKmInt = (int)distanceKm;
                        if(distanceKmInt < radiusKm){
                            if(!itemsInRadius.contains(i)){
                                itemsInRadius.add(i);
                            }
                        }
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
     * @brief update the user items to the user's new location
     * @param u the firebase user
     *
     * @note Due to database structure, this is NOT efficient but it is a working fix for now
     */
    public static void updateUserItems(FirebaseUser u){
        if(u != null){
            ArrayList<Item> userItems = getUserItems(u);
            for(Item i : userItems){
                Item tmp = i;
                tmp.setLocation(new SimpleLocation(LocationHelper.getLocation()));
                updateItem(i, tmp);
            }
        }
    }



    /**
     * @brief helper method to access the database child node for the item list
     * @return database reference to the item node
     */
    private static DatabaseReference getKeyNode(){
        return FirebaseDatabase.getInstance().getReference().child(dbItemListKeyName);
    }


    /**
     * @brief get the distance to an item (from the current user) in kilometers
     * @param i the item to seek
     * @return the distance to the item in kilometers
     */
    public static double getDistanceToItemKm(Item i){
        Location myLocation = LocationHelper.getLocation();   /* user's location */
        SimpleLocation SimpleItemLocation = i.getLocation();  /* item location   */

        //The reason why this is so complicated is because
        // manually performing the trigonometry along the surface of the earth
        // IS REALLY DIFFICULT to do without bugs. Floating point rounding errors
        // and epsilon errors are occurring EVERYWHERE because GPS precision (num decimals)
        // is much less than required to accurately represent doubles or floats
        // (double representation is 14 places (decimal rep) and float is 7 places (decimal rep)
        // but gps fine grained coordinates for lat and long are 4 decimals each
        //
        // As a solution, we are leveraging the Android.location::distanceTo API - Carl
        Location itemLocation = new Location(LocationManager.GPS_PROVIDER);
        itemLocation.setLatitude(SimpleItemLocation.Latitude);
        itemLocation.setLongitude(SimpleItemLocation.Longitude);
        double distance = myLocation.distanceTo(itemLocation);

        /* convert to Km because Android.location::distanceTo returns metres */
        distance = distance/1000.0d;

        /* Account for epsilon rounding */
        if(Math.abs(distance) < 1e-6d){
            distance = 0.0d;
        }
        return distance;
    }


    /**
     * @brief Insert an item into the child list of database items if it isn't already there
     * @param i the item to insert if not exist
     * @note item "existence" is determined by checking if NO other item shares the same UID
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
     * @brief remove an item from the database if it exists
     * @param i the item to remove
     * @note if item is not in DB nothing will happen
     */
    private static void removeIfExists(Item i){
        if(getKeyNode().orderByChild("uid").equalTo(i.getUid()) != null){
            getKeyNode().child(i.getUid()).getRef().removeValue((databaseError, databaseReference) -> itemList.remove(i));
        }
        else {
            Log.d("[ITEM SERVICE]", "removeIfExists: getKeyNode().orderByChild(\"uid\").equalTo(i.getUid()) was null for UID:" + i.getUid());
        }
    }

    /**
     * @brief helper method to launch the database data change event listener for item service
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

    public static HashMap<String, String> getItemMap(Item item, HashMap<String, String> map){
        if (item!=null){
            map.put("Name: ", item.getName());
            map.put("Labels: ", item.getLabels().toString());
            map.put("Description: ", item.getDescription());
            //@todo use uid to get owner name
            map.put("Owner Name: ", item.getOwnerId());
        }

        return map;
    }

    public static String printItemMap(HashMap<String, String> map){
        String mapString = "";
        if (map!=null && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()) {
                mapString += entry.getKey() + entry.getValue() + "\n";
            }
        }
        return mapString;
    }

    public static String printItemMapList(ArrayList<HashMap<String, String>> mapList){
        String mapListString = "";
        if (mapList!=null && !mapList.isEmpty()){
            for (HashMap<String, String> map:mapList){
                String mapString = printItemMap(map);
                mapListString += mapString + "\n";
            }

        }
        return mapListString;
    }
}
