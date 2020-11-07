package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewMyRequestPageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private ArrayList<ItemRequest> itemRequests;
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requestspage);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        itemRequests = new ArrayList<>();
        items = new ArrayList<>();
        itemRequests = (ArrayList<ItemRequest>)getIntent().getSerializableExtra("itemRequestsExtra");
        items  = (ArrayList<Item>)getIntent().getSerializableExtra("itemsExtra");
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
        TextView itemR = (TextView) findViewById(R.id.itemRequest1);
        Item requestItem = UidService.findItemByItemUid(itemRequests.get(0).getRequestItemId(), items);
        if(requestItem!=null){
            itemR.setText("Request id: " + itemRequests.get(0).getUid() + " : " + requestItem.getName());
        }
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
}
