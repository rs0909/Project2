package com.example.firstweek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactListViewAdapter extends BaseAdapter {


    private ArrayList<Contact> arrayList;
    public ContactListViewAdapter(ArrayList<Contact> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        ViewGroup viewGroup = parent;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
        }
        TextView textViewForName = (TextView) convertView.findViewById(R.id.NameTextView);
        TextView textViewForPhoneNumber = (TextView) convertView.findViewById(R.id.phoneNumberTextView);

        Contact contact = arrayList.get(position);
        textViewForName.setText(contact.getName());
        textViewForPhoneNumber.setText(contact.getPhoneNumber());

        return convertView;
    }
}
