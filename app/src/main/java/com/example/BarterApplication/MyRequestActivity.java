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
import com.example.BarterApplication.helpers.MyAdapter;
import com.example.BarterApplication.helpers.Toaster;
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
    private FirebaseUser currentUser;
    private ArrayList<Item> items;
    private ItemRequest receivedItemRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_request);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        items = ItemService.getItemList();

        receivedItemRequest = (ItemRequest)getIntent().getSerializableExtra("itemRequestSelected");
        if(receivedItemRequest == null) {
            Toaster.generateToast(this, "Internal error, please try again");
            goBackToMainActivity(false);
        }

        updateUI(currentUser);
    }

    public void onStart(){
        super.onStart();
        TextView requestId = (TextView) findViewById(R.id.requestID);
        TextView requestItemInfo = (TextView) findViewById(R.id.requestItemInfo);
        TextView offerItemInfo = (TextView) findViewById(R.id.offeredItemInfo);
        Button acceptBtn = (Button) findViewById(R.id.acceptRequestBtn);
        Button refuseBtn = (Button) findViewById(R.id.refuseRequestBtn);
        Button closeBtn = (Button) findViewById(R.id.closeBtn);

        //for requests sent by current user, hide accept and refuse button
        if (receivedItemRequest.getRequesterId().equals(currentUser.getUid())){
            refuseBtn.setVisibility(View.INVISIBLE);
            acceptBtn.setVisibility(View.INVISIBLE);
            closeBtn.setVisibility(View.INVISIBLE);
            refuseBtn.setEnabled(false);
            acceptBtn.setEnabled(false);
            closeBtn.setEnabled(false);
        } else {
            refuseBtn.setVisibility(View.VISIBLE);
            acceptBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            refuseBtn.setEnabled(true);
            acceptBtn.setEnabled(true);
            closeBtn.setEnabled(true);
        }

        if (items!=null && !items.isEmpty()){
            HashMap<String, String> requestItemInfoMap = new LinkedHashMap<>();
            ArrayList<HashMap<String, String>> offeredItemInfoMapList = new ArrayList<>();
            Item requestItem = ItemService.findItemByUid(receivedItemRequest.getRequestItemId());
            requestItemInfoMap = ItemService.getItemMap(requestItem, requestItemInfoMap);

            for(String id: receivedItemRequest.getItemIdsOffered()){
                Item offeredItem = UidService.findItemByItemUid(id, items);
                HashMap<String, String> map = new LinkedHashMap<>();
                map = ItemService.getItemMap(offeredItem, map);
                offeredItemInfoMapList.add(map);
            }

            String requestUid = receivedItemRequest.getUid();
            requestId.setText(requestUid);
            requestItemInfo.setText(ItemService.printItemMap(requestItemInfoMap));
            offerItemInfo.setText(ItemService.printItemMapList(offeredItemInfoMapList));
        }

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedItemRequest.setAccepted(true);
                acceptBtn.setEnabled(false);
                refuseBtn.setEnabled(true);
            }
        });

        refuseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedItemRequest.setAccepted(false);
                refuseBtn.setEnabled(false);
                acceptBtn.setEnabled(true);
            }
        });

        //save changes only when save button is clicked
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemRequestService.updateItemRequestStatus(receivedItemRequest);
                goBackToMainActivity(true);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        goBackToMainActivity(false);
    }

    public void goBackToMainActivity(boolean updateStatusFromMyRequest){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("updateStatusFromMyRequest", updateStatusFromMyRequest);
        startActivity(intent);
    }
}
