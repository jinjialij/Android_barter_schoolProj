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
        setContentView(R.layout.activity_manage_items);

        mAuth = FirebaseAuth.getInstance();
        myDb = FirebaseDatabase.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

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
}





