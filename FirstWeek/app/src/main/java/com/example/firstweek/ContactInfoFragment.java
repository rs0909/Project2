package com.example.firstweek;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactInfoFragment extends Fragment {
    private static ContactInfoFragment _instance;

    public static ContactInfoFragment newInstance(){
        if(_instance == null){
            _instance = new ContactInfoFragment();
        }
        return _instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);
        return view;
    }

}
