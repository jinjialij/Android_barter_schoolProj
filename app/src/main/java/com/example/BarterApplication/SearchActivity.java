package com.example.BarterApplication;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    public void goBackToPrevPage(View v){
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

}
