package com.example.BarterApplication;

public class UserAccount extends User {
    private Item[] items;

    //@todo preferences
    //@todo photo
    ItemRequest[] requests;

    //@todo chat?

    //@todo ITERATION 3: RATING

    public UserAccount(String email, String password){
        super(email, password);
        items = null;
        requests = null;
    }
}
