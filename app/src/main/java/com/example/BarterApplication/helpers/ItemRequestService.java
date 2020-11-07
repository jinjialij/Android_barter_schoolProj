package com.example.BarterApplication.helpers;

import android.util.Log;
import com.example.BarterApplication.ItemRequest;
import com.example.BarterApplication.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ItemRequestService {
    public static void createNewItemRequest(DatabaseReference db, ItemRequest itemReq) {
        db.child("ItemRequests").child(itemReq.getUid()).setValue(itemReq);
    }

    public static void readItemRequestData(DatabaseReference itemReqNode, ArrayList<ItemRequest> itemReqs) {
        itemReqNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot itReqDataSnapshot: dataSnapshot.getChildren()){
                    ItemRequest itemReq = itReqDataSnapshot.getValue(ItemRequest.class);
                    itemReqs.add(itemReq);
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

    public static void updateItemRequest(DatabaseReference db, ItemRequest itemReq) {
        db.child("ItemRequests").child(itemReq.getUid()).child("accepted").setValue(itemReq.isAccepted());
    }
}