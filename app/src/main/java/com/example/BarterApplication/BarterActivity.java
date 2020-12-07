package com.example.BarterApplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Base64;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TextChangedListener;
import com.example.BarterApplication.helpers.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class BarterActivity extends AppCompatActivity {
    private final int DEFAULT_SEARCH_RADIUS_KM = 10;
    private final int PERIODIC_SEARCH_INTERVAL_MS = 60*1000; /* once per minute */
    private static int itemDisplayIndex = 0;
    private ArrayList<Item> nearbyItems = new ArrayList<Item>();
    private ImageView currentItemImageFrame;
    private TextView currentItemNameFrame;
    private Button currentItemDescBtn;
    private Button requestBtn;
    private TextView itemDistanceDisplayFrame;
    private EditText searchRadiusEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Handler refreshHandler;
    private Item requestItem;
    private boolean ownEmptyItem;
    private AlertDialog.Builder alertBuilder;

    private int searchRadius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barter);
        mAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentItemImageFrame = findViewById(R.id.BarterActivityCurrentItemImageView);
        currentItemDescBtn = findViewById(R.id.BarterActivityDescBtn);
        currentItemNameFrame = findViewById(R.id.BarterActivityCurrentItemNameTextView);
        searchRadiusEditText = findViewById(R.id.BarterActivityItemSearchRadiusEditText);
        itemDistanceDisplayFrame = findViewById(R.id.BarterActivityitemDistanceDisplayTextView);
        requestBtn = (Button) findViewById(R.id.BarterActivitySendRequestButton);
        ownEmptyItem = ItemService.getUserItems(currentUser).isEmpty();
        alertBuilder = new AlertDialog.Builder(this);

        /* Get initial default list of items */
        searchRadius = DEFAULT_SEARCH_RADIUS_KM;
        refreshHandler = new Handler();

        //get from request page or description page
        requestItem = (Item)getIntent().getSerializableExtra("requestedItem");
        if(requestItem != null) {
            displayItem(requestItem);
            currentItemDescBtn.setEnabled(true);
        } else {
            updateItemList();
        }

        if(searchRadiusEditText != null){
            searchRadiusEditText.addTextChangedListener(new TextChangedListener<EditText>(searchRadiusEditText) {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(EditText target, Editable s) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String str = s.toString();
                    int dist;
                    if(str.isEmpty()) {
                        dist = DEFAULT_SEARCH_RADIUS_KM;
                    }
                    else {
                        try {
                            dist = Integer.parseInt(str);
                        }
                        catch (Exception e) {
                            Log.e("[BARTER ACTIVITY]", "searchRadiusEditText.addTextChangedListener threw exception on afterTextChanged event. Exception: " + e.toString());
                            dist = DEFAULT_SEARCH_RADIUS_KM;
                        }
                    }
                    searchRadius = dist;
                    updateItemList();
                }
            });
        }

        refreshHandler.postDelayed(periodicNearbyItemRefreshCallback, 0);
        disableRequestBtn(requestItem, ownEmptyItem);

        currentItemDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertBuilder.setMessage(requestItem.getDescription());
                alertBuilder.setTitle(R.string.description);
                alertBuilder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        });
    }


    /**
     * @brief return to the homepage activity
     * @param v The current layout content view for the activity
     */
    public void goBackToHomepage(View v) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);

    }


    /**
     * @brief send a barter request to the owner of the currently displayed item
     * @param v The current layout content view for the activity
     * @note ANALOGOUS TO SWIPE RIGHT (or click the green heart button) in tinder - carl
     */
    public void sendRequest(View v) {
        Intent intent = new Intent(this, CreateRequestActivity.class);
        intent.putExtra("requestedItem", requestItem);
        startActivity(intent);
    }


    /**
     * @brief display the next item from the item feed on click
     * @param v The current layout content view for the activity
     */
    public void nextItemOnClick(View v){
        displayNextItem();
    }


    /**
     * @brief update the barter activity item feed
     */
    private void updateItemList(){
        /* Get all nearby items that are NOT owned by current user */
        this.nearbyItems = ItemService.getOtherItemsInRadius(this.searchRadius);
        displayCurrentItem();
    }


    /**
     * @brief Display the next item in the item feed
     */
    private void displayNextItem(){
        itemDisplayIndex++;
        itemDisplayIndex = itemDisplayIndex % nearbyItems.size();
        displayCurrentItem();
    }


    /**
     * @brief display the current item in the nearby item feed
     */
    private void displayCurrentItem(){
        if(nearbyItems.size() > 0){
            displayItem(nearbyItems.get(itemDisplayIndex));
        }
        else {
            displayItem(null);
        }
    }

    /**
     * @brief Display an item in the item display window
     * @param i the item to display
     * @note if i is null (the frame will display as if there are NO nearby items)
     */
    private void displayItem(Item i){
        if(i == null){
            currentItemDescBtn.setEnabled(false);
            this.itemDistanceDisplayFrame.setText("");
            this.currentItemNameFrame.setText("There are no items within that search radius");
            this.currentItemImageFrame.setVisibility(View.INVISIBLE);
        }
        else {
            this.currentItemNameFrame.setText(i.getName());
            currentItemDescBtn.setEnabled(true);

            /* Update the distance */
            String distanceString = new String();
            int dist = (int)ItemService.getDistanceToItemKm(i);
            distanceString += Integer.toString(dist);
            distanceString += " Km away";
            this.itemDistanceDisplayFrame.setText(distanceString);


            ImageView imageView = findViewById(R.id.BarterActivityCurrentItemImageView);

            try{
                byte[] decodedString = Base64.decode(i.Bas64Image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imageView.setImageBitmap(decodedByte);
            }catch
            (Exception ignored){
                // no picture
                imageView.setImageBitmap(null);
            }

            this.currentItemImageFrame.setVisibility(View.VISIBLE);
        }
        requestItem = i;
        disableRequestBtn(requestItem, ownEmptyItem);
    }

    private Runnable periodicNearbyItemRefreshCallback = new Runnable() {
        public void run() {
            updateItemList();
            refreshHandler.postDelayed(this, PERIODIC_SEARCH_INTERVAL_MS);
            Log.i("[BARTER ACTIVITY]", "run: updated nearby item list");
        }
    };

    protected void disableRequestBtn(Item requestItem, boolean ownEmptyItem){
        if (requestItem ==null || ownEmptyItem){
            requestBtn.setEnabled(false);
        } else {
            requestBtn.setEnabled(true);
        }
    }
}
