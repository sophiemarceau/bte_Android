package com.btetop.adapter;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] mtitles;
    private FragmentManager fm;


    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, String[] tabTitle) {
        super(fm);
        this.fm=fm;
        this.fragmentList = fragments;
        this.mtitles=tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitles[position];
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void clearAll(){
        if (fragmentList==null || fragmentList.size()==0) {
            return;
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            fm.beginTransaction().remove(fragmentList.get(i)).commit();
        }
        fragmentList.clear();
    }
}
