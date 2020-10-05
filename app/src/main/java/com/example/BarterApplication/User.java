package com.example.BarterApplication;

import java.util.UUID;

public class User {
    public String password;
    public String email;
    public String guid;
    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // create a random Guid just in case we need to identify users by something other than email
        guid = UUID.randomUUID().toString();
    }
    public User(String email, String pass) {
        this();
        this.email = email;
        this.password = pass;

    }
}
