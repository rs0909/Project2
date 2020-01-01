package com.example.realone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.realone.SettingContactHistoryDB.dbname;

public class LargePhotoDialog {
    private Context context;

    public LargePhotoDialog(Context context) {
        this.context = context;
    }

    public void callFunction(Uri photoUri, final String phoneNumber){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_large_photo);

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = 1000;
        wm.height = 1000;
        dialog.show();
        Window window = dialog.getWindow();
        window.setAttributes(wm);

        PhotoView photoView = (PhotoView) dialog.findViewById(R.id.photoView);
        Button contactButton = (Button)dialog.findViewById(R.id.button);
        Button cancelButton = (Button)dialog.findViewById(R.id.button2);
        TextView textView = (TextView)dialog.findViewById(R.id.textView);

        photoView.setImageURI(photoUri);
        int count = getHowManyTimesCallWith(phoneNumber);
        if(count != -1){
            textView.setText("친밀도:" + count+"0%");
        }

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber.replaceAll("-", "")));
                context.startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void callFunction(Uri photoUri, final String phoneNumber, String selectedOne){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_large_photo);

        WindowManager.LayoutParams wm = new WindowManager.LayoutParams();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = 1000;
        wm.height = 1000;
        dialog.show();
        Window window = dialog.getWindow();
        window.setAttributes(wm);

        PhotoView photoView = (PhotoView) dialog.findViewById(R.id.photoView);
        Button contactButton = (Button)dialog.findViewById(R.id.button);
        Button cancelButton = (Button)dialog.findViewById(R.id.button2);
        TextView textView = (TextView)dialog.findViewById(R.id.textView);

        photoView.setImageURI(photoUri);
        int count = getHowManyTimesCallWith(selectedOne);
        if(count != -1){
            textView.setText("친밀도:" + count+"0%");
        }

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber.replaceAll("-", "")));
                context.startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public int getHowManyTimesCallWith(String phoneNumber){
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(dbname, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CONTACT_HISTORY_TABLE", null);
        if(cursor.moveToFirst()){
            do{
                if(phoneNumber.replaceAll("-", "").equals(cursor.getString(cursor.getColumnIndex("PHONE")).replaceAll("-", ""))){
                    return cursor.getInt(cursor.getColumnIndex("COUNT"));
                }
            }while (cursor.moveToNext());
        }
        return -1;
    }
}
