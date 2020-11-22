package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemService;

import java.util.ArrayList;

public class BarterActivity extends AppCompatActivity {
    private ImageView currentItemImageFrame;
    private TextView currentItemNameFrame;
    private TextView currentItemDescFrame;
    private int searchRadiusKm;
    private ArrayList<Item> nearbyItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barter);

        currentItemImageFrame = findViewById(R.id.BarterActivityCurrentItemImageView);
        currentItemDescFrame = findViewById(R.id.BarterActivityCurrentItemDescriptionTextView);
        currentItemNameFrame = findViewById(R.id.BarterActivityCurrentItemNameTextView);

        if(currentItemImageFrame != null) {
            currentItemImageFrame.setImageResource(R.drawable.stickfigure);

            /* finally , reveal the item */
            currentItemImageFrame.setVisibility(View.VISIBLE);
        }

        searchRadiusKm = 5; /** @todo MOVE TO USER EDITABLE NUMBER FIELD */

        updateItemList();
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

    }


    /**
     * @brief go to the next item
     * @param v The current layout content view for the activity
     * @note ANALOGOUS TO SWIPE LEFT (or click the red X button) in tinder - carl
     */
    public void nextItem(View v){

    }


    /**
     * @brief update the item list based on the nearby users in the firebase database
     */
    private void updateItemList(){
        nearbyItems = ItemService.getItemsInRadius(this.searchRadiusKm);
    }
}
