package com.example.realone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.realone.SettingContactHistoryDB.dbname;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSIONS_READ_CONTACTS = 1000;
    private final int PERMISSIONS_READ_CALL_LOG = 1001;
    private final int PERMISSIONS_SEND_SMS = 1002;
    private final int PERMISSIONS_READ_PHONE_STATE = 1003;
    private Context context;
    private SharedPreferences sharedPreference;
    private boolean signUpDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        callPermission();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" Friends");
        TextView tv = new TextView(this);
        RelativeLayout.LayoutParams Ip = new RelativeLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(Ip);
        tv.setText(actionBar.getTitle());
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,26);

        // Set the ActionBar display option
        Typeface font = Typeface.createFromAsset(getAssets(),
                "font/rubik.ttf");
        tv.setTypeface(font);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);

        callPermission();

        while (!checkPermission()){
            //ㅈㄴ 임시방편임
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 150);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (signUpDone == true) {


            sharedPreference = getSharedPreferences("Info", MODE_PRIVATE);

            ImageView profileImg = findViewById(R.id.proImg);
            TextView profileName = findViewById(R.id.proName);
            TextView profilePhone = findViewById(R.id.proPhone);

            ListView listView = (ListView) findViewById(R.id.listview);
            final ContactList contact = new ContactList(this);
            final ArrayList<Contact> arrayList = contact.getContactList();
            ListViewAdapter adapter = new ListViewAdapter(this, arrayList);
            listView.setAdapter(adapter);

            Uri uri = Uri.parse(context.getSharedPreferences("Info", Context.MODE_PRIVATE).getString("uri", "notFound"));
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = this.getContentResolver().openFileDescriptor(uri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            try {
                parcelFileDescriptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            profileImg.setImageBitmap(image);
            profileName.setText(sharedPreference.getString("name", "notFound"));
            profilePhone.setText("           ");

            profileImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GalleryActivity.class);
                    ContactHistoryDBHelper contactHistoryDBHelper = new ContactHistoryDBHelper(context, dbname, null, 1);
                    SettingContactHistoryDB settingContactHistoryDB = new SettingContactHistoryDB(context);
                    if (settingContactHistoryDB.getHaveNoCallLog() == true) {
                        intent.putParcelableArrayListExtra("friendsList", arrayList);
                    } else {
                        SQLiteDatabase sqLiteDatabase = contactHistoryDBHelper.getReadableDatabase();
                        ArrayList<Contact> arrayList2 = settingContactHistoryDB.getFriendsArray(sqLiteDatabase);
                        intent.putParcelableArrayListExtra("friendsList", arrayList2);
                    }
                    intent.putExtra("name", sharedPreference.getString("name", "notFound"));
                    intent.putExtra("phoneNumber", sharedPreference.getString("phoneNumber", "notFound"));
                    intent.putExtra("owner", true);
                    context.startActivity(intent);
                }
            });
            profileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GalleryActivity.class);
                    ContactHistoryDBHelper contactHistoryDBHelper = new ContactHistoryDBHelper(context, dbname, null, 1);
                    SettingContactHistoryDB settingContactHistoryDB = new SettingContactHistoryDB(context);

                    SQLiteDatabase sqLiteDatabase = contactHistoryDBHelper.getReadableDatabase();
                    ArrayList<Contact> arrayList2 = settingContactHistoryDB.getFriendsArray(sqLiteDatabase);
                    intent.putParcelableArrayListExtra("friendsList", arrayList2);
                    intent.putExtra("name", sharedPreference.getString("name", "notFound"));
                    intent.putExtra("phoneNumber", sharedPreference.getString("phoneNumber", "notFound"));
                    intent.putExtra("owner", true);
                    context.startActivity(intent);
                }
            });

        }
    }
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                                && checkSelfPermission(Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALL_LOG, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE}, 0);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_READ_CONTACTS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_READ_CALL_LOG);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_SEND_SMS);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_READ_PHONE_STATE);
        }


    }

    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 150){
            if(resultCode == 1){
                signUpDone = true;
            }
        }
    }
}
