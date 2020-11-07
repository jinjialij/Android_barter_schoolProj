package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import java.util.ArrayList;

public class ItemRequest {
    private String requesterId;
    private ArrayList<String> ItemIdsOffered;
    private String requestItemId;

    public ItemRequest(UserAccount u, Item wanted, ArrayList<Item> offered ) {
        if(offered.size() != 0){
            this.requesterId = u.getUid();
            this.requestItemId = wanted.getUid();
            this.ItemIdsOffered = new ArrayList<String>();
            int i = 0;
            for(i = 0; i < offered.size(); i++){
                if(!this.ItemIdsOffered.contains(offered.get(i).getUid())){
                    this.ItemIdsOffered.add(offered.get(i).getUid());
                }
            }
        }
    }

    //This constructor is only used for FirebaseUser currentUser who log in the app
    public ItemRequest(String requesterId, Item wanted, ArrayList<Item> offered ) {
        if(offered.size() != 0){
            this.requesterId = requesterId;
            this.requestItemId = wanted.getUid();
            this.ItemIdsOffered = new ArrayList<String>();
            int i = 0;
            for(i = 0; i < offered.size(); i++){
                if(!this.ItemIdsOffered.contains(offered.get(i).getUid())){
                    this.ItemIdsOffered.add(offered.get(i).getUid());
                }
            }
        }
    }

    public String getRequesterId() {
        return requesterId;
    }

    public ArrayList<String> getItemIdsOffered(){
        /** @todo */
        return this.ItemIdsOffered;
    }

    public String getRequestedItemId() {
        return requestItemId;
    }
}
