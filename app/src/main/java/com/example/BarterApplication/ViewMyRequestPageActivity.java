package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        itemRequests = ItemRequestService.getNotDeletedItemRequestList();
        items = ItemService.getItemList();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Button logoutBtn = (Button) findViewById(R.id.viewMyRequestLogoutBtn);

        Button viewAllBtn = (Button) findViewById(R.id.viewAllRequestsBtn);
        Button viewSentBtn = (Button) findViewById(R.id.viewSentRequestBtn);
        Button viewReceivedBtn = (Button) findViewById(R.id.viewReceivedRequestBtn);
        Button viewCompletedBtn = (Button) findViewById(R.id.ViewMyRequestCompletedBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                updateUI(null);
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemRequests = ItemRequestService.getNotDeletedItemRequestList();
                ArrayList<ItemRequest> currentUserRelatedRequests = new ArrayList<>();
                for (ItemRequest itemRequest:itemRequests){
                    boolean add = false;
                    Item requestItem = UidService.findItemByItemUid(itemRequest.getRequestItemId(), ItemService.getItemList());
                    if (requestItem.getOwnerId().equals(currentUser.getUid())){
                        add = true;
                    }

                    if (itemRequest.getRequesterId().equals(currentUser.getUid())){
                        add = true;
                    }

                    if (add){
                        currentUserRelatedRequests.add(itemRequest);
                    }
                }
                itemRequests = currentUserRelatedRequests;
                updateList();
            }
        });

        //filter requests sent by current user
        viewSentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemRequest> requestFromOthers = new ArrayList<>();
                itemRequests = ItemRequestService.getNotDeletedItemRequestList();
                for (ItemRequest itemRequest:itemRequests){
                    if (itemRequest.getRequesterId().equals(currentUser.getUid()) && !itemRequest.isCompleted()){
                        requestFromOthers.add(itemRequest);
                    }
                }
                itemRequests = requestFromOthers;
                updateList();
            }
        });

        //filter received requests from others
        viewReceivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemRequest> requestOfOneself = new ArrayList<>();
                itemRequests = ItemRequestService.getNotDeletedItemRequestList();
                for (ItemRequest itemRequest:itemRequests){
                    if (!itemRequest.getRequesterId().equals(currentUser.getUid())  && !itemRequest.isCompleted() && currentUser.getUid().equals(UidService.findItemByItemUid(itemRequest.getRequestItemId(), ItemService.getItemList()).getOwnerId())){
                        requestOfOneself.add(itemRequest);
                    }
                }
                itemRequests = requestOfOneself;
                updateList();
            }
        });

        viewCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ItemRequest> completedRequest = new ArrayList<>();
                itemRequests = ItemRequestService.getNotDeletedItemRequestList();
                //only show user related completed itemRequest
                for (ItemRequest itemRequest:itemRequests){
                    boolean add = false;
                    if (itemRequest.isCompleted()){
                        Item requestItem = UidService.findItemByItemUid(itemRequest.getRequestItemId(), ItemService.getItemList());
                        if (requestItem.getOwnerId().equals(currentUser.getUid())){
                            add = true;
                        }

                        if (itemRequest.getRequesterId().equals(currentUser.getUid())){
                            add = true;
                        }

                        if (add){
                            completedRequest.add(itemRequest);
                        }
                    }
                }
                itemRequests = completedRequest;
                updateList();
            }
        });

    }

    public void onStart(){
        super.onStart();
        updateList();
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void updateList() {
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
