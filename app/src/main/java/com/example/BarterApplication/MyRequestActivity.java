package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
            goBackToHomepageActivity(false, false);
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
        Button saveBtn = (Button) findViewById(R.id.BarterMyRequestSaveBtn);

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

        //save changes only when save button is clicked. Also, send email
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedItemRequest.setMatched(true);
                ItemRequestService.updateItemRequestStatus(receivedItemRequest);
                Toaster.generateToast(MyRequestActivity.this, getString(R.string.BarterActivity_SaveRequestSuccess));
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{receivedItemRequest.getRequesterEmail()});
                String acceptSubject = "Your Barter request has been accepted";
                String refuseSubject = "Your Barter request has been refused";
                String content = "One of your sent request just changes its status. Please find more information in the View My Request of Barter.";
                if (receivedItemRequest.isAccepted()&&receivedItemRequest.isMatched()){
                    i.putExtra(Intent.EXTRA_SUBJECT, acceptSubject);
                }
                else if (!receivedItemRequest.isAccepted()&&receivedItemRequest.isMatched()){
                    i.putExtra(Intent.EXTRA_SUBJECT, refuseSubject);
                }
                i.putExtra(Intent.EXTRA_TEXT   , content);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MyRequestActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                saveBtn.setEnabled(false);
                acceptBtn.setEnabled(false);
                refuseBtn.setEnabled(false);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToHomepageActivity(true, true);
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
        goBackToHomepageActivity(false, false);
    }

    public void goBackToHomepageActivity(boolean updateStatusFromMyRequest, boolean emailSentSuccess){
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("updateStatusFromMyRequest", updateStatusFromMyRequest);
        intent.putExtra("emailSentSuccess", emailSentSuccess);
        startActivity(intent);
    }
}
