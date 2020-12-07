package com.example.BarterApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TextChangedListener;
import com.example.BarterApplication.helpers.Toaster;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class ManageItemsActivity extends AppCompatActivity {

    private EditText label;
    private EditText name;
    private DatabaseReference myRef;
    private String ValueDatabase;
    private String refinedData;
    private ListView listView;
    private EditText SearchViewEditText;
    FirebaseUser currentUser;
    ArrayList<Item> userItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        myRef = FirebaseDatabase.getInstance().getReference().child(ItemService.getItemKeyName());
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        listView = findViewById(R.id.ManageItemsFilteredItemsListView);

        int db_sync_time_ms = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                userItems = ItemService.getUserItems(currentUser);
                displayItems(listView, userItems);
            }
        }, db_sync_time_ms);

        ArrayList<String> filterStrings = new ArrayList<String>();
        SearchViewEditText = findViewById(R.id.ManageItemsSearchBoxEditText);
        SearchViewEditText.addTextChangedListener(new TextChangedListener<EditText>(SearchViewEditText) {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(EditText target, Editable s) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String [] searchFieldArray = s.toString().split("\\s+");
                filterStrings.clear();
                for(int i = 0; i < searchFieldArray.length; i++){
                    filterStrings.add(searchFieldArray[i]);
                }

                /* After user is done editing the text, we go through the filter list and update the lists */
                ArrayList<Item> userItems = ItemService.getUserItems(currentUser);

                if(filterStrings.isEmpty()){
                    /* If we aren't filterig, display all the user items */
                    displayItems(listView, userItems);
                }
                else {
                    /* Filter the items and only display matches */
                    ArrayList<Item> filteredItems = new ArrayList<Item>();
                    for(Item i : userItems){
                        if(itemMatchesFilter(i, filterStrings)){
                            filteredItems.add(i);
                        }
                    }
                    displayItems(listView, filteredItems);
                }
            }
        });
    }

    public void goToHomepage(View v) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);

    }

    private boolean itemMatchesFilter(Item i, ArrayList<String> filterStrings){
        for(String substring : filterStrings){
            if(itemMatchesSubstring(i, substring)){
                return true;
            }
        }
        return false;
    }

    private boolean itemMatchesSubstring(Item i, String sub){
        for(String lbl : i.getLabels()){
            if(lbl.contains(sub)){
                return true;
            }
        }
        if(i.getName().contains(sub)){
            return true;
        }
        return false;
    }


    private void displayItems(ListView view, ArrayList<Item> itemsToDisplay){

        ArrayList<String> displayStrings = new ArrayList<String>();
        for(Item i : itemsToDisplay){
            String displayString = "ID: "+i.getUid() + " Name: " + i.getName() + "\nDescription: " + i.getDescription() + "\nLabels:";
            for(String lbl : i.getLabels()){
                displayString += "- " + lbl + "\n";
            }
            displayStrings.add(displayString);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String rawDisplayString = parent.getItemAtPosition(position).toString();
                String[] splitDisplayString = rawDisplayString.split(" ");
                String uuid = splitDisplayString[1];

               displayImage(view, ItemService.findItemByUid(uuid));
            }
        });
        view.setAdapter(new ArrayAdapter<String>(ManageItemsActivity.this, android.R.layout.simple_list_item_1, displayStrings));
    }

    public void displayImage(View v, Item item){

            Dialog builder = new Dialog(this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });
            byte[] decodedString;
            try{
                decodedString = Base64.decode(item.Bas64Image, Base64.DEFAULT);

            }catch (Exception e){
                return;
            }
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(decodedByte);
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            builder.show();

    }
}





