package com.example.BarterApplication;

import com.example.BarterApplication.helpers.CredentialHelper;

public class User {
    public String password;
    public String email;
    int guid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // create a random Guid just in case we need to identify users by something other than email
    }

    public User(String email, String pass) {
        this.email = email;
        this.password = pass;
    }


    public  String getPassword(){

        return null;
    }


    public String getEmail(){
        return null;

    }
}
