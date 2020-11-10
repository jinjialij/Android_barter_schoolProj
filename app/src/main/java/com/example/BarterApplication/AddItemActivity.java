package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BarterApplication.helpers.AddItemHelper;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.Toaster;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;


public class AddItemActivity extends AppCompatActivity {

    FirebaseDatabase fDB;
    DatabaseReference dbRef;

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void confirmAddOnClick(View view){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String ownerId = user.getUid();

        EditText itemNameEditText = findViewById(R.id.AddItemNameEditText);
        EditText itemDescEditText = findViewById(R.id.AddItemDescriptionEditText);
        EditText itemLabelsEditText = findViewById(R.id.AddItemLabelsEditText);

        String itemName = itemNameEditText.getText().toString();
        String itemDesc = itemDescEditText.getText().toString();
        String giantLabelString = itemLabelsEditText.getText().toString();
        String[] labelArray = giantLabelString.split(",");
        ArrayList<String> labelArrayList = new ArrayList<String>();
        for(int i = 0; i < labelArray.length; i++){
            labelArrayList.add(labelArray[i]);
        }

        final String userId = UidService.getCurrentUserUUID();
        boolean titleIsValid = itemName.matches("^.*[^a-zA-Z0-9 ].*$");

        if (itemName.isEmpty()){
            Toaster.generateToast(AddItemActivity.this,"Please enter Item Title/Description.");
        }else if(itemName.equals(R.string.AddItemNameHint)){
            Toaster.generateToast(AddItemActivity.this,"Item title not valid.");
        }
        else if(titleIsValid){
            Toaster.generateToast(AddItemActivity.this, "Invalid item name");
        }
        else {
            fDB = FirebaseDatabase.getInstance();
            dbRef = fDB.getReference(ItemService.getItemKeyName());

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Item i = new Item(itemName, itemDesc, labelArrayList, userId);
                    ItemService.addItem(i);

                    int db_sync_time_ms = 3000; /** @todo FIX THE SYNC METHOD LATER, I'M FIXING THIS AT 11:00 AM THE DAY BEFORE ITERATION */

                    /* simple way to sync with db : wait 3 seconds before doing anything */
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if(ItemService.hasItem(i)){
                                Toaster.generateToast(AddItemActivity.this, "Add item " + itemName + " successfully. Redirecting to homepage");
                                goBackToHomepage(view);
                            }
                            else{
                                Toaster.generateToast(AddItemActivity.this, "Error adding item " + itemName);
                            }
                        }
                    }, db_sync_time_ms);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toaster.generateToast(AddItemActivity.this, "Error adding item " + itemName);
                }
            });
        }
    }

    public void goBackToHomepage(View view){
        Intent intent = new Intent(this, HomepageActivity.class);//need update to item list page
        startActivity(intent);
    }

}
