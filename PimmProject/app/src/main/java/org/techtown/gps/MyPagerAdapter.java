package org.techtown.gps;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.techtown.gps.jinseongPart.AlarmFragment;

public class MyPagerAdapter extends FragmentPagerAdapter
{

    private static final int ITEMS_NUM = 3;

    public MyPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return DrinkWaterFrag.newInstance();
            case 1:
                return AlarmFragment.newInstance();
            case 2:
                return Fragment3.newInstance();
            default:
                return null;
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        

    }

    @Override
    public int getCount() {
        return ITEMS_NUM;
    }
}
