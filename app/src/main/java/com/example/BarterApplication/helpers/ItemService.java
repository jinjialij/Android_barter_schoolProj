package com.example.BarterApplication.helpers;

import android.util.Log;
import com.example.BarterApplication.Item;
import com.example.BarterApplication.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ItemService {
    private static ArrayList<Item> itemList = new ArrayList<Item>();
    private static String databaseItemListKey = "Items";

    public static void updateItem( Item oldItem, Item newItem){
        /** @todo NEEDED FOR ITERATION 3 */
    }

    public static void addItem(Item i){
        DatabaseReference itemNode =  FirebaseDatabase.getInstance().getReference().child(databaseItemListKey);
        itemNode.child(i.getUid()).setValue(i);
    }

    /** @todo REFACTOR SO WE AREN'T LITERALLY GOING THROUGH THE ENTIRE ITEM LIST */
    public static ArrayList<Item> getUserItems(FirebaseUser u){

        if(u != null){
            // filter the entire database item list for just the user's items
            ArrayList<Item> userItems = new ArrayList<Item>();
            int i;
            for( i = 0; i < itemList.size(); i++){
                Item current = itemList.get(i);
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

    private ArrayList<Item> readItemData() {
        DatabaseReference itemNode =  FirebaseDatabase.getInstance().getReference().child(databaseItemListKey);
        ArrayList<Item> allItems = new ArrayList<Item>();
        itemNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itDataSnapshot: dataSnapshot.getChildren()){
                    Item item = itDataSnapshot.getValue(Item.class);
                    allItems.add(item);
                }
                Log.d("TAG", "Operation is successful!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        return allItems;
    }

    private Item findItemByUid(String uid){
        for(Item i : getItemList()){
            if(i.getUid().equals(uid)){
                return i;
            }
        }
        return null;
    }

}
