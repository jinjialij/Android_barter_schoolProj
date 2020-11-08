package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.BarterApplication.helpers.MyAdapter;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private ChildEventListener mChild;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button bt;
    private EditText search;
    private TextView back;
    private ListView result;
    private ArrayList<Item> items;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemRequest> itemRequests;

    List<Object> Array = new ArrayList<Object>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        bt = findViewById(R.id.searchbtn);
        search = findViewById(R.id.itemName);
        back = findViewById(R.id.backBtn);
        result = findViewById(R.id.listView);
        mAuth = FirebaseAuth.getInstance();
        items = new ArrayList<>();
        items = (ArrayList<Item>) getIntent().getSerializableExtra("name");
        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_search, list);
        result.setAdapter(adapter);
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
        for (ItemRequest itemRequest:itemRequests){
            Item requestItem = UidService.findItemByItemUid(itemRequest.getRequestItemId(), items);
            if(requestItem!=null){
                String text = "Request id: " + itemRequests.get(0).getUid() + " : " + requestItem.getName();
                data.add(text);
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.requestRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String [] mData = new String[data.size()];
        mData = data.toArray(mData);
        mAdapter = new MyAdapter(mData);
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
}


