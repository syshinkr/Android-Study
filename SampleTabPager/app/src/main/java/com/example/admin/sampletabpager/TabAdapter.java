package com.example.admin.sampletabpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by admin on 2017-12-27.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0 :
                TabFragment1 tabFragment1 = new TabFragment1();
                return tabFragment1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
