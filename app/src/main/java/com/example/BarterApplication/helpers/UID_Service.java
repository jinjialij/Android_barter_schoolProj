package com.example.BarterApplication.helpers;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.User;

import java.util.UUID;

public class UID_Service {

    static public Item findItemById(String id) {
        return null;
    }

    static public User findUserById(String id) {
        return null;
    }


    static public User findOwnerById(String id){
        return null;
    }

    static public String newUID() {
        return UUID.randomUUID().toString();
    }

}
