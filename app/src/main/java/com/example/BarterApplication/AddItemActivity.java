package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BarterApplication.helpers.AddItemHelper;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

        EditText input_item_title = findViewById(R.id.viewMyItemsName);
        EditText input_item_description = findViewById(R.id.viewMyItemsDescription);
        final TextView addItemMessage = findViewById(R.id.addItemMessage);

        final String title = input_item_title.getText().toString();
        final String description = input_item_description.getText().toString();


        // Generate uuid
        final String uuid = UUID.randomUUID().toString().replace("-", "");

        boolean title_validator = title.matches("^.*[^a-zA-Z0-9 ].*$");

        if (title.isEmpty() || description.isEmpty()){
            Toaster.generateToast(AddItemActivity.this,"Please enter Item Title/Description.");
        }else if(title.equals("Name")){
            Toaster.generateToast(AddItemActivity.this,"Item title not valid.");
        }else if(description.equals("description")){
            Toaster.generateToast(AddItemActivity.this,"Item description not valid.");
        }
        else if(title_validator){
            addItemMessage.setText("Item add failed.");
        }else{
            fDB = FirebaseDatabase.getInstance();
            dbRef = fDB.getReference("Items");

            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    AddItemHelper addItemHelper = new AddItemHelper(title,description,uuid,ownerId);
                    dbRef.child(uuid).setValue(addItemHelper);
                    addItemMessage.setText("Item add successful.\nUUID: "+uuid+"\nRedirecting in 5 seconds...");


                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            goToMyItems(null);
                        }
                    }, 5000);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    addItemMessage.setText("Item add failed.\nUUID: "+uuid);
                }
            });
        }
    }

    public void goToMyItems(View view){
        Intent intent = new Intent(this, HomepageActivity.class);//need update to item list page
        startActivity(intent);
    }

}
