package com.example.BarterApplication.helpers;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.User;
import com.example.BarterApplication.UserAccount;

import java.util.ArrayList;
import java.util.UUID;

public class UidService {

    public static Item findItemByItemUid(String id, ArrayList<Item> items) {
        for (Item item : items){
            if (item.getUid().equals(id)){
                return item;
            }
        }
        return null;
    }

    public static User findUserById(String id) {
        return null;
    }

    //Use UserAccount instead of User to avoid firebase problems during tests since the app uses FirebaseUser to get user info
    public static UserAccount findItemOwnerByOwnerId(String itemOwnerId, ArrayList<UserAccount> userAccounts){
        for(UserAccount userAccount:userAccounts){
            if(userAccount.getUid().equals(itemOwnerId)){
                return userAccount;
            }
        }
        return null;
    }

    public static String newUID() {
        return UUID.randomUUID().toString();
    }

}
