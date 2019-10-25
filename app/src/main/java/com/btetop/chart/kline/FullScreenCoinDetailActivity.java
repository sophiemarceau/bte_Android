package com.btetop.chart.kline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.activity.CommonWebViewActivity;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.adapter.CoinPairAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.GridItem;
import com.btetop.bean.KlineBean;
import com.btetop.bean.OptionalBean;
import com.btetop.config.UrlConfig;
import com.btetop.dialog.CommonGridPopup;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.guoziwei.klinelib.chart.CoupleChartGestureListener;
import com.guoziwei.klinelib.chart.KLineView;
import com.guoziwei.klinelib.chart.TimeView;
import com.guoziwei.klinelib.model.KLineFullData;
import com.guoziwei.klinelib.util.DateUtils;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.functions.Action1;

public class FullScreenCoinDetailActivity extends BaseActivity implements CoupleChartGestureListener.InfoListener {

    TimeView mTimeView;
    ImageView iv_time_index;
    TextView tv_time_index;
    RelativeLayout rl_time_index;

    ImageView iv_main_index;
    TextView tv_main_index;
    RelativeLayout rl_main_index;

    ImageView iv_all_index;
    TextView tv_all_index;
    RelativeLayout rl_all_index;

    GridView gv_time_index;
    GridView gv_main_index;
    GridView gv_all_index;

    KLineView mklineView;

    RecyclerView mRecyclerView;
    TextView tv_pair_name;
    TextView tv_pair_value;
    View iv_pair;
    TextView price, cnyPrice, tv_price_percent;
    private TextView tvOption;

    String time_index[] = {"分时", "1分", "5分", "15分", "30分", "1小时", "4小时", "1天"};
    String main_index[] = {"MA", "EMA", "BOLL", "SAR"};
    String all_index[] = {"MACD", "KDJ", "RSI"};
    List<KLineFullData> mData = new ArrayList<>();
    private String baseAsset = "";
    private String quoteAsset = "";
    private String exchange = "";
    private String TimeType = "1m";
    private String lineType = "KLine";//默认设置为k线

    private boolean mRefreshLast = true;

    private int intervalTime = 3 * 1000;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            endLoop();
            refreshLast(lineType);
        }
    };
    private CoinInfo coinInfo;
    private String coinName;

    private boolean isAlAdd;

    protected TextView
            tv_price_time,
            tv_time_line_open_price,
            tv_time_line_high_value,
            tv_time_line_low_value,
            tv_time_line_end_value,
            tv_time_line_up_values,
            tv_time_line_down_values;

    private CommonGridPopup gridItemFiveTimePopup_time;
    private CommonGridPopup gridItemPopup_main;
    private CommonGridPopup gridItemPopup_all;

    @Override
    public int intiLayout() {
        return R.layout.fragment_full_screen_coin_detail;
    }

    @Override
    public void initView() {
        tvOption = findViewById(R.id.option);
        mTimeView = findViewById(R.id.time_view);
        mklineView = findViewById(R.id.kline);
        iv_time_index = findViewById(R.id.iv_time_index);
        tv_time_index = findViewById(R.id.tv_time_index);
        rl_time_index = findViewById(R.id.rl_time_index);
        iv_main_index = findViewById(R.id.iv_main_index);
        tv_main_index = findViewById(R.id.tv_main_index);
        rl_main_index = findViewById(R.id.rl_main_index);
        iv_all_index = findViewById(R.id.iv_all_index);
        tv_all_index = findViewById(R.id.tv_all_index);
        rl_all_index = findViewById(R.id.rl_all_index);
        gv_time_index = findViewById(R.id.gv_time_index);
        gv_main_index = findViewById(R.id.gv_main_index);
        gv_all_index = findViewById(R.id.gv_all_index);
        mRecyclerView = findViewById(R.id.rv_trade_pair);
        tv_pair_name = findViewById(R.id.tv_exchange);
        tv_pair_value = findViewById(R.id.tv_pair_value);
        iv_pair = findViewById(R.id.iv_pair);

        price = (TextView) findViewById(R.id.price);
        cnyPrice = (TextView) findViewById(R.id.cny_price);
        tv_price_percent = (TextView) findViewById(R.id.tv_price_percent);

        tv_price_time = findViewById(R.id.tv_price_time);
        tv_time_line_open_price = findViewById(R.id.tv_time_line_open_price);
        tv_time_line_high_value = findViewById(R.id.tv_time_line_high_value);
        tv_time_line_low_value = findViewById(R.id.tv_time_line_low_value);
        tv_time_line_end_value = findViewById(R.id.tv_time_line_end_value);
        tv_time_line_up_values = findViewById(R.id.tv_time_line_up_values);
        tv_time_line_down_values = findViewById(R.id.tv_time_line_down_values);
    }

    @Override
    public void initData() {

        initCoinPairViewFromTransactionDetailActivity();
        mklineView.setDateFormat("HH:mm");
        mklineView.setKlineInfoListener(this);
        mTimeView.setKlineInfoListener(this);
        //涨跌幅隐藏
        mklineView.setUpAndDownValueGone();
        mTimeView.setUpAndDownValueGone();
        initTimeGridView();
        initMainGridView();
        initAllGridView();
        coinInfo = (CoinInfo) getIntent().getExtras().get("coinInfo");
        if (coinInfo != null) {
            coinName = coinInfo.getSymbol();
        } else {
            coinName = "BTC";
        }
        LoadCoinPairData("", coinName);

        //默认显示ma、macd
        mklineView.showMa();
        mklineView.showMacd();
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    /**
     * TransactionDetailsActivity从这里传递基础数据到当前页面
     */
    private void initCoinPairViewFromTransactionDetailActivity() {
        coinInfo = (CoinInfo) getIntent().getSerializableExtra("coinInfo");
        if (coinInfo != null) {
            exchange = coinInfo.getExchange();
            baseAsset = coinInfo.getSymbol();
            quoteAsset = coinInfo.getQuote();
        }
        tv_pair_name.setText(exchange + "");
        tv_pair_value.setText(baseAsset + "/" + quoteAsset);
    }

    /**
     * 第一次加载数据 在交易所数据取出完成后
     *
     * @param address
     * @param CoinName
     */
    protected void LoadCoinPairData(String address, String CoinName) {
        BteTopService.getCoinPairBean(address, CoinName)
                .compose(RxUtil.<BaseBean<List<CoinPairBean>>>mainAsync())
                .subscribe(new Action1<BaseBean<List<CoinPairBean>>>() {
                    @Override
                    public void call(BaseBean<List<CoinPairBean>> coinPairBeanBaseBean) {
                        if (coinPairBeanBaseBean != null
                                && coinPairBeanBaseBean.getData() != null
                                && coinPairBeanBaseBean.getData().size() > 0) {

                            initCoinPairView(coinPairBeanBaseBean.getData());
                            //第一次加载数据
                            initKLineData(TimeType);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    /**
     * 初始化交易对列表
     * 点击item的事件也在这里
     *
     * @param coinPairList
     */
    private void initCoinPairView(List<CoinPairBean> coinPairList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        final CoinPairAdapter adapter = new CoinPairAdapter(coinPairList, new CoinPairAdapter.CoinPairListen() {
            @Override
            public void getCoinPairInfo(String baseAsset, String quoteAsset, String exchange, String name) {
                FullScreenCoinDetailActivity.this.baseAsset = baseAsset;
                FullScreenCoinDetailActivity.this.quoteAsset = quoteAsset;
                FullScreenCoinDetailActivity.this.exchange = exchange;
                tv_pair_name.setText(exchange + "");
                tv_pair_value.setText(baseAsset + "/" + quoteAsset);
                iv_pair.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                tv_pair_value.setBackgroundColor(ContextCompat.getColor(FullScreenCoinDetailActivity.this, R.color.white));
                tv_pair_value.setTextColor(ContextCompat.getColor(FullScreenCoinDetailActivity.this, R.color.color_626A75));

                initKLineData(TimeType);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        startLoop();

        this.getOptionStatus();
    }


    /**
     * 得到自选按钮的状态
     */
    private void getOptionStatus() {
        BteTopService.getOptionList()
                .compose(RxUtil.<BaseBean<OptionalBean>>mainAsync())
                .subscribe(new Action1<BaseBean<OptionalBean>>() {
                    @Override
                    public void call(BaseBean<OptionalBean> optionalListBeanBaseBean) {
                        List<OptionalBean.ResultBean> result = optionalListBeanBaseBean.getData().getResult();
                        isAlAdd = false;
                        for (OptionalBean.ResultBean item : result) {
                            if (exchange.equalsIgnoreCase(item.getExchange()) && baseAsset.equalsIgnoreCase(item.getBase()) && quoteAsset.equalsIgnoreCase(item.getQuote())) {
                                isAlAdd = true;
                                break;
                            }

                        }
                        if (isAlAdd) {
                            tvOption.setText("-自选");
                            int color = ContextCompat.getColor(mContext, R.color.color_626A75);
                            tvOption.setTextColor(color);
//                            Glide.with(FullScreenCoinDetailActivity.this).load(R.mipmap.ic_subtract).into(tvOption);
                        } else {
                            int color = ContextCompat.getColor(mContext, R.color.color_308CDD);
//                            Glide.with(FullScreenCoinDetailActivity.this).load(R.mipmap.ic_add).into(tvOption);
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

    /**
     * 分时指标
     */
    private void initTimeGridView() {

        gridItemFiveTimePopup_time = new CommonGridPopup(rl_time_index, this, R.layout.grid_list_five_popup, time_index, new CommonGridPopup.GridItemClickListen() {
            @Override
            public void getGridItemInfo(int position, GridItem gridItem) {
                gridItemFiveTimePopup_time.dismiss();
//                mTimeGridData.get(5).setChecked(true);
                setTimeTitle(gridItem.getTitle());
                //类型,取值分钟 1m 5m 15m 30m 小时 1h 4h 天1d 周1w 月1mo,建议先限制在1d以下
//                tvRealTime.setTextColor(getResources().getColor(R.color.color_626A75));
//                imRealTime.setImageResource(R.mipmap.real_time_reviews_off);
                switch (position) {
                    case 0:

                        lineType = "TimeView";
                        TimeType = "1m";
                        initKLineData(TimeType);
                        break;
                    case 1:

                        lineType = "KLine";
                        TimeType = "1m";
                        initKLineData(TimeType);
                        break;
                    case 2:

                        lineType = "KLine";
                        TimeType = "5m";
                        initKLineData(TimeType);
                        break;
                    case 3:

                        lineType = "KLine";
                        TimeType = "15m";
                        initKLineData(TimeType);
                        break;
                    case 4:

                        lineType = "KLine";
                        TimeType = "30m";
                        initKLineData(TimeType);
                        break;
                    case 5:

                        lineType = "KLine";
                        TimeType = "1h";
                        initKLineData(TimeType);
//                        if (showInfo) {
//                            tvRealTime.setTextColor(getResources().getColor(R.color.color_308CDD));
//                            imRealTime.setImageResource(R.mipmap.real_time_reviews_on);
//                        }
                        break;
                    case 6:

                        lineType = "KLine";
                        TimeType = "4h";
                        initKLineData(TimeType);
                        break;
                    case 7:

                        lineType = "KLine";
                        TimeType = "1d";
                        initKLineData(TimeType);
                        break;
                    case 8:

                        lineType = "KLine";
                        TimeType = "1w";
                        initKLineData(TimeType);
                        break;
                    case 9:

                        lineType = "KLine";
                        TimeType = "1mo";
                        initKLineData(TimeType);
                        break;
                }
            }

            @Override
            public void downPopupDismiss() {
                gridItemFiveTimePopup_time.dismiss();
            }

            @Override
            public void popupDismiss() {
                initViewGrid();
            }
        });

        //设置默认选择
        gridItemFiveTimePopup_time.SetDefaultSelect(5);
    }

    /**
     * 显示k线或者分时线
     *
     * @param type
     */
    public void showKlineViewOrTimeView(String type) {
        switch (type) {
            case "KLine":
                mTimeView.setVisibility(View.INVISIBLE);
                mklineView.setVisibility(View.VISIBLE);
                break;
            case "TimeView":
                mTimeView.setVisibility(View.VISIBLE);
                mklineView.setVisibility(View.INVISIBLE);
                break;
        }
    }

    /**
     * boll 指标
     */
    private void initMainGridView() {
        gridItemPopup_main = new CommonGridPopup(rl_main_index, this, R.layout.grid_list_one_popup, main_index, new CommonGridPopup.GridItemClickListen() {
            @Override
            public void getGridItemInfo(int position, GridItem gridItem) {
                gridItemPopup_main.dismiss();
//                mMainGridData.get(0).setChecked(true);
                setMainTitle(gridItem.getTitle());
                switch (position) {
                    case 0:
                        mklineView.showMa();
                        switch (lineType) {
                            case "KLine":
                                mklineView.showMa();
                                break;
                            case "TimeView":
                                mTimeView.showMa();
                                break;
                        }
                        break;
                    case 1:

                        switch (lineType) {
                            case "KLine":
                                mklineView.showEma();
                                break;
                            case "TimeView":
                                mTimeView.showEma();
                                break;
                        }

                        break;
                    case 2:
                        switch (lineType) {
                            case "KLine":
                                mklineView.showBoll();
                                break;
                            case "TimeView":
                                mTimeView.showBoll();
                                break;
                        }
                        break;

                    case 3:

                        switch (lineType) {
                            case "KLine":
                                mklineView.showSar();
                                break;
                            case "TimeView":
                                mTimeView.showSar();
                                break;
                        }
                        break;
                }

            }

            @Override
            public void downPopupDismiss() {
                gridItemPopup_main.dismiss();
            }

            @Override
            public void popupDismiss() {
                initViewGrid();
            }
        });

        //设置默认选择
        gridItemPopup_main.SetDefaultSelect(0);
    }

    /**
     * 指标
     */
    private void initAllGridView() {
        gridItemPopup_all = new CommonGridPopup(rl_all_index, this, R.layout.grid_list_one_popup, all_index, new CommonGridPopup.GridItemClickListen() {
            @Override
            public void getGridItemInfo(int position, GridItem gridItem) {
                gridItemPopup_all.dismiss();
//                mAllGridData.get(0).setChecked(true);
                setAllTitle(gridItem.getTitle());
                switch (position) {
                    case 2:
                        switch (lineType) {
                            case "KLine":
                                mklineView.showRsi();
                                break;
                            case "TimeView":
                                mTimeView.showRsi();
                                break;
                        }
                        break;
                    case 0:
                        switch (lineType) {
                            case "KLine":
                                mklineView.showMacd();
                                break;
                            case "TimeView":
                                mTimeView.showMacd();
                                break;
                        }
                        break;
                    case 1:
                        switch (lineType) {
                            case "KLine":
                                mklineView.showKdj();
                                break;
                            case "TimeView":
                                mTimeView.showKdj();
                                break;
                        }
                        break;
                }
            }

            @Override
            public void downPopupDismiss() {
                gridItemPopup_all.dismiss();
            }

            @Override
            public void popupDismiss() {
                initViewGrid();
            }
        });

        //设置默认选择
        gridItemPopup_all.SetDefaultSelect(0);
    }

    private void setTimeTitle(String itemText) {
        tv_time_index.setText(itemText);
    }

    private void setMainTitle(String itemText) {
        tv_main_index.setText(itemText);
    }

    private void setAllTitle(String itemText) {
        tv_all_index.setText(itemText);
    }

    @OnClick({R.id.rl_time_index, R.id.iv_retract_pic,
            R.id.rl_main_index, R.id.rl_all_index,
            R.id.tv_pair_value, R.id.option,R.id.dingpan})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_time_index:
                gridItemFiveTimePopup_time.showPricePopup();
                rl_time_index.setBackgroundColor(ContextCompat.getColor(this, R.color.color_308CDD));
                tv_time_index.setTextColor(ContextCompat.getColor(this, R.color.white));
                iv_time_index.setVisibility(View.INVISIBLE);
                break;
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

                Intent intent1 = new Intent(mContext, CommonWebViewActivity.class);
                intent1.putExtra("url", sb.toString());
                startActivity(intent1);
                break;


            case R.id.rl_main_index:
                gridItemPopup_main.showPricePopup();
                rl_main_index.setBackgroundColor(ContextCompat.getColor(this, R.color.color_308CDD));
                tv_main_index.setTextColor(ContextCompat.getColor(this, R.color.white));
                iv_main_index.setVisibility(View.INVISIBLE);
                break;

            case R.id.rl_all_index:
                gridItemPopup_all.showPricePopup();
                rl_all_index.setBackgroundColor(ContextCompat.getColor(this, R.color.color_308CDD));
                tv_all_index.setTextColor(ContextCompat.getColor(this, R.color.white));
                iv_all_index.setVisibility(View.INVISIBLE);
                break;

            case R.id.iv_retract_pic:
                finish();
                break;
            case R.id.tv_pair_value:
                if (mRecyclerView.getVisibility() == View.GONE) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    iv_pair.setVisibility(View.INVISIBLE);
                    tv_pair_value.setBackgroundColor(ContextCompat.getColor(this, R.color.color_308CDD));
                    tv_pair_value.setTextColor(ContextCompat.getColor(this, R.color.white));
                } else {
                    iv_pair.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    tv_pair_value.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    tv_pair_value.setTextColor(ContextCompat.getColor(this, R.color.color_626A75));
                }
                break;
            default:
                break;
        }
    }

    private void clickOption() {
        if (isAlAdd) {
            delteOptional();
        } else {
            addOptional();
        }

    }


    private void addOptional() {
        BteTopService.addOption(exchange, baseAsset, quoteAsset)
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {

                        ToastUtils.showShortToast(baseBean.getMessage());
                        if ("-1".equals(baseBean.getCode())) {
                            startActivity(new Intent(FullScreenCoinDetailActivity.this, YZMLoginActivity.class));
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
        BteTopService.deleteOption(exchange, baseAsset, quoteAsset)
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        ToastUtils.showShortToast(baseBean.getMessage());

                        if ("-1".equals(baseBean.getCode())) {
                            startActivity(new Intent(FullScreenCoinDetailActivity.this, YZMLoginActivity.class));
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

    public void initViewGrid() {
        rl_time_index.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tv_time_index.setTextColor(ContextCompat.getColor(this, R.color.color_626A75));
        iv_time_index.setVisibility(View.VISIBLE);
        rl_main_index.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tv_main_index.setTextColor(ContextCompat.getColor(this, R.color.color_626A75));
        iv_main_index.setVisibility(View.VISIBLE);
        rl_all_index.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tv_all_index.setTextColor(ContextCompat.getColor(this, R.color.color_626A75));
        iv_all_index.setVisibility(View.VISIBLE);
    }

    protected void initKLineData(String timeType) {
        BteTopService.getKlineBean(exchange, baseAsset, quoteAsset, 0, 0, timeType)
                .compose(RxUtil.<BaseBean<KlineBean>>mainAsync())
                .subscribe(new Action1<BaseBean<KlineBean>>() {
                    @Override
                    public void call(BaseBean<KlineBean> klineBeanBaseBean) {
                        //获取基本信息 然后赋
                        KlineBean.TickerBean ticker = klineBeanBaseBean.getData().getTicker();
                        InitKlineIconInfo(ticker);

                        List<KlineBean.KlineBeanInner> listBaseBean = klineBeanBaseBean.getData().getKline();
                        if (mData != null && mData.size() > 0) {
                            mData.clear();
                        }
                        for (int i = 0; i < listBaseBean.size(); i++) {
                            KlineBean.KlineBeanInner item = listBaseBean.get(i);

                            KLineFullData hisData = new KLineFullData(item.getOpen(),
                                    item.getClose(), item.getHigh(),
                                    item.getLow(), item.getVol(),
                                    item.getDate());
                            mData.add(hisData);
                        }
                        if (mData != null && mData.size() > 0) {
                            if (lineType.equalsIgnoreCase("TimeView")) {
                                mTimeView.initDataTime(mData);
//                                mTimeView.setInfoOneLine(1);
                            } else {
                                mklineView.initDataKline(mData);
                                mklineView.setLimitLine();
                            }
                        }
                        mklineView.LeftLoadMoreDragTrue();
                        mTimeView.LeftLoadMoreDragTrue();

                        if (klineBeanBaseBean.getData().getKline() == null
                                || klineBeanBaseBean.getData().getKline().size() == 0) {
                            mTimeView.setVisibility(View.INVISIBLE);
                            mklineView.setVisibility(View.INVISIBLE);
                        } else {
                            //根据类型来判断
                            showKlineViewOrTimeView(lineType);
                        }
                        setKLineInfo(listBaseBean.size() - 1);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //请求失败后也要打开滑动
                        mklineView.LeftLoadMoreDragTrue();
                        mTimeView.LeftLoadMoreDragTrue();


                    }
                });

    }

    private void InitKlineIconInfo(KlineBean.TickerBean ticker) {
        if (ticker == null) {
            return;
        }
        if (ticker.getPair() == null && ticker.getSymbol() != null) {
            tv_pair_value.setText("");
        } else {
            tv_pair_value.setText(ticker.getSymbol() + "/" + ticker.getPair());
        }

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "font/DINMittelschrift-Alternate.otf");
        price.setTypeface(typeface);
        tv_pair_name.setText(ticker.getExchange());
        price.setText("$" + ticker.getPrice());

        cnyPrice.setText("≈" + ticker.getCnyPrice() + "CNY");

        tv_price_percent.setText(ticker.getFormatChange());
        tv_price_percent.setTextColor(ticker.getFromatColor());

    }


    private String priceConvert(double value) {
        String s;
        if (value > 10000) {
            s = (int) (value / 10000) + "万";
        } else if (value > 1000) {
            s = (int) (value / 1000) + "千";
        } else if (value > 100000) {
            s = (int) (value / 100000) + "十万";
        } else if (value > 1000000) {
            s = (int) (value / 1000000) + "百万";
        } else if (value > 10000000) {
            s = (int) (value / 10000000) + "千万";
        } else if (value > 100000000) {
            s = (int) (value / 100000000) + "亿";
        } else {
            s = (int) value + "";
        }
        return s;
    }


    /**
     * 参数都应该提成全局的
     *
     * @param lineType
     */
    private void refreshLast(final String lineType) {

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
                        InitKlineIconInfo(ticker);

                        handler.sendEmptyMessageDelayed(0, intervalTime);
                        if (!mRefreshLast) {
                            return;
                        }
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
                        switch (lineType) {
                            case "KLine":
                                mklineView.addDataLast(newDataList);
                                break;
                            case "TimeView":
                                mTimeView.addDataLast(newDataList);
                                break;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        handler.sendEmptyMessageDelayed(0, intervalTime);
                    }
                });


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
        LoadMoreKLineData(0, "1m");
        mklineView.LeftLoadMoreDragFalse();
    }

    @Override
    public void RightLoadMore() {
        mRefreshLast = true;
        startLoop();
    }

    @Override
    public void noNeedRefreshRight() {
        mRefreshLast = false;
        endLoop();
    }

    protected void LoadMoreKLineData(long start, String timeType) {
        long endTime = mData.get(0).getDate();
        BteTopService.getKlineBean(exchange, baseAsset, quoteAsset, endTime, 0, timeType)
                .compose(RxUtil.<BaseBean<KlineBean>>mainAsync())
                .subscribe(new Action1<BaseBean<KlineBean>>() {
                    @Override
                    public void call(BaseBean<KlineBean> klineBeanBaseBean) {
                        //验证
                        if (!"0000".equalsIgnoreCase(klineBeanBaseBean.getCode()) ||
                                klineBeanBaseBean.getData() == null ||
                                klineBeanBaseBean.getData().getKline() == null ||
                                klineBeanBaseBean.getData().getKline().size() == 0) {

                            return;
                        }

                        List<KlineBean.KlineBeanInner> listBaseBean = klineBeanBaseBean.getData().getKline();
                        List<KLineFullData> loadMoreData = new ArrayList<>(klineBeanBaseBean.getData().getKline().size());
                        for (int i = 0; i < listBaseBean.size(); i++) {
                            KlineBean.KlineBeanInner item = listBaseBean.get(i);

                            KLineFullData hisData = new KLineFullData(item.getOpen(),
                                    item.getClose(), item.getHigh(),
                                    item.getLow(), item.getVol(),
                                    item.getDate());
                            loadMoreData.add(hisData);
                        }
                        //更新mdata
                        mData.addAll(0, loadMoreData);
                        mklineView.addDataFirst(mData);
                        mklineView.LeftLoadMoreDragTrue();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        //请求失败后也要打开滑动
                        mklineView.LeftLoadMoreDragTrue();
                    }
                });
    }

    private void startLoop() {
        if (mRefreshLast) {
            handler.removeMessages(0);
            handler.sendEmptyMessageDelayed(0, intervalTime);
        }
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
        KLineFullData hisData = mData.get(currentIndex);
        KLineFullData PreHisdata = mData.get(preIndex);
        double Preclose = PreHisdata.getClose();
        double v = ((hisData.getClose() - Preclose) / Preclose) * 100;

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/DINMittelschrift-Alternate.otf");
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

        //涨幅 振幅
        if (v > 0) {
            tv_time_line_up_values.setTextColor(ContextCompat.getColor(this, R.color.main_color_red));
            tv_time_line_up_values.setText(DoubleUtil.format2Decimal(v) + "%");
        } else {
            tv_time_line_up_values.setTextColor(ContextCompat.getColor(this, R.color.main_color_green));
            tv_time_line_up_values.setText(DoubleUtil.format2Decimal(v) + "%");
        }
        tv_time_line_down_values.setText(DoubleUtil.format2Decimal(((hisData.getHigh() - hisData.getLow()) / Preclose) / 100) + "%");

    }

}
