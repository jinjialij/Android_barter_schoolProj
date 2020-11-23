package com.example.BarterApplication.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.BarterApplication.CreateRequestActivity;
import com.example.BarterApplication.Item;
import com.example.BarterApplication.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class OfferingItemListViewAdapter extends ArrayAdapter<Item> {
    private static final String LOG_TAG = "OfferingItemListViewAdapter";
    private static Context context;
    private ArrayList<Item> AddedOfferingItems;

    /**
     * This is a customized constructor.
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param items A List of Item objects to display in a list
     */

    public OfferingItemListViewAdapter(Activity context, ArrayList<Item> items) {
        super(context, 0, items);
        this.AddedOfferingItems = items;
    }

    /**
     * Provides a view for an AdapterView (ListView)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.text_button_row_items, parent, false);
        }

        context = parent.getContext();
        Item currentItem = getItem(position);
        TextView itemTextView = (TextView) listItemView.findViewById(R.id.ViewItemsInfoTextView);
        Button deleteBtn = (Button) listItemView.findViewById(R.id.ViewItemsMakeRequestBtn);
        deleteBtn.setText(R.string.Delete);
        String displayString = ItemService.printItemMap(ItemService.getItemMap(currentItem, new LinkedHashMap<>()));
        itemTextView.setText(displayString);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddedOfferingItems.remove(getItem(position));
                notifyDataSetChanged();
            }
        });
        return listItemView;
    }
}
