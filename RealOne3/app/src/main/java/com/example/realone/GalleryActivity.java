package com.example.realone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.TypedValue;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity {
    Context context = this;
    ArrayList<Contact> arrayList;
    String phoneNumber;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Friend's Friends");
        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams Ip = new RelativeLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(Ip);
        tv.setText(actionBar.getTitle());
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,26);

        // Set the ActionBar display option
        Typeface font = Typeface.createFromAsset(getAssets(),
                "font/rubik.ttf");
        tv.setTypeface(font);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);

        GridView gridView = findViewById(R.id.gridView);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        Intent intent = getIntent();

        if(intent.getExtras().getBoolean("owner") == true){
            phoneNumber = intent.getExtras().getString("phoneNumber");
            String name = intent.getExtras().getString("name");
            arrayList = intent.getParcelableArrayListExtra("friendsList");

            ImageAdapter imageAdapter = new ImageAdapter(arrayList, this, size);
            gridView.setAdapter(imageAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact contact = arrayList.get(i);
                    LargePhotoDialog largePhotoDialog = new LargePhotoDialog(context);
                    largePhotoDialog.callFunction(ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, arrayList.get(i).getPhotoId()), arrayList.get(i).getPhoneNumber());
                }
            });
        }else {
            final String selectedPhone = intent.getExtras().getString("selectedOne");
            ImageAdapter2 imageAdapter2 = new ImageAdapter2(this, size);
            gridView.setAdapter(imageAdapter2);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Info", MODE_PRIVATE);
                    LargePhotoDialog largePhotoDialog = new LargePhotoDialog(context);
                    largePhotoDialog.callFunction(Uri.parse(sharedPreferences.getString("uri", "notFound")), sharedPreferences.getString("phoneNumber", "notfound"), selectedPhone);
                }
            });
        }
    }
}
