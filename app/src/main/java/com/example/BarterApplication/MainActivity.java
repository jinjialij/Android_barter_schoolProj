package com.example.BarterApplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textviewFindPassword;

    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textviewFindPassword = (TextView) findViewById(R.id.textviewFindPassword);
        textviewFindPassword.setOnClickListener(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /* code to get current user
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        */

    }

    public void goToRegistration(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToLogin(View v) {
        //TODO; implement logic
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }

    public void goToLoginReset(View v) {
        Intent intent = new Intent(this, LoginResetActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        if (view == textviewFindPassword) {
            finish();
            startActivity(new Intent(this, FindActivity.class));
        }
    }
}