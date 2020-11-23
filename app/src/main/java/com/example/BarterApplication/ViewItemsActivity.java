package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemListViewAdapter;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TextChangedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewItemsActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseUser currentUser;
    private ListView listView;
    private EditText SearchViewEditText;
    ArrayList<Item> otherUserItems = new ArrayList<Item>();
    ArrayList<Item> currentUserItems = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        myRef = FirebaseDatabase.getInstance().getReference().child(ItemService.getItemKeyName());
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        listView = findViewById(R.id.ViewItemsFilteredItemsListView);
        currentUserItems = ItemService.getUserItems(currentUser);

        int db_sync_time_ms = 1000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                otherUserItems = ItemService.getOtherUserItems(currentUserItems);
                ItemListViewAdapter adapter = new ItemListViewAdapter(ViewItemsActivity.this, otherUserItems, currentUserItems.isEmpty());
                listView.setAdapter(adapter);
            }
        }, db_sync_time_ms);

        ArrayList<String> filterStrings = new ArrayList<String>();
        SearchViewEditText = findViewById(R.id.ViewItemsSearchBoxEditText);
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
                otherUserItems = ItemService.getOtherUserItems(currentUserItems);

                if(filterStrings.isEmpty()){
                    /* If we aren't filterig, display all the user items */
                    ItemListViewAdapter adapter = new ItemListViewAdapter(ViewItemsActivity.this, otherUserItems, currentUserItems.isEmpty());
                    listView.setAdapter(adapter);
                }
                else {
                    /* Filter the items and only display matches */
                    ArrayList<Item> filteredItems = new ArrayList<Item>();
                    for(Item i : otherUserItems){
                        if(itemMatchesFilter(i, filterStrings)){
                            filteredItems.add(i);
                        }
                    }
                    ItemListViewAdapter adapter = new ItemListViewAdapter(ViewItemsActivity.this, otherUserItems, currentUserItems.isEmpty());
                    listView.setAdapter(adapter);
                }
            }
        });
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    public void goToCreateRequest(View v) {
        Intent intent = new Intent(this, CreateRequestActivity.class);
        startActivity(intent);
    }
}
