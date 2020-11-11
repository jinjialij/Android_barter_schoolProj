package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import com.example.BarterApplication.helpers.MyAdapter;

public class ViewMyRequestPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private ArrayList<ItemRequest> itemRequests;
    private ArrayList<Item> items;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requestspage);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        itemRequests = ItemRequestService.getItemRequestList();
        items = ItemService.getItemList();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Button logoutBtn = (Button) findViewById(R.id.viewMyRequestLogoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                updateUI(null);
            }
        });
    }

    public void onStart(){
        super.onStart();
        ArrayList<String> data = new ArrayList<>();
        ArrayList<ItemRequest> itemRequestsData = new ArrayList<>();
        for (ItemRequest itemRequest:itemRequests){
            itemRequestsData.add(itemRequest);
            Item requestItem = UidService.findItemByItemUid(itemRequest.getRequestItemId(), items);
            if(requestItem!=null){
                String text = "Request id: " + itemRequest.getUid() + " : " + requestItem.getName();
                data.add(text);
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.requestRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String [] mData = new String[data.size()];
        mData = data.toArray(mData);
        mAdapter = new MyAdapter(mData, itemRequestsData, items);
        recyclerView.setAdapter(mAdapter);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
