package com.example.quand.clock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

/**
 * Created by quand on 27-Mar-18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new SparseArray<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new FragmentBaoThuc();
                break;
            case 1:
                fragment = new FragmentThoiGian();
                break;
            case 2:
                fragment = new FragmentDemNguoc();
                break;
            case 3:
                fragment = new FragmentBamGio();
        }
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    public Fragment getFragmentByPosition(int position) {
        return fragments.get(position);
    }
}
