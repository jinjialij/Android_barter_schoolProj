package com.example.BarterApplication.helpers;
//https://stackoverflow.com/questions/32886546/how-to-get-all-child-list-from-firebase-android

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.BarterApplication.Item;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


    public static void addItem(Item i){
        initDbListener();
        getKeyNode().child(i.getUid()).setValue(i);
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
            ArrayList<Item> synchronizedItems = getItemList();
            int i;
            for( i = 0; i < synchronizedItems.size(); i++){
                Item current = synchronizedItems.get(i);
                if(current.getOwnerId().equals(u.getUid())){
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

    private static void initDbListener() {
        getKeyNode().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot itDataSnapshot : dataSnapshot.getChildren()){
                    Item item = itDataSnapshot.getValue(Item.class);
                    if(!itemList.contains(item)){
                        itemList.add(item); /* add */
                    }
                    else{   /* update */
                        itemList.set(itemList.indexOf(item), item);
                    }
                    Log.i("DEBUG", "onDataChange: " + item);
                }
                Log.i("TAG", "Operation is successful!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private static Item findItemByUid(String uid){
        for(Item i : getItemList()){
            if(i.getUid().equals(uid)){
                return i;
            }
        }
        return null;
    }

    private static DatabaseReference getKeyNode(){
        return FirebaseDatabase.getInstance().getReference().child(dbItemListKeyName);
    }


    private static DatabaseReference getDbNode(){
        return FirebaseDatabase.getInstance().getReference();
    }




}
