package com.example.BarterApplication.helpers;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.User;

import java.util.UUID;

public class UidService {

    static public Item findItemByItemUid(String id) {
        return null;
    }

    static public User findUserById(String id) {
        return null;
    }


    static public User findItemOwnerByOwnerId(String id){
        return null;
    }

    static public String newUID() {
        return UUID.randomUUID().toString();
    }

}
