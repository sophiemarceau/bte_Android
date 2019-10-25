package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.KLineFullData;
import com.guoziwei.klinelib.util.DataUtils;
import com.guoziwei.klinelib.util.DisplayUtils;
import com.guoziwei.klinelib.util.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * kline
 * Created by guoziwei on 2017/10/26.
 */
public class KLineViewSimple extends BaseView implements CoupleChartGestureListener.InfoListener {

    private static final String TAG = "KLineView";

    protected AppCombinedChart mChartPrice;
    protected AppCombinedChart mChartVolume;
    protected AppCombinedChart mChartExtra;
    protected TextView
            tv_time_line_up_values,
            tv_time_line_down_values,
            tv_time_line_up,
            tv_time_line_down;
    protected RelativeLayout rl_coin_info;

    protected Context mContext;

    private TextView tvMainIndicators, tvMiddleIndicators, tvExtraIndicators;

    /**
     * last price
     */
    private double mLastPrice;

    /**
     * yesterday close price
     */
    private double mLastClose;

    private float x, y;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setOffset();
        }
    };
    private CoupleChartGestureListener mCoupleChartGestureListener;
    private CoupleChartGestureListener.InfoListener minfoListener;

    private boolean showCommentInfo = true;


    public KLineViewSimple(Context context) {
        this(context, null);
    }

    public KLineViewSimple(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KLineViewSimple(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initUI(context);
    }

    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_kline_simple, this);
        mChartPrice = (AppCombinedChart) findViewById(R.id.price_chart);
        mChartVolume = (AppCombinedChart) findViewById(R.id.vol_chart);
        mChartExtra = (AppCombinedChart) findViewById(R.id.extra_chart);

        //涨跌幅
        tv_time_line_up = findViewById(R.id.tv_time_line_up);
        tv_time_line_down = findViewById(R.id.tv_time_line_down);
        tv_time_line_up_values = findViewById(R.id.tv_time_line_up_values);
        tv_time_line_down_values = findViewById(R.id.tv_time_line_down_values);


        rl_coin_info = findViewById(R.id.rl_coin_info);
        mChartPrice.setNoDataText(context.getString(R.string.loading));
        mChartVolume.setNoDataText(context.getString(R.string.loading));
        mChartExtra.setNoDataText(context.getString(R.string.loading));

        tvMainIndicators = findViewById(R.id.tv_main_indicators);
        tvMiddleIndicators = findViewById(R.id.tv_middle_indicators);
        tvExtraIndicators = findViewById(R.id.tv_extra_indicators);

        initChartPrice();
        initMiddleChart(mChartVolume);
        initBottomChart(mChartExtra);
        handler.sendEmptyMessageDelayed(0, 0);
        initChartListener();
        setKlineInfoListener(minfoListener);

    }

    public void showKdj() {
        setChartKdjData();
        updateIndicators(-1);
    }

    public void showMacd() {
        setChartMacdData();
        updateIndicators(-1);
    }

    public void showRsi() {
        setChartRsiData();
        updateIndicators(-1);
    }


    public void showBoll() {
        setBollKlineByChart(mChartPrice);
        updateIndicators(-1);
    }

    public void showMa() {
        setMaKlineByChart(mChartPrice);
        updateIndicators(-1);
    }

    public void showEma(){
        setEmalineByChart(mChartPrice);
        updateIndicators(-1);
    }

    public void showSar(){
        setSarKlineByChart(mChartPrice);
        updateIndicators(-1);
    }

    protected void initChartPrice() {
        mChartPrice.setHighlightPerTapEnabled(false);
        mChartPrice.setDoubleTapToZoomEnabled(false);
        mChartPrice.setBorderColor(Color.parseColor("#D1D1D6"));
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(true);
        mChartPrice.setBorderWidth(1);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleYEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        //手松开后，继续滑动
        mChartPrice.setDragDecelerationEnabled(true);

        mChartPrice.setDragOffsetX(offsetX);
        mChartPrice.getDescription().setEnabled(false);

        //x轴坐标
//        LineChartXMarkerView mvx = new LineChartXMarkerView(mContext, mData);
//        mvx.setChartView(mChartPrice);
//        mChartPrice.setXMarker(mvx);

        LineChartYMarkerView mvy = new LineChartYMarkerView(mContext, 2);
        mvy.setChartView(mChartPrice);
        mChartPrice.setMarker(mvy);

        //k线短评
        MyMarkerView markerView = new MyMarkerView(mContext, R.layout.kline_marker, mData);
        mChartPrice.setmBubbleMarker(markerView);

        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(false);

        XAxis xAxisPrice = mChartPrice.getXAxis();

        xAxisPrice.setDrawLabels(false);
        xAxisPrice.setDrawAxisLine(false);
        xAxisPrice.setDrawGridLines(false);
        xAxisPrice.setAxisMinimum(-0.5f);


        //左边轴线不显示
        YAxis axisLeftPrice = mChartPrice.getAxisLeft();
        axisLeftPrice.setDrawGridLines(false);
        axisLeftPrice.setEnabled(true);

        //右边显示
        YAxis axisRightPrice = mChartPrice.getAxisRight();
        axisRightPrice.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightPrice.setLabelCount(5, true);
        axisRightPrice.setDrawLabels(true);
        axisRightPrice.setDrawGridLines(false);
        axisRightPrice.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DoubleUtil.formatDataCompare10(value);
            }
        });

        axisRightPrice.setAxisLineWidth(0.2f);
        axisRightPrice.setAxisLineColor(Color.parseColor("#ADAFB6"));


        //设置标签Y渲染器
        Transformer rightYTransformer = mChartPrice.getRendererRightYAxis().getTransformer();
        PriceContentYAxisRenderer priceContentYAxisRenderer = new PriceContentYAxisRenderer(mChartPrice.getViewPortHandler(), mChartPrice.getAxisRight(), rightYTransformer);
        priceContentYAxisRenderer.setMarker(mvy);
        priceContentYAxisRenderer.setLabelInContent(true);
        priceContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        mChartPrice.setRendererRightYAxis(priceContentYAxisRenderer);

    }


    public void setKlineInfoListener(CoupleChartGestureListener.InfoListener infoListener) {
        this.minfoListener = infoListener;
        mCoupleChartGestureListener.setInfoListener(minfoListener, this);
    }

    private void initChartListener() {
        mCoupleChartGestureListener = new CoupleChartGestureListener(mChartPrice, mChartVolume, mChartExtra);
        mChartPrice.setOnChartGestureListener(mCoupleChartGestureListener);
        mChartPrice.setOnChartValueSelectedListener(mCoupleChartGestureListener);
        mChartPrice.setOnTouchListener(mCoupleChartGestureListener);
    }

    /**
     * K线图初始化
     */
    public void initDataKline(List<KLineFullData> hisDatas) {
        addLast = false;
        removeAllViews();
        initUI(mContext);
        mData.clear();
        mData.addAll(DataUtils.calculateHisData(hisDatas));
        mChartPrice.setRealCount(mData.size());
        refreshMainChart();
        moveToLast(mChartPrice);
        //显示最后一个bobble
        Highlight highlight = new Highlight(x, y, 0);
        highlight.setDataIndex(2);
        mChartPrice.highlightValue(highlight, false);
        initChartVolumeData();


        refreshExtraChart();

        mChartPrice.getXAxis().setAxisMaximum(mChartPrice.getData().getXMax() + 0.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 0.5f);
        mChartExtra.getXAxis().setAxisMaximum(mChartExtra.getData().getXMax() + 0.5f);
//        mChartExtra.getAxisLeft().setAxisMaximum(mChartExtra.getYMax() * 1.5f);


//        mChartPrice.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
//        mChartVolume.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
//        mChartMacd.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
//        mChartKdj.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);
//        mChartRsi.zoom(MAX_COUNT * 1f / INIT_COUNT, 0, 0, 0);


        updateIndicators(-1);
        //取最后一条数据 设置高开低收数据
        setKLineInfo(hisDatas.size() - 1);
        //
        if (minfoListener!=null) {
            if (mData.size()>0) {
                minfoListener.getMarkerDataIndex(mData.size()-1);
            }
        }
        mLastClose = hisDatas.get(hisDatas.size() - 1).getClose();

        setLimitLine();

    }

    private void getBubbleItemBean(ArrayList<BubbleEntry> bubbleEntries, int i, KLineFullData hisData) {
        if (!TextUtils.isEmpty(hisData.getBubbleText())) {
            BubbleEntry bubbleEntry = new BubbleEntry(i, (float) hisData.getBubbleY(), 3f);
            bubbleEntry.setmBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.inner_cricle));
            bubbleEntries.add(bubbleEntry);

            x = i;
            y = (float) hisData.getBubbleY();
        }
    }

    public void hiddenInfo() {
        IBarLineScatterCandleBubbleDataSet<? extends Entry> bubblel = mChartPrice.getData().getDataSetByLabel("bubble", true);
        mChartPrice.getData().removeDataSet(bubblel);
        mChartPrice.getData().notifyDataChanged();
        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        showCommentInfo = false;
    }

    public void showInfo() {
        showCommentInfo = true;

        refreshMainChart();
        updateIndicators(-1);
        //显示最后一个bobble
        Highlight highlight = new Highlight(x, y, 0);
        highlight.setDataIndex(2);
        mChartPrice.highlightValue(highlight, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setOffset();
        mChartPrice.invalidate();
    }

    public void setMaKlineByChart(AppCombinedChart combinedChart) {
        mainIndicators = 0;
        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma5Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma10Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma20Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma30Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));

            if (!Double.isNaN(hisData.getMa5())) {
                ma5Entries.add(new Entry(i, (float) hisData.getMa5()));
            }

            if (!Double.isNaN(hisData.getMa10())) {
                ma10Entries.add(new Entry(i, (float) hisData.getMa10()));
            }

            if (!Double.isNaN(hisData.getMa20())) {
                ma20Entries.add(new Entry(i, (float) hisData.getMa20()));
            }

            if (!Double.isNaN(hisData.getMa30())) {
                ma30Entries.add(new Entry(i, (float) hisData.getMa30()));
            }

            getBubbleItemBean(bubbleEntries, i, hisData);
        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }

        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MA5, ma5Entries),
                setLine(MA10, ma10Entries),
                setLine(MA20, ma20Entries),
                setLine(MA30, ma30Entries));

        CombinedData combinedData = new CombinedData();
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));

        if (bubbleEntries.size() > 0 && showCommentInfo) {
            BubbleData bubbleData = new BubbleData(setBubbleDataSet(bubbleEntries));
            combinedData.setData(bubbleData);
        }
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);


        combinedChart.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();
    }



    public void setEmalineByChart(AppCombinedChart combinedChart) {
        mainIndicators = 1;
        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ema7Entries=new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ema30Entries=new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>(INIT_COUNT);


        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));

            if (!Double.isNaN(hisData.getEma7())) {
                ema7Entries.add(new Entry(i, (float) hisData.getEma7()));
            }

            if (!Double.isNaN(hisData.getEma30())) {
                ema30Entries.add(new Entry(i, (float) hisData.getEma30()));
            }


            getBubbleItemBean(bubbleEntries, i, hisData);


        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }


        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(EMA7, ema7Entries),
                setLine(EMA30, ema30Entries)
        );

        CombinedData combinedData = new CombinedData();
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));

        if (bubbleEntries.size() > 0 && showCommentInfo) {
            BubbleData bubbleData = new BubbleData(setBubbleDataSet(bubbleEntries));
            combinedData.setData(bubbleData);
        }
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);


        combinedChart.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();
    }


    public void setBollKlineByChart(AppCombinedChart combinedChart) {
        mainIndicators = 2;
        ArrayList<Entry> upEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> mbEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> dnEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>(INIT_COUNT);

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));

            if (!Double.isNaN(hisData.getUb())) {
                upEntries.add(new Entry(i, (float) hisData.getUb()));
            }

            if (!Double.isNaN(hisData.getMb())) {
                mbEntries.add(new Entry(i, (float) hisData.getMb()));
            }

            if (!Double.isNaN(hisData.getDn())) {
                dnEntries.add(new Entry(i, (float) hisData.getDn()));
            }

            getBubbleItemBean(bubbleEntries, i, hisData);


        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }


        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MB, mbEntries),
                setLine(UP, upEntries),
                setLine(DN, dnEntries)
        );

        CombinedData combinedData = new CombinedData();
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));

        if (bubbleEntries.size() > 0 && showCommentInfo) {
            BubbleData bubbleData = new BubbleData(setBubbleDataSet( bubbleEntries));
            combinedData.setData(bubbleData);
        }
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);

        combinedChart.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();

    }

    public void setSarKlineByChart(AppCombinedChart combinedChart){
        mainIndicators = 3;
        ArrayList<BubbleEntry> sarEntries=new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>(INIT_COUNT);

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));


            if (!Double.isNaN(hisData.getSar())) {
                BubbleEntry sarEntry = new BubbleEntry(i, (float) hisData.getSar(), 3f);
                sarEntry.setDrawMarker(false);
                sarEntries.add(sarEntry);
            }

            getBubbleItemBean(bubbleEntries, i, hisData);


        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }


        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries)
        );



        CombinedData combinedData = new CombinedData();
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));

        if (bubbleEntries.size() > 0 && showCommentInfo) {
            BubbleData bubbleData = new BubbleData(
                    setBubbleDataSet( bubbleEntries),
                    setSarDataSet(sarEntries));
            combinedData.setData(bubbleData);
        }else {
            BubbleData bubbleData=new BubbleData(
                    setSarDataSet(sarEntries)
            );
            combinedData.setData(bubbleData);
        }

        combinedData.setData(lineData);
        combinedData.setData(candleData);
        combinedChart.setData(combinedData);

        combinedChart.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        combinedChart.notifyDataSetChanged();
        combinedChart.invalidate();
    }


    private void initChartVolumeData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, (float) t.getVol(), t));
        }
        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        mChartVolume.setData(combinedData);

        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartVolume.notifyDataSetChanged();
//        mChartVolume.moveViewToX(combinedData.getEntryCount());
        moveToLast(mChartVolume);

    }

    private void setChartVolumeData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, (float) t.getVol(), t));
        }
        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        mChartVolume.setData(combinedData);

        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartVolume.notifyDataSetChanged();
//        mChartVolume.moveViewToX(combinedData.getEntryCount());
        mChartVolume.invalidate();


//        mChartVolume.moveViewToX(combinedData.getEntryCount());

    }


    private void initChartRsiData() {
        extraIndicators = 2;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> rsiEntries6 = new ArrayList<>();
        ArrayList<Entry> rsiEntries12 = new ArrayList<>();
        ArrayList<Entry> rsiEntries24 = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, 0));
            rsiEntries6.add(new Entry(i, (float) t.getRsi6()));
            rsiEntries12.add(new Entry(i, (float) t.getRsi12()));
            rsiEntries24.add(new Entry(i, (float) t.getRsi24()));
        }

        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        LineData lineData = new LineData(
                setLine(RSI6, rsiEntries6),
                setLine(RSI12, rsiEntries12),
                setLine(RSI24, rsiEntries24));

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();

        combinedData.setData(barData);
        combinedData.setData(lineData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();
        moveToLast(mChartExtra);

    }

    private void addChartRsiData() {
        extraIndicators = 2;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> rsiEntries6 = new ArrayList<>();
        ArrayList<Entry> rsiEntries12 = new ArrayList<>();
        ArrayList<Entry> rsiEntries24 = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, 0));
            rsiEntries6.add(new Entry(i, (float) t.getRsi6()));
            rsiEntries12.add(new Entry(i, (float) t.getRsi12()));
            rsiEntries24.add(new Entry(i, (float) t.getRsi24()));
        }

        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        LineData lineData = new LineData(
                setLine(RSI6, rsiEntries6),
                setLine(RSI12, rsiEntries12),
                setLine(RSI24, rsiEntries24));

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        combinedData.setData(lineData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();
        mChartExtra.invalidate();
    }

    private void setChartRsiData() {
        extraIndicators = 2;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> rsiEntries6 = new ArrayList<>();
        ArrayList<Entry> rsiEntries12 = new ArrayList<>();
        ArrayList<Entry> rsiEntries24 = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, 0));

            if (!Double.isNaN(t.getRsi6())) {
                rsiEntries6.add(new Entry(i, (float) t.getRsi6()));
            }

            if (!Double.isNaN(t.getRsi12())) {
                rsiEntries12.add(new Entry(i, (float) t.getRsi12()));
            }

            if (!Double.isNaN(t.getRsi24())) {
                rsiEntries24.add(new Entry(i, (float) t.getRsi24()));
            }

        }

        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        LineData lineData = new LineData(
                setLine(RSI6, rsiEntries6),
                setLine(RSI12, rsiEntries12),
                setLine(RSI24, rsiEntries24));

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        combinedData.setData(lineData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();
//        moveToLast(mChartExtra);
        mChartExtra.invalidate();


    }

    private void initChartMacdData() {
        extraIndicators = 0;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        ArrayList<Entry> difEntries = new ArrayList<>();
        ArrayList<Entry> deaEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, (float) t.getMacd()));
            difEntries.add(new Entry(i, (float) t.getDif()));
            deaEntries.add(new Entry(i, (float) t.getDea()));
        }
        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        LineData lineData = new LineData(setLine(DIF, difEntries), setLine(DEA, deaEntries));
        combinedData.setData(lineData);
        mChartExtra.setData(combinedData);


        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartExtra.notifyDataSetChanged();
//        mChartVolume.moveViewToX(combinedData.getEntryCount());
        moveToLast(mChartExtra);
    }



    private void setChartMacdData() {
        extraIndicators = 0;
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<BarEntry> paddingEntries = new ArrayList<>();
        ArrayList<Entry> difEntries = new ArrayList<>();
        ArrayList<Entry> deaEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KLineFullData t = mData.get(i);
            barEntries.add(new BarEntry(i, (float) t.getMacd()));
            difEntries.add(new Entry(i, (float) t.getDif()));
            deaEntries.add(new Entry(i, (float) t.getDea()));
        }
        int maxCount = MAX_COUNT;
        if (!mData.isEmpty() && mData.size() < maxCount) {
            for (int i = mData.size(); i < maxCount; i++) {
                paddingEntries.add(new BarEntry(i, 0));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE), setBar(paddingEntries, INVISIABLE_LINE));
        barData.setBarWidth(0.75f);
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        LineData lineData = new LineData(setLine(DIF, difEntries), setLine(DEA, deaEntries));
        combinedData.setData(lineData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();
//        moveToLast(mChartExtra);
        mChartExtra.invalidate();


    }



    private void addlastKdjData() {
        extraIndicators = 1;
        //barEntries就是为了只让显示y轴的high
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> kEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> dEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> jEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            kEntries.add(new Entry(i, (float) mData.get(i).getK()));
            dEntries.add(new Entry(i, (float) mData.get(i).getD()));
            jEntries.add(new Entry(i, (float) mData.get(i).getJ()));
            barEntries.add(new BarEntry(i, 0));
        }
        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getK()));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE));
        barData.setBarWidth(0.75f);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(K, kEntries));
        sets.add(setLine(D, dEntries));
        sets.add(setLine(J, jEntries));
        sets.add(setLine(INVISIABLE_LINE, paddingEntries));
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();

        mChartExtra.invalidate();
    }

    private void setChartKdjData() {
        extraIndicators = 1;
        //barEntries就是为了只让显示y轴的high
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<Entry> kEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> dEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> jEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            kEntries.add(new Entry(i, (float) mData.get(i).getK()));
            dEntries.add(new Entry(i, (float) mData.get(i).getD()));
            jEntries.add(new Entry(i, (float) mData.get(i).getJ()));
            barEntries.add(new BarEntry(i, 0));
        }
        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getK()));
            }
        }

        BarData barData = new BarData(setBar(barEntries, NORMAL_LINE));
        barData.setBarWidth(0.75f);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(setLine(K, kEntries));
        sets.add(setLine(D, dEntries));
        sets.add(setLine(J, jEntries));
        sets.add(setLine(INVISIABLE_LINE, paddingEntries));
        LineData lineData = new LineData(sets);

        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(barData);
        mChartExtra.setData(combinedData);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.notifyDataSetChanged();
//        moveToLast(mChartExtra);

        mChartExtra.invalidate();

    }


    /**
     * according to the price to refresh the last data of the chart
     */
    public void refreshData(float price) {
        if (price <= 0 || price == mLastPrice) {
            return;
        }
        mLastPrice = price;
        CombinedData data = mChartPrice.getData();
        if (data == null) return;
        LineData lineData = data.getLineData();
        if (lineData != null) {
            ILineDataSet set = lineData.getDataSetByIndex(0);
            if (set.removeLast()) {
                set.addEntry(new Entry(set.getEntryCount(), price));
            }
        }
        CandleData candleData = data.getCandleData();
        if (candleData != null) {
            ICandleDataSet set = candleData.getDataSetByIndex(0);
            if (set.removeLast()) {
                KLineFullData hisData = mData.get(mData.size() - 1);
                hisData.setClose(price);
                hisData.setHigh(Math.max(hisData.getHigh(), price));
                hisData.setLow(Math.min(hisData.getLow(), price));
                set.addEntry(new CandleEntry(set.getEntryCount(), (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), price));

            }
        }
        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
    }

    public void addDataFirst(List<KLineFullData> newData) {
        //添加到最前面，没有实现
//        if (mainIndicators) {
//            setMaDataKLine(newData);
//        } else {
//            setBollDataKline(newData);
//        }
        updateIndicators(-1);
    }


    private void setBollDataKline(List<KLineFullData> newData) {


        mData.clear();
        mData.addAll(DataUtils.calculateHisData(newData));
        mChartPrice.setRealCount(mData.size());

        ArrayList<Entry> upEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> mbEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> dnEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));

            if (!Double.isNaN(hisData.getUb())) {
                upEntries.add(new Entry(i, (float) hisData.getUb()));
            }

            if (!Double.isNaN(hisData.getMb())) {
                mbEntries.add(new Entry(i, (float) hisData.getMb()));
            }

            if (!Double.isNaN(hisData.getDn())) {
                dnEntries.add(new Entry(i, (float) hisData.getDn()));
            }


        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }


        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MB, mbEntries),
                setLine(UP, upEntries),
                setLine(DN, dnEntries)
        );

        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        mChartPrice.setData(combinedData);
        mChartPrice.notifyDataSetChanged();
        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        setChartVolumeData();


        //刷新附图
        refreshExtraChart();

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 0.5f);
        mChartExtra.getXAxis().setAxisMaximum(mChartExtra.getData().getXMax() + 0.5f);
    }

    private void setMaDataKLine(List<KLineFullData> newData) {
        mData.clear();
        mData.addAll(DataUtils.calculateHisData(newData));
        mChartPrice.setRealCount(mData.size());

        ArrayList<CandleEntry> lineCJEntries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma5Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma10Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma20Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> ma30Entries = new ArrayList<>(INIT_COUNT);
        ArrayList<Entry> paddingEntries = new ArrayList<>(INIT_COUNT);

        for (int i = 0; i < mData.size(); i++) {
            KLineFullData hisData = mData.get(i);
            lineCJEntries.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));

            if (!Double.isNaN(hisData.getMa5())) {
                ma5Entries.add(new Entry(i, (float) hisData.getMa5()));
            }

            if (!Double.isNaN(hisData.getMa10())) {
                ma10Entries.add(new Entry(i, (float) hisData.getMa10()));
            }

            if (!Double.isNaN(hisData.getMa20())) {
                ma20Entries.add(new Entry(i, (float) hisData.getMa20()));
            }

            if (!Double.isNaN(hisData.getMa30())) {
                ma30Entries.add(new Entry(i, (float) hisData.getMa30()));
            }
        }

        if (!mData.isEmpty() && mData.size() < MAX_COUNT) {
            for (int i = mData.size(); i < MAX_COUNT; i++) {
                paddingEntries.add(new Entry(i, (float) mData.get(mData.size() - 1).getClose()));
            }
        }

        LineData lineData = new LineData(
                setLine(INVISIABLE_LINE, paddingEntries),
                setLine(MA5, ma5Entries),
                setLine(MA10, ma10Entries),
                setLine(MA20, ma20Entries),
                setLine(MA30, ma30Entries));
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, lineCJEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);
        mChartPrice.setData(combinedData);
        mChartPrice.notifyDataSetChanged();
        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(combinedData.getXMax() + 0.5f);

        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        setChartVolumeData();


        //刷新附图
        refreshExtraChart();

        mChartPrice.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartVolume.setVisibleXRange(MAX_COUNT, MIN_COUNT);
        mChartExtra.setVisibleXRange(MAX_COUNT, MIN_COUNT);

        mChartPrice.getXAxis().setAxisMaximum(mChartPrice.getData().getXMax() + 0.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 0.5f);
        mChartExtra.getXAxis().setAxisMaximum(mChartExtra.getData().getXMax() + 0.5f);


    }

//    private void addLastRefrestExtraChart() {
//        switch (extraIndicators) {
//            case 0:
//                addLastMacdData();
//                break;
//            case 1:
//                addlastKdjData();
//                break;
//            case 2:
//                addChartRsiData();
//                break;
//        }
//    }

    private void refreshExtraChart() {
        switch (extraIndicators) {
            case 0:
                setChartMacdData();
                break;
            case 1:
                setChartKdjData();
                break;
            case 2:
                setChartRsiData();
                break;
        }
    }

    private void refreshMainChart() {

        switch (mainIndicators) {
            case 0:
                setMaKlineByChart(mChartPrice);
                break;
            case 1:
                setEmalineByChart(mChartPrice);
                break;
            case 2:
                setBollKlineByChart(mChartPrice);
                break;
            case 3:
                setSarKlineByChart(mChartPrice);
                break;
        }
    }

    //k线图
    public void addDataLast(List<KLineFullData> newData) {
        List<KLineFullData> completeNewDataLists = DataUtils.calculateRightListHisData(newData, mData);
        if (completeNewDataLists == null || completeNewDataLists.size() == 0) {
            return;
        }
        addLast = true;

        mData.clear();
        mData.addAll(completeNewDataLists);
        mChartPrice.setRealCount(mData.size());

        refreshMainChart();


        setChartVolumeData();

//        addLastRefrestExtraChart();
        refreshExtraChart();

        mChartPrice.getXAxis().setAxisMaximum(mChartPrice.getData().getXMax() + 1.5f);
        mChartVolume.getXAxis().setAxisMaximum(mChartVolume.getData().getXMax() + 1.5f);
        mChartExtra.getXAxis().setAxisMaximum(mChartExtra.getData().getXMax() + 1.5f);

        mLastClose = newData.get(newData.size() - 1).getClose();
        setLimitLine();
    }


    /**
     * align two chart
     */
    private void setOffset() {
        mChartPrice.animateX(300);
        mChartVolume.animateX(300);
        mChartExtra.animateX(300);
        int chartHeight = getResources().getDimensionPixelSize(R.dimen.all_bottom_chart_height);
        int rightPadding = getResources().getDimensionPixelSize(R.dimen.right_padding);
        int bottom = DisplayUtils.dip2px(mContext, 20);
        mChartPrice.setViewPortOffsets(0, bottom, rightPadding, 0);
        mChartVolume.setViewPortOffsets(0, 0, rightPadding, 0);
        mChartExtra.setViewPortOffsets(0, 0, rightPadding, bottom);
        moveToLast(mChartPrice);
        moveToLast(mChartVolume);
        moveToLast(mChartExtra);
    }



    public void setLimitLine() {
        setLimitLine(mLastClose);
    }


    @Override
    public void getMarkerDataIndex(int dataIndex) {
        updateIndicators(dataIndex);
        setKLineInfo(dataIndex);
        setBubbleHighlight(dataIndex);
    }

    private void setBubbleHighlight(int dataIndex) {
        KLineFullData data = mData.get(dataIndex);
        if (!TextUtils.isEmpty(data.getBubbleText()) && showCommentInfo) {
            Highlight highlight = new Highlight(dataIndex,(float) data.getBubbleY(), 0);
            highlight.setDataIndex(2);
            mChartPrice.highlightValue(highlight, false);

        }
    }


    //设置高开低收
    public void setKLineInfo(int dataIndex) {
        if (landScape == true) {
            //隐藏
            tv_time_line_up.setVisibility(GONE);
            tv_time_line_down.setVisibility(GONE);
            tv_time_line_up_values.setVisibility(GONE);
            tv_time_line_down_values.setVisibility(GONE);
            return;
        }
        int currentIndex,preIndex;

        if (dataIndex == 0) {
            currentIndex=0;
            preIndex=0;
        }else {
            currentIndex=dataIndex;
            preIndex=dataIndex-1;
        }
        KLineFullData hisData = mData.get(currentIndex);
        KLineFullData PreHisdata = mData.get(preIndex);
        double Preclose = PreHisdata.getClose();
        double v = ((hisData.getClose() - Preclose) / Preclose) * 100;

                if (v > 0) {
                    tv_time_line_up_values.setTextColor(ContextCompat.getColor(mContext, R.color.main_info_color_green));
                    tv_time_line_up_values.setText(DoubleUtil.format2Decimal(v) + "%");
                } else {
                    tv_time_line_up_values.setTextColor(ContextCompat.getColor(mContext, R.color.main_info_color_red));
                    tv_time_line_up_values.setText(DoubleUtil.format2Decimal(v) + "%");
                }
        double swing=(hisData.getHigh()-hisData.getLow())/Preclose*100;
        tv_time_line_down_values.setText(DoubleUtil.format2Decimal(swing) + "%");


    }


    @Override
    public void marketHidden() {
//        rl_one_coin_info.setVisibility(GONE);
//        rl_coin_info.setVisibility(GONE);
        updateIndicators(-1);
    }

    @Override
    public void leftLoadMore() {

    }

    @Override
    public void RightLoadMore() {

        Log.e(TAG, "RightLoadMore: ");
    }

    @Override
    public void noNeedRefreshRight() {
        Log.e(TAG, "noNeedRefreshRight: ");

    }


    public void LeftLoadMoreDragTrue() {
        mCoupleChartGestureListener.setLeftLoadMoreDragTrue();
    }

    public void LeftLoadMoreDragFalse() {
        mCoupleChartGestureListener.setLeftLoadMoreDragFalse();
    }

    /**
     * -1 最后一个数据 else 当前坐标点的数据
     */
    private void updateIndicators(int index) {
        KLineFullData data;
        if (mData == null || mData.size() == 0) {
            return;
        }
        int ma5Color = getResources().getColor(R.color.ma5);
        if (index == -1) {
            data = mData.get(mData.size() - 1);
        } else {
            //下标越界
            if (index < 0 || index > mData.size()) {
                return;
            }
            data = mData.get(index);
        }
        //主图
        Spanned tvMainString=Utils.fromHtml("");
        switch (mainIndicators) {
            case 0:
                tvMainString = Utils.fromHtml("<font color='#F0BC79'>MA5:" + DoubleUtil.formatDataCompare10(data.getMa5()) + "</font> " +
                        "<font color='#E39FE3'> MA10:" + DoubleUtil.formatDataCompare10(data.getMa10()) + "</font> " +
//                    "<font color='#34A9FF'> MA20:" + DoubleUtil.getStringByDigits(data.getMa20()) + "</font> " +
                        "<font color='#aed3d3'> MA30:" + DoubleUtil.formatDataCompare10(data.getMa30()) + "</font> ");
                break;
            case 1:
                tvMainString = Utils.fromHtml("<font color='#30D2B3'>EMA7:" + DoubleUtil.formatDataCompare10(data.getEma7()) + "</font> " +
                        "<font color='#FFB400'> EMA30:" + DoubleUtil.formatDataCompare10(data.getEma30()) + "</font> ");
                break;
            case 2:
                tvMainString = Utils.fromHtml("<font color='#808790'>BOLL(20,2):</font> " +
                        "<font color='#78C078'> BOLL:" + DoubleUtil.formatDataCompare10(data.getMb()) + "</font>" +
                        "<font color='#F0BC79'> UB:" + DoubleUtil.formatDataCompare10(data.getUb()) + "</font> " +
                        "<font color='#E39FE3'> LB:" + DoubleUtil.formatDataCompare10(data.getDn()) + "</font> ");
                break;
            case 3:
                 tvMainString = Utils.fromHtml("<font color='#808790'>SAR(2,20):</font> " +
                        "<font color='#2FD2B2'> LB:" + DoubleUtil.formatDataCompare10(data.getSar()) + "</font> ");
                break;
        }
        tvMainIndicators.setText(tvMainString);
        //交易量图
        tvMiddleIndicators.setText(Utils.fromHtml("<font color='#9BA0A8'>量:" + DoubleUtil.format2Decimal(data.getVol()) + "</font>"));
        //附图

        Spanned tvExtraString;
        switch (extraIndicators) {
            case 0:
                tvExtraString = Utils.fromHtml("<font color='#9BA0A8'>MACD(12,26,9):</font> " +
                        "<font color='#ACD2E5'> DIF:" + DoubleUtil.formatDataCompare10(data.getDif()) + "</font> " +
                        "<font color='#F0BC79'> DEA:" + DoubleUtil.formatDataCompare10(data.getDea()) + "</font> " +
                        "<font color='#DF8ADF'> MACD:" + DoubleUtil.formatDataCompare10(data.getMacd()) + "</font> ");
                break;
            case 1:
                tvExtraString = Utils.fromHtml("<font color='#9BA0A8'>KDJ(9,3,3):</font> " +
                        "<font color='#b2dbef'> K:" + DoubleUtil.formatDataCompare10(data.getK()) + "</font> " +
                        "<font color='#fdc071'> D:" + DoubleUtil.formatDataCompare10(data.getD()) + "</font> " +
                        "<font color='#ea9bea'> J:" + DoubleUtil.formatDataCompare10(data.getJ()) + "</font> ");
                break;
            case 2:
                tvExtraString = Utils.fromHtml("<font color='#9BA0A8'>RSI(6,12,24):</font> " +
                        "<font color='#62BEA7'> RSI6:" + DoubleUtil.formatDataCompare10(data.getRsi6()) + "</font> " +
                        "<font color='#CFB85B'> RSI12:" + DoubleUtil.formatDataCompare10(data.getRsi12()) + "</font> " +
                        "<font color='#c24ea9'> RSI24:" + DoubleUtil.formatDataCompare10(data.getRsi24()) + "</font> ");
                break;
            default:
                tvExtraString = Utils.fromHtml("");
        }

        tvExtraIndicators.setText(tvExtraString);
    }


    //涨跌幅隐藏
    public void setUpAndDownValueGone() {
        landScape = true;
    }

    public KLineFullData getKlineData(int index){
        return mData.get(index);
    }

    /**
     * add limit line to chart
     */
    public void setLimitLine(double lastClose) {
        mChartPrice.getAxisRight().removeAllLimitLines();
        LimitLine limitLine = new LimitLine((float) lastClose);
        limitLine.setLineColor(getResources().getColor(R.color.limit_color));
        mChartPrice.getAxisRight().addLimitLine(limitLine);
    }
}

