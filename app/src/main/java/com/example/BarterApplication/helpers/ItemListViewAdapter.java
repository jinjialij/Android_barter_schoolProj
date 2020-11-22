package com.example.BarterApplication.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.BarterApplication.Item;
import com.example.BarterApplication.R;

import java.util.ArrayList;

public class ItemListViewAdapter extends ArrayAdapter<Item> {
    private static final String LOG_TAG = "ItemListViewAdapter";

    /**
     * This is a customized constructor.
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context  The current context. Used to inflate the layout file.
     * @param items A List of Item objects to display in a list
     */
    public ItemListViewAdapter(Activity context, ArrayList<Item> items) {
        super(context, 0, items);
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

        Item currentItem = getItem(position);
        String displayString = "Name: " + currentItem.getName() + "\nDescription: " + currentItem.getDescription() + "\nLabels:" + currentItem.getLabels().toString();
        TextView itemTextView = (TextView) listItemView.findViewById(R.id.ViewItemsInfoTextView);
        itemTextView.setText(displayString);

        return listItemView;
    }
}
