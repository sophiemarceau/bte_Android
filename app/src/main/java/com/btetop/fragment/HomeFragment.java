package com.btetop.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.btetop.R;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.activity.KLineActivity;
import com.btetop.activity.MessageActivity;
import com.btetop.activity.MiningActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.HomeGouRecycleviewAdapter;
import com.btetop.adapter.HomeNewsAdapter;
import com.btetop.adapter.HomeProductAdapter;
import com.btetop.adapter.HomeRecycleviewAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BannerInfo;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.Course;
import com.btetop.bean.HomeHeadInfoBean;
import com.btetop.bean.HomeLatestBean;
import com.btetop.bean.HomeMarketNewsBean.HomeMarketNewsData;
import com.btetop.bean.MiningBean;
import com.btetop.bean.ProductHomeBean;
import com.btetop.bean.UnreadMesBean;
import com.btetop.bean.UserCheck;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.ShareDialog;
import com.btetop.dialog.SharePicDialog;
import com.btetop.helper.PagerSnapHelper;
import com.btetop.manager.SmoothLinearLayoutManager;
import com.btetop.message.RouteMessage;
import com.btetop.monitor.M;
import com.btetop.net.BteTopService;
import com.btetop.service.DogsService;
import com.btetop.service.MiningService;
import com.btetop.service.UserService;
import com.btetop.utils.AppUtils;
import com.btetop.utils.CommonUtils;
import com.btetop.utils.GlideImageLoader;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.ToastUtils;
import com.btetop.utils.TypeFaceUtils;
import com.btetop.widget.MyListView;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;


/**
 * Created by Administrator on 2018/3/21.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static HomeFragment _instance = null;
    private String shareUrl;
    private ImageView mShare;

    private RecyclerView mHomeList;
    private RecyclerView home_fragment_gou_list;
    private HomeRecycleviewAdapter homeListAdapter;
    private HomeGouRecycleviewAdapter homeGouRecycleviewAdapter;

    private HomeNewsAdapter newsAdapter;
    private RecyclerView mRecyclerView;
    private SmoothLinearLayoutManager layoutManager;
    private CoinInfo coinInfo;

    private ArrayList<CoinInfo> latestsLists;

    private ScrollView homeScroll;

    private String reportId;
    private String analysisTitle;
    private String description;
    private RefreshLayout refreshLayout;
    private ImageView iv_home_three_sign, iv_home_three_invite, iv_home_three_get_score, iv_home_mining;

    private MyListView homeCelueListView;
    private HomeProductAdapter homeProductAdapter;
    private RelativeLayout ceLueMore, mark_data_search, ll_fenxi;
    private boolean isSignOrNo;
    private LinearLayout ll_ce_lue;

    private int intervalTime = 3 * 1000;
    private boolean mIsFirstHomeLatests = true;
    TextView tvAirNum;
    TextView tvAirNumValue;
    TextView tvTradeNum;
    TextView tvTradeNumValue;
    TextView tvTradeNumValue2;
    TextView tvFundFlow;
    TextView tvFundFlowValue2;
    TextView tvFundFlowValue;
    TextView tv_fenxi_title;
    TextView tv_fenxi_title_time, iv_menu_left_point;
    Banner banner;
    @BindView(R.id.bannerContainer)
    LinearLayout bannerContainer;
    ImageView menu_left;
    RelativeLayout ll_head_left, ll_head_middle, ll_head_right;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            endLoop();
            initHomeLatests(false, mIsFirstHomeLatests);
        }
    };

    public static HomeFragment instance() {
        _instance = new HomeFragment();
        return _instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView(View view) {
        mShare = view.findViewById(R.id.share);
        mShare.setOnClickListener(this);


        refreshLayout = view.findViewById(R.id.home_page_refresh);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setNoMoreData(false);
                        initData();
                        refreshlayout.finishRefresh();
                    }
                }, 2000);

            }
        });
        mHomeList = view.findViewById(R.id.home_fragment_listView);
        home_fragment_gou_list = view.findViewById(R.id.home_fragment_gou_list);

        mRecyclerView = view.findViewById(R.id.market_recyclerView);
        layoutManager = new SmoothLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
            }
        }, 2000, 5000, TimeUnit.MILLISECONDS);

        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);


        homeScroll = view.findViewById(R.id.home_scrollview);

        mark_data_search = view.findViewById(R.id.mark_data_search);
        mark_data_search.setOnClickListener(this);

        ll_ce_lue = view.findViewById(R.id.ll_ce_lue);

        iv_home_three_sign = view.findViewById(R.id.iv_home_three_sign);
        iv_home_mining = view.findViewById(R.id.iv_home_mining);
        iv_home_three_invite = view.findViewById(R.id.iv_home_three_invite);
        iv_home_three_get_score = view.findViewById(R.id.iv_home_three_get_score);
        iv_home_three_sign.setOnClickListener(this);
        iv_home_mining.setOnClickListener(this);
        iv_home_three_invite.setOnClickListener(this);
        iv_home_three_get_score.setOnClickListener(this);
        //设置默认图片
        Glide.with(getContext()).load(R.mipmap.iv_home_three_sign).into(iv_home_three_sign);
        homeCelueListView = view.findViewById(R.id.home_ce_lue_listView);

        ceLueMore = view.findViewById(R.id.ce_lue_more);
        ceLueMore.setOnClickListener(this);
        ll_fenxi = view.findViewById(R.id.ll_fenxi);
        ll_fenxi.setOnClickListener(this);
        banner = (Banner) view.findViewById(R.id.banner);

        tvAirNum = view.findViewById(R.id.tv_air_num);
        tvAirNumValue = view.findViewById(R.id.tv_air_num_value);
        tvTradeNum = view.findViewById(R.id.tv_trade_num);
        tvTradeNumValue = view.findViewById(R.id.tv_trade_num_value);
        tvTradeNumValue2 = view.findViewById(R.id.tv_trade_num_value2);
        tvFundFlow = view.findViewById(R.id.tv_fund_flow);
        tvFundFlowValue2 = view.findViewById(R.id.tv_fund_flow_value2);
        tvFundFlowValue = view.findViewById(R.id.tv_fund_flow_value);
        tv_fenxi_title = view.findViewById(R.id.tv_fenxi_title);
        tv_fenxi_title_time = view.findViewById(R.id.tv_fenxi_title_time);
        ll_head_left = view.findViewById(R.id.ll_head_left);
        ll_head_middle = view.findViewById(R.id.ll_head_middle);
        ll_head_right = view.findViewById(R.id.ll_head_right);

        menu_left = view.findViewById(R.id.menu_left);
        iv_menu_left_point = view.findViewById(R.id.iv_menu_left_point);
        menu_left.setOnClickListener(this);
        menu_left.setVisibility(View.VISIBLE);
        menu_left.setImageResource(R.mipmap.xiaoxi);

        ll_head_left.setOnClickListener(this);
        ll_head_middle.setOnClickListener(this);
        ll_head_right.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        //设定homeFragment的Tag
        setFragmentNameTag("homeFragment");

        //fixme 解决性能问题 拆成两个接口
        initHomeLatests(true, mIsFirstHomeLatests);
        initHead();


        DogsService.loadSetting(new Action1() {
            @Override
            public void call(Object o) {
                initDogsInfo();
            }
        });

        initHomeCeLue();
        initHomeNews();
        checkUserSingOrNo();
        initBanner();
        initLeftMessage();
    }


    private void initHead() {
        BteTopService.getHomeHeadInfo()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<HomeHeadInfoBean>>() {
                    @Override
                    public void call(BaseBean<HomeHeadInfoBean> homeHeadInfoBeanBaseBean) {
                        HomeHeadInfoBean data = homeHeadInfoBeanBaseBean.getData();
                        if (data != null) {

                            tvAirNumValue.setText(data.getAirIndex() + "");
                            tvTradeNumValue.setText(CommonUtils.HomePriceConvert(data.getAmount()));
                            tvTradeNumValue2.setText(CommonUtils.HomeGetWord(data.getAmount()) + "");
                            if (data.getNetAmount() > 0) {
                                tvFundFlowValue.setText(CommonUtils.HomePriceConvert(data.getNetAmount()));
                                tvFundFlowValue2.setText(CommonUtils.HomeGetWord(data.getNetAmount()) + "");
                            } else {
                                tvFundFlowValue.setText(CommonUtils.HomeLosePriceConvert(data.getNetAmount()));
                                tvFundFlowValue2.setText(CommonUtils.HomeGetLoseWord(data.getNetAmount()) + "");
                            }
                            TypeFaceUtils.setTVTypeFace(getActivity(), tvAirNumValue);
                            TypeFaceUtils.setTVTypeFace(getActivity(), tvTradeNumValue);
                            TypeFaceUtils.setTVTypeFace(getActivity(), tvFundFlowValue);
                            TypeFaceUtils.setTVTypeFace(getActivity(), tvTradeNumValue2);
                            TypeFaceUtils.setTVTypeFace(getActivity(), tvFundFlowValue2);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initBanner() {
        final ArrayList<String> objects = new ArrayList<>();
        BteTopService.getBannerInfo()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<BannerInfo>>>() {
                    @Override
                    public void call(final BaseBean<List<BannerInfo>> baseBean) {
                        if (baseBean != null
                                && baseBean.getData() != null
                                && baseBean.getData().size() > 0) {

                            bannerContainer.setVisibility(View.VISIBLE);
                            for (int i = 0; i < baseBean.getData().size(); i++) {
                                objects.add(baseBean.getData().get(i).getUrl());
                            }

                            banner.setImages(objects).setImageLoader(new GlideImageLoader()).start();
                            //banner点击事件
                            banner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(int position) {
                                    String url = baseBean.getData().get(position).getTarget() + "";
                                    Intent intent = new Intent(getContext(), CommonWebViewActivity.class);
                                    intent.putExtra("url", url);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            //长度=0 或者 数据为空则隐藏banner
                            bannerContainer.setVisibility(View.GONE);
                            return;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e("HomeFragment-initBanner异常");
                    }
                });

    }

    private void initLeftMessage() {
        BteTopService.UnReadMessage()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<UnreadMesBean>() {
                    @Override
                    public void call(UnreadMesBean baseBean) {
                        if (baseBean != null && baseBean.getData() != null) {
                            iv_menu_left_point.setVisibility(View.VISIBLE);
                            iv_menu_left_point.setText(baseBean.getData());
                        } else {
                            iv_menu_left_point.setVisibility(View.GONE);
                        }
                    }

                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e("HomeFragment-initLeftMessage异常");
                    }
                });

    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }

    @Override
    public void onResume() {

        super.onResume();
        checkUserSingOrNo();
        //空为未登录
        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            ll_ce_lue.setVisibility(View.GONE);
        } else {
            ll_ce_lue.setVisibility(View.VISIBLE);
        }

        startLoop();
    }

    @Override
    public void onPause() {
        super.onPause();
        endLoop();
    }

    private void initDogsInfo() {
        List<Course> dogsBean = DogsService.getDogsBean();
        if (dogsBean != null) {
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            home_fragment_gou_list.setLayoutManager(layoutManager);
            homeGouRecycleviewAdapter = new HomeGouRecycleviewAdapter(getActivity(), dogsBean, new HomeGouRecycleviewAdapter.GouItemClick() {
                @Override
                public void GouItemClick(String type) {
                    switch (type) {
                        case Constant.EVENT_LZ_DOG:
                            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_LU_DOG_PAGE));
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_LZ_DOG, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.GOTO_EVENT_LZ_DOG);
                            break;
                        case Constant.EVENT_BAND_DOG:
                            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_BO_DOG_PAGE));
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_BAND_DOG, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.GOTO_EVENT_BAND_DOG);
                            break;
                        case Constant.EVENT_FUTURE_DOG:
                            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_HEYUE_DOG_PAGE));
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_FUTURE_DOG, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.GOTO_EVENT_FUTURE_DOG);
                            break;
                        case Constant.EVENT_RESEARCH_DOG:
                            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_RESEARCH_DOG_PAGE));
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_RESEARCH_DOG, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.GOTO_EVENT_RESEARCH_DOG);
                            break;
                        case Constant.EVENT_STRATEGY_DOG:
                            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_DINGPAN_DOG_PAGE));
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_STRATEGY_DOG, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.GOTO_EVENT_STRATEGY_DOG);
                            break;
                        case Constant.EVENT_LIAN_CHACHA:
                            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_LIAN_CHACHA, "", "", "");
                            M.monitor().onEvent(getContext(), Constant.EVENT_LIAN_CHACHA);
                            Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                            intent.putExtra("url", UrlConfig.CHAINSEARCH_HOME_URL);
                            startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });
            home_fragment_gou_list.setAdapter(homeGouRecycleviewAdapter);
        }
    }

    /**
     * 用户是否签到
     */
    private void checkUserSingOrNo() {
        BteTopService.checkUserSingOrNo()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean == null || baseBean.getData() == null) isSignOrNo = false;
                        else isSignOrNo = (boolean) baseBean.getData();
                        if (isSignOrNo) {
                            Glide.with(getContext()).load(R.mipmap.iv_home_three_sign_yes).into(iv_home_three_sign);
                        } else {
                            Glide.with(getContext()).load(R.mipmap.iv_home_three_sign).into(iv_home_three_sign);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Glide.with(getContext()).load(R.mipmap.iv_home_three_sign).into(iv_home_three_sign);
                    }
                });
    }

    /**
     * 用户签到
     */
    private void doUserSign() {
        BteTopService.doUserSign("android")
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<UserCheck>>() {
                    @Override
                    public void call(BaseBean<UserCheck> baseBean) {

                        checkUserSingOrNo();
                        if ("0000".equals(baseBean.getCode())) {

                            if (baseBean.getData() != null
                                    && baseBean.getData().getPoint() > 0) {
                                ToastUtils.showShortToast(getContext(), baseBean.getMessage() + " 积分+" + baseBean.getData().getPoint());
                            } else {
                                ToastUtils.showShortToast(getContext(), baseBean.getMessage() + "");
                            }
                        } else if ("-1".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast(getContext(), baseBean.getMessage() + "");
                            startActivity(new Intent(getContext(), YZMLoginActivity.class));
                        } else if ("U013".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast(getContext(), baseBean.getMessage() + "");
                        } else {
                            ToastUtils.showShortToast(getContext(), baseBean.getMessage() + "");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    /**
     * 设置滚动回到顶部
     */

    public void scrollToUp() {
        homeScroll.smoothScrollTo(0, 0);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    /**
     * 获取最新的推荐列表
     *
     * @param isRefreshAnalysis
     * @param isFirstHomeLatests
     */
    private void initHomeLatests(final boolean isRefreshAnalysis, final boolean isFirstHomeLatests) {

        //1s刷新一次
        handler.sendEmptyMessageDelayed(0, intervalTime);

        BteTopService.getHomeLatests()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<HomeLatestBean.HomeLatestsData>>() {
                    @Override
                    public void call(BaseBean<HomeLatestBean.HomeLatestsData> latestsBean) {
                        latestsLists = new ArrayList<>();

                        if (latestsBean != null) {
                            String code = latestsBean.getCode();
                            String message = latestsBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    latestsLists = latestsBean.getData().getList();
//                                    addCurrencyMarketData(latestsLists, marketName);
                                    if (latestsLists != null && latestsLists.size() != 0) {
                                        mHomeList.setVisibility(View.VISIBLE);
                                        setHomeRecyclerViewData(latestsLists, isFirstHomeLatests);
                                        mIsFirstHomeLatests = false;
                                    } else {
                                        mHomeList.setVisibility(View.GONE);
                                    }

                                    if (isRefreshAnalysis) {
                                        showMarketAnalysis(latestsBean);
                                    }

                                } else {
                                    if (message != null) {
                                        mHomeList.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("HomeFragment-->首页数据失败");
                    }
                });
    }

    public void setHomeRecyclerViewData(final ArrayList<CoinInfo> latestsLists, boolean isFirstHomeLatests) {

        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mHomeList.setLayoutManager(layoutManager);
        homeListAdapter = new HomeRecycleviewAdapter(getActivity(), latestsLists, isFirstHomeLatests, new HomeRecycleviewAdapter.HomeRecycleviewItemClick() {
            @Override
            public void hangqingItemClick(int position) {
                coinInfo = latestsLists.get(position);
                String exchange = coinInfo.getExchange();
                String symbol = coinInfo.getSymbol();
                String quote = coinInfo.getQuote();
                switch (position) {
                    case 0:
                        M.monitor().onEvent(getContext(), Constant.COIN1);
                        break;
                    case 1:
                        M.monitor().onEvent(getContext(), Constant.COIN2);
                        break;
                    case 2:
                        M.monitor().onEvent(getContext(), Constant.COIN3);
                        break;
                    case 3:
                        M.monitor().onEvent(getContext(), Constant.COIN4);
                        break;
                }
                Intent intent = new Intent(getActivity(), KLineActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("coinInfo", coinInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mHomeList.setAdapter(homeListAdapter);
    }

    /**
     * 显示市场分析
     *
     * @param latestsBean
     */

    private void showMarketAnalysis(BaseBean<HomeLatestBean.HomeLatestsData> latestsBean) {
        if (null != latestsBean) {
            if (null != latestsBean.getData()) {
                if (null != latestsBean.getData().getReport()) {
                    reportId = latestsBean.getData().getReport().getId();
                    SPUtils.put("HomeReportId", reportId);
                    analysisTitle = latestsBean.getData().getReport().getTitle();
                    description = latestsBean.getData().getReport().getSummary();
                    String date = latestsBean.getData().getReport().getDate();
                    String pvLiang = latestsBean.getData().getReport().getPv();
                    String marketTag = latestsBean.getData().getReport().getTag();

                    if (!TextUtils.isEmpty(analysisTitle) && null != analysisTitle) {
                        tv_fenxi_title.setText(analysisTitle);
                        TypeFaceUtils.setTVTypeFace(getActivity(), tv_fenxi_title);
                    }
                    if (!TextUtils.isEmpty(date) && null != date) {
                        tv_fenxi_title_time.setText(date);
                    }
                }
            }
        }
    }

    /**
     * 获取首页显示的策略信息
     */
    private void initHomeCeLue() {
        BteTopService.getHomeProduct()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<ProductHomeBean.ProductHomeData>>>() {
                    @Override
                    public void call(BaseBean<List<ProductHomeBean.ProductHomeData>> productBean) {
                        if (productBean != null) {
                            String code = productBean.getCode();
                            String message = productBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    final List<ProductHomeBean.ProductHomeData> productHomeList = productBean.getData();
                                    if (productHomeList.size() > 0) {
                                        homeCelueListView.setVisibility(View.VISIBLE);
                                        homeProductAdapter = new HomeProductAdapter(getActivity(), productHomeList);
                                        homeCelueListView.setAdapter(homeProductAdapter);
                                        homeCelueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String productId = String.valueOf(productHomeList.get(position).getId());
                                                String url = UrlConfig.CE_LUE_DETAILS_URL + productId;
                                                Bundle bundle = new Bundle();
                                                bundle.putString("url", url);
                                                RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_URL, bundle);
                                                EventBusUtils.sendEvent(event);
                                            }
                                        });
                                    } else {
                                        homeCelueListView.setVisibility(View.GONE);
                                    }

                                } else {
                                    if (message != null) {
                                        homeCelueListView.setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        homeCelueListView.setVisibility(View.GONE);
                        Log.e("ProductHomeBean", throwable.toString());
                        System.out.println("HomeFragment-->获取首页策略列表---链接服务器失败");
                    }
                });
    }

    /**
     * 获取首页新闻
     */
    private void initHomeNews() {
        BteTopService.getNewsLatest()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<HomeMarketNewsData>>>() {
                    @Override
                    public void call(BaseBean<List<HomeMarketNewsData>> newsBean) {
                        if (newsBean != null) {
                            String code = newsBean.getCode();
                            String message = newsBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    List<HomeMarketNewsData> data = newsBean.getData();
                                    if (data.size() != 0) {
                                        mRecyclerView.setVisibility(View.VISIBLE);
                                        newsAdapter = new HomeNewsAdapter(getActivity(), data);
                                        mRecyclerView.setAdapter(newsAdapter);
                                        mRecyclerView.scrollToPosition(data.size() * 10);
                                        newsAdapter.notifyDataSetChanged();
                                    } else {
                                        mRecyclerView.setVisibility(View.GONE);

                                    }
                                } else {
                                    if (message != null) {

                                        mRecyclerView.setVisibility(View.GONE);

                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mRecyclerView.setVisibility(View.GONE);
                        Log.e("HomeMarketNewsBean", throwable.toString());
                        System.out.println("HomeFragment-->获取10条新闻---链接服务器失败");
                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_fenxi:
                String url = UrlConfig.MARKET_REPORT_URL + "/" + reportId;
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                RouteMessage event = new RouteMessage(RouteMessage.MESSAGE_SHOW_URL, bundle);
                EventBusUtils.sendEvent(event);
                M.monitor().onEvent(getContext(), Constant.HOME_MARKET_ANALYZE);
                break;
            case R.id.mark_data_search:
                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_MARKET_DATA));
                M.monitor().onEvent(getContext(), Constant.HOME_MARKET_DATA);
                break;
            case R.id.ce_lue_more:
//                EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_CE_LUE_PAGE));
//                M.monitor().onEvent(getContext(), Constant.HOME_CE_LVE_MORE);
//                M.monitor().onEvent(getContext(), Constant.HOME_CE_LVE);

                Intent intent = new Intent(getActivity(), CommonWebViewActivity.class);
                intent.putExtra("url", UrlConfig.CE_LUE_URL);
                startActivity(intent);
                break;

            case R.id.iv_home_three_sign:
                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    startActivity(new Intent(getContext(), YZMLoginActivity.class));
                } else if (!isSignOrNo && !TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    doUserSign();
                } else {
                    ToastUtils.showShortToast(getContext(), "已签到");
                }
                break;

            case R.id.iv_home_three_invite:
                SharePicDialog sharePicDialog = SharePicDialog.newInstance(getContext());
                sharePicDialog.setTargetFragment(HomeFragment.this, 0);
                sharePicDialog.show(getFragmentManager(), "sharePicDialog");
//                ShareMiningPicDialog sharePicDialog = ShareMiningPicDialog.newInstance(getContext(), new ShareMiningPicDialog.doShare() {
//                    @Override
//                    public void doShare() {
//
//                    }
//                });
//                sharePicDialog.setTargetFragment(HomeFragment.this, 0);
//                sharePicDialog.show(getFragmentManager(), "homeFragmentShare");

                M.monitor().onEvent(getContext(), Constant.HOME_BUTTUN_INVITATION);
                break;

            case R.id.iv_home_three_get_score:
                if (!TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_GET_SCORE));
                    M.monitor().onEvent(getContext(), Constant.HOME_BUTTON_GET_POINT);
                } else {
                    startActivity(new Intent(getActivity(), YZMLoginActivity.class));
                }
                break;
            case R.id.share:
                ShareDialog.getInstance().showShareDialog(getActivity(), UrlConfig.DEFAULT_SHARE_URL);
//                ShareMiningPicDialog sharePicDialog2 = ShareMiningPicDialog.newInstance(getContext(), new ShareMiningPicDialog.doShare() {
//                    @Override
//                    public void doShare() {
//
//                    }
//                });
//                sharePicDialog2.setTargetFragment(HomeFragment.this, 0);
//                sharePicDialog2.show(getFragmentManager(), "homeFragmentShare");


                break;


            case R.id.ll_head_left:
                Intent airindex = new Intent(getContext(), CommonWebViewActivity.class);
                airindex.putExtra("url", UrlConfig.HOME_INDEX_HEADER_URL_AIRINDEX);
                airindex.putExtra("showShare", false);
                startActivity(airindex);
                break;
            case R.id.ll_head_middle:
                Intent amount = new Intent(getContext(), CommonWebViewActivity.class);
                amount.putExtra("url", UrlConfig.HOME_INDEX_HEADER_URL_AMOUNT);
                startActivity(amount);
                break;
            case R.id.ll_head_right:
                Intent netflow = new Intent(getContext(), CommonWebViewActivity.class);
                netflow.putExtra("url", UrlConfig.HOME_INDEX_HEADER_URL_NETFLOW);
                startActivity(netflow);
                break;
            case R.id.menu_left:
                Intent message = new Intent(getContext(), MessageActivity.class);
                startActivity(message);
                break;
            case R.id.iv_home_mining:
                M.monitor().onEvent(getContext(), Constant.MINING_ICON);
                if (!TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    MiningService.loadMiningInfo(new MiningService.loadMiningInfo() {
                        @Override
                        public void success(MiningBean data) {
                        }

                        @Override
                        public void open() {
                            Intent mining = new Intent(getActivity(), MiningActivity.class);
                            startActivity(mining);
                        }

                        @Override
                        public void noOpen(String inviteCode) {
                            //数据为空说明是是第一次 进入挖矿
                            Intent mining = new Intent(getContext(), CommonWebViewActivity.class);
                            mining.putExtra("url", UrlConfig.MINING_INDEX_URL + "index?inviteCode=" + inviteCode);
                            startActivity(mining);
                        }

                    });
                } else {
                    startActivity(new Intent(getActivity(), YZMLoginActivity.class));
                }


                break;

        }
    }

    private void startLoop() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, intervalTime);
    }

    private void endLoop() {
        handler.removeMessages(0);

    }
}
