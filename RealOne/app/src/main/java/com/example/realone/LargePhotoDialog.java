package com.example.realone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.github.chrisbanes.photoview.PhotoView;

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

        InputStream inputStream = null;
        try {
            inputStream = (InputStream) context.getContentResolver().openInputStream(photoUri);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        photoView.setImageURI(photoUri);

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
}
