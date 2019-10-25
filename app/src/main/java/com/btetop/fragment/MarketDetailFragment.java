package com.btetop.fragment;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.btetop.R;
import com.btetop.base.BaseFragment;

import java.lang.reflect.Field;

import butterknife.BindView;

public class MarketDetailFragment extends BaseFragment {

    private static MarketDetailFragment _instance = null;

    public static MarketDetailFragment newInstance() {
        if(_instance == null)
            _instance = new MarketDetailFragment();
        return  _instance;
    }
    @BindView(R.id.tab_main)
    TabLayout tabLayout;


    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_market_detail;
    }

    @Override
    protected void initView(View view) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 10, 10);
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("全网总资金流向"));
        tabLayout.addTab(tabLayout.newTab().setText("BTC资金流向"));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }



    //利用反射设置tablayout下划线长度
    private void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
