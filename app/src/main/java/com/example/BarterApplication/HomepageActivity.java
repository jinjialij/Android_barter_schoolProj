package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
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
    private ArrayList<ItemRequest> itemRequests = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private Button bt;
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
         bt = (Button) findViewById(R.id.bTS);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                updateUI(null);
            }
        });

        //get items and requests
        itemReqDbRef = FirebaseDatabase.getInstance().getReference("ItemRequests");
        ItemRequestService.readItemRequestData(itemReqDbRef, itemRequests);

        itemDbRef = FirebaseDatabase.getInstance().getReference("Items");
        items = ItemService.getItemList();
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

    public void goToManageItems(View v){
        Intent intent = new Intent(this, ManageItemsActivity.class);
        startActivity(intent);
    }

}
