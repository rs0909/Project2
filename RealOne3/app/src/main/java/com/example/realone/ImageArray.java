package com.example.realone;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageArray {
    Context context;
    public ImageArray(Context context) {
        this.context = context;
    }

    public ArrayList<Bitmap> getUriArray(){
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};
        Cursor imageCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

        if(imageCursor.moveToFirst()){
            do {
                Uri photoUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageCursor.getString(0));
                InputStream inputStream = null;
                try {
                    inputStream = (InputStream) context.getContentResolver().openInputStream(photoUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(inputStream), 300, 300);

                bitmapArrayList.add(bitmap);

            }while (imageCursor.moveToNext());
        }
        return bitmapArrayList;
    }
}
