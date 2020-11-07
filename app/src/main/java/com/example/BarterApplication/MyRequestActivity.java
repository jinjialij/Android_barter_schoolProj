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
import com.example.BarterApplication.helpers.MyAdapter;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyRequestActivity extends AppCompatActivity {
    private static final String TAG = "myRequest";

    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private ArrayList<Item> items;
    private ItemRequest receivedItemRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_request);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        items = new ArrayList<>();

        ArrayList<ItemRequest> itemRequests = (ArrayList<ItemRequest>)getIntent().getSerializableExtra("itemRequestSelected");
        if(itemRequests!=null && !itemRequests.isEmpty()) {
            receivedItemRequest = itemRequests.get(0);
        }

        items  = (ArrayList<Item>)getIntent().getSerializableExtra("itemsExtra");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void onStart(){
        super.onStart();
        TextView requestId = (TextView) findViewById(R.id.requestID);
        TextView requestItemInfo = (TextView) findViewById(R.id.requestItemInfo);
        TextView offerItemInfo = (TextView) findViewById(R.id.offeredItemInfo);
        Button acceptBtn = (Button) findViewById(R.id.acceptRequestBtn);
        Button refuseBtn = (Button) findViewById(R.id.refuseRequestBtn);

        if (receivedItemRequest!=null && items!=null && !items.isEmpty()){
            if (receivedItemRequest.isAccepted()){
                refuseBtn.setEnabled(true);
            }
            else{
                refuseBtn.setEnabled(false);
            }
            HashMap<String, String> requestItemInfoMap = new LinkedHashMap<>();
            ArrayList<HashMap<String, String>> offeredItemInfoMapList = new ArrayList<>();
            String a = receivedItemRequest.getRequestItemId();
            Item requestItem = UidService.findItemByItemUid(receivedItemRequest.getRequestItemId(), items);
            getItemMap(requestItem, requestItemInfoMap);

            for(String id: receivedItemRequest.getItemIdsOffered()){
                Item offeredItem = UidService.findItemByItemUid(id, items);
                HashMap<String, String> map = new LinkedHashMap<>();
                getItemMap(offeredItem, map);
                offeredItemInfoMapList.add(map);
            }

            String requestUid = receivedItemRequest.getUid();
            requestId.setText(requestUid);
            requestItemInfo.setText(requestItemInfoMap.toString());
            offerItemInfo.setText(offeredItemInfoMapList.toString());
        }

        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedItemRequest.setAccepted(false);
                ItemRequestService.updateItemRequest(dbRef, receivedItemRequest);
                refuseBtn.setEnabled(false);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void getItemMap(Item item, HashMap<String, String> map){
        if (item!=null){
            map.put("Name: ", item.getName());
            map.put("Labels: ", item.getLabels().toString());
            map.put("Description: ", item.getDescription());
            //@todo use uid to get owner name
            map.put("Owner Name: ", item.getOwnerId());
        }
    }
}
