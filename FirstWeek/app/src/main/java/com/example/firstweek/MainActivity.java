package com.example.firstweek;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private final int PERMISSIONS_READ_CONTACTS = 1000;
    private final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1001;
    private final int PERMISSIONS_ACCESS_MEDIA_LOCATION = 1002;
    private final int PERMISSIONS_READ_CALL_LOG = 1003;

    private boolean isPermission = false;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callPermission();
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        fragmentPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("연락처"));
        tabLayout.addTab(tabLayout.newTab().setText("갤러리"));
        tabLayout.addTab(tabLayout.newTab().setText("뭐하지"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_READ_CONTACTS);
        } else {
            isPermission = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_READ_EXTERNAL_STORAGE);
        }else{
            isPermission = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_MEDIA_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION}, PERMISSIONS_ACCESS_MEDIA_LOCATION);
        }else{
            isPermission = true;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_READ_CALL_LOG);
        }else{
            isPermission = true;
        }
    }

}
