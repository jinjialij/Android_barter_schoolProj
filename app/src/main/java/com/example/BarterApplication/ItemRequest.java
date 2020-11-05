package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UID_Service;

import java.util.ArrayList;
import java.util.Collections;

public class ItemRequest {
        private String requesterId;
        private ArrayList<String> ItemIdsOffered;
        private String itemId;

        public ItemRequest(User u, Item wanted, ArrayList<Item> offered ) {
            if(offered.size() != 0){
                this.requesterId = u.getUid();
                this.itemId = wanted.getUid();
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

    public Item getItem(){
        /** @todo */
        return UID_Service.findItemById(this.itemId);
    }

    public User getRequester(){
        /** @todo */
        return UID_Service.findUserById(this.requesterId);
    }
}
