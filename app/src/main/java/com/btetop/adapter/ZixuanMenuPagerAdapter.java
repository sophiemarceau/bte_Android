package com.btetop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.btetop.base.BaseFragment;

import java.util.List;

public class ZixuanMenuPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragment;

    public ZixuanMenuPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.mFragment = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment == null ? 0 : mFragment.size();
    }
}
