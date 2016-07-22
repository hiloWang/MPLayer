package com.hhly.lawyer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hhly.lawyer.ui.view.PagerFragment1;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public final int COUNT = 2;
    private String[] titles = new String[]{"Tab1", "Tab2"};

    public MyFragmentPagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm, fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragment1.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
