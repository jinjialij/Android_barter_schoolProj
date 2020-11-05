package com.example.BarterApplication;

import com.example.BarterApplication.helpers.CredentialHelper;

public class User {
    public String email;
    public String uid;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // create a random Guid just in case we need to identify users by something other than email
    }

    public User(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }


    public  String getUid(){
        return this.uid;
    }


    public String getEmail(){
        return this.email;
    }
}
