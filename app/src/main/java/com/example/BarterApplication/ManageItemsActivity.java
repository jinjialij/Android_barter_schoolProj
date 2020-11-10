package com.example.BarterApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.BarterApplication.helpers.ItemService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageItemsActivity extends AppCompatActivity {

    private EditText label;
    private EditText name;
    private DatabaseReference myRef;
    private String ValueDatabase;
    private String refinedData;
    private ListView listView;
    private SearchView searchView;
    private TextView textViewSearch;
    ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        myRef = FirebaseDatabase.getInstance().getReference().child("Items");

        name = findViewById(R.id.name);
        label = findViewById(R.id.label);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchViewManageItems);
        textViewSearch = findViewById(R.id.textViewSearch);

        items = ItemService.getItemList();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int SearchIndex = refinedData.indexOf(query);
                String SearchResult = refinedData.substring(SearchIndex);
                String SearchSplit[] = SearchResult.split(",");
                textViewSearch.setText(SearchSplit[0]);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        /*
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ValueDatabase = dataSnapshot.getValue().toString();
                refinedData = ValueDatabase.substring(1, ValueDatabase.length() - 1);
                String List[] = refinedData.split(",");
                listView.setAdapter(new ArrayAdapter<String>(ManageItemsActivity.this, android.R.layout.simple_list_item_1, List));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */


    }

    public void goToHomepage(View v) {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);

    }

}





