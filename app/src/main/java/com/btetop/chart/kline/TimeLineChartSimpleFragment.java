package com.btetop.chart.kline;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.KlineBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.guoziwei.klinelib.chart.CoupleChartGestureListener;
import com.guoziwei.klinelib.chart.KLineViewSimple;
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
public class TimeLineChartSimpleFragment extends BaseFragment implements CoupleChartGestureListener.InfoListener {
    private static final String TAG = "TimeLineChartFragment";

    KLineViewSimple mklineView;

    @BindView(R.id._1m_shadow)
    View _1mShadow;
    @BindView(R.id._5m_shadow)
    View _5mShadow;
    @BindView(R.id._15m_shadow)
    View _15mShadow;
    @BindView(R.id._30m_shadow)
    View _30mShadow;
    @BindView(R.id._1h_shadow)
    View _1hShadow;
    @BindView(R.id._4h_shadow)
    View _4hShadow;
    @BindView(R.id._1d_shadow)
    View _1dShadow;

    //header 的两种布局,根据收缩显示
    @BindView(R.id.ll_price)
    LinearLayout llPrice;
    @BindView(R.id.ll_indicators)
    LinearLayout llIndicators;

    //指标
    @BindView(R.id.tv_time_line_up_values)
    TextView tvGains;
    @BindView(R.id.tv_time_line_down_values)
    TextView tvSwing;
    //高 开 地 收
    @BindView(R.id.tv_price_time)
    TextView tvDate;
    @BindView(R.id.tv_time_line_open_price)
    TextView tvOpen;
    @BindView(R.id.tv_time_line_high_value)
    TextView tvHigh;
    @BindView(R.id.tv_time_line_low_value)
    TextView tvLow;
    @BindView(R.id.tv_time_line_end_value)
    TextView tvEnd;




    //币的基本信息


    TextView tv_coin_name, tv_coin_price, tv_coin_change;

    RelativeLayout rlHead, rlDetail;

    ImageView iv_expand;


    List<KLineFullData> mData = new ArrayList<>();





    private int intervalTime = 1 * 1000;
    private int updateTimes = 3 * 2000;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //endLoop();
            refreshLast();


        }
    };



    private String baseAsset = "";
    private String quoteAsset = "";
    private String exchange = "";
    private String TimeType = "1h";


    public String getBaseAsset() {
        return baseAsset;
    }

    public String getQuoteAsset() {
        return quoteAsset;
    }

    public String getExchange() {
        return exchange;
    }


//    private CoinInfo coinInfo;

    public TimeLineChartSimpleFragment() {
    }

    public static TimeLineChartSimpleFragment newInstance(String exchange,String baseAsset,String Quote) {
        TimeLineChartSimpleFragment fragment = new TimeLineChartSimpleFragment();
            fragment.exchange = exchange;
            fragment.baseAsset = baseAsset;
            fragment.quoteAsset = Quote;
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_time_line_simple;
    }

    @Override
    protected void initView(View view) {
        initViews(view);


        tv_coin_name.setText(baseAsset + "/" + quoteAsset);
        mklineView.setDateFormat("HH:mm");
        mklineView.setKlineInfoListener(this);
        mklineView.showMa();

        TimeType = "1h";
        initKLineData(TimeType);
        _1hShadow.setVisibility(View.VISIBLE);
        llPrice.setVisibility(View.VISIBLE);
        llIndicators.setVisibility(View.GONE);


    }

    private void initViews(View view) {

        mklineView = view.findViewById(R.id.kline_view);


        tv_coin_change = view.findViewById(R.id.coin_change);
        tv_coin_name = view.findViewById(R.id.coin_name);
        tv_coin_price = view.findViewById(R.id.coin_price);

        iv_expand = view.findViewById(R.id.iv_expand);

        rlHead = view.findViewById(R.id.rl_simple_kline_head);
        rlDetail = view.findViewById(R.id.rl_simple_kline_detail);


    }


    @Override
    protected void initData() throws NullPointerException {
    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }




    @OnClick({ R.id.rl_simple_kline_head})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_simple_kline_head:
                if(rlDetail.getVisibility() == View.VISIBLE){
                    iv_expand.setImageResource(R.drawable.xiala);
                    rlDetail.setVisibility(View.GONE);
                }
                else {
                    iv_expand.setImageResource(R.drawable.shouqi);
                    rlDetail.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id._1m,R.id._5m,R.id._15m,R.id._30m,R.id._1h,R.id._4h,R.id._1d})
    public void setSelectTime(View view){
        resetAllBottomShadow();
        switch (view.getId()) {
            case R.id._1m:
                TimeType = "1m";
                initKLineData(TimeType);
                _1mShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._5m:
                TimeType = "5m";
                initKLineData(TimeType);
                _5mShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._15m:
                TimeType = "15m";
                initKLineData(TimeType);
                _15mShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._30m:
                TimeType = "30m";
                initKLineData(TimeType);
                _30mShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._1h:
                TimeType = "1h";
                initKLineData(TimeType);
                _1hShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._4h:
                TimeType = "4h";
                initKLineData(TimeType);
                _4hShadow.setVisibility(View.VISIBLE);
                break;
            case R.id._1d:
                TimeType = "1d";
                initKLineData(TimeType);
                _1dShadow.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void resetAllBottomShadow(){
        _1mShadow.setVisibility(View.INVISIBLE);
        _5mShadow.setVisibility(View.INVISIBLE);
        _15mShadow.setVisibility(View.INVISIBLE);
        _30mShadow.setVisibility(View.INVISIBLE);
        _1hShadow.setVisibility(View.INVISIBLE);
        _4hShadow.setVisibility(View.INVISIBLE);
        _1dShadow.setVisibility(View.INVISIBLE);


    }


    protected void initKLineData(final String timeType) {

        BteTopService.getKlineBean(exchange, baseAsset, quoteAsset, 0, 0, timeType)
                .compose(RxUtil.<BaseBean<KlineBean>>mainAsync())
                .subscribe(new Action1<BaseBean<KlineBean>>() {
                    @Override
                    public void call(BaseBean<KlineBean> klineBeanBaseBean) {
                        //获取基本信息 然后赋
                        KlineBean.TickerBean ticker = klineBeanBaseBean.getData().getTicker();
                        setTickerInfo(ticker);

                        List<KlineBean.KlineBeanInner> listBaseBean = klineBeanBaseBean.getData().getKline();
                        if (mData != null && mData.size() > 0) {
                            mData.clear();
                        }

                        //最多显示5天的点评

                        for (KlineBean.KlineBeanInner item : listBaseBean) {

                            KLineFullData hisData = new KLineFullData(item.getOpen(),
                                    item.getClose(), item.getHigh(),
                                    item.getLow(), item.getVol(),
                                    item.getDate());
                            mData.add(hisData);
                        }
                        if (mData != null && mData.size() > 0) {

                            mklineView.initDataKline(mData);
                            mklineView.setLimitLine();

                        }
                        mklineView.LeftLoadMoreDragTrue();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        //请求失败后也要打开滑动
                        mklineView.LeftLoadMoreDragTrue();
                        mklineView.setVisibility(View.INVISIBLE);
                    }
                });

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

    /**
     * 参数都应该提成全局的
     */
    private void refreshLast() {
        if (updateTimes % 4 != 0) {
            handler.sendEmptyMessageDelayed(0, intervalTime);
            return;
        }

        if (mData == null || mData.size() == 0) {
            handler.sendEmptyMessageDelayed(0, intervalTime);
            return;
        }

        long endDate = mData.get(mData.size() - 1).getDate();
        BteTopService.getKlineBean(exchange, baseAsset, quoteAsset, 0, endDate, TimeType)
                .compose(RxUtil.<BaseBean<KlineBean>>mainAsync())
                .subscribe(new Action1<BaseBean<KlineBean>>() {
                    @Override
                    public void call(BaseBean<KlineBean> klineBeanBaseBean) {
                        //获取基本信息 然后赋
                        //3s刷新一次基本信息
                        KlineBean.TickerBean ticker = klineBeanBaseBean.getData().getTicker();
                        setTickerInfo(ticker);
                        handler.sendEmptyMessageDelayed(0, intervalTime);

                        //验证
                        if (!"0000".equalsIgnoreCase(klineBeanBaseBean.getCode()) ||
                                klineBeanBaseBean.getData() == null ||
                                klineBeanBaseBean.getData().getKline() == null ||
                                klineBeanBaseBean.getData().getKline().size() == 0) {

                            return;
                        }
                        //更新mdata
                        mData.remove(mData.size() - 1);


                        List<KLineFullData> newDataList = new ArrayList<>(klineBeanBaseBean.getData().getKline().size());
                        for (KlineBean.KlineBeanInner item : klineBeanBaseBean.getData().getKline()) {
                            KLineFullData hisData = new KLineFullData(item.getOpen(),
                                    item.getClose(), item.getHigh(),
                                    item.getLow(), item.getVol(),
                                    item.getDate());
                            newDataList.add(hisData);
                        }
                        mData.addAll(newDataList);

                        mklineView.addDataLast(newDataList);


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        handler.sendEmptyMessageDelayed(0, intervalTime);
                    }
                });


    }

    private void setTickerInfo(KlineBean.TickerBean tickerBean){
        tv_coin_price.setText(priceConvert(tickerBean.getCloseX()));
        tv_coin_change.setText(tickerBean.getFormatChange());
        tv_coin_change.setTextColor(tickerBean.getFromatColor());

    }
    private void startLoop() {
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, intervalTime);
    }

    private void endLoop() {
        handler.removeMessages(0);

    }

    @Override
    public void onPause() {
        super.onPause();
        endLoop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        endLoop();
    }

    @Override
    public void onResume() {
        super.onResume();
        startLoop();
    }


    @Override
    public void getMarkerDataIndex(int dataIndex) {
        int currentIndex, preIndex;

        if (dataIndex == 0) {
            currentIndex = 0;
            preIndex = 0;
        } else {
            currentIndex = dataIndex;
            preIndex = dataIndex - 1;
        }

        KLineFullData hisData = new KLineFullData();
        KLineFullData preHisdata = new KLineFullData();
        hisData = mklineView.getKlineData(currentIndex);
        preHisdata = mklineView.getKlineData(preIndex);


        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/DINMittelschrift-Alternate.otf");
        tvDate.setText(DateUtils.formatDate(hisData.getDate(), "yyyy-MM-dd HH:mm"));
        tvDate.setTypeface(typeface);
        tvOpen.setText(DoubleUtil.formatDataCompare10(hisData.getOpen()) + "");
        tvOpen.setTypeface(typeface);
        tvHigh.setText(DoubleUtil.formatDataCompare10(hisData.getHigh()) + "");
        tvHigh.setTypeface(typeface);
        tvLow.setText(DoubleUtil.formatDataCompare10(hisData.getLow()) + "");
        tvLow.setTypeface(typeface);
        tvEnd.setText(DoubleUtil.formatDataCompare10(hisData.getClose()) + "");
        tvEnd.setTypeface(typeface);

        //涨幅跌幅
            double Preclose = preHisdata.getClose();
        double v = ((hisData.getClose() - Preclose) / Preclose) * 100;

                if (v > 0) {
                    tvGains.setTextColor(ContextCompat.getColor(getContext(), R.color.main_info_color_green));
                    tvGains.setText(DoubleUtil.format2Decimal(v) + "%");
                } else {
                    tvGains.setTextColor(ContextCompat.getColor(getContext(), R.color.main_info_color_red));
                    tvGains.setText(DoubleUtil.format2Decimal(v) + "%");
                }
        double swing=(hisData.getHigh()-hisData.getLow())/Preclose*100;
        tvSwing.setText(DoubleUtil.format2Decimal(swing) + "%");
    }

    @Override
    public void marketHidden() {

    }

    @Override
    public void leftLoadMore() {

    }

    @Override
    public void RightLoadMore() {

    }

    @Override
    public void noNeedRefreshRight() {

    }
}
