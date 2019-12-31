package com.example.realone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity {
    Context context = this;
    ArrayList<Contact> arrayList;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_activity);

        Intent intent = getIntent();
        phoneNumber = intent.getExtras().getString("phoneNumber");
        String name = intent.getExtras().getString("name");
        arrayList = intent.getParcelableArrayListExtra("friendsList");

        GridView gridView = findViewById(R.id.gridView);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ImageAdapter imageAdapter = new ImageAdapter(arrayList, this, size);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = arrayList.get(i);
                LargePhotoDialog largePhotoDialog = new LargePhotoDialog(context);
                largePhotoDialog.callFunction(ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, arrayList.get(i).getPhotoId()), phoneNumber);
            }
        });
    }
}
