package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Item> items;
    private ItemRequest receivedItemRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_request);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        items = ItemService.getItemList();

        receivedItemRequest = (ItemRequest)getIntent().getSerializableExtra("itemRequestSelected");
        if(receivedItemRequest == null) {
            Toaster.generateToast(this, "Internal error, please try again");
            goBackToMainActivity(false);
        }

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
        Button closeBtn = (Button) findViewById(R.id.closeBtn);
        Button deleteBtn = findViewById(R.id.deleteMatchButton);
        TextView itemRequestTitle = findViewById(R.id.itemRequestTitle);

        if (receivedItemRequest!=null && items!=null && !items.isEmpty()){
            if (receivedItemRequest.isDeleted()){
                refuseBtn.setEnabled(false);
                acceptBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
            }else if (receivedItemRequest.isAccepted()&& !receivedItemRequest.isDeleted()){
                refuseBtn.setEnabled(false);
                acceptBtn.setEnabled(false);
                deleteBtn.setEnabled(true);
            }
            else{
                refuseBtn.setEnabled(true);
                acceptBtn.setEnabled(true);
                deleteBtn.setEnabled(false);
            }
            HashMap<String, String> requestItemInfoMap = new LinkedHashMap<>();
            ArrayList<HashMap<String, String>> offeredItemInfoMapList = new ArrayList<>();
            Item requestItem = ItemService.findItemByUid(receivedItemRequest.getRequestItemId());
            getItemMap(requestItem, requestItemInfoMap);

            for(String id: receivedItemRequest.getItemIdsOffered()){
                Item offeredItem = UidService.findItemByItemUid(id, items);
                HashMap<String, String> map = new LinkedHashMap<>();
                getItemMap(offeredItem, map);
                offeredItemInfoMapList.add(map);
            }

            String requestUid = receivedItemRequest.getUid();
            requestId.setText(requestUid);
            requestItemInfo.setText(printItemMap(requestItemInfoMap));
            offerItemInfo.setText(printItemMapList(offeredItemInfoMapList));
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receivedItemRequest.setDeleted(true);
                refuseBtn.setEnabled(false);
                acceptBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                itemRequestTitle.setText("Request has been deleted");
                Toast toast=Toast.makeText(MyRequestActivity.this, "The accepted request has been deleted！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 10);
                toast.show();
                ItemRequestService.updateItemRequestStatus(receivedItemRequest);
                goBackToMyRequestActivity(true);
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

    private void getItemMap(Item item, HashMap<String, String> map){
        if (item!=null){
            map.put("Name: ", item.getName());
            map.put("Labels: ", item.getLabels().toString());
            map.put("Description: ", item.getDescription());
            //@todo use uid to get owner name
            map.put("Owner Name: ", item.getOwnerId());
        }
    }

    private String printItemMap(HashMap<String, String> map){
        String mapString = "";
        if (map!=null && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()) {
                mapString += entry.getKey() + entry.getValue() + "\n";
            }
        }
        return mapString;
    }

    private String printItemMapList(ArrayList<HashMap<String, String>> mapList){
        String mapListString = "";
        if (mapList!=null && !mapList.isEmpty()){
            for (HashMap<String, String> map:mapList){
                String mapString = printItemMap(map);
                mapListString += mapString + "\n";
            }

        }
        return mapListString;
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

    public void goBackToMyRequestActivity(boolean updateStatusFromMyRequest){
        Intent intent = new Intent(this, ViewMyRequestPageActivity.class);
        intent.putExtra("updateStatusFromMyRequest", updateStatusFromMyRequest);
        startActivity(intent);
    }
}
