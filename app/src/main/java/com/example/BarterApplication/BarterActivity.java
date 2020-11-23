package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TextChangedListener;

import java.util.ArrayList;

public class BarterActivity extends AppCompatActivity {
    private ImageView currentItemImageFrame;
    private TextView currentItemNameFrame;
    private TextView currentItemDescFrame;
    private EditText searchRadiusEditText;
    private final int DEFAULT_SEARCH_RADIUS_KM = 10;
    private ArrayList<Item> nearbyItems = new ArrayList<Item>();
    private static int itemDisplayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barter);

        currentItemImageFrame = findViewById(R.id.BarterActivityCurrentItemImageView);
        currentItemDescFrame = findViewById(R.id.BarterActivityCurrentItemDescriptionTextView);
        currentItemNameFrame = findViewById(R.id.BarterActivityCurrentItemNameTextView);
        searchRadiusEditText = findViewById(R.id.BarterActivityItemSearchRadiusEditText);

        /* Get initial default list of items */
        updateItemList(DEFAULT_SEARCH_RADIUS_KM);

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
                    if(str.isEmpty()) {
                        updateItemList(DEFAULT_SEARCH_RADIUS_KM);
                    }
                    else {
                        try {
                            int dist = Integer.parseInt(str);
                            updateItemList(dist);
                        }
                        catch (Exception e) {
                            /* Do nothing because java is based on faulty paradigms */
                        }
                    }
                }
            });
        }

        if(currentItemImageFrame != null) {
            currentItemImageFrame.setImageResource(R.drawable.stickfigure);

            /* finally , reveal the item */
            currentItemImageFrame.setVisibility(View.VISIBLE);
        }
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
        boolean sendReqWasSuccessful = true; /** @todo SEND THE REQUEST AND CHECK ERROR CODE */
        if(sendReqWasSuccessful){
            displayNextItem();
        }
        else {
            /** @todo ERROR HANDLE */
        }
    }


    /**
     * @brief go to the next item
     * @param v The current layout content view for the activity
     * @note ANALOGOUS TO SWIPE LEFT (or click the red X button) in tinder - carl
     */
    public void nextItem(View v){
        displayNextItem();
    }

    /**
     * @brief update the item list based on the nearby users in the firebase database
     */
    private void updateItemList(int radius){
        nearbyItems = ItemService.getItemsInRadius(radius);
    }


    /**
     * @brief Display the next item from the list of nearby items
     */
    private void displayNextItem(){
        if(nearbyItems.size() > 0){
            itemDisplayIndex++;
            itemDisplayIndex = itemDisplayIndex % nearbyItems.size();
            displayItem(nearbyItems.get(itemDisplayIndex));
        }
    }

    /**
     * @brief Display an item in the item display window
     * @param i the item to display
     */
    private void displayItem(Item i){
        this.currentItemNameFrame.setText(i.getName());
        this.currentItemDescFrame.setText(i.getDescription());
        /**@todo DISPLAY ITEM IMAGE BY URL */
    }
}
