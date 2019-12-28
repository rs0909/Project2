package com.example.firstweek;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


public class GalleryFragment extends Fragment
{
    private View view;
    private static GalleryFragment _instance;

    public static GalleryFragment newInstance(){
        if(_instance == null)
            _instance = new GalleryFragment();

        return _instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        GridView gridView = view.findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(getContext());
        gridView.setAdapter(imageAdapter);

        return view;
    }
}