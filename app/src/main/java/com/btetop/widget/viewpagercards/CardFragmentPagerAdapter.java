package com.btetop.widget.viewpagercards;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.btetop.bean.BannerInfo;
import com.btetop.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private final BaseBean<List<BannerInfo>> mBannerInfo;
    private List<CardFragment> mFragments;
    private float mBaseElevation;

    public CardFragmentPagerAdapter(BaseBean<List<BannerInfo>> baseBean, FragmentManager fm, float baseElevation) {
        super(fm);
        this.mBannerInfo = baseBean;
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for (int i = 0; i < baseBean.getData().size(); i++) {
            addCardFragment(CardFragment.newInstance(mBannerInfo.getData().get(i).getUrl()));
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (CardFragment) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        mFragments.add(fragment);
    }

}
