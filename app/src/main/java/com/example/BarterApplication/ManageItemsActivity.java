package com.example.BarterApplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.ArrayAdapter;
import android.widget.Button;
=======
>>>>>>> 55a24a0d0530a52ff51c9454462e8b60d2a63e13
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
<<<<<<< HEAD
import androidx.annotation.NonNull;
=======

>>>>>>> 55a24a0d0530a52ff51c9454462e8b60d2a63e13
import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageItemsActivity extends AppCompatActivity{

    private EditText name;
    private EditText label;
    private String ValueDatabase;
    private String refinedData;
    private ListView listView;
    private SearchView searchView;
    private TextView textViewSearch;
  

    private FirebaseDatabase myDb;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_info);

<<<<<<< HEAD
        myRef = FirebaseDatabase.getInstance().getReference().child("Items");
        name = findViewById(R.id.name);
        label = findViewById(R.id.label);
        listView =findViewById(R.id.listView);
        searchView = findViewById(R.id.search);
        textViewSearch = findViewById(R.id.textViewSearch);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ValueDatabase = dataSnapshot.getValue().toString();
                refinedData = ValueDatabase.substring(1,ValueDatabase.length()-1);

                String List[] = refinedData.split(",");

                listView.setAdapter(new ArrayAdapter<String>(ManageItemsActivity.this, android.R.layout.simple_list_item_1, List));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                int SearchIndex = refinedData.indexOf(query);
                String SearchResult = refinedData.substring(SearchIndex);
                String SearchSplit[] = SearchResult.split(",");
                textViewSearch.setText(SearchSplit[0]);

                return false;
            }
=======
        mAuth = FirebaseAuth.getInstance();
        myDb = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
>>>>>>> 55a24a0d0530a52ff51c9454462e8b60d2a63e13

    public void displayItems(View v){
        displayItemList();
    }

    private void displayItemList(){
        TextView itemDisplay = (TextView)findViewById(R.id.ItemDisplayTextBoxId);
        ArrayList<Item> userItems = ItemService.getUserItems(currentUser);
        String format = "";

        if(userItems.size() == 0) {
            //format = String.copyValueOf((char)R.string.EmptyItemListMessage);
        }
        else {
            for(Item i : userItems){
                format += "\n" + i.getName();
            }
        }
        itemDisplay.setText(format);
    }

    public void goToHomepage(View v){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}





