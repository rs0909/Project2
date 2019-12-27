package com.example.firstweek;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactInfoFragment extends Fragment {

    ArrayList<Contact> contactArrayList;

    private static ContactInfoFragment _instance;

    public static ContactInfoFragment newInstance(){
        if(_instance == null){
            _instance = new ContactInfoFragment();
        }
        return _instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactList contactList = new ContactList(getContext());
        contactArrayList = contactList.getContactList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);

        ContactListViewAdapter contactListViewAdapter = new ContactListViewAdapter(contactArrayList);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(contactListViewAdapter);

        return view;
    }
}
