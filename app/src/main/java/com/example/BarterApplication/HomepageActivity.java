package com.example.BarterApplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ArrayList<ItemRequest> itemRequests;
    private ArrayList<Item> items;

    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        //initialize service and get items and requests
        ItemService.init();
        ItemRequestService.init();
        itemRequests = ItemRequestService.getItemRequestList();
        items = ItemService.getItemList();

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
        //check if inserted is success when directing from the addItemActivity
        Item insertedItem = (Item) getIntent().getSerializableExtra("insertedItem");
        boolean updateStatusFromMyRequest = getIntent().getBooleanExtra("updateStatusFromMyRequest", false);
        boolean emailSentSuccess = getIntent().getBooleanExtra("emailSentSuccess", false);
//        ItemRequest updatedItemRequest = (ItemRequest) getIntent().getSerializableExtra("receivedItemRequest");
        if (insertedItem!=null && ItemService.isLastInsertSucceed()){
            boolean insertedSuccess = false;
            for (Item item : items){
                if (item.getUid().equals(insertedItem.getUid())){
                    Toaster.generateToast(HomepageActivity.this, "Add item " + insertedItem.getName() + " successfully!");
                    insertedSuccess = true;
                    break;
                }
            }
            if (!insertedSuccess){
                Toaster.generateToast(HomepageActivity.this, "Failed to add item " + insertedItem.getName() + ",please try again");
            }
        }

        //check if updated is success when directing from the myRequestActivity
        if (updateStatusFromMyRequest && ItemRequestService.isLastUpdateSucceed()){
            if (emailSentSuccess){
                Toaster.generateToast(HomepageActivity.this, getString(R.string.BarterActivity_SaveRequestSuccess) + "\n" +getString(R.string.BarterActivity_SuccessfulSentRequestEmail));
            }
        }
        else if (updateStatusFromMyRequest && !ItemRequestService.isLastUpdateSucceed()){
            Toaster.generateToast(HomepageActivity.this, "Fail to update your request, please try again");
        }

        //check if inserted is success when directing from the createRequestActivity
        ItemRequest insertedItemReq = (ItemRequest) getIntent().getSerializableExtra("insertedItemReq");
        if (insertedItemReq!=null && ItemRequestService.isLastInsertSucceed()){
            boolean insertedSuccess = false;
            for (ItemRequest itemRequest : itemRequests){
                if (itemRequest.getUid().equals(insertedItemReq.getUid())){
                    Toaster.generateToast(HomepageActivity.this, getString(R.string.NewRequestSubmissionSuccessMessage));
                    insertedSuccess = true;
                    break;
                }
            }
            if (!insertedSuccess){
                Toaster.generateToast(HomepageActivity.this, getString(R.string.NewRequestSubmissionFailure));
            }
        }
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
        startActivity(intent);
    }

    public void goToAddItem(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
    public void goToManageItems(View v){
        Intent intent = new Intent(this, ManageItemsActivity.class);
        startActivity(intent);
    }

    public void goToBarterActivity(View v){
        Intent intent = new Intent(this, BarterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}
