package com.example.BarterApplication.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.ItemRequest;
import com.example.BarterApplication.MyRequestActivity;
import com.example.BarterApplication.R;
import com.example.BarterApplication.ViewMyRequestPageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "myAdapter";
    private static Context context;
    private Map<String, ArrayList<Item>> requestAndItemIdsOfferedMap;
    private Map<String, Item> requestAndRequestItemMap;
    private String[] mDataset;
    private static ArrayList<ItemRequest> itemRequests;
    private static ArrayList<Item> itemLists;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    Intent intent = new Intent(context, MyRequestActivity.class);
                    ArrayList<ItemRequest> itReqs = new ArrayList<>();
                    itReqs.add(itemRequests.get(getAdapterPosition()));
                    intent.putExtra("itemRequestSelected", itReqs);
                    intent.putExtra("itemsExtra", itemLists);
                    context.startActivity(intent);
                }
            });
            textView = (TextView) v.findViewById(R.id.itemRequestTextView);
        }
        public TextView getTextView() {
            return textView;
        }
    }

    public MyAdapter(String[] data, ArrayList<ItemRequest> itemRequestsData, ArrayList<Item> items) {
        mDataset = data;
        itemRequests = itemRequestsData;
        itemLists = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FrameLayout v = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        holder.getTextView().setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

