package com.example.realone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LargePhotoDialog {
    private Context context;

    public LargePhotoDialog(Context context) {
        this.context = context;
    }

    public void callFunction(Uri photoUri, final String phoneNumber){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_large_photo);
        dialog.show();

        ImageView imageView = (ImageView)dialog.findViewById(R.id.imageView);
        Button contactButton = (Button)dialog.findViewById(R.id.button);
        Button cancelButton = (Button)dialog.findViewById(R.id.button2);


        InputStream inputStream = null;
        try {
            inputStream = (InputStream) context.getContentResolver().openInputStream(photoUri);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(inputStream), imageView.getWidth(), imageView.getHeight());
        imageView.setImageBitmap(bitmap);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel :" + phoneNumber));
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
}
