package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class MyRequestActivity extends AppCompatActivity {
    private static final String TAG = "myRequest";

    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ArrayList<Item> items;
    private ItemRequest receivedItemRequest;
    private boolean updateStatusFromMyRequest = false;
    private boolean emailSentSuccess = false;

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
        TextView requestTitle = (TextView) findViewById(R.id.itemRequestTitle);
        TextView requestId = (TextView) findViewById(R.id.requestID);
        TextView requestItemInfo = (TextView) findViewById(R.id.requestItemInfo);
        TextView offerItemInfo = (TextView) findViewById(R.id.offeredItemInfo);
        Button acceptBtn = (Button) findViewById(R.id.acceptRequestBtn);
        Button refuseBtn = (Button) findViewById(R.id.refuseRequestBtn);
        Button closeBtn = (Button) findViewById(R.id.closeBtn);
        Button saveBtn = (Button) findViewById(R.id.BarterMyRequestSaveBtn);
        Button deleteBtn = findViewById(R.id.deleteMatchButton);

        if (receivedItemRequest.isCompleted()){
            String title = getString(R.string.itemRequestTitle) + ":\t" + getString(R.string.itemRequestMatchStatusComplete);
            requestTitle.setText(title);
            saveBtn.setVisibility(View.INVISIBLE);
            saveBtn.setEnabled(false);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setEnabled(true);
            if (receivedItemRequest.isAccepted()) {
                acceptBtn.setVisibility(View.VISIBLE);
                acceptBtn.setEnabled(false);
                acceptBtn.setText(R.string.itemRequestAccepted);
                refuseBtn.setVisibility(View.INVISIBLE);
                refuseBtn.setEnabled(false);
            }else if((receivedItemRequest.isCompleted() && !receivedItemRequest.isAccepted())){
                refuseBtn.setVisibility(View.VISIBLE);
                refuseBtn.setEnabled(false);
                refuseBtn.setText(R.string.itemRequestRefused);
                acceptBtn.setVisibility(View.INVISIBLE);
                acceptBtn.setEnabled(false);
            }
        } else {
            String title = getString(R.string.itemRequestTitle) + ":\t" + getString(R.string.itemRequestMatchStatusNew);
            requestTitle.setText(title);
            saveBtn.setVisibility(View.VISIBLE);
            saveBtn.setEnabled(true);
            deleteBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setEnabled(false);
        }

        //for requests sent by current user, only show close button
        if (receivedItemRequest.getRequesterId().equals(currentUser.getUid())){
            refuseBtn.setVisibility(View.INVISIBLE);
            acceptBtn.setVisibility(View.INVISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
            saveBtn.setEnabled(false);
            refuseBtn.setEnabled(false);
            acceptBtn.setEnabled(false);
            closeBtn.setEnabled(true);
        } else if (!receivedItemRequest.getRequesterId().equals(currentUser.getUid()) && !receivedItemRequest.isCompleted()){
            //for received and not matched requests
            refuseBtn.setVisibility(View.VISIBLE);
            acceptBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);
            refuseBtn.setEnabled(true);
            acceptBtn.setEnabled(true);
            closeBtn.setEnabled(true);
            saveBtn.setEnabled(true);
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
                receivedItemRequest.setCompleted(true);
                ItemRequestService.updateItemRequestStatus(receivedItemRequest);
                updateStatusFromMyRequest = true;
                emailSentSuccess = true;
                Toaster.generateToast(MyRequestActivity.this, getString(R.string.BarterActivity_SaveRequestSuccess));

                Intent i = sendEmail(receivedItemRequest);
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
                goBackToHomepageActivity(updateStatusFromMyRequest, emailSentSuccess);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receivedItemRequest.setDeleted(true);
                ItemRequestService.updateItemRequestStatus(receivedItemRequest);
                saveBtn.setEnabled(false);
                deleteBtn.setEnabled(false);
                Toast toast=Toast.makeText(MyRequestActivity.this, "The request has been deleted!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.BOTTOM, 0, 10);
                toast.show();
                updateStatusFromMyRequest = true;
                emailSentSuccess = false;
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

    public Intent sendEmail(ItemRequest receivedItemRequest){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{receivedItemRequest.getRequesterEmail()});
        String acceptSubject = "Your Barter request has been accepted";
        String refuseSubject = "Your Barter request has been refused";
        String content = "One of your sent request just changes its status. Please find more information in the View My Request of Barter.";
        if (receivedItemRequest.isAccepted()&&receivedItemRequest.isCompleted()){
            i.putExtra(Intent.EXTRA_SUBJECT, acceptSubject);
        }
        else if (!receivedItemRequest.isAccepted()&&receivedItemRequest.isCompleted()){
            i.putExtra(Intent.EXTRA_SUBJECT, refuseSubject);
        }
        i.putExtra(Intent.EXTRA_TEXT, content);

        return i;
    }
}
