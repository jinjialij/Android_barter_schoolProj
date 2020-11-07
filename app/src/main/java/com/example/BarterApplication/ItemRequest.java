package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import java.util.ArrayList;

public class ItemRequest {
    private String requesterId;
    private ArrayList<String> itemIdsOffered;
    private String requestItemId;
    private String uid;

    public ItemRequest(UserAccount u, Item wanted, ArrayList<Item> offered ) {
        this.uid = UidService.newUID();
        if(offered.size() != 0){
            this.requesterId = u.getUid();
            this.requestItemId = wanted.getUid();
            this.itemIdsOffered = new ArrayList<String>();
            int i = 0;
            for(i = 0; i < offered.size(); i++){
                if(!this.itemIdsOffered.contains(offered.get(i).getUid())){
                    this.itemIdsOffered.add(offered.get(i).getUid());
                }
            }
        }
    }

    //This constructor is only used for FirebaseUser currentUser who log in the app
    public ItemRequest(String requesterId, String requestItemId, String uid) {
        this.uid = uid;
        this.requesterId = requesterId;
        this.requestItemId = requestItemId;
        this.itemIdsOffered = new ArrayList<>();
    }

    public String getRequesterId() {
        return requesterId;
    }

    public ArrayList<String> getItemIdsOffered(){
        /** @todo */
        return this.itemIdsOffered;
    }

    public String getRequestedItemId() {
        return requestItemId;
    }

    public String getUid() {
        return this.uid;
    }

    public void addOfferedItemIds(ArrayList<String> offeredItemIds){
        for(String id : offeredItemIds){
            if(!this.itemIdsOffered.contains(id)){
                this.itemIdsOffered.add(id);
            }
        }
    }
}
