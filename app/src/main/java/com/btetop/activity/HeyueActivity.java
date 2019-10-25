package com.btetop.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import com.btetop.bean.CoinPairBean;
import com.btetop.dialog.SharePicDialog;
import com.btetop.fragment.BaochangFragment;
import com.btetop.fragment.ChatContainerFragment;
import com.btetop.fragment.DadanFragment;
import com.btetop.fragment.DepthListFragment;
import com.btetop.fragment.HeyueFragment;
import com.btetop.fragment.WalletTransactionFragment;
import com.btetop.fragment.ZijinFragment;
import com.btetop.utils.BitmapUtil;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;


public class HeyueActivity extends BaseActivity implements HeyueFragment.CoinInfoListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab_main)
    TabLayout tabLayout;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.iv_go_top)
    ImageView iv_go_top;

    @BindView(R.id.cl_contain)
    CoordinatorLayout cl_contain;

    @BindView(R.id.appbar)
    AppBarLayout appbar;

    String HeYueName = "FUTURE";

    private String[] mtitles = new String[]{"异动", "深度", "爆仓", "资金","钱包","讨论"};

    private DadanFragment dadanFragment;
    private DepthListFragment shenduFragment;
    private BaochangFragment baocangFragment;
    private ZijinFragment zijinFragment;
    private WalletTransactionFragment walletTransactionFragment;
    private ChatContainerFragment chatContainerFragment;


    private TabFragmentAdapter adapter;
    private CoinPairBean coinPairBean;
    private HeyueFragment headerFragment;


    @Override
    public int intiLayout() {
        return R.layout.activity_heyue;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

        Bundle bundle = getIntent().getExtras();
        String base = "";
        if (bundle != null) {
            base = bundle.getString("base");
        }

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 10, 10);
            }
        });

        //合约头部K线
        headerFragment = HeyueFragment.newInstance(base);
        headerFragment.setCoinInfoListener(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, headerFragment)
                .commit();

        //下面的tablayout的fragment
        dadanFragment = DadanFragment.newInstance();
        shenduFragment = DepthListFragment.newInstance();
        baocangFragment = BaochangFragment.newInstance();
        zijinFragment = ZijinFragment.newInstance();

        walletTransactionFragment = WalletTransactionFragment.newInstance();

        //聊天室的fragment
        chatContainerFragment = ChatContainerFragment.newInstance();
        chatContainerFragment.setGroupName(HeYueName);




        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(dadanFragment);
        fragmentList.add(shenduFragment);
        fragmentList.add(baocangFragment);
        fragmentList.add(zijinFragment);
        fragmentList.add(walletTransactionFragment);
        fragmentList.add(chatContainerFragment);

        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, mtitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);




        //动态设置实时讨论的tabItem

        TabLayout.Tab chatTab = tabLayout.getTabAt(5);
        chatTab.setCustomView(createChatTabView());

        chatContainerFragment.getGroupUserCount(new Action1<String>() {
            @Override
            public void call(String s) {
                if(chatTabSubTitle!=null) chatTabSubTitle.setText(s+"人");
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 5) {
                    if(chatTabBubble!=null){
                        chatTabBubble.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        iv_go_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarGotoTop();
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Bundle bundle = getIntent().getExtras();
        String base = "";
        if (bundle != null) {
            base = bundle.getString("base");
        }

        headerFragment.setLastSelectedIndex(base);

    }

    private TextView chatTabSubTitle;
    private TextView chatTabBubble;


    public View createChatTabView(){
        RelativeLayout view = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        view.setClipChildren(false);
        view.setClipToPadding(false);
        TextView tv= (TextView) view.findViewById(R.id.tv_title);
        tv.setText("讨论");
        chatTabSubTitle = (TextView) view.findViewById(R.id.tv_subTitle);
        chatTabBubble = (TextView) view.findViewById(R.id.tv_bubble);
        return view;
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
    public boolean statisticsActivity() {
        return true;
    }

    @Override
    public void onCoinPairChange(CoinPairBean pairBean) {
        coinPairBean = pairBean;
        tvTitle.setText(pairBean.getName());
        dadanFragment.setCoinPair(pairBean);
        shenduFragment.setCoinPair(pairBean);
        baocangFragment.setCoinPair(pairBean);
        zijinFragment.setCoinPair(pairBean);
        walletTransactionFragment.setCoinPair(pairBean);

    }

    @OnClick({R.id.icon_share, R.id.icon_back})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.icon_share:
                //使用合约狗文案
                Bitmap bitmap = BitmapUtil.snapActivityToBitmap(this);
                BitmapFactory.decodeResource(getResources(), R.mipmap.kline_share_qr3);
                Bitmap heYueShareQRBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.kline_share_qr3);
                SharePicDialog sharePicDialog = SharePicDialog.newInstance(bitmap, heYueShareQRBitmap);
                sharePicDialog.show(getSupportFragmentManager(), "sharePicDialog");

                break;
            case R.id.icon_back:
                backStack();
                break;
        }
    }

    @Override
    public void onIsHeyueUserChange(boolean b) {
        if (b) {
            viewPager.setVisibility(View.VISIBLE);
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
        } else {
            viewPager.setVisibility(View.GONE);
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
            layoutParams.setScrollFlags(0);
        }
    }

    @Override
    public void onPriceChange(double price) {
        if(coinPairBean!=null) {
            String s = DoubleUtil.formatDataCompare10(price);
            tvTitle.setText(coinPairBean.getName()+"("+s+")");
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

    private void backStack(){
        int currentItem = viewPager.getCurrentItem();
        if (currentItem==adapter.getCount()-1) {
            appBarGotoTop();
            viewPager.setCurrentItem(0);
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        backStack();
    }
}
