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
    private static String databaseItemListKey = "Items";
    public static void createNewItem(FirebaseDatabase db, Item item) {
        DatabaseReference itemNode =  db.getReference().child(databaseItemListKey);
        itemNode.child(item.getUid()).setValue(item);
    }

    public static void updateItem(FirebaseDatabase db, Item oldItem, Item newItem){
        /** @todo NEEDED FOR ITERATION 3 */
    }

    private Item findItemByUid(FirebaseDatabase db, String uid){
        ArrayList<Item> allItems = readItemData(db);
        for(Item i : allItems){
            if(i.getUid().equals(uid)){
                return i;
            }
        }
        return null;
    }

    public static ArrayList<Item> readItemData(FirebaseDatabase db) {
        DatabaseReference itemNode =  db.getReference().child(databaseItemListKey);
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

    /** @todo REFACTOR SO WE AREN'T LITERALLY GOING THROUGH THE ENTIRE ITEM LIST */
    public static ArrayList<Item> getUserItems(FirebaseDatabase db, FirebaseUser u){
        ArrayList<Item> allItems = readItemData(db);

        // filter the entire database item list for just the user's items
        ArrayList<Item> userItems = new ArrayList<Item>();
        int i;
        for( i = 0; i < allItems.size(); i++){
            Item current = allItems.get(i);
            if(current.getOwnerId().equals(u.getUid())){
                userItems.add(current);
            }
        }
        return userItems;
    }
}
