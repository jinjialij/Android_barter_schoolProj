package com.example.BarterApplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                builder.setTitle("Bartender");
//                builder.setMessage("Register Successful!");
//                builder.setNeutralButton("OK",null);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
    }

    /**
     * Creates a user in the database
     * Stores the email as a MD5 hash for the identifier
     * @param v
     * @throws NoSuchAlgorithmException
     */
    public void registerOnClick(View v) throws NoSuchAlgorithmException {
        EditText passInitial = (EditText)findViewById(R.id.passwordInitial);
        EditText passConfirm = (EditText)findViewById(R.id.passwordConfirm);

        EditText emailInitial = (EditText)findViewById(R.id.emailInitial);
        EditText emailConfirm = (EditText)findViewById(R.id.emailConfirm);

        String email = emailInitial.getText().toString();
        String pass =  passInitial.getText().toString();

        if(email.isEmpty() || pass.isEmpty()){
            generateDialog("Please enter both an email and password.");
            return;
        }else if(!pass.equals(passConfirm.getText().toString()) ||
            !email.equalsIgnoreCase(emailConfirm.getText().toString())){
            generateDialog("Emails and/or passwords do not match.");
            return;
        }else if(!isValidEmail(email)){
            generateDialog("Entered email address is not valid.");
            return;
        }
        else if(!isValidPassword(pass)){
            generateDialog("Password must be 4-24 characters ");
            return;
        }

        // we store emails as hashes because firebase doesn't allow periods in identifiers
        if(isEmailTaken(MD5.generateHash(email))){
            generateDialog("Sorry, that email is already taken.");
            return;
        }

        createUser(email, pass);

        // redirect to login page
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Registration");
        builder.setMessage("Registration Successful.");
        builder.setNeutralButton("OK", new android.content.DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    //USER PRESSES THE BACK BUTTON
    public void goBackToLoginPage(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void createUser(String email, String password) throws NoSuchAlgorithmException {
        // generate hashes to store user
        String emailHash = MD5.generateHash(email);
        password = MD5.generateHash(password);

        User u = new User(email, password);
        dbRef.child("users").child(emailHash).setValue(u);
    }


    private boolean isEmailTaken(final String email){
        // get db reference with email
        DatabaseReference postRef = dbRef.child("users").child(email);

        final boolean[] x = {false}; // only way to access from inside event listener
        postRef
            .addListenerForSingleValueEvent(new ValueEventListener() {

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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{4,24}");
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    private void generateDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Password Reset");
        builder.setMessage(msg);
        builder.setNeutralButton("OK",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
