package com.example.BarterApplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TextChangedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        userItems = ItemService.getUserItems(currentUser);
        displayItems(listView, userItems);

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


    private boolean itemMatchesFilter(Item i, ArrayList<String> strings_to_match){
        for(String substring : strings_to_match){
            if(i.hasLabel(substring)){
                return true;
            }
            else {
                for(String lbl : i.getLabels()){
                    if(lbl.contains(substring)){
                        return true;
                    }
                }

                if(i.getName().contains(substring)){
                    return true;
                }
            }
        }
        return false;
    }

    private void displayItems(ListView view, ArrayList<Item> itemsToDisplay){
        ArrayList<String> displayStrings = new ArrayList<String>();
        for(Item i : itemsToDisplay){
            String displayString = "Name: " + i.getName() + "\nDescription: " + i.getDescription() + "\nLabels:";
            for(String lbl : i.getLabels()){
                displayString += "- " + lbl + "\n";
            }
            displayStrings.add(displayString);
        }
        view.setAdapter(new ArrayAdapter<String>(ManageItemsActivity.this, android.R.layout.simple_list_item_1, displayStrings));
    }
}





