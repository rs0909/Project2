package com.example.firstweek;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private ArrayList<Bitmap> ArrayForImage;
    private ImageArray imageArray;
    private Context context;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ImageAdapter(Context context) {
        this.context = context;
        imageArray = new ImageArray(context);
        ArrayForImage = imageArray.getUriArray();
    }

    @Override
    public int getCount() {
        return ArrayForImage.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayForImage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
        }else{
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = ArrayForImage.get(position);
        if(bitmap != null){
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            imageView.setImageBitmap(resized);
        }

        return imageView;
    }
}
