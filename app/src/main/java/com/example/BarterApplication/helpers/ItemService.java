package com.example.BarterApplication.helpers;
//https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.BarterApplication.AddItemActivity;
import com.example.BarterApplication.HomepageActivity;
import com.example.BarterApplication.Item;
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

    public static void addItem(Item i){
        lastInsertSucceed = false;
        insertIfNotExist(i);
        initDbListener();
    }

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

    public static ArrayList<Item> getItemList(){
        return itemList;
    }

    public static void init(){
        if(!initialized){
            initDbListener();
            initialized = true;
        }
    }

    //initialized and refresh to synchronize with database
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

    //@todo Currently only check uid, need to check other attribute?
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

    public static Item findItemByUid(String uid){
        return UidService.findItemByItemUid(uid, getItemList());
    }

    public static ArrayList<Item> getItemsInRadius(int radiusKm){
        ArrayList<Item> itemsInRadius = new ArrayList<Item>();
        if(radiusKm > 0) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            for(Item i : itemList){
                if(DistanceHelper.getDistanceToItem(i) < radiusKm){
                    if(!itemsInRadius.contains(i)){
                        itemsInRadius.add(i);
                    }
                }
            }
        }
        return itemsInRadius;
    }

    private static DatabaseReference getKeyNode(){
        return FirebaseDatabase.getInstance().getReference().child(dbItemListKeyName);
    }


    private static DatabaseReference getDbNode(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static boolean isLastInsertSucceed() {
        return lastInsertSucceed;
    }
}
