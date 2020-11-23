package com.example.BarterApplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemListViewAdapter;
import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.OfferingItemListViewAdapter;
import com.example.BarterApplication.helpers.Toaster;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CreateRequestActivity extends AppCompatActivity {
    private static final String TAG = "CreateNewRequest";

    private FirebaseUser currentUser;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private ArrayList<Item> userItems;
    private ArrayList<Item> AddedOfferingItems;
    private Map<String, String> spinnerItemListMap;
    private Item requestItem;
    private Spinner offeringItemsList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();
        userItems = ItemService.getUserItems(currentUser);

        requestItem = (Item)getIntent().getSerializableExtra("requestedItem");
        if(requestItem == null) {
            Toaster.generateToast(this, "Internal error, please try again");
            onBackPressed();
        }

        TextView requestItemInfo = (TextView) findViewById(R.id.CreateNewRequestRequestedItemInfo);
        HashMap<String, String> requestItemInfoMap = new LinkedHashMap<>();
        requestItemInfo.setText(ItemService.printItemMap(ItemService.getItemMap(requestItem, requestItemInfoMap)));
        AddedOfferingItems = new ArrayList<>();
        this.offeringItemsList();
        listView = findViewById(R.id.CreateNewRequestAddedItemsList);
        Button add = (Button) findViewById(R.id.CreateNewRequestOfferingItemAddBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                offeringItemsList = (Spinner) findViewById(R.id.CreateNewRequestOfferingItemSpinner);
                String offeringItemSelected = offeringItemsList.getSelectedItem().toString();
                Item selectedItem = getSelectedItem(offeringItemSelected);
                AddedOfferingItems.add(selectedItem);
                OfferingItemListViewAdapter adapter = new OfferingItemListViewAdapter(CreateRequestActivity.this, AddedOfferingItems);
                listView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ViewItemsActivity.class);
        startActivity(intent);
    }

    public void offeringItemsList(){
        offeringItemsList = (Spinner) findViewById(R.id.CreateNewRequestOfferingItemSpinner);
        List<String> offeringItemInfo = new ArrayList<String>();
        spinnerItemListMap = new LinkedHashMap<>();
        int count = 1;
        for (Item item : userItems){
            String info = "Item: " + count + " Name: " + item.getName();
            offeringItemInfo.add(info);
            spinnerItemListMap.put(info, item.getUid());
        }
        @SuppressLint("ResourceType") ArrayAdapter<String> itemListAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, offeringItemInfo);
        itemListAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        offeringItemsList.setAdapter(itemListAdapter);
    }

    protected Item getSelectedItem(String itemSelected){
        String selectedItemUid = spinnerItemListMap.get(itemSelected);
        Item selectedItem = ItemService.findItemByUid(selectedItemUid);
        return selectedItem;
    }

}
