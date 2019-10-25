package com.btetop.fragment;

import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.btetop.R;
import com.btetop.adapter.TabFragmentAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.utils.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MarketFragment extends BaseFragment{

    @BindView(R.id.tab_main)
    TabLayout tabLayout;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.cl_contain)
    CoordinatorLayout cl_contain;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    private String[] mtitles = new String[]{"市场", "币种", "热度指数", "空气指数","板块","平台"};

    private MarketDetailFragment marketDetailFragment;
    private MarketCoinPairFragment marketCoinPairFragment;
    private MarketAirIndexFragment marketAirIndexFragment;
    private MarketHeatIndexFragment marketHeatIndexFragment;

    private TabFragmentAdapter adapter;


    private static MarketFragment _instance = null;

    public static MarketFragment newInstance() {
        if(_instance == null)
            _instance = new MarketFragment();
        return  _instance;
    }
    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initView(View view) {

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 10, 10);
            }
        });

        marketDetailFragment = MarketDetailFragment.newInstance();
        marketCoinPairFragment = MarketCoinPairFragment.newInstance();
        marketHeatIndexFragment = MarketHeatIndexFragment.newInstance();
        marketAirIndexFragment = MarketAirIndexFragment.newInstance();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.remove(getParentFragment());
//        fragmentTransaction
//                .add(R.id.fragment_container, marketDetailFragment)
//                .commit();


        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(marketDetailFragment);
        fragmentList.add(marketCoinPairFragment);
        fragmentList.add(marketHeatIndexFragment);
        fragmentList.add(marketAirIndexFragment);

        adapter = new TabFragmentAdapter(getFragmentManager(), fragmentList, mtitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }
//
//    @OnClick({ R.id.icon_back})
//    public void setClick(View view) {
//        switch (view.getId()) {
//
//            case R.id.icon_back:
//                backStack();
//                break;
//        }
//    }


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

    private void backStack(){
        int currentItem = viewPager.getCurrentItem();
        if (currentItem==adapter.getCount()-1) {
            viewPager.setCurrentItem(0);
        }else {
            ToastUtils.showShortToast("backStack");
        }
    }
}
