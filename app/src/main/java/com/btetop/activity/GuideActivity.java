package com.btetop.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.btetop.R;
import com.btetop.adapter.GuidePageAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.utils.ImageUtils;
import com.btetop.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.btetop.config.Constant.FIRST_INSTALL;

/**
 * Created by lje on 2018/1/11.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<View> mViews;
    private int[] imagearray;

    @Override
    public int intiLayout() {
        return R.layout.activity_guide_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        initViewPager();
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    private void initViewPager() {
        mViews = new ArrayList<>();
        imagearray = new int[]{R.mipmap.guide_page_1, R.mipmap.guide_page_2, R.mipmap.guide_page_3, R.mipmap.guide_page_4, R.mipmap.guide_page_5};
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imagearray.length; i++) {
            ImageView imageView = new ImageView(this);

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundResource(imagearray[i]);
            mViews.add(imageView);
        }
        mViewPager.setAdapter(new GuidePageAdapter(mViews, this));
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imagearray.length;
        if (position == length - 1) {
            mViews.get(position).setClickable(true);
            mViews.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPUtils.put(FIRST_INSTALL, true);
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                }
            });
        } else {
            mViews.get(position).setClickable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
