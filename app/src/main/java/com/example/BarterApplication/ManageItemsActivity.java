package com.example.BarterApplication;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.BarterApplication.helpers.ItemService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
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
    private ArrayList<Item> userItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        mAuth = FirebaseAuth.getInstance();
        myDb = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
        Item i1 = new Item("ItemName1", currentUser.getUid());
        Item i2 = new Item("ItemName2", currentUser.getUid());
        ItemService.addItem(i1);
        ItemService.addItem(i2);

        userItems = ItemService.getUserItems(currentUser);
        displayItems(userItems);
    }

    private void displayItems(ArrayList<Item> userItems){
        TextView itemDisplay = (TextView)findViewById(R.id.ItemDisplayTextBoxId);
        String format = "";
        for(Item i : userItems){
            format.concat("" + i.getName());
        }

        itemDisplay.setText(format);

        /* when user loads this activity, we should be loading a list of the users items
        (and if they have none, display some sort of message) */


        /*
        name = findViewById(R.id.findname);
        label = findViewById(R.id.findlabel);
        listView =findViewById(R.id.listView);
        searchView = findViewById(R.id.search);
        textViewSearch = findViewById(R.id.result);
        */


        /*
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
                   */

        /*
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

                 */
    }

    /*

    public void InsertButton(View view){
        try {
            myDb.child(name.getText().toString()).setValue(label.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

     */
}





