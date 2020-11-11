package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BarterApplication.helpers.AddItemHelper;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.Toaster;
import com.example.BarterApplication.helpers.UidService;
import com.example.BarterApplication.helpers.ValidationHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;


public class AddItemActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void confirmAddOnClick(View view){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String ownerId = user.getUid();

        EditText itemNameEditText = findViewById(R.id.AddItemNameEditText);
        EditText itemDescEditText = findViewById(R.id.AddItemDescriptionEditText);
        EditText itemLabelsEditText = findViewById(R.id.AddItemLabelsEditText);

        String itemName = itemNameEditText.getText().toString();
        String itemDesc = itemDescEditText.getText().toString();
        String giantLabelString = itemLabelsEditText.getText().toString();
        String[] labelArray = giantLabelString.split(",");
        ArrayList<String> labelArrayList = new ArrayList<String>();
        for(int i = 0; i < labelArray.length; i++){
            labelArrayList.add(labelArray[i]);
        }

        final String userId = UidService.getCurrentUserUUID();

        boolean titleIsValid = ValidationHelper.isValidItemTitle(itemName);

        if (itemName.isEmpty()){
            Toaster.generateToast(AddItemActivity.this,this.getString(R.string.emptyItemName));
        }else if(itemName.equals(R.string.AddItemNameHint)){
            Toaster.generateToast(AddItemActivity.this, this.getString(R.string.emptyItemName));
        }
        else if(!titleIsValid){
            Toaster.generateToast(AddItemActivity.this, this.getString(R.string.invalidItemNme));
        }
        else {
            Item i = new Item(itemName, itemDesc, labelArrayList, userId);
            ItemService.addItem(i);
            goToHomepage(i);
        }
    }

    public void goToHomepage(Item i) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AddItemActivity.this, HomepageActivity.class);
                intent.putExtra("insertedItem", i);
                startActivity(intent);
            }
        }, 3000);
    }

    public void goBackToHomepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);//need update to item list page
        startActivity(intent);
    }
}
