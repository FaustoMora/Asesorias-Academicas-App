package com.passeapp.dark_legion.asacapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class TemaCustomAdapter extends ArrayAdapter<TemaClass>{

    public TemaCustomAdapter(Context context, ArrayList<TemaClass> objects) {
        super(context, R.layout.custom_row, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        TemaClass contact = getItem(position);
        TextView contactName = (TextView) customView.findViewById(R.id.lblText);
        //ImageView icon = (ImageView)customView.findViewById(R.id.iconFolder);
        contactName.setText(contact.getNombre());
        //icon.setImageResource(R.drawable.folder);

        if (position == TemaActivity.selectedListPos) {
            customView.setBackgroundResource(R.color.pressed_color);
        }

        return customView;

        /*ViewHolderItem viewHolder;

        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);

            // well set up the ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = (TextView) convertView.findViewById(R.id.lblText);

            // store the holder with the view.
            convertView.setTag(viewHolder);

        }else{
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        // object item based on the position
        TemaClass objectItem = getItem(position);

        // assign values if the object is not null
        if(objectItem != null) {
            // get the TextView from the ViewHolder and then set the text (item name) and tag (item ID) values
            viewHolder.textViewItem.setText(objectItem.getNombre());
            viewHolder.textViewItem.setTag(objectItem.getNombre());
        }

        return convertView;*/
    }

    static class ViewHolderItem {
        TextView textViewItem;
    }

}
