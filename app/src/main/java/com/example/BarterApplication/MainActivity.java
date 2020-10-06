package com.example.BarterApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

        //startActivity(new Intent(this, MainActivity.class));
    }

    public void goToRegistration(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    public void goToReset(View v){
        Intent intent = new Intent(this, LoginResetActivity.class);
        startActivity(intent);
    }

    /*
    public void goToLogin(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    */


}
