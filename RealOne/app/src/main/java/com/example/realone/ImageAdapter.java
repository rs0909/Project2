package com.example.realone;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<Contact> arrayList;
    private Context context;
    private Point size;

    public ImageAdapter(ArrayList<Contact> arrayList, Context context, Point size) {
        this.arrayList = arrayList;
        this.context = context;
        this.size = size;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return arrayList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(context);
        }else {
            imageView = (ImageView)view;
        }

        Uri uri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, arrayList.get(i).getPhotoId());
        InputStream inputStream = null;
        try {
            inputStream = (InputStream) context.getContentResolver().openInputStream(uri);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, size.x/3, size.x/3, true));

        return imageView;
    }
}
