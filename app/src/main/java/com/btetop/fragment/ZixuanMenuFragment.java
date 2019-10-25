package com.btetop.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.ZixuanMenuPagerAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.widget.IsCanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.btetop.config.UrlConfig.MINE_CAPITAL;

public class ZixuanMenuFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = "ZixuanMenuFragment";
    @BindView(R.id.item_left)
    TextView tvLeft;
    @BindView(R.id.item_right)
    TextView tvRight;
    @BindView(R.id.fragment_vieapager)
    IsCanScrollViewPager viewPager;

    private ZixuanMenuLeftFragment leftFragment;
    private CommonWebViewFragment rightFragment;
    private ZixuanMenuPagerAdapter adapter;
    private int currentPosition;
    private List<BaseFragment> fragments;

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_menu_zixuan;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
        leftFragment=new ZixuanMenuLeftFragment();
        rightFragment = CommonWebViewFragment.newInstance(MINE_CAPITAL,"自选menu",false);
        fragments = new ArrayList<>();
        fragments.add(leftFragment);
        fragments.add(rightFragment);
        adapter=new ZixuanMenuPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCanScroll(false);
        viewPager.addOnPageChangeListener(this);
        showLeft();
    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }

    @OnClick({R.id.item_left, R.id.item_right})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.item_left:
                showLeft();
                break;
            case R.id.item_right:
                showRight();
                break;
        }

    }

    private void showLeft() {
        tvLeft.setSelected(true);
        tvRight.setSelected(false);
        viewPager.setCurrentItem(0);

    }

    private void showRight() {
        tvRight.setSelected(true);
        tvLeft.setSelected(false);
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition=position;
        fragments.get(currentPosition).onViewPagerShow();
        switch (position) {
            case 0:
                showLeft();
                fragments.get(1).onViewPagerHidden();
                break;
            case 1:
                showRight();
                fragments.get(0).onViewPagerHidden();
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onHidden() {
        super.onHidden();
        fragments.get(currentPosition).onViewPagerHidden();
    }

    @Override
    protected void onShow() {
        super.onShow();
        fragments.get(currentPosition).onViewPagerShow();
    }
}
