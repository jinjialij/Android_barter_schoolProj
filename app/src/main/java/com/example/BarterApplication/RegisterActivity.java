package com.example.BarterApplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.BarterApplication.helpers.CredentialHelper;
import com.example.BarterApplication.helpers.Toaster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Creates a user in the database
     */
    public void registerOnClick(View v){
        EditText passInitial = (EditText)findViewById(R.id.passwordInitial);
        EditText passConfirm = (EditText)findViewById(R.id.passwordConfirm);

        EditText emailInitial = (EditText)findViewById(R.id.emailInitial);
        EditText emailConfirm = (EditText)findViewById(R.id.emailConfirm);

        String email = emailInitial.getText().toString();
        String pass =  passInitial.getText().toString();

        if(email.isEmpty() || pass.isEmpty()){
            Toaster.generateToast(RegisterActivity.this,"Please enter both an email and password.");
            return;
        }else if(!pass.equals(passConfirm.getText().toString()) ||
            !email.equalsIgnoreCase(emailConfirm.getText().toString())){
            Toaster.generateToast(RegisterActivity.this,"Emails and/or passwords do not match.");
            return;
        }else if(!CredentialHelper.isValidEmail(email)){
            Toaster.generateToast(RegisterActivity.this,"Entered email address is not valid.");
            return;
        }
        else if(!CredentialHelper.isValidPassword(pass)){
            Toaster.generateToast(RegisterActivity.this,"Password must be 4-24 characters ");
            return;
        }

        createUser(email, pass);

    }

    //USER PRESSES THE BACK BUTTON
    public void goBackToLoginPage(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mAuth.signOut();
                            Toaster.generateToast(RegisterActivity.this,
                                    "Registratin Successfully, going back to login..");

                            // redirect after 3 seconds
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class );
                                    startActivity(intent);
                                }
                            }, 3000);

                        } else // email is taken
                            Toaster.generateToast(RegisterActivity.this,"Sorry, that email is already taken.");

                    }
                });
    }




}
