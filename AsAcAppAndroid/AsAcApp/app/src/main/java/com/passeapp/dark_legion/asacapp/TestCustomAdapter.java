package com.passeapp.dark_legion.asacapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class TestCustomAdapter extends ArrayAdapter<TestClass> {

    public TestCustomAdapter(@NonNull Context context, @NonNull ArrayList<TestClass> objects) {
        super(context, R.layout.custom_row, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        TestClass contact = getItem(position);
        TextView contactName = (TextView) customView.findViewById(R.id.lblText);
        //ImageView icon = (ImageView)customView.findViewById(R.id.iconFolder);
        contactName.setText(contact.getNombre());
        //icon.setImageResource(R.drawable.folder);

        if (position == TestActivity.selectedListPos) {
            customView.setBackgroundResource(R.color.pressed_color);
        }

        return customView;
    }
}
