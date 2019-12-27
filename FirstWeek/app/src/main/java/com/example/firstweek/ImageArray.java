package com.example.firstweek;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;

import androidx.annotation.RequiresApi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageArray {
    Context context;
    public ImageArray(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public ArrayList<Bitmap> getUriArray(){
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};
        Cursor imageCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();

        if(imageCursor.moveToFirst()){
            do {
                Uri photoUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageCursor.getString(0));
                photoUri = MediaStore.setRequireOriginal(photoUri);

                Bitmap bitmap = null;
                try {
                    bitmap = context.getContentResolver().loadThumbnail(photoUri, new Size(100, 100), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                bitmapArrayList.add(bitmap);

            }while (imageCursor.moveToNext());
        }
        return bitmapArrayList;
    }
}