package com.btetop.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.btetop.R;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.GridItem;
import com.btetop.bean.KlineBean;
import com.btetop.bean.UserInfo;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.CoinPairPopup;
import com.btetop.dialog.CommonGridPopup;
import com.btetop.net.BteTopService;
import com.btetop.service.DogsService;
import com.btetop.service.FutureDataService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.btetop.widget.PopDialogFragment;
import com.guoziwei.klinelib.chart.CoupleChartGestureListener;
import com.guoziwei.klinelib.chart.KLineView;
import com.guoziwei.klinelib.model.KLineFullData;
import com.guoziwei.klinelib.util.DateUtils;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;


/**
 * 分时Fragment
 */
public class HeyueFragment extends BaseFragment implements CoupleChartGestureListener.InfoListener, FutureDataService.DataListener {
    private static final String TAG = "TimeLineChartFragment";
    private static int lastSelectedIndex = 0;

    private String base;

    //header
    @BindView(R.id.tv_info_usd)
    TextView mTvMainPrice;
    @BindView(R.id.tv_info_status)
    TextView mTvChange;
    @BindView(R.id.tv_info_rmb)
    TextView mTvRmb;
    @BindView(R.id.tv_24_vol_value)
    TextView mTvVol;
    @BindView(R.id.tv_24_amount_value)
    TextView mTvAmount;

    //k线的选择器
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_time)
    ImageView ivTime;

    @BindView(R.id.rl_factor)
    RelativeLayout rlFactor;
    @BindView(R.id.tv_factor)
    TextView tvFactor;
    @BindView(R.id.iv_factor)
    ImageView ivFactor;


    @BindView(R.id.rl_curve)
    RelativeLayout rlCurve;
    @BindView(R.id.tv_curve)
    TextView tvCurve;
    @BindView(R.id.iv_curve)
    ImageView ivCurce;


    @BindView(R.id.rl_major)
    RelativeLayout rlMajor;
    @BindView(R.id.tv_major)
    TextView tvMajor;
    @BindView(R.id.iv_major)
    ImageView ivMajor;

    //fix me later
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    @BindView(R.id.pairContainer)
    RelativeLayout rlPair;
    @BindView(R.id.select_pair_exchange)
    TextView tvPairExchange;
    @BindView(R.id.pair_pair)
    TextView tvPairPair;
    @BindView(R.id.iv_pair)
    ImageView ivPair;


    private CommonGridPopup settingPopp;

    //k线头部的指标高开低收
    @BindView(R.id.tv_price_time)
    TextView mTvTime;
    @BindView(R.id.tv_time_line_open_price)
    TextView mTvOpenPrice;
    @BindView(R.id.tv_time_line_high_value)
    TextView tv_time_line_high_value;
    @BindView(R.id.tv_time_line_low_value)
    TextView tv_time_line_low_value;
    @BindView(R.id.tv_time_line_end_value)
    TextView tv_time_line_end_value;


    //遮罩层
    @BindView(R.id.tv_btn_heyue_above)
    TextView tv_btn_heyue_above;
    @BindView(R.id.hy_btn_below2)
    TextView hy_btn_below2;
    @BindView(R.id.bg_heyue_shadow)
    RelativeLayout rlShadow;

    //k线图
    @BindView(R.id.kline)
    KLineView mKlineView;


    //K线获取服务
    FutureDataService dataService;


    //K线基本设置
    KlineSetting klineSetting;
    List<CoinPairBean> pairList = new ArrayList<>();

    PopDialogFragment popDialogFragment;



    //类型,取值分钟 1m 5m 15m 30m 小时 1h 4h 天1d 周1w 月1mo,建议先限制在1d以下
    private String time_index[] = {"5分", "15分", "30分", "1小时"};
    private String factor_index[] = {"成交分布", "压力线", "MA", "EMA", "BOLL", "SAR",};
    private String curve_index[] = {"MACD", "KDJ", "RSI"};
    //设置选择器监听
    private String major_index[] = {"主力意图", "关闭"};
    private String setting_index[] = {"获取积分", "开启/暂停"};

    private CommonGridPopup timePopp;
    private CommonGridPopup facotrPop;
    private CommonGridPopup curvePop;
    private CommonGridPopup majorPop;
    private CoinPairPopup coinPairPopup;



    private CoinPairPopup.GridItemClickListen pairListener=new CoinPairPopup.GridItemClickListen() {

        @Override
        public void getGridItemInfo(int position, CoinPairBean gridItem) {

            //根据选择的index获取币对
            if (pairList == null) return;

            coinPairPopup.dismiss();
            tvPairExchange.setText(gridItem.getExchange());
            tvPairPair.setText(gridItem.getName());

            CoinPairBean coinPair = pairList.get(position);

            klineSetting.setCoinPair(coinPair);
            klineSetting.setInited(true);


            //将最后一次选择存下来，下次打开时使用这个作为默认
            lastSelectedIndex = position;
            refreshKlineData();
            if (coinInfoListener != null) {
                coinInfoListener.onCoinPairChange(coinPair);
            }

        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {
            rlPair.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvPairExchange.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            tvPairPair.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivPair.setVisibility(View.VISIBLE);
        }
    };
    //时间选择Spinner监听
    private CommonGridPopup.GridItemClickListen timeListener = new CommonGridPopup.GridItemClickListen() {

        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            timePopp.dismiss();
            tvTime.setText(time_index[position]);
            switch (position) {
                case 0:
                    klineSetting.setTimeType("5m");
                    refreshKlineData();
                    break;
                case 1:
                    klineSetting.setTimeType("15m");
                    refreshKlineData();
                    break;
                case 2:
                    klineSetting.setTimeType("30m");
                    refreshKlineData();
                    break;
                case 3:
                    klineSetting.setTimeType("1h");
                    refreshKlineData();
                    break;
            }
        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {
            rlTime.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivTime.setVisibility(View.VISIBLE);
        }
    };

    //MA EMA选择器监听
    private CommonGridPopup.GridItemClickListen factorListener=new CommonGridPopup.GridItemClickListen() {
        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            facotrPop.dismiss();
            tvFactor.setText(factor_index[position]);
            switch (position) {
                case 0:
                    klineSetting.setFactorType("volumeProfile");
                    break;
                case 1:
                    klineSetting.setFactorType("Resistance");
                    break;
                case 2:
                    klineSetting.setFactorType("MA");
                    break;
                case 3:
                    klineSetting.setFactorType("EMA");
                    break;
                case 4:
                    klineSetting.setFactorType("BOLL");
                    break;
                case 5:
                    klineSetting.setFactorType("SAR");
                    break;
            }
        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {
            rlFactor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvFactor.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivFactor.setVisibility(View.VISIBLE);
        }
    };

    //MACD KDJ RSI选择器监听
    private CommonGridPopup.GridItemClickListen curveListener=new CommonGridPopup.GridItemClickListen() {
    @Override
    public void getGridItemInfo(int position, GridItem gridItem) {

        curvePop.dismiss();
        tvCurve.setText(curve_index[position]);
        switch (position) {
            case 0: //MACD
                klineSetting.setCurveType("MACD");

                break;
            case 1: //KDJ
                klineSetting.setCurveType("KDJ");

                break;
            case 2: //RSI
                klineSetting.setCurveType("RSI");
                break;

        }
    }

    @Override
    public void downPopupDismiss() {

    }

    @Override
    public void popupDismiss() {
        rlCurve.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        tvCurve.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
        ivCurce.setVisibility(View.VISIBLE);
    }
};


    private CommonGridPopup.GridItemClickListen majorListener=new CommonGridPopup.GridItemClickListen() {
        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            majorPop.dismiss();
            tvMajor.setText(major_index[position]);
            switch (position) {
                case 0: //主力意图
                    klineSetting.setMajorType(true);
                    break;
                case 1: //关闭主力意图
                    klineSetting.setMajorType(false);
                    break;
            }
        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {
            rlMajor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvMajor.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivMajor.setVisibility(View.VISIBLE);
        }
    };

    //设置下拉菜单监听
    private CommonGridPopup.GridItemClickListen setttingListener = new CommonGridPopup.GridItemClickListen() {
        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            settingPopp.dismiss();
            int isFutureDogUser = 0;
            UserInfo userInfo = UserService.getCurrentUserInfo();
            if (userInfo != null) {
                isFutureDogUser = userInfo.getIsFutureDogUser();
            }
            switch (position) {
                case 0://获取积分
                    Intent intent = new Intent(getContext(), CommonWebViewActivity.class);
                    intent.putExtra("url", UrlConfig.HEYUE_DOG_GET_COIN_URL);
                    startActivity(intent);
                    break;
                case 1: //切换合约狗功能
                    if (userInfo != null) {
                        isFutureDogUser = userInfo.getIsFutureDogUser();
                    }
                    //未登录用户
                    if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                        startActivity(new Intent(getContext(), YZMLoginActivity.class));
                    } else {
                        if (checkJiFen()) {//积分足够，开启积分
                            switchFuture(isFutureDogUser == 0 ? 1 : 0);
                        } else {//积分不足,获取积分
                            if (isFutureDogUser == 1) {
                                switchFuture(0);//关闭
                            } else {
                                showPopGetCoin();
                            }
                        }
                    }
                    break;
            }

        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {

            rlSetting.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvSetting.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivSetting.setVisibility(View.VISIBLE);
        }
    };

    public HeyueFragment() {
        // Required empty public constructor
    }

    public static HeyueFragment newInstance(String base) {
        HeyueFragment fragment = new HeyueFragment();
        fragment.base = base;
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_heyue_dog;
    }


    @Override
    protected void initView(View view) {

        mKlineView.setDateFormat("HH:mm");
        mKlineView.setKlineInfoListener(this);


        //状态栏高度
        int height = 0;
        int resourceId = BteTopApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = BteTopApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        //50:header 100:价格到24小时量 20:小时，压力线 22：高开低收 8:底部阴影 44：tablayout的高度
        int heightExceptActionBar = ScreenUtils.getScreenHeight() - SizeUtils.dp2px(50 + 20 + 22 + 44 + 100 + 8);
        //遮罩层高度
        int heightShandow = ScreenUtils.getScreenHeight() - SizeUtils.dp2px(50 + 20 + 44 + 100 + 8);
        int klineHeight = heightExceptActionBar - height;
        int shadowHeight = heightShandow - height;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, klineHeight);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.kline_header_layout);
        mKlineView.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, shadowHeight);


        rlShadow.setLayoutParams(params);

    }


    @Override
    protected void initData() throws NullPointerException {

        klineSetting = new KlineSetting(mKlineView);
        initHYStatus();
        initTimeSpinner();
        initFactorSpinner();
        initCurveSpinner();
        initMajorSpinner();
        initSettingSpinner();

        LoadCoinPairData();

        dataService = FutureDataService.newInstance(this);
        dataService.setListener(this);

    }


    private void refreshKlineData() {
        if (klineSetting == null || !klineSetting.isInited()) return;
        if (klineSetting.getCoinPair() == null) return;
        String key = klineSetting.getCoinPair().getExchange() + "-"
                + klineSetting.getCoinPair().getBaseAsset() + "-"
                + klineSetting.getCoinPair().getQuoteAsset() + "-"
                + klineSetting.getTimeType();

        dataService.start(key);

    }

    @Override
    public void onData(String key, int mode, List<KLineFullData> data, KlineBean.TickerBean ticker) {

        if (ticker != null) {
            setTickerInfo(ticker);
            if (coinInfoListener != null) coinInfoListener.onPriceChange(ticker.getPrice());
        }

        switch (mode) {
            case 0:
                mKlineView.initDataKline(data);
                mKlineView.setLimitLine();
                mKlineView.LeftLoadMoreDragTrue();

                //取最后一条数据 设置高开低收数据
                setKLineInfo(data.size() - 1);
                break;
            case 1:
                mKlineView.addDataLast(data);
                break;
            case -1:
                //没有实现
                break;

        }
    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }

    private void initHYStatus() {

        UserInfo userInfo = UserService.getCurrentUserInfo();
        int userPoint = 0;
        int isFutureDogUser = 0;

        if (userInfo != null) {
            isFutureDogUser = userInfo.getIsFutureDogUser();
            userPoint = userInfo.getPoint();
        }

        //是合约狗用户
        if (isFutureDogUser == 1) {
            rlShadow.setVisibility(View.GONE);
//            viewPager.setVisibility(View.VISIBLE);
            if (coinInfoListener != null) {
                coinInfoListener.onIsHeyueUserChange(true);
            }
        } else {
            String heyueAboveString1 = com.btetop.utils.TextUtils.getColoredSpanned("深度挖掘合约交易数据，技术派合约玩家的必备工具。已有", "#626A75");
            String heyueAboveString2 = com.btetop.utils.TextUtils.getColoredSpanned(DogsService.getFutureDog_userCount(), "#4CABFE");
            String heyueAboveString3 = com.btetop.utils.TextUtils.getColoredSpanned("人在使用", "#626A75");
            tv_btn_heyue_above.setText(Html.fromHtml(heyueAboveString1 + heyueAboveString2 + heyueAboveString3));
            rlShadow.setVisibility(View.VISIBLE);
//            viewPager.setVisibility(View.GONE);
            if (coinInfoListener != null) {
                coinInfoListener.onIsHeyueUserChange(false);
            }
        }
        if (!TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            String heyeBelow1 = com.btetop.utils.TextUtils.getColoredSpanned("使用合约狗将每天消耗", "#626A75");
            String heyeBelow2 = com.btetop.utils.TextUtils.getColoredSpanned(DogsService.getFutureDog_point(), "#4CABFE");
            String heyeBelow3 = com.btetop.utils.TextUtils.getColoredSpanned("积分，当前您有", "#626A75");
            String heyeBelow4 = com.btetop.utils.TextUtils.getColoredSpanned("" + userPoint, "#4CABFE");
            String heyeBelow5 = com.btetop.utils.TextUtils.getColoredSpanned("积分", "#626A75");
            hy_btn_below2.setText(Html.fromHtml(heyeBelow1 + heyeBelow2 + heyeBelow3 + heyeBelow4 + heyeBelow5));
//            hy_btn_below2.setText("使用合约狗将每天消耗" + DogsService.getFutureDog_point() + "积分，当前您有" + userPoint + "积分。");
        } else {
            String heyeBelow1 = com.btetop.utils.TextUtils.getColoredSpanned("使用合约狗将每天消耗", "#626A75");
            String heyeBelow2 = com.btetop.utils.TextUtils.getColoredSpanned(DogsService.getFutureDog_point(), "#4CABFE");
            String heyeBelow3 = com.btetop.utils.TextUtils.getColoredSpanned("积分", "#626A75");
//            hy_btn_below2.setText("使用合约狗将每天消耗" + DogsService.getFutureDog_point() + "积分");
            hy_btn_below2.setText(Html.fromHtml(heyeBelow1 + heyeBelow2 + heyeBelow3));
        }


    }


    /**
     * 第一次加载数据 在交易所数据取出完成后
     */
    protected void LoadCoinPairData() {
        BteTopService.getCurrency()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<CoinPairBean>>>() {
                    @Override
                    public void call(BaseBean<List<CoinPairBean>> listBaseBean) {
                        if (listBaseBean != null && ("0000").equals(listBaseBean.getCode()) && listBaseBean.getData() != null && listBaseBean.getData().size() > 0) {
                            initPairSpinner(listBaseBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });


    }


    private void initPairSpinner(List<CoinPairBean> coinPairList) {
        if (coinPairList == null) return;
        this.pairList = coinPairList;
        calLastSelectIndex();

        coinPairPopup = new CoinPairPopup(rlPair, getActivity(), R.layout.grid_list_five_popup, coinPairList, pairListener);
        coinPairPopup.SetDefaultSelect(lastSelectedIndex);

    }


    /**
     * 分时指标
     */

    private void initTimeSpinner() {
        timePopp = new CommonGridPopup(rlTime, getActivity(), R.layout.grid_list_one_popup, time_index, timeListener);
        timePopp.SetDefaultSelect(3);

    }

    //MA EMA下拉菜单
    private void initFactorSpinner() {
        facotrPop = new CommonGridPopup(rlFactor, getActivity(), R.layout.grid_list_one_popup, factor_index, factorListener);
        facotrPop.SetDefaultSelect(0);
    }

    //MACD KDJ下拉菜单
    private void initCurveSpinner() {
        curvePop = new CommonGridPopup(rlCurve, getActivity(), R.layout.grid_list_one_popup, curve_index, curveListener);
        curvePop.SetDefaultSelect(0);
    }

    //设置主力意图下拉
    private void initMajorSpinner() {
        majorPop = new CommonGridPopup(rlMajor, getActivity(), R.layout.grid_list_one_popup, major_index, majorListener);
        majorPop.SetDefaultSelect(0);
    }

    // /设置下拉菜单
    private void initSettingSpinner() {
        settingPopp = new CommonGridPopup(rlSetting, getActivity(), R.layout.grid_list_one_popup, setting_index, setttingListener);
        settingPopp.setRememberStatus(false);
    }


    @OnClick({R.id.hy_btn_below, R.id.bg_heyue_shadow, R.id.rl_setting, R.id.dingpan,
            R.id.rl_time,R.id.rl_factor,R.id.rl_curve,R.id.rl_major,R.id.pairContainer})
    public void setClick(View view) {

        switch (view.getId()) {
            case R.id.bg_heyue_shadow:

                int isFutureDogUser = 0;
                UserInfo userInfo = UserService.getCurrentUserInfo();
                if (userInfo != null) {
                    isFutureDogUser = userInfo.getIsFutureDogUser();
                }
                //未登录用户
                if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
                    startActivity(new Intent(getContext(), YZMLoginActivity.class));
                } else {
                    if (checkJiFen()) {//积分足够，开启积分
                        switchFuture(1);
                    } else {//积分不足,获取积分
                        showPopGetCoin();
                    }
                }

                break;
            case R.id.hy_btn_below:
//                rlShadow.setVisibility(View.VISIBLE);
                break;

            case R.id.rl_setting:
                settingPopp.showPricePopup();
                rlSetting.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvSetting.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivSetting.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_time:
                timePopp.showPricePopup();
                rlTime.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvTime.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivTime.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_factor:
                facotrPop.showPricePopup();
                rlFactor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvFactor.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivFactor.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_curve:
                curvePop.showPricePopup();
                rlCurve.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvCurve.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivCurce.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_major:
                majorPop.showPricePopup();
                rlMajor.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvMajor.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivMajor.setVisibility(View.INVISIBLE);
                break;
            case R.id.dingpan:
                CoinPairBean coinInfo = klineSetting.getCoinPair();
                if (coinInfo == null || TextUtils.isEmpty(coinInfo.getSymbol()) ||
                        TextUtils.isEmpty(coinInfo.getQuoteAsset()) || TextUtils.isEmpty(coinInfo.getExchange())) {
                    return;
                }
                Intent intent1 = new Intent(getContext(), CommonWebViewActivity.class);
                StringBuffer sb = new StringBuffer();
                sb.append(UrlConfig.DINGPAN_URL);
                sb.append("?");
                sb.append("base=" + coinInfo.getBaseAsset());
                sb.append("&");
                sb.append("quote=" + coinInfo.getQuoteAsset());
                sb.append("&");
                sb.append("exchange=" + coinInfo.getExchange());

                intent1.putExtra("url", sb.toString());
                startActivity(intent1);
                break;
            case R.id.pairContainer:
                coinPairPopup.showPricePopup();
                rlPair.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvPairExchange.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvPairPair.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivPair.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    private void switchFuture(int onOff) {
        //1表示需要开启
        switch (onOff) {
            case 1:
                popDialogFragment = PopDialogFragment.newInstance(
                        "确定要开启合约狗功能吗？", "取消", "确定", new PopDialogFragment.PopDialogListener() {
                            @Override
                            public void leftClick() {
                                popDialogFragment.dismiss();
                            }

                            @Override
                            public void rightClick() {
                                popDialogFragment.dismiss();
                                doSwithFuture("1");
                            }

                            @Override
                            public void cancleClick() {
                                popDialogFragment.dismiss();

                            }
                        });
                popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                popDialogFragment.show(getFragmentManager(), "popDialogFragment");

                break;
            case 0:
                popDialogFragment = PopDialogFragment.newInstance(
                        "确定要暂停合约狗功能吗？", "取消", "确定", new PopDialogFragment.PopDialogListener() {
                            @Override
                            public void leftClick() {
                                popDialogFragment.dismiss();

                            }

                            @Override
                            public void rightClick() {
                                popDialogFragment.dismiss();
                                doSwithFuture("0");

                            }

                            @Override
                            public void cancleClick() {
                                popDialogFragment.dismiss();

                            }
                        });
                popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                popDialogFragment.show(getFragmentManager(), "popDialogFragment");

                break;
        }
    }

    /**
     * 获取积分
     */
    private void showPopGetCoin() {
        String title = "您的积分不足，去赚取积分吧";
        popDialogFragment = PopDialogFragment.newInstance(
                title, "取消", "确定", new PopDialogFragment.PopDialogListener() {
                    @Override
                    public void leftClick() {
                        popDialogFragment.dismiss();
                    }

                    @Override
                    public void rightClick() {
                        popDialogFragment.dismiss();
                        showGetJifen();
                    }

                    @Override
                    public void cancleClick() {
                        popDialogFragment.dismiss();

                    }
                });
        popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
        popDialogFragment.show(getFragmentManager(), "popDialogFragment");
    }

    /**
     * true 积分足够  false 积分不够
     */
    private boolean checkJiFen() {
        UserInfo userInfo = UserService.getCurrentUserInfo();
        int userPoint = 0;
        int isFutureDogUser = 0;

        if (userInfo != null) {
            isFutureDogUser = userInfo.getIsFutureDogUser();
            userPoint = userInfo.getPoint();
        }
        if (DogsService.getFutureDogPoing() <= userPoint) {
            return true;
        } else {
            return false;
        }

    }

    private void showGetJifen() {
        Intent intent = new Intent(getContext(), CommonWebViewActivity.class);
        intent.putExtra("url", UrlConfig.HEYUE_DOG_GET_COIN_URL);
        startActivity(intent);

    }

    public void doSwithFuture(final String i) {
        BteTopService.switchFuture(i)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (("0000").equals(baseBean.getCode())) {
                            if (i.equals("1")) {
                                rlShadow.setVisibility(View.GONE);
                                if (coinInfoListener != null) {
                                    coinInfoListener.onIsHeyueUserChange(true);
                                }
                                UserService.loadUserInfo();
                            } else if (i.equals("0")) {
                                rlShadow.setVisibility(View.VISIBLE);
                                if (coinInfoListener != null) {
                                    coinInfoListener.onIsHeyueUserChange(false);
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void setTickerInfo(KlineBean.TickerBean ticker) {
        if (ticker == null) {
            return;
        }
        if (mTvMainPrice != null) {
            mTvMainPrice.setText(DoubleUtil.formatDataCompare10(ticker.getPrice()) + "");
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/DINMittelschrift-Alternate.otf");
            mTvMainPrice.setTypeface(typeface);
        }
        if (mTvRmb != null)
            mTvRmb.setText("≈" + ticker.getCnyPrice() + "CNY");

        if (mTvChange != null) {
            mTvChange.setText(ticker.getFormatChange());
            mTvChange.setTextColor(ticker.getFromatColor());
        }

        if (mTvVol != null)
            mTvVol.setText(priceConvert(ticker.getVolX()) + "张");
        if (mTvAmount != null)
            mTvAmount.setText(priceConvert(ticker.getAmountVol()) + "元");
    }

    private String priceConvert(double value) {
        String s;
        if (value > 100000000) {
            s = DoubleUtil.format2Decimal(value / 100000000) + "亿";
        } else if (value > 10000) {
            s = DoubleUtil.format2Decimal(value / 10000) + "万";
        } else {
            s = DoubleUtil.format2Decimal(value) + "";
        }
        return s;
    }

    @Override
    public void getMarkerDataIndex(int dataIndex) {
        setKLineInfo(dataIndex);
    }

    @Override
    public void marketHidden() {
    }

    @Override
    public void leftLoadMore() {
        Log.e(TAG, "leftLoadMore: ");
        //LoadMoreKLineData(0, "1m");
        //mKlineView.LeftLoadMoreDragFalse();
    }

    @Override
    public void RightLoadMore() {
        //startLoop();
    }

    @Override
    public void noNeedRefreshRight() {
        //endLoop();
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataService.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(UserService.getCurrentUserToken())) {
            if (rlShadow != null) {
                rlShadow.setVisibility(View.VISIBLE);
//                viewPager.setVisibility(View.GONE);
            }
        }
    }


    //设置高开低收
    public void setKLineInfo(int dataIndex) {

        int currentIndex, preIndex;

        if (dataIndex == 0) {
            currentIndex = 0;
            preIndex = 0;
        } else {
            currentIndex = dataIndex;
            preIndex = dataIndex - 1;
        }

        try {
            double preClose = 0;
            double v = 0;
            KLineFullData hisData = new KLineFullData();
            KLineFullData preHisdata = new KLineFullData();
            hisData = mKlineView.getKlineData(currentIndex);
            preHisdata = mKlineView.getKlineData(preIndex);
            preClose = preHisdata.getClose();
            v = ((hisData.getClose() - preClose) / preClose) * 100;
            Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/DINMittelschrift-Alternate.otf");
            mTvTime.setText(DateUtils.formatDate(hisData.getDate(), "yyyy-MM-dd HH:mm"));
            mTvTime.setTypeface(typeface);
            mTvOpenPrice.setText(DoubleUtil.formatDataCompare10(hisData.getOpen()) + "");
            mTvOpenPrice.setTypeface(typeface);
            tv_time_line_high_value.setText(DoubleUtil.formatDataCompare10(hisData.getHigh()) + "");
            tv_time_line_high_value.setTypeface(typeface);
            tv_time_line_low_value.setText(DoubleUtil.formatDataCompare10(hisData.getLow()) + "");
            tv_time_line_low_value.setTypeface(typeface);
            tv_time_line_end_value.setText(DoubleUtil.formatDataCompare10(hisData.getClose()) + "");
            tv_time_line_end_value.setTypeface(typeface);
        } catch (Exception e) {

        }
    }

    class KlineSetting {

        private String timeType; //K线时间级别
        private CoinPairBean coinPair; //币对信息
        private String factorType;//主指标线类型 MA EMA BOOL SAR

        private String curveType;//副标线类型 MACD KDJ RSI

        private boolean majorType; //是否显示主力意图

        private boolean inited = false; //是否初始化完成

        private KLineView kLineView;

        public KlineSetting(KLineView kLineView) {
            this.kLineView = kLineView;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {

            this.timeType = timeType;
        }

        public CoinPairBean getCoinPair() {
            return coinPair;
        }

        public void setCoinPair(CoinPairBean coinPairBean) {
            if (this.coinPair == coinPairBean) return;
            this.coinPair = coinPairBean;
            //刷新数据

        }

        public boolean isInited() {
            return inited;
        }

        public void setInited(boolean inited) {
            this.inited = inited;
        }


        public String getFactorType() {
            return factorType;
        }

        public void setFactorType(String factorType) {
            if (StringUtils.equalsIgnoreCase(this.factorType, factorType)) return;
            this.factorType = factorType;
            switch (factorType) {
                case "volumeProfile":
                    if (kLineView != null) {
                        kLineView.showVolumeProfile();
                        kLineView.showOnlyPrice();
                    }
                    ;
                    break;
                case "MA":
                    if (kLineView != null) {
                        kLineView.showMa();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
                case "EMA":
                    if (kLineView != null) {
                        kLineView.showEma();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
                case "BOLL":
                    if (kLineView != null) {
                        kLineView.showBoll();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
                case "SAR":
                    if (kLineView != null) {
                        kLineView.showSar();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
                case "Resistance":
                    if (kLineView != null) {
                        kLineView.showResistance();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
            }
        }

        public String getCurveType() {
            return curveType;
        }

        public void setCurveType(String curveType) {
            if (StringUtils.equalsIgnoreCase(this.curveType, curveType)) return;
            this.curveType = curveType;
            switch (curveType) {
                case "MACD":
                    if (kLineView != null) kLineView.showMacd();
                    break;
                case "KDJ":
                    if (kLineView != null) kLineView.showKdj();
                    break;
                case "RSI":
                    if (kLineView != null) kLineView.showRsi();
                    break;
            }
        }

        public boolean getMajorType() {
            return majorType;
        }

        public void setMajorType(boolean majorType) {
            if (this.majorType == majorType) return;
            this.majorType = majorType;
            if (majorType == true) {
                if (kLineView != null) kLineView.showZhuliYiTu();

            } else {
                if (kLineView != null) kLineView.hiddenZhiLiYiTu();
            }
        }
    }


    public void setCoinInfoListener(CoinInfoListener coinInfoListener) {
        this.coinInfoListener = coinInfoListener;
    }


    private void calLastSelectIndex() {
        if (pairList == null || TextUtils.isEmpty(base)) {
            return;
        }
        for (int i = 0; i < pairList.size(); i++) {
            if (pairList.get(i).getBaseAsset().equalsIgnoreCase(base)) {
                lastSelectedIndex = i;
            }
        }
    }

    public void setLastSelectedIndex(String base) {
        this.base = base;
        calLastSelectIndex();
        if (coinPairPopup!=null) {
            coinPairPopup.SetDefaultSelect(lastSelectedIndex);
        }
    }

    //币对选择回调到activity
    private CoinInfoListener coinInfoListener;

    public interface CoinInfoListener {
        public void onCoinPairChange(CoinPairBean pairBean);

        public void onIsHeyueUserChange(boolean b);

        public void onPriceChange(double price);
    }
}

