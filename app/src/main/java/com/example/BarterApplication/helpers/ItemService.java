package com.example.BarterApplication.helpers;

import android.util.Log;
import com.example.BarterApplication.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ItemService {
    public static void createNewItem(DatabaseReference db, Item item) {
        db.child("Items").child(item.getUid()).setValue(item);
    }

    public static void readItemData(DatabaseReference itemNode, final ArrayList<Item> items) {
        itemNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itDataSnapshot: dataSnapshot.getChildren()){
                    Item item = itDataSnapshot.getValue(Item.class);
                    items.add(item);
                }
                Log.d("TAG", "Operation is successful!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}
