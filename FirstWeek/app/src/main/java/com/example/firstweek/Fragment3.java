package com.example.firstweek;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.firstweek.SettingContactHistoryDB.dbname;


public class Fragment3 extends Fragment {
    private View view;
    private static Fragment3 _instance;
    SettingContactHistoryDB settingContactHistoryDB;
    ContactHistoryDBHelper contactHistoryDBHelper;
    SQLiteDatabase sqLiteDatabase;


    public static Fragment3 newInstance() {
        if (_instance == null)
            _instance = new Fragment3();

        return _instance;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        if(contactHistoryDBHelper == null){
            contactHistoryDBHelper = new ContactHistoryDBHelper(getContext(), dbname, null, 1);
        }

        if(settingContactHistoryDB == null){
            settingContactHistoryDB = new SettingContactHistoryDB(getContext());
        }

        sqLiteDatabase = contactHistoryDBHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CONTACT_HISTORY_TABLE order by COUNT desc", null);
        if(cursor.moveToFirst()){
            do{
                Log.d("adkl", cursor.getString(0)  + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getInt(3));
            }while(cursor.moveToNext());
        }

        return view;
    }
}