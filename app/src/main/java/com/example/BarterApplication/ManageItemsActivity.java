package com.example.BarterApplication;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageItemsActivity extends AppCompatActivity{

    private EditText name;
    private EditText label;
    private DatabaseReference myRef;
    private String ValueDatabase;
    private String refinedData;
    private ListView listView;

    private SearchView searchView;
    private TextView textViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_items);

        myRef = FirebaseDatabase.getInstance().getReference("item");
        name = findViewById(R.id.findname);
        label = findViewById(R.id.findlabel);
        listView =findViewById(R.id.listView);
        searchView = findViewById(R.id.search);
        textViewSearch = findViewById(R.id.result);

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

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void InsertButton(View view){
        try {
            myRef.child(name.getText().toString()).setValue(label.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}





