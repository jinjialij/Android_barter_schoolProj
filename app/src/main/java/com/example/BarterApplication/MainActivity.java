package com.example.BarterApplication;

import android.content.Intent;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.BarterApplication.helpers.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        Button loginBtn = (Button) findViewById(R.id.buttonLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailAddress = (EditText)findViewById(R.id.editTextTextEmailAddress);
                EditText password = (EditText)findViewById(R.id.editTextTextPassword);

                String email = emailAddress.getText().toString();
                String pass = password.getText().toString();

                if (ValidationHelper.isValidEmail(email) && !pass.isEmpty()){
                    signIn(email, pass);
                }
                else{
                    Toaster.generateToast(MainActivity.this,"Email or password is not valid.");
                    return;
                }

            }
        });

    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            Log.e("Error", "onComplete: Failed=" + task.getException().getMessage());
                        }
                    }
                });
    }



    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toaster.generateToast(MainActivity.this,
                    "Login successfully. Welcome!");
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        } else {
            //
        }
    }


    public void goToRegistration(View v){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToLoginReset(View v) {
        Intent intent = new Intent(this, PasswordResetActivity.class);
        startActivity(intent);
    }


        }

