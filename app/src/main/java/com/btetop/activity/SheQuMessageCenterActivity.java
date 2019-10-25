package com.btetop.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.ZixuanMenuPagerAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MyReleaseBeanOuter;
import com.btetop.fragment.DiscussFragment;
import com.btetop.fragment.PostFragment;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.widget.IsCanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.btetop.activity.PostShequActivity.HINT_TAG;

public class SheQuMessageCenterActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.fragment_vieapager)
    IsCanScrollViewPager viewPager;
    @BindView(R.id.item_left)
    RelativeLayout tvLeft;
    @BindView(R.id.item_left_text)
    TextView itemLeftText;
    @BindView(R.id.item_right_text)
    TextView itemRightText;
    @BindView(R.id.item_right)
    RelativeLayout tvRight;
    @BindView(R.id.tran_title_detail)
    TextView title;
    @BindView(R.id.newReleaseCount)
    TextView releaseCount;
    @BindView(R.id.newDisussCount)
    TextView discussCount;

    private PostFragment leftFragment;
    private DiscussFragment rightFragment;
    private ZixuanMenuPagerAdapter adapter;
    private List<BaseFragment> fragments;

    private String hintString = "";


    @Override
    public int intiLayout() {
        return R.layout.activity_zixuan_message_center;
    }

    @Override
    public void initView() {
        title.setText("消息中心");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hintString = extras.getString(HINT_TAG);
        }
        leftFragment = PostFragment.getInstance(hintString);
        rightFragment = new DiscussFragment();
        fragments = new ArrayList<>();
        fragments.add(leftFragment);
        fragments.add(rightFragment);
        adapter = new ZixuanMenuPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCanScroll(false);
        viewPager.addOnPageChangeListener(this);
        showLeft();
    }

    @Override
    public void initData() {
        requestData();
    }


    @OnClick({R.id.item_left, R.id.item_right, R.id.img_fan_hui, R.id.all_read})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.item_left:
                showLeft();
                break;
            case R.id.item_right:
                showRight();
                break;
            case R.id.img_fan_hui:
                finish();
                break;
            case R.id.all_read:
                requestAllRead();
                break;
        }

    }

    private void requestData() {
        BteTopService.getMessageCount()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<MyReleaseBeanOuter>>() {
                    @Override
                    public void call(BaseBean<MyReleaseBeanOuter> myReleaseBeanOuterBaseBean) {
                        if (!"0000".equals(myReleaseBeanOuterBaseBean.getCode()) || myReleaseBeanOuterBaseBean.getData()==null) {
                            return;
                        }
                        MyReleaseBeanOuter data = myReleaseBeanOuterBaseBean.getData();
                        if (data.getMyReleaseCount()!=0) {
                            releaseCount.setVisibility(View.VISIBLE);
                            releaseCount.setText("+"+data.getMyReleaseCount());

                        }else {
                            releaseCount.setVisibility(View.GONE);
                        }
                        if (data.getMyCommnetCount()!=0) {
                            discussCount.setVisibility(View.VISIBLE);
                            discussCount.setText("+"+data.getMyCommnetCount());
                        }else {
                            discussCount.setVisibility(View.GONE);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void requestAllRead() {
        int readType = 1;
        if (viewPager == null) {
            return;
        }
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == 0) {
            readType = 1;
        } else {
            readType = 2;
        }
        int finalReadType = readType;
        BteTopService.setAllRead(readType)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        requestData();
                        if (finalReadType ==1) {
                            leftFragment.refreshFragment();
                        }else {
                            rightFragment.refreshFragment();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showLeft() {
        tvLeft.setSelected(true);
        tvRight.setSelected(false);
        itemLeftText.setSelected(true);
        itemRightText.setSelected(false);
        viewPager.setCurrentItem(0);

    }

    private void showRight() {
        tvRight.setSelected(true);
        tvLeft.setSelected(false);
        viewPager.setCurrentItem(1);
    }
}
