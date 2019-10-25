package com.btetop.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
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
import com.btetop.bean.CoinInfo;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.GridItem;
import com.btetop.bean.HomeLatestBean;
import com.btetop.bean.KlineBean;
import com.btetop.bean.OptionalBean;
import com.btetop.chart.kline.FullScreenCoinDetailActivity;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.CoinPairPopup;
import com.btetop.dialog.CommonGridPopup;
import com.btetop.monitor.M;
import com.btetop.net.BteTopService;
import com.btetop.service.KlineDataService;
import com.btetop.utils.AppUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.guoziwei.klinelib.chart.BaseView;
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
public class KLineFragment extends BaseFragment implements CoupleChartGestureListener.InfoListener, BaseView.fullScreenListener,KlineDataService.DataListener {
    private static final String TAG = "KLineFragment";



    @BindView(R.id.kline)
    KLineView mKlineView;


    //币的基本信息

    @BindView(R.id.tv_info_usd)
    TextView tv_info_usd;

    @BindView(R.id.tv_info_rmb)
    TextView tv_info_rmb;

    @BindView(R.id.tv_info_status)
    TextView tv_info_status;

    @BindView(R.id.tv_24_vol_value)
    TextView tv_24_vol_value;

    @BindView(R.id.tv_24_amount_value)
    TextView tv_24_amount_value;

    @BindView(R.id.right_arrow)
    ImageView imRightArrow;
    @BindView(R.id.bg_air)
    ImageView bgAir;

    @BindView(R.id.tv_air)
    TextView tvAir;

    @BindView(R.id.option)
    TextView tvOption;

    @BindView(R.id.pairContainer)
    RelativeLayout rlPair;
    @BindView(R.id.select_pair_exchange)
    TextView tvPairExchange;
    @BindView(R.id.pair_pair)
    TextView tvPairPair;
    @BindView(R.id.iv_pair)
    ImageView ivPair;

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
    @BindView(R.id.rl_real_time)
    RelativeLayout rlRealTime;
    @BindView(R.id.tv_real_time)
    TextView tvRealTime;
    @BindView(R.id.iv_real_time)
    ImageView ivRealTime;

    @BindView(R.id.tv_price_time)
    TextView tv_price_time;

    @BindView(R.id.tv_time_line_open_price)
    TextView tv_time_line_open_price;

    @BindView(R.id.tv_time_line_high_value)
    TextView tv_time_line_high_value;

    @BindView(R.id.tv_time_line_low_value)
    TextView tv_time_line_low_value;

    @BindView(R.id.tv_time_line_end_value)
    TextView tv_time_line_end_value;





    KlineSetting klineSetting;


    //类型,取值分钟 1m 5m 15m 30m 小时 1h 4h 天1d 周1w 月1mo,建议先限制在1d以下
    String time_index[] = {"1分", "5分", "15分", "30分", "1小时", "4小时", "1天"};
    String factor_index[] = {"MA", "EMA", "BOLL", "SAR"};
    String curve_index[] = {"MACD", "KDJ", "RSI"};
    String jiepan_index_type_full[] = {"实时解盘", "成交分布", "关闭"};//有实时解盘的时候
    String jiepan_index[] = {"成交分布", "关闭"};//无实时解盘的时候

    private CommonGridPopup timePopp;
    private CommonGridPopup facotrPop;
    private CommonGridPopup curvePop;
    private CommonGridPopup realTimePop;
    private CoinPairPopup coinPairPopup;

    private KlineDataService dataService;


    private CoinInfo coinInfo;

    private boolean isAlAdd;

    public KLineFragment() {
    }

    public static KLineFragment newInstance(CoinInfo coinInfo) {
        KLineFragment fragment = new KLineFragment();
        fragment.coinInfo = coinInfo;
        return fragment;
    }

    List<CoinPairBean> pairList = new ArrayList<>();



    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_time_line_contain;
    }

    @Override
    protected void initView(View view) {
        mKlineView.setDateFormat("HH:mm");
        mKlineView.setKlineInfoListener(this);
        mKlineView.setFullListener(this);

//        showGuidImg();

        //状态栏高度
        int height = 0;
        int resourceId = BteTopApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = BteTopApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        //50:header 100:价格到24小时量 30:小时，压力线 22：高开低收 8:底部阴影 44：tablayout的高度
        int heightExceptActionBar = ScreenUtils.getScreenHeight() - SizeUtils.dp2px(50 + 100 + 30 + 22 + 8 + 44);
        //遮罩层高度
        int klineHeight = heightExceptActionBar - height;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, klineHeight);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.kline_header_layout);
        mKlineView.setLayoutParams(layoutParams);


    }


    /**
     * 分时指标
     */

    private void initTimeSpinner() {
        timePopp = new CommonGridPopup(rlTime, getActivity(), R.layout.grid_list_one_popup, time_index, timeListener);
        timePopp.SetDefaultSelect(4);
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

    //试试解盘 成交分布 关闭
    private void initRealItmeSpinner() {
        if (isShowRealTime()) {
            realTimePop=new CommonGridPopup(rlRealTime,getActivity(),R.layout.grid_list_one_popup,jiepan_index_type_full,realTimeListener);
        }else {
            realTimePop=new CommonGridPopup(rlRealTime,getActivity(),R.layout.grid_list_one_popup,jiepan_index,realTimeListener);
        }

        if (isShowRealTime()) {
            getIsRead();
            updateNotice();
        }else {
            realTimePop.SetDefaultSelect(0);
        }


    }

    private boolean isShowRealTime(){
        if (coinInfo.getSymbol().equalsIgnoreCase("BTC")
                || coinInfo.getSymbol().equalsIgnoreCase("EOS")
                || coinInfo.getSymbol().equalsIgnoreCase("ETH")
                || coinInfo.getSymbol().equalsIgnoreCase("BCH")) {
            return true;
        }
        return false;

    }


    private void updateNotice(){
        if (isShowRealTime()) {
            AppUtils.addEvent(Constant.EVENT_MODULE_HOME, "click", Constant.EVENT_MARKET_KLINE, coinInfo.getExchange(), coinInfo.getSymbol(), coinInfo.getQuote());
        }
    }


    //时间选择Spinner监听

    private CommonGridPopup.GridItemClickListen timeListener = new CommonGridPopup.GridItemClickListen() {

        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            timePopp.dismiss();
            tvTime.setText(time_index[position]);
            if(position !=4){
                mKlineView.hiddenInfo();
//                tvRealTime.setTextColor(getResources().getColor(R.color.color_626A75));
//                imRealTime.setImageResource(R.mipmap.real_time_reviews_off);
//                isShowComment = false;
            }

            switch (position) {
                case 0:
                    klineSetting.setTimeType("1m");
                    refreshKlineData();
                    break;
                case 1:
                    klineSetting.setTimeType("5m");
                    refreshKlineData();
                    break;
                case 2:
                    klineSetting.setTimeType("15m");
                    refreshKlineData();
                    break;
                case 3:
                    klineSetting.setTimeType("30m");
                    refreshKlineData();
                    break;
                case 4:
                    klineSetting.setTimeType("1h");
                    refreshKlineData();
                    break;
                case 5:
                    klineSetting.setTimeType("4h");
                    refreshKlineData();
                    break;
                case 6:
                    klineSetting.setTimeType("1d");
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
                case 0: //MA
                    klineSetting.setFactorType("MA");
                    break;
                case 1: //EMA
                    klineSetting.setFactorType("EMA");
                    break;
                case 2: //Boll
                    klineSetting.setFactorType("BOLL");

                    break;
                case 3: //SAR
                    klineSetting.setFactorType("SAR");
                    break;
                case 4: //压力线
                    klineSetting.setFactorType("Resistance");
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
//    private AdapterView.OnItemSelectedListener factorSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            switch (position) {
//                case 0: //MA
//                    klineSetting.setFactorType("MA");
//                    break;
//                case 1: //EMA
//                    klineSetting.setFactorType("EMA");
//                    break;
//                case 2: //Boll
//                    klineSetting.setFactorType("BOLL");
//
//                    break;
//                case 3: //SAR
//                    klineSetting.setFactorType("SAR");
//                    break;
//                case 4: //压力线
//                    klineSetting.setFactorType("Resistance");
//                    break;
//            }
//        }
//    };
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

    //{"实时解盘","交易分布","关闭"};
    //{"交易分布","关闭"};

    private CommonGridPopup.GridItemClickListen realTimeListener=new CommonGridPopup.GridItemClickListen() {
        @Override
        public void getGridItemInfo(int position, GridItem gridItem) {
            realTimePop.dismiss();
            if (isShowRealTime()) {
                tvRealTime.setText(jiepan_index_type_full[position]);
                switch (position) {

                    case 0: //实时解盘

                        if (!klineSetting.getTimeType().equals("1h")) {
                            klineSetting.setTimeType("1h");
                            refreshKlineData();
                            realTimePop.SetDefaultSelect(4);
                        }
                        klineSetting.setRealTime(0);
                        break;
                    case 1: //交易分布
                        klineSetting.setRealTime(1);
                        break;
                    case 2: //关闭
                        klineSetting.setRealTime(2);
                        break;
                }
            }else {
                tvRealTime.setText(jiepan_index[position]);
                switch (position) {
                    case 0: //交易分布
                        klineSetting.setRealTime(1);
                        break;
                    case 1: //关闭
                        klineSetting.setRealTime(2);
                        break;
                }
            }
        }

        @Override
        public void downPopupDismiss() {

        }

        @Override
        public void popupDismiss() {

            rlRealTime.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            tvRealTime.setTextColor(ContextCompat.getColor(getContext(), R.color.color_626A75));
            ivRealTime.setVisibility(View.VISIBLE);
        }
    };

    private void getIsRead(){
        BteTopService.getHomeLatests()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<HomeLatestBean.HomeLatestsData>>() {
                    @Override
                    public void call(BaseBean<HomeLatestBean.HomeLatestsData> homeLatestsDataBaseBean) {
                        int selectIndex=0;
                        for (CoinInfo info : homeLatestsDataBaseBean.getData().getList()) {
                            if (info.getSymbol().equalsIgnoreCase(coinInfo.getSymbol())) {
                                selectIndex=1;
                            }
                        }
                        realTimePop.SetDefaultSelect(selectIndex);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });
    }





    @Override
    protected void initData() throws NullPointerException {

        klineSetting = new KlineSetting(mKlineView);

        initTimeSpinner();
        initFactorSpinner();
        initCurveSpinner();
        initRealItmeSpinner();

        LoadCoinPairData("", coinInfo.getSymbol());


        dataService = KlineDataService.newInstance(this);
        dataService.setListener(this);


        //this.getKlineHourCommentData();
        //统计进入k线页面的次数
        M.monitor().onEvent(getContext(), Constant.ENTER_K_LINE);
    }


    @Override
    public void onData(String key, int mode, List<KLineFullData> data, KlineBean.TickerBean ticker) {
        if(ticker!=null){
            setTickerInfo(ticker);
            if(coinInfoListener !=null) coinInfoListener.onCoinPriceChange(ticker.getPrice());
        }

//        for(KLineFullData item:data){
//            //如果发现K线数据上存在点评，则显示点评按钮
//            if(!TextUtils.isEmpty(item.getBubbleText()))
//                rlRealTime.setVisibility(View.VISIBLE);
//        }
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



    private void refreshKlineData() {
        if (klineSetting == null || !klineSetting.isInited()) return;
        if (klineSetting.getCoinPair() == null) return;
        String key = klineSetting.getCoinPair().getExchange()+"-"
                +klineSetting.getCoinPair().getBaseAsset()+"-"
                +klineSetting.getCoinPair().getQuoteAsset()+"-"
                +klineSetting.getTimeType();

        dataService.start(key);

    }


    @Override
    public boolean statisticsFragment() {
        return true;
    }


    //初始化币对选择器

    private void initPairSpinner(List<CoinPairBean> coinPairList) {
        if (coinPairList == null || coinInfo==null ) return;
        this.pairList = coinPairList;
        int selectedIndex = 0;

        for (int i = 0; i < coinPairList.size(); i++) {
            if (coinInfo.getSymbol().equalsIgnoreCase(coinPairList.get(i).getBaseAsset())
            &&coinInfo.getQuote().equalsIgnoreCase(coinPairList.get(i).getQuoteAsset())
            &&coinInfo.getExchange().equalsIgnoreCase(coinPairList.get(i).getExchange())) {
                selectedIndex = i;
            }
        }

//        PairSpinerAdapter itemAdapter = new PairSpinerAdapter(this,pairList);
//        spinner_pair.setAdapter(itemAdapter);
//        spinner_pair.setOnItemSelectedListener(pairSpinnerItemSelectedListener);
//
//        spinner_pair.setSelection(selectedIndex, true);
        coinPairPopup = new CoinPairPopup(rlPair, getActivity(), R.layout.grid_list_five_popup, coinPairList, pairListener);
        coinPairPopup.SetDefaultSelect(selectedIndex);

    }

    private CoinPairPopup.GridItemClickListen pairListener=new CoinPairPopup.GridItemClickListen() {
        @Override
        public void getGridItemInfo(int position, CoinPairBean gridItem) {

            coinPairPopup.dismiss();
            tvPairExchange.setText(gridItem.getExchange());
            tvPairPair.setText(gridItem.getName());

            //根据选择的index获取币对
            if (pairList == null) return;


            CoinPairBean coinPair = pairList.get(position);
            klineSetting.setCoinPair(coinPair);


            //给当前coinInfo 设置 必备的三个要素 然后刷新
            coinInfo.setExchange(coinPair.getExchange());
            coinInfo.setSymbol(coinPair.getBaseAsset());
            coinInfo.setQuote(coinPair.getQuoteAsset());
            klineSetting.setInited(true);

            refreshKlineData();


            //通知外层刷新资金 详情 筹码 评级
            if (coinInfoListener != null) {
                coinInfoListener.onCoinInfoChange(coinInfo);
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

//    private AdapterView.OnItemSelectedListener pairSpinnerItemSelectedListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            //根据选择的index获取币对
//            if (pairList == null) return;
//
//
//            CoinPairBean coinPair = pairList.get(position);
//            klineSetting.setCoinPair(coinPair);
//
//
//            //给当前coinInfo 设置 必备的三个要素 然后刷新
//            coinInfo.setExchange(coinPair.getExchange());
//            coinInfo.setSymbol(coinPair.getBaseAsset());
//            coinInfo.setQuote(coinPair.getQuoteAsset());
//            klineSetting.setInited(true);
//
//            refreshKlineData();
//
//
//            //通知外层刷新资金 详情 筹码 评级
//            if (coinInfoListener != null) {
//                coinInfoListener.onCoinInfoChange(coinInfo);
//            }
//        }
//    };

//    //自定义币对Spiner的Adapter
//    public class PairSpinerAdapter extends BaseAdapter {
//        private Fragment container;
//        private List<CoinPairBean> pairList;
//
//        public PairSpinerAdapter(Fragment container,List<CoinPairBean> pairList){
//            this.container = container;
//            this.pairList = pairList;
//        }
//        @Override
//        public int getCount() {
//            return pairList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return pairList.get(position);
//        }
//
//        @Override
//        public View getDropDownView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater linflater = LayoutInflater.from(container.getContext());
//            convertView = linflater.inflate(R.layout.spinner_item_coinpair_dropdown, null);
//            TextView tv_exchange =(TextView) convertView.findViewById(R.id.tv_exchange);
//            tv_exchange.setText(pairList.get(position).getExchange());
//            TextView tv_pair =(TextView) convertView.findViewById(R.id.tv_pair_name);
//            tv_pair.setText(pairList.get(position).getName());
//            return convertView;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater linflater = LayoutInflater.from(container.getContext());
//            convertView = linflater.inflate(R.layout.spinner_item_coinpair, null);
//            TextView tv_exchange =(TextView) convertView.findViewById(R.id.tv_exchange);
//            tv_exchange.setText(pairList.get(position).getExchange());
//            TextView tv_pair =(TextView) convertView.findViewById(R.id.tv_pair_name);
//            tv_pair.setText(pairList.get(position).getName());
//            return convertView;
//        }
//    }


    /**
     * 得到自选按钮的状态
     */
    private void getOptionStatus() {
        BteTopService.getOptionList()
                .compose(this.<BaseBean<OptionalBean>>bindToLifecycle())
                .compose(RxUtil.<BaseBean<OptionalBean>>mainAsync())
                .subscribe(new Action1<BaseBean<OptionalBean>>() {
                    @Override
                    public void call(BaseBean<OptionalBean> optionalListBeanBaseBean) {
                        List<OptionalBean.ResultBean> result = optionalListBeanBaseBean.getData().getResult();
                        isAlAdd = false;
                        for (OptionalBean.ResultBean item : result) {
                            if (coinInfo.getExchange().equalsIgnoreCase(item.getExchange()) &&
                                    coinInfo.getSymbol().equalsIgnoreCase(item.getBase()) &&
                                    coinInfo.getQuote().equalsIgnoreCase(item.getQuote())) {
                                isAlAdd = true;
                                break;
                            }

                        }
                        if (isAlAdd) {
                            tvOption.setText("-自选");
                            int color = ContextCompat.getColor(getContext(), R.color.color_626A75);
                            tvOption.setTextColor(color);
                        } else {
                            int color = ContextCompat.getColor(getContext(), R.color.color_308CDD);
                            tvOption.setText("+自选");
                            tvOption.setTextColor(color);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();

                    }
                });
    }
/*
    private void getKlineHourCommentData() {
        BteTopService.getHourShortComment(coinInfo.getSymbol())
                .compose(this.<BaseBean<List<HourShortCommentBean>>>bindToLifecycle())
                .compose(RxUtil.<BaseBean<List<HourShortCommentBean>>>mainAsync())
                .subscribe(new Action1<BaseBean<List<HourShortCommentBean>>>() {
                    @Override
                    public void call(BaseBean<List<HourShortCommentBean>> listBaseBean) {
                        if (listBaseBean == null || listBaseBean.getData() == null || listBaseBean.getData().size() == 0) {
                            return;
                        }
                        mListComment = listBaseBean.getData();
                        rlRealTime.setVisibility(View.VISIBLE);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }
*/
    /**
     * 第一次加载数据 在交易所数据取出完成后
     *
     * @param address
     * @param CoinName
     */
    protected void LoadCoinPairData(String address, String CoinName) {
        BteTopService.getCoinPairBean(address, CoinName)
                .compose(this.<BaseBean<List<CoinPairBean>>>bindToLifecycle())
                .compose(RxUtil.<BaseBean<List<CoinPairBean>>>mainAsync())
                .subscribe(new Action1<BaseBean<List<CoinPairBean>>>() {
                    @Override
                    public void call(BaseBean<List<CoinPairBean>> coinPairBeanBaseBean) {
                        if (coinPairBeanBaseBean != null
                                && coinPairBeanBaseBean.getData() != null
                                && coinPairBeanBaseBean.getData().size() > 0) {
                                initPairSpinner(coinPairBeanBaseBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }


    @OnClick({R.id.option, R.id.dingpan,R.id.rl_time,R.id.rl_factor,R.id.rl_curve,R.id.rl_real_time,R.id.pairContainer    })

    public void click(View view) {
        Intent intent = new Intent(getContext(), FullScreenCoinDetailActivity.class);
        intent.putExtra("coinInfo", coinInfo);
        switch (view.getId()) {
            case R.id.option:
                clickOption();
                break;
            case R.id.dingpan:
                if (coinInfo == null || TextUtils.isEmpty(coinInfo.getSymbol()) ||
                        TextUtils.isEmpty(coinInfo.getQuote()) || TextUtils.isEmpty(coinInfo.getExchange())) {
                    return;
                }

                StringBuffer sb = new StringBuffer();
                sb.append(UrlConfig.DINGPAN_URL);
                sb.append("?");
                sb.append("base=" + coinInfo.getSymbol());
                sb.append("&");
                sb.append("quote=" + coinInfo.getQuote());
                sb.append("&");
                sb.append("exchange=" + coinInfo.getExchange());

                Intent intent1 = new Intent(getContext(), CommonWebViewActivity.class);
                intent1.putExtra("url", sb.toString());
                startActivity(intent1);
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
            case R.id.rl_real_time:
                realTimePop.showPricePopup();
                rlRealTime.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_308CDD));
                tvRealTime.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                ivRealTime.setVisibility(View.INVISIBLE);
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

    private void clickOption() {
        UrlConfig.NO_REFRESH_HANGQING = true;
        if (isAlAdd) {
            delteOptional();
        } else {
            addOptional();
        }

    }

    private void addOptional() {
        BteTopService.addOption(coinInfo.getExchange(), coinInfo.getSymbol(), coinInfo.getQuote())
                .compose(this.<BaseBean>bindToLifecycle())
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {

                        ToastUtils.showShortToast(baseBean.getMessage());
                        if ("-1".equals(baseBean.getCode())) {
                            startActivity(new Intent(getContext(), YZMLoginActivity.class));
                        } else {
                            getOptionStatus();
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void delteOptional() {
        BteTopService.deleteOption(coinInfo.getExchange(), coinInfo.getSymbol(), coinInfo.getQuote())
                .compose(this.<BaseBean>bindToLifecycle())
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        ToastUtils.showShortToast(baseBean.getMessage());

                        if ("-1".equals(baseBean.getCode())) {
                            startActivity(new Intent(getContext(), YZMLoginActivity.class));
                        } else {
                            getOptionStatus();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void setTickerInfo(KlineBean.TickerBean ticker) {
        if (ticker == null) {
            return;
        }
        tv_info_usd.setText(DoubleUtil.formatDataCompare10(ticker.getPrice()) + "");
        tv_info_usd.setTextColor(ticker.getFromatColor());
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/DINMittelschrift-Alternate.otf");
        tv_info_usd.setTypeface(typeface);
        tv_info_rmb.setText("≈" + ticker.getCnyPrice() + "CNY");

        tv_info_status.setText(ticker.getFormatChange());
        tv_info_status.setTextColor(ticker.getFromatColor());
        tv_24_vol_value.setText(priceConvert(ticker.getVolX()));
        tv_24_amount_value.setText(priceConvert(ticker.getAmountVol()) + "元");
        tvAir.setText(ticker.getAirString());
        //设置热度偏移量
        int totalHeight = SizeUtils.dp2px(46);
        int totalRight = SizeUtils.dp2px(5);

        int marginBottom = (int) (ticker.getAir() * totalHeight);
        int marginRight = (int) (ticker.getAir() * totalRight + totalRight);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imRightArrow.getLayoutParams();
        layoutParams.setMargins(0, 0, marginRight, marginBottom);
        imRightArrow.setLayoutParams(layoutParams);

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
//        startLoop();
    }

    @Override
    public void noNeedRefreshRight() {
//        endLoop();
    }



    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getOptionStatus();
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
        KLineFullData hisData = mKlineView.getKlineData().get(currentIndex);
        KLineFullData PreHisdata = mKlineView.getKlineData().get(preIndex);
        double Preclose = PreHisdata.getClose();
        double v = ((hisData.getClose() - Preclose) / Preclose) * 100;

        //1是全屏  2是竖屏
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/DINMittelschrift-Alternate.otf");
        tv_price_time.setText(DateUtils.formatDate(hisData.getDate(), "yyyy-MM-dd HH:mm"));
        tv_price_time.setTypeface(typeface);
        tv_time_line_open_price.setText(DoubleUtil.formatDataCompare10(hisData.getOpen()) + "");
        tv_time_line_open_price.setTypeface(typeface);
        tv_time_line_high_value.setText(DoubleUtil.formatDataCompare10(hisData.getHigh()) + "");
        tv_time_line_high_value.setTypeface(typeface);
        tv_time_line_low_value.setText(DoubleUtil.formatDataCompare10(hisData.getLow()) + "");
        tv_time_line_low_value.setTypeface(typeface);
        tv_time_line_end_value.setText(DoubleUtil.formatDataCompare10(hisData.getClose()) + "");
        tv_time_line_end_value.setTypeface(typeface);

    }

    @Override
    public void fullScreenClick() {
        Intent intent = new Intent(getContext(), FullScreenCoinDetailActivity.class);
        intent.putExtra("coinInfo", coinInfo);
        startActivity(intent);
    }


    public void setCoinInfoListener(CoinInfoListener coinInfoListener) {
        this.coinInfoListener = coinInfoListener;
    }

    private CoinInfoListener coinInfoListener;


    public interface CoinInfoListener {
        void onCoinInfoChange(CoinInfo coinInfo);
        void onCoinPriceChange(double price);
    }



    class KlineSetting {

        private String timeType; //K线时间级别
        private CoinPairBean coinPair; //币对信息
        private String factorType;//主指标线类型 MA EMA BOOL SAR

        private int realTime;//0 实时解盘 1 成交分布 2关闭



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
                case "MA":
                    if (kLineView != null) kLineView.showMa();
                    break;
                case "EMA":
                    if (kLineView != null) kLineView.showEma();
                    break;
                case "BOLL":
                    if (kLineView != null) kLineView.showBoll();
                    break;
                case "SAR":
                    if (kLineView != null) kLineView.showSar();
                    break;
            }
        }

        //0 实时解盘 1 成交分布 2关闭
        public void setRealTime(int type) {
            this.realTime = type;
            switch (type) {
                case 0:
                    if(kLineView!=null) {
                        kLineView.showInfo();
                        kLineView.hiddenVoluemProfile();
                    }
                    break;
                case 1:
                    if (kLineView!=null) {
                        kLineView.hiddenInfo();
                        kLineView.showVolumeProfile();
                    }
                    break;
                case 2:
                    if(kLineView!=null) {
                        kLineView.hiddenInfo();
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

    }
}
