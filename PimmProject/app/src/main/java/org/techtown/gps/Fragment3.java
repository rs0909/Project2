package org.techtown.gps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment
{
    private View view;
    private static Fragment3 _instance;

    public static Fragment3 newInstance(){

        if(_instance == null)
            _instance = new Fragment3();
        return _instance;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }
}
