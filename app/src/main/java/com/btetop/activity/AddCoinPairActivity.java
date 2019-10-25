package com.btetop.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.TabFragmentAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.dialog.ShareDialog;
import com.btetop.fragment.ZixuanCheckFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddCoinPairActivity extends BaseActivity {


    @BindView(R.id.img_fan_hui)
    ImageView imgFanHui;
    @BindView(R.id.tran_title_detail)
    TextView tranTitleDetail;
    @BindView(R.id.tran_share_detail)
    ImageView tranShareDetail;
    @BindView(R.id.tv_search_coin_pair)
    TextView tvSearchCoinPair;
    @BindView(R.id.tab_main)
    TabLayout tabMain;
    @BindView(R.id.vp_main)
    ViewPager vpMain;


    private TabFragmentAdapter adapter;


    private String[] mtitles = new String[]{"自选","BTC","ETH","BCH","EOS"};
    private List<Fragment> fragmentList;

    @Override
    public int intiLayout() {
        return R.layout.activity_add_coin_pair;
    }

    @Override
    public void initView() {
        fragmentList=new ArrayList<>(mtitles.length);
        fragmentList.add(ZixuanCheckFragment.newInstance());
        fragmentList.add(SearchCoinPairFragment.newInstance(mtitles[1]));
        fragmentList.add(SearchCoinPairFragment.newInstance(mtitles[2]));
        fragmentList.add(SearchCoinPairFragment.newInstance(mtitles[3]));
        fragmentList.add(SearchCoinPairFragment.newInstance(mtitles[4]));

        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mtitles);
        vpMain.setAdapter(adapter);
        tabMain.setupWithViewPager(vpMain);

    }

    @Override
    public void initData() {
        tranTitleDetail.setText("添加币对");
//        tranShareDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @OnClick({R.id.tv_search_coin_pair,R.id.tran_share_detail,R.id.img_fan_hui})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_search_coin_pair:
                startActivity(new Intent(mContext, SearchCoinPairActivity.class));
                break;
            case R.id.tran_share_detail:
                ShareDialog.getInstance().showShareDialog(this);
                break;
            case R.id.img_fan_hui:
                finish();
                break;
        }
    }
}
