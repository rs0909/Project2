package com.example.realone;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageAdapter2 extends BaseAdapter {
    private Context context;
    private Point size;

    public ImageAdapter2(Context context, Point size) {
        this.context = context;
        this.size = size;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view == null){
            imageView = new ImageView(context);
        }else {
            imageView = (ImageView)view;
        }
        Uri uri = Uri.parse(context.getSharedPreferences("Info", Context.MODE_PRIVATE).getString("uri", "notFound"));
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, size.x/3, size.x/3, true));

        try {
            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return imageView;
    }
}
