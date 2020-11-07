package com.example.BarterApplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.BarterApplication.helpers.AddItemHelper;
import com.example.BarterApplication.helpers.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


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
        EditText input_item_title = findViewById(R.id.viewMyItemsTitle);
        EditText input_item_description = findViewById(R.id.viewMyItemsDescription);

        final String title = input_item_title.getText().toString();
        final String description = input_item_description.getText().toString();

        // Generate uuid
        final String uuid = UUID.randomUUID().toString().replace("-", "");

        if (title.isEmpty() || description.isEmpty()){
            Toaster.generateToast(AddItemActivity.this,"Please enter Item Title/Description.");
        }//else if(title.equals("Title")){
           // Toaster.generateToast(AddItemActivity.this,"Item title not valid.");
        //}else if(description.equals("Description")){
          //  Toaster.generateToast(AddItemActivity.this,"Item description not valid.");
       // }
        else{
            fDB = FirebaseDatabase.getInstance();
            dbRef = fDB.getReference("Items");

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    AddItemHelper addItemHelper = new AddItemHelper(title,description,uuid);
                    dbRef.child(uuid).setValue(addItemHelper);
                    Toaster.generateToast(AddItemActivity.this,"Item add successful.\nUUID: "+uuid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
