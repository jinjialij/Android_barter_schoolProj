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


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{4,24}");
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

    public void resetOnClick(View v) {

        EditText passInitial = (EditText)findViewById(R.id.passwordInitial);
        EditText passConfirm = (EditText)findViewById(R.id.passwordConfirm);

        EditText emailInitial = (EditText)findViewById(R.id.emailInitial);

        String email = emailInitial.getText().toString();
        String pass =  passInitial.getText().toString();

        if(email.isEmpty() || pass.isEmpty()){
            generateDialog("Please enter both an email and password.");
            return;
        }else if(!pass.equals(passConfirm.getText().toString())){
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
        if(true // TODO Fix me
            /*!isEmailTaken(MD5.generateHash(email))*/){
            // redirect to login page
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginResetActivity.this);

            //PUT THE STUFF IN THE DB

            updateUser(email, pass);


            builder.setTitle("Password Reset");
            builder.setMessage("Successful.");
            builder.setNeutralButton("OK", new android.content.DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(LoginResetActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else
        {
            generateDialog("Sorry, no account exists with that email");
        }
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
