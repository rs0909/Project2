package org.techtown.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener;

import org.techtown.gps.jinseongPart.AlarmFragment;
import org.techtown.gps.jinseongPart.AlarmSettingReceiver1;

public class MainActivity extends AppCompatActivity {

    public UserInfo userInfo;

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private final int RESULT_DELETE = 2;

    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private GpsInfo gps;
    private WeatherItem weatherItem;
    private ParsingItem parseItem;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager vPager;
    private FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInfo = new UserInfo();

        vPager = findViewById(R.id.pager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);

        toolbar.setNavigationIcon(R.drawable.ic_glass04);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vPager.setAdapter(adapterViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("물마시기"));
        tabLayout.addTab(tabLayout.newTab().setText("알람설정"));
        tabLayout.addTab(tabLayout.newTab().setText("사용자 정보"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        vPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        callPermission();

        gps = new GpsInfo(MainActivity.this);


        if (gps.isGetLocation()) {
            double latitude = gps.getLat();
            double longitude = gps.getLon();

            weatherItem = new WeatherItem(latitude, longitude);
            weatherItem.start();

            try {
                weatherItem.join(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            parseItem = new ParsingItem(weatherItem.xml);
        } else {
            gps.showSettingsAlert();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Fragment fragment = DrinkWaterFrag.newInstance();
        fragment.onActivityResult(requestCode,resultCode,data);

        Fragment alarmFragment = AlarmFragment.newInstance();
        if(resultCode == 0){
            int hour = data.getExtras().getInt("time hour");
            int minute = data.getExtras().getInt("time minute");

            Bundle bundle = new Bundle();
            bundle.putInt("time hour", hour);
            bundle.putInt("time minute", minute);
            alarmFragment.setArguments(bundle);

            settingAlarm(data.getExtras().getLong("millitime"), hour*100 + minute);
        }
    }

    public String getHumidityText()
    {
        return parseItem.getHumidity();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    // 전화번호 권한 요청
    private void callPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
    //shibal
    private void settingAlarm(long time, int rawTime){
        ComponentName receiver = new ComponentName(this, AlarmSettingReceiver1.class);
        PackageManager pm = this.getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent intent = new Intent(this, AlarmSettingReceiver1.class);
        intent.putExtra("time", rawTime);
        intent.setAction("android.intent.action.BOOT_COMPLETED");
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, rawTime, intent, 0);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
