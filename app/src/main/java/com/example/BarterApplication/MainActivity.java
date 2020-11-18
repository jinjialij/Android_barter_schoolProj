package com.example.BarterApplication;

import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.widget.EditText;
import android.widget.Toast;
import com.example.BarterApplication.helpers.*;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/* add comment to main activity to test automation of merge requests */
public class MainActivity extends AppCompatActivity  {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FirebaseAuth mAuth;
    private boolean fromMyRequest;
    private LocationHelper locationHelper;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LocationHelper needs an activity to function, so we have to 'prime' it initially
        locationHelper = new LocationHelper(LocationServices.getFusedLocationProviderClient(this));

        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        fromMyRequest = getIntent().getBooleanExtra("fromMyRequest", false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, getString(R.string.locationPermissionAllowed), Toast.LENGTH_SHORT).show();
            updateUI(mAuth.getCurrentUser(), false);
        }
        else {
            EasyPermissions.requestPermissions(this, getString(R.string.locationPermissionUnallowed), REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    /*
        Once we have location permissions, check for the redirect
     */
    public void updateUI(FirebaseUser user, boolean justLogin) {
        // Check if user is signed in (non-null) and update UI accordingly.
        if (user != null) {
            if (justLogin){
                Toaster.generateToast(MainActivity.this,
                        "Login successful. Welcome!");
            }
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        }
    }

    public void signInButtonClick(View v) {
        EditText emailAddress = (EditText)findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText)findViewById(R.id.editTextTextPassword);
        String email = emailAddress.getText().toString();
        String pass = password.getText().toString();

        if (!ValidationHelper.isValidEmail(email) || pass.isEmpty()){
            Toaster.generateToast(MainActivity.this,"Email or password is not valid.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, false);
                        }

                        if (!task.isSuccessful()) {
                            Log.e("Error", "onComplete: Failed=" + task.getException().getMessage());
                        }
                    }
                });
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




