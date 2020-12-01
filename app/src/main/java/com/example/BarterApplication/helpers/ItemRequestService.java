package com.example.BarterApplication.helpers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.ItemRequest;
import com.example.BarterApplication.User;
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
import java.util.Map;

public class ItemRequestService {
    private static ArrayList<ItemRequest> itemRequestList = new ArrayList<ItemRequest>();
    private static String dbItemListKeyName = "ItemRequests";
    private static boolean initialized = false;
    private static boolean lastInsertSucceed = false;
    private static boolean lastUpdateSucceed = false;

    public static void addItemRequest(ItemRequest i){
        lastInsertSucceed = false;
        insertIfNotExist(i);
        initDbListener();
    }

    public static void updateItemRequestStatus(ItemRequest itemReq) {
        if (itemReq.isDeleted()){
            getKeyNode().child(itemReq.getUid()).child("deleted").setValue(true)
                    .addOnSuccessListener(aVoid -> {
                        // Write was successful!
                        lastUpdateSucceed = true;
                        Log.d("IR_updateItemRequest","UpdateItemRequest itemRequest " + itemReq.getUid() + " is successful!");
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    lastUpdateSucceed = false;
                    Log.d("IR_updateItemRequest","UpdateItemRequest itemRequest " + itemReq.getUid());
                }
            });
        }else {
            getKeyNode().child(itemReq.getUid()).child("accepted").setValue(itemReq.isAccepted())
                    .addOnSuccessListener(aVoid -> {
                        // Write was successful!
                        lastUpdateSucceed = true;
                        Log.d("IR_updateItemRequest", "UpdateItemRequest itemRequest " + itemReq.getUid() + " is successful!");
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Write failed
                    lastUpdateSucceed = false;
                    Log.d("IR_updateItemRequest", "UpdateItemRequest itemRequest " + itemReq.getUid());
                }
            });
        }
        initDbListener();
    }

    public static String getDbItemListKeyName() {
        return dbItemListKeyName;
    }

    public static ArrayList<ItemRequest> getItemRequestList() {
        return itemRequestList;
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

    public static boolean isLastUpdateSucceed() {
        return lastUpdateSucceed;
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
                itemRequestList.clear();
                for(DataSnapshot itDataSnapshot : dataSnapshot.getChildren()){
                    ItemRequest itemRequest = itDataSnapshot.getValue(ItemRequest.class);
                    itemRequestList.add(itemRequest);
                    Log.i("DEBUG", "onDataChange: " + itemRequest);
                }
                Log.i("IR_initDbListener", "Operation is successful!");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("IR_initDbListener", "Failed to read value.", error.toException());
            }
        });
    }

    //@todo Currently only check uid
    private static void insertIfNotExist(ItemRequest itReq) {
        Query queryToGetData = getKeyNode().orderByChild("uid").equalTo(itReq.getUid());
        queryToGetData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    getKeyNode().child(itReq.getUid()).setValue(itReq).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Write was successful!
                            lastInsertSucceed = true;

                            Log.d("IR_insertIfNotExist","insertIfNotExist itemRequest " + itReq.getUid() + " is successful!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            lastInsertSucceed = false;
                            Log.d("IR_insertIfNotExist","Fail to insertIfNotExist itemRequest " + itReq.getUid());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}