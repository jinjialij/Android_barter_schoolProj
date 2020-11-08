package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {
    private DatabaseReference dbRef;
    private DatabaseReference itemDbRef;
    private DatabaseReference itemReqDbRef;
    private FirebaseAuth mAuth;
    private ArrayList<ItemRequest> itemRequests;
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Button logoutBtn = (Button) findViewById(R.id.buttonLogout);
        Button viewMyRequestBtn = (Button) findViewById(R.id.viewMyRequestLogoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                updateUI(null);
            }
        });

        //get items and requests
        itemReqDbRef = FirebaseDatabase.getInstance().getReference("ItemRequests");
        itemRequests = new ArrayList<>();
        ItemRequestService.readItemRequestData(itemReqDbRef, itemRequests);

        itemDbRef = FirebaseDatabase.getInstance().getReference("Items");
        items = new ArrayList<>();
        ItemService.readItemData(itemDbRef, items);
    }

    public void onStart(){
        super.onStart();
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    public void goToViewMyRequestPage(View v){
        Intent intent = new Intent(this, ViewMyRequestPageActivity.class);

        intent.putExtra("itemRequestsExtra", itemRequests);
        intent.putExtra("itemsExtra", items);
        startActivity(intent);
    }

    public void goToAddItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}
