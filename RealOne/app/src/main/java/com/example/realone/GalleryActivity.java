package com.example.realone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class GalleryActivity extends AppCompatActivity {

    ArrayList<String[]> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_activity);

        Intent intent = getIntent();
        final String phoneNumber = intent.getExtras().getString("phoneNumber");

        ImageView[] imageViews = new ImageView[9];
        imageViews[0] = (ImageView) findViewById(R.id.imageView1);
        imageViews[1] = (ImageView) findViewById(R.id.imageView2);
        imageViews[2] = (ImageView) findViewById(R.id.imageView3);
        imageViews[3] = (ImageView) findViewById(R.id.imageView4);
        imageViews[4] = (ImageView) findViewById(R.id.imageView5);
        imageViews[5] = (ImageView) findViewById(R.id.imageView6);
        imageViews[6] = (ImageView) findViewById(R.id.imageView7);
        imageViews[7] = (ImageView) findViewById(R.id.imageView8);
        imageViews[8] = (ImageView) findViewById(R.id.imageView9);
        for(int i = 0; i < 9; i++){
            imageViews[i].setOnClickListener(new ImageViewClick(i) {
                @Override
                public void onClick(View view) {
                    String[] info = arrayList.get(i);
                    LargePhotoDialog largePhotoDialog = new LargePhotoDialog(getApplicationContext());
                    largePhotoDialog.callFunction(Uri.parse(info[1]), phoneNumber);
                }
            });
        }

        for(int i = 0; i < arrayList.size(); i++){
            Uri uri = Uri.parse(arrayList.get(i)[1]);
            InputStream inputStream = null;
            try {
                inputStream = (InputStream) this.getContentResolver().openInputStream(uri);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeStream(inputStream), 300, 300);
            imageViews[i].setImageBitmap(bitmap);
        }
    }
}

abstract class ImageViewClick implements View.OnClickListener{
    int i;
    public ImageViewClick(int i) {
        this.i = i;
    }
}
