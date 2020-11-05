package com.example.BarterApplication;

import java.util.ArrayList;

public class UserAccount extends User {
    private ArrayList<Item> items;
    private ArrayList<String> requestIds;
    //@todo preferences
    //@todo photo

    //@todo chat?

    //@todo ITERATION 3: RATING

    public UserAccount(String email, String uid){
        super(email, uid);
        items = new ArrayList<Item>();
        requestIds = new ArrayList<String>();
    }

    /*
        on data shapshot change (userAccount my_account) {
            database_inst d = someowmfomfwe firebase.database.getInst().databaseObj;
            get the key "requestIDs"
            my_req_keys = data.getGetKey()

            ids = database.getValueFromKey(my_req_keys)

            for(String s : ids){
                if ( s not in this.requestIds) {
                    this.requestIds.insert(s)
                }
            }
        }
     */
}
