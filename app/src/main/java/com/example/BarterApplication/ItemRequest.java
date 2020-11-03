package com.example.BarterApplication;

public class ItemRequest {
    User requester;
    Item item;
    public ItemRequest(User u, Item i)
    {
        this.item = i;
        this.requester = u;
    }
}
