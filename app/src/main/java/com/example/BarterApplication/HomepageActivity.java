package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomepageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Button logoutBtn = (Button) findViewById(R.id.buttonLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                updateUI(null);
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }


    public void goToAddItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }
}
