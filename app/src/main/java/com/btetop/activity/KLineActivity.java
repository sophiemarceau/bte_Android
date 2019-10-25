package com.btetop.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.TabFragmentAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.CoinInfo;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.SharePicDialog;
import com.btetop.fragment.ChatContainerFragment;
import com.btetop.fragment.DetailDadanFragment;
import com.btetop.fragment.DetailShenduFragment;
import com.btetop.fragment.KLineFragment;
import com.btetop.fragment.NestWebViewFragment;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.SPUtils;
import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class KLineActivity extends BaseActivity implements KLineFragment.CoinInfoListener {

    public static final String FIRST_INTO_KLINE = "first_into_kline";
    private CoinInfo mCoinInfo;

    @BindView(R.id.bg_guide)
    ImageView imGuide;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_main)
    TabLayout tabLayout;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.icon_back)
    ImageView icon_back;
    @BindView(R.id.iv_go_top)
    ImageView iv_go_top;
    @BindView(R.id.cl_contain)
    CoordinatorLayout cl_contain;
    @BindView(R.id.appbar)
    AppBarLayout appbar;


    private boolean MainCoinFlag;
    //主要币种
    private DetailShenduFragment depthFragment;
    private DetailDadanFragment detailDadanFragment;
    private NestWebViewFragment zijinWebViewFragment;
    private NestWebViewFragment bizhongWebViewFragment;
    private ChatContainerFragment chatContainerFragment;
    private String[] MainCointabTitles = new String[]{"超级深度", "大单成交", "资金流向", "币种详情", "讨论"};
    //小币种
    private String[] otherCointabTitles = new String[]{"资金流向", "币种详情"};
    private NestWebViewFragment otherZijinWebViewFragment;
    private NestWebViewFragment otherBizhongWebViewFragment;

    private TabFragmentAdapter adapter;
    private KLineFragment timeLineChartfragment;


    @Override
    public int intiLayout() {
        return R.layout.activity_transaction_details;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.color_white).init();
        iv_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                appBarGotoTop();
            }
        });


        showGuideImg();


    }

    private void showGuideImg() {
        boolean b = SPUtils.get(FIRST_INTO_KLINE, false);
        if (!b) {

            mImmersionBar.statusBarColor(R.color.clolor_80_black).init();
            Glide.with(this).load(R.mipmap.bg_guide).into(imGuide);
            SPUtils.put(FIRST_INTO_KLINE, true);
        }
    }

    public void appBarGotoTop() {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) appbar.getLayoutParams()).getBehavior();
        AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
        int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
        if (topAndBottomOffset != 0) {
            appBarLayoutBehavior.setTopAndBottomOffset(0);
        }
    }

    @Override
    public void initData() {

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 10, 10);
            }
        });
        Log.e(TAG, "initData: ");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCoinInfo = (CoinInfo) bundle.getSerializable("coinInfo");
        }
        showKlineFragment();

        //区分大币小币
        if (mCoinInfo.getSymbol().equalsIgnoreCase("BTC")
                || mCoinInfo.getSymbol().equalsIgnoreCase("EOS")
                || mCoinInfo.getSymbol().equalsIgnoreCase("ETH")
                || mCoinInfo.getSymbol().equalsIgnoreCase("BCH")) {
            initMainCoin();
            MainCoinFlag = true;
        } else {
            intOtherCoin();
            MainCoinFlag = false;
        }


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Log.e(TAG, "onNewIntent: ");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCoinInfo = (CoinInfo) bundle.getSerializable("coinInfo");
        }
        updateKlineFragment();

        //区分大币小币
        if (mCoinInfo.getSymbol().equalsIgnoreCase("BTC")
                || mCoinInfo.getSymbol().equalsIgnoreCase("EOS")
                || mCoinInfo.getSymbol().equalsIgnoreCase("ETH")
                || mCoinInfo.getSymbol().equalsIgnoreCase("BCH")) {
            initMainCoin();
            MainCoinFlag = true;
        } else {
            intOtherCoin();
            MainCoinFlag = false;
        }
    }

    private void initMainCoin() {
        depthFragment = DetailShenduFragment.newInstance();
        depthFragment.setCoinPair(mCoinInfo);

        detailDadanFragment = DetailDadanFragment.newInstance();
        detailDadanFragment.setCoinPair(mCoinInfo);


        zijinWebViewFragment = NestWebViewFragment.newInstance(getUrl(1), "zijin");
        bizhongWebViewFragment = NestWebViewFragment.newInstance(getUrl(2), "xiangqing");

        //聊天室的fragment
        chatContainerFragment = ChatContainerFragment.newInstance();
        chatContainerFragment.setGroupName(mCoinInfo.getSymbol());

        if (adapter!=null) {
            adapter.clearAll();
        }
        viewPager.removeAllViews();
        viewPager.setAdapter(null);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(depthFragment);
        fragmentList.add(detailDadanFragment);
        fragmentList.add(zijinWebViewFragment);
        fragmentList.add(bizhongWebViewFragment);
        fragmentList.add(chatContainerFragment);
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, MainCointabTitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();


        //动态设置实时讨论的tabItem

        TabLayout.Tab tab = tabLayout.getTabAt(4);
        tab.setCustomView(createChatTabView());

        chatContainerFragment.getGroupUserCount(new Action1<String>() {
            @Override
            public void call(String s) {
                if (chatTabSubTitle != null) chatTabSubTitle.setText(s + "人");
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 4) {
                    if (chatTabBubble != null) {
                        chatTabBubble.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private TextView chatTabSubTitle;
    private TextView chatTabBubble;


    public View createChatTabView() {
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        view.setClipChildren(false);
        view.setClipToPadding(false);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText("实时讨论");
        chatTabSubTitle = (TextView) view.findViewById(R.id.tv_subTitle);
        chatTabBubble = (TextView) view.findViewById(R.id.tv_bubble);
        return view;
    }


    private void intOtherCoin() {
        otherZijinWebViewFragment = NestWebViewFragment.newInstance(getUrl(1), "otherZijin");
        otherBizhongWebViewFragment = NestWebViewFragment.newInstance(getUrl(2), "otherZijin");


        if (adapter!=null) {
            adapter.clearAll();
        }
        viewPager.removeAllViews();
        viewPager.setAdapter(null);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(otherZijinWebViewFragment);
        fragmentList.add(otherBizhongWebViewFragment);
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, otherCointabTitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }


    @OnClick({R.id.icon_back, R.id.icon_share, R.id.bg_guide})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icon_back:
                backStack();
                break;
            case R.id.icon_share:
                Bitmap bitmap = BitmapUtil.snapActivityToBitmap(this);
                BitmapFactory.decodeResource(getResources(), R.mipmap.kline_share_qr3);
                Bitmap heYueShareQRBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.kline_share_qr3);
                SharePicDialog sharePicDialog = SharePicDialog.newInstance(bitmap, heYueShareQRBitmap);
                sharePicDialog.show(getSupportFragmentManager(), "sharePicDialog");
                break;
            case R.id.bg_guide:
                imGuide.setVisibility(View.GONE);
                mImmersionBar.statusBarColor(R.color.color_white).init();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private String getUrl(int index) {

        if (1==index) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(UrlConfig.MARKET_DETAIL_URl);
            buffer.append(mCoinInfo.getSymbol());
            buffer.append("?base=");
            buffer.append(mCoinInfo.getSymbol());
            buffer.append("&");
            buffer.append("quote=");
            buffer.append(mCoinInfo.getQuote());
            buffer.append("&");
            buffer.append("exchange=");
            buffer.append(mCoinInfo.getExchange());
            buffer.append("&");
            buffer.append("index=");
            buffer.append(index);
            buffer.append("&hideMenu=true");
            return buffer.toString();
        }else {
            String url=UrlConfig.H5_BASE_URL+"chainsearchprojectdetail/"+mCoinInfo.getSymbol();
            return url;
        }
    }


    private void showKlineFragment() {
        //图标状态
        android.support.v4.app.FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        if (timeLineChartfragment == null) {
            timeLineChartfragment = KLineFragment.newInstance(mCoinInfo);
            timeLineChartfragment.setCoinInfoListener(this);
            fm.add(R.id.fragment_container, timeLineChartfragment);
        } else {
            fm.show(timeLineChartfragment);
        }
        fm.commit();
    }

    private void updateKlineFragment() {
        //图标状态
        android.support.v4.app.FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        timeLineChartfragment = KLineFragment.newInstance(mCoinInfo);
        fm.add(R.id.fragment_container, timeLineChartfragment);
        fm.commit();
    }

    @Override
    public void onCoinInfoChange(CoinInfo coinInfo) {
        mCoinInfo = coinInfo;
        tvTitle.setText(coinInfo.getSymbol() + "/" + coinInfo.getQuote());
        if (MainCoinFlag) {
            depthFragment.setCoinPair(coinInfo);
            detailDadanFragment.setCoinPair(coinInfo);
            zijinWebViewFragment.reload(getUrl(1));
            bizhongWebViewFragment.reload(getUrl(2));
        } else {
            otherZijinWebViewFragment.reload(getUrl(1));
            otherBizhongWebViewFragment.reload(getUrl(2));
        }
    }

    @Override
    public void onCoinPriceChange(double price) {
        tvTitle.setText(mCoinInfo.getSymbol() + "/" + mCoinInfo.getQuote() + "(" + String.format("%.2f", price) + ")");
    }

    @Override
    public void onBackPressed() {
        backStack();
    }

    private void backStack() {
        if (MainCoinFlag) {
            backMainStack();
        } else {
            finish();
        }
    }

    private void backMainStack() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == adapter.getCount() - 1) {
            appBarGotoTop();
            viewPager.setCurrentItem(0);
        } else {
            finish();
        }
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
