package com.example.BarterApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FindActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "FindActivity";

    //define view objects
    private EditText editTextUserEmail;
    private Button buttonFind;
    private TextView textviewMessage;
    private ProgressDialog progressDialog;
    //define firebase object
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        editTextUserEmail = (EditText) findViewById(R.id.editTextUserEmail);
        buttonFind = (Button) findViewById(R.id.buttonFind);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        buttonFind.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonFind){
            progressDialog.setMessage("Processing...");
            progressDialog.show();
            //send email for users to change their password
            String emailAddress = editTextUserEmail.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FindActivity.this, "Email sent.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(FindActivity.this, "Failed to send Email!", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

        }
    }
}