package com.example.BarterApplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class LoginResetActivity extends AppCompatActivity {
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reset);
    }


    //USER PRESSES THE BACK BUTTON
    public void goBackToLoginPage(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private boolean isEmailTaken(final String email){
        // get db reference with email
        DatabaseReference postRef = dbRef.child("users").child(email);

        final boolean[] x = {false}; // only way to access from inside event listener
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        x[0] = dataSnapshot.exists();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // don't do anything here bc we don't really care for this project
                    };
                });

        return x[0];
    }


    private void updateUser(String email, String password)  {
//        // generate hashes to store user
//        String emailHash = MD5.generateHash(email);
//        password = MD5.generateHash(password);
//        User u = new User(email, password);
//        dbRef.child("users").child(emailHash).setValue(u);
    }

    public void resetOnClick(View v) {
    }


    private void generateDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Password Reset");
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
