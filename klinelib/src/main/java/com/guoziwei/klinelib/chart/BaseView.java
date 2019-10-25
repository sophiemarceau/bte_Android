package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.Transformer;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.KLineFullData;
import com.guoziwei.klinelib.util.DateUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public abstract class BaseView extends LinearLayout {

    protected String mDateFormat = "HH:mm";


    public static final int NORMAL_LINE = 0;
    /**
     * average line
     */
    public static final int AVE_LINE = 1;
    /**
     * hide line
     */
    public static final int INVISIABLE_LINE = 6;


    public static final int MA5 = 5;
    public static final int MA10 = 10;
    public static final int MA20 = 20;
    public static final int MA30 = 30;

    public static final int EMA7 = 70;
    public static final int EMA30 = 71;

    public static final int K = 31;
    public static final int D = 32;
    public static final int J = 33;

    public static final int DIF = 34;
    public static final int DEA = 35;

    public static final int UP = 41;
    public static final int MB = 42;
    public static final int DN = 43;

    public static final int RSI6 = 51;
    public static final int RSI12 = 52;
    public static final int RSI24 = 53;

    public static final int BUBBLE = 61;

    public static final int SELLPRICE1 = 71;
    public static final int SELLPRICE2 = 72;
    public static final int BUYPRICE1 = 73;
    public static final int BUYPRICE2 = 74;
    public static final int BUYPRICE5 = 75;
    public static final int SELLPRICE5 = 76;


    protected int mDecreasingColor;
    protected int mIncreasingColor;
    protected int mAxisColor;
    protected int mTransparentColor;
    protected int mBorderColor;


    public int MAX_COUNT = 160;
    public int MIN_COUNT = 20;
    public int INIT_COUNT = 40;

    protected int offsetX = 3;

    //主图指标 ma 0  ema 1 boll 2 sar 3 压力线 4 关闭5
    protected  int mainIndicators = 0;

    //异动指标 0 打开 1 关闭
    protected int ydIndicatiors=1;

    //交易量指标 0 交易量 1 净交易量
    protected int volumeIndicators=0;


    //附图指标macd 0 kdj 1 rsi 2
    protected  int extraIndicators = 2;

    protected int mDigits = 2;

    protected boolean addLast;

    protected Context mContext;

    //横竖屏
    protected boolean landScape = false;

    protected fullScreenListener listener;

    protected ArrayList<KLineFullData> mData = new ArrayList<>(300);

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAxisColor = ContextCompat.getColor(getContext(), R.color.axis_color);
        mTransparentColor = ContextCompat.getColor(getContext(), android.R.color.transparent);
        mDecreasingColor = ContextCompat.getColor(getContext(), R.color.decreasing_color);
        mIncreasingColor = ContextCompat.getColor(getContext(), R.color.increasing_color);
        mBorderColor=ContextCompat.getColor(getContext(),R.color.border_color);

    }

    protected void initBottomChart(AppCombinedChart chart) {
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);
        chart.setDrawBorders(true);
        chart.setBorderWidth(0.5f);
        chart.setBorderColor(mBorderColor);
        chart.setDragEnabled(true);
        chart.setScaleYEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setDragDecelerationEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        Legend lineChartLegend = chart.getLegend();
        lineChartLegend.setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.setDragOffsetX(offsetX);

        XAxis xAxisVolume = chart.getXAxis();
        xAxisVolume.setEnabled(true);
        xAxisVolume.setDrawLabels(true);
        xAxisVolume.setDrawAxisLine(false);
        xAxisVolume.setDrawGridLines(false);
        xAxisVolume.setTextColor(mAxisColor);
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisVolume.setLabelCount(5, true);
        xAxisVolume.setAvoidFirstLastClipping(true);
        xAxisVolume.setAxisMinimum(-0.5f);

        xAxisVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mData.isEmpty()) {
                    return "";
                }
                if (value < 0) {
                    value = 0;
                }
                if (value < mData.size()) {
                    return DateUtils.formatDate(mData.get((int) value).getDate(), mDateFormat);
                }
                return "";
            }
        });

        //左边y
        YAxis axisLeftVolume = chart.getAxisLeft();
        //这句可以让volume从底部对齐
        axisLeftVolume.setDrawGridLines(false);
        axisLeftVolume.setEnabled(true);

//        //右边y
        YAxis axisRightVolume = chart.getAxisRight();
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setLabelCount(3);

        axisRightVolume.setDrawLabels(true);
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setLabelCount(3, true);
        axisRightVolume.setDrawAxisLine(false);
        axisRightVolume.setTextColor(mAxisColor);
        axisRightVolume.setSpaceTop(10);
        axisRightVolume.setSpaceBottom(0);
        axisRightVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightVolume.setAxisLineWidth(0.5f);
        axisRightVolume.setAxisLineColor(Color.parseColor("#ADAFB6"));

//        axisRightVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        axisRightVolume.setDrawLabels(true);
//        axisRightVolume.setDrawGridLines(false);
//        axisRightVolume.setDrawAxisLine(false);
//
//        axisRightVolume.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                String s;
//                if (value > 10000) {
//                    s = (int) (value / 10000) + "万";
//                } else if (value > 1000) {
//                    s = (int) (value / 1000) + "千";
//                } else {
//                    s = (int) value + "";
//                }
//                return String.format(Locale.getDefault(), "%1$5s", s);
//            }
//        });
//
        Transformer rightYTransformer = chart.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer rightColorContentYAxisRenderer = new ColorContentYAxisRenderer(chart.getViewPortHandler(), axisRightVolume, rightYTransformer);
        rightColorContentYAxisRenderer.setLabelInContent(true);
        rightColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        chart.setRendererRightYAxis(rightColorContentYAxisRenderer);


    }

    protected void initMiddleChart(AppCombinedChart chart) {
        chart.setScaleEnabled(true);
        chart.setDrawBorders(true);
        chart.setBorderColor(mBorderColor);
        chart.setDragEnabled(true);
        chart.setBorderWidth(0.5f);
        chart.setScaleYEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setDragDecelerationEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        Legend lineChartLegend = chart.getLegend();
        lineChartLegend.setEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.setDragOffsetX(offsetX);


        XAxis xAxisVolume = chart.getXAxis();
        xAxisVolume.setDrawLabels(false);
        xAxisVolume.setDrawAxisLine(false);
        xAxisVolume.setDrawGridLines(false);
        xAxisVolume.setTextColor(mAxisColor);
        xAxisVolume.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxisVolume.setLabelCount(3, true);
        xAxisVolume.setAvoidFirstLastClipping(true);
        xAxisVolume.setAxisMinimum(-0.5f);

        xAxisVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mData.isEmpty()) {
                    return "";
                }
                if (value < 0) {
                    value = 0;
                }
                if (value < mData.size()) {
                    return DateUtils.formatDate(mData.get((int) value).getDate(), mDateFormat);
                }
                return "";
            }
        });

        YAxis axisLeftVolume = chart.getAxisLeft();
        axisLeftVolume.setDrawAxisLine(false);
        axisLeftVolume.setDrawGridLines(false);
        axisLeftVolume.setAxisLineWidth(0.5f);
        axisLeftVolume.setAxisLineColor(Color.parseColor("#ADAFB6"));
//        axisLeftVolume.setEnabled(false);
//
//        axisLeftVolume.setDrawLabels(true);
//        axisLeftVolume.setLabelCount(3, true);
//        axisLeftVolume.setTextColor(mAxisColor);
//        axisLeftVolume.setSpaceTop(10);
//        axisLeftVolume.setSpaceBottom(0);
//        axisLeftVolume.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


        /*axisLeftVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "w";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "k";
                } else {
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });
*/
//        Transformer leftYTransformer = chart.getRendererLeftYAxis().getTransformer();
//        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(chart.getViewPortHandler(), chart.getAxisLeft(), leftYTransformer);
//        leftColorContentYAxisRenderer.setLabelInContent(true);
//        leftColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
//        chart.setRendererLeftYAxis(leftColorContentYAxisRenderer);

        //右边y
//        YAxis axisRightVolume = chart.getAxisRight();
//        axisRightVolume.setDrawLabels(true);
//        axisRightVolume.setDrawGridLines(false);
//        axisRightVolume.setDrawAxisLine(false);
//        axisRightVolume.setLabelCount(3,true);
//        axisRightVolume.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        YAxis axisRightVolume = chart.getAxisRight();
        axisRightVolume.setEnabled(true);

        axisRightVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 1000) + "K";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "K";
                } else {
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s).trim();
            }
        });

        axisRightVolume.setDrawLabels(true);
        axisRightVolume.setDrawGridLines(false);
        axisRightVolume.setLabelCount(3, true);
        axisRightVolume.setDrawAxisLine(false);
        axisRightVolume.setTextColor(mAxisColor);
        axisRightVolume.setSpaceTop(10);
        axisRightVolume.setSpaceBottom(0);
        axisRightVolume.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        axisRightVolume.setAxisLineWidth(0.5f);
        axisRightVolume.setAxisLineColor(Color.parseColor("#ADAFB6"));
        /*axisLeftVolume.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "w";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "k";
                } else {
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });
*/
        Transformer leftYTransformer = chart.getRendererRightYAxis().getTransformer();
        ColorContentYAxisRenderer leftColorContentYAxisRenderer = new ColorContentYAxisRenderer(chart.getViewPortHandler(), chart.getAxisRight(), leftYTransformer);
        leftColorContentYAxisRenderer.setLabelInContent(true);
        leftColorContentYAxisRenderer.setUseDefaultLabelXOffset(false);
        chart.setRendererRightYAxis(leftColorContentYAxisRenderer);

    }

    protected BarDataSet setBar(ArrayList<BarEntry> barEntries, int type) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "vol");
        barDataSet.setHighLightAlpha(120);
        barDataSet.setDrawValues(false);
        barDataSet.setVisible(type != INVISIABLE_LINE);
        barDataSet.setHighlightEnabled(type != INVISIABLE_LINE);
        barDataSet.setColors(getResources().getColor(R.color.increasing_color), getResources().getColor(R.color.decreasing_color));
        return barDataSet;
    }


    @android.support.annotation.NonNull
    protected LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        if (type == NORMAL_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
//            lineDataSetMa.setDrawFilled(true);
            lineDataSetMa.setCircleColor(getResources().getColor(R.color.normal_line_color));
        } else if (type == K) {
            lineDataSetMa.setColor(getResources().getColor(R.color.k));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == D) {
            lineDataSetMa.setColor(getResources().getColor(R.color.d));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == J) {
            lineDataSetMa.setColor(getResources().getColor(R.color.j));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == DIF) {
            lineDataSetMa.setColor(getResources().getColor(R.color.dif));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == DEA) {
            lineDataSetMa.setColor(getResources().getColor(R.color.dea));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == AVE_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == UP) {
            lineDataSetMa.setColor(getResources().getColor(R.color.up));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MB) {
            lineDataSetMa.setColor(getResources().getColor(R.color.mb));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == DN) {
            lineDataSetMa.setColor(getResources().getColor(R.color.dn));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == RSI6) {
            lineDataSetMa.setColor(getResources().getColor(R.color.rsi1));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == RSI12) {
            lineDataSetMa.setColor(getResources().getColor(R.color.rsi2));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == RSI24) {
            lineDataSetMa.setColor(getResources().getColor(R.color.rsi3));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == EMA7) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ema7));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == EMA30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ema30));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == SELLPRICE1) {
            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == SELLPRICE2) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == BUYPRICE5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.main_color_red));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == SELLPRICE5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.main_color_green));
            lineDataSetMa.setCircleColor(mTransparentColor);
            lineDataSetMa.setHighlightEnabled(false);
        }else {

            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(0.5f);
        lineDataSetMa.setCircleRadius(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);

        return lineDataSetMa;
    }

    @android.support.annotation.NonNull
    public CandleDataSet setKLine(int type, ArrayList<CandleEntry> lineEntries) {
        CandleDataSet set = new CandleDataSet(lineEntries, "KLine" + type);
        set.setDrawIcons(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowColor(Color.DKGRAY);
        set.setShadowWidth(0.75f);
        set.setDecreasingColor(mDecreasingColor);
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setShadowColorSameAsCandle(true);
        set.setIncreasingColor(mIncreasingColor);
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        set.setDrawValues(true);
        set.setValueTextSize(10);
        set.setHighlightEnabled(true);
        if (type != NORMAL_LINE) {
            set.setVisible(false);
        }
        return set;
    }

    public BubbleDataSet setBubbleDataSet(ArrayList<BubbleEntry> bubbleEntries) {
        BubbleDataSet set = new BubbleDataSet(bubbleEntries, "bubble");
        set.setDrawIcons(false);
        set.setHighlightEnabled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.parseColor("#DADADA"));
        set.setDrawValues(false);
        return set;
    }

    public BubbleDataSet setSarDataSet(ArrayList<BubbleEntry> bubbleEntries) {
        BubbleDataSet set = new BubbleDataSet(bubbleEntries, "sar");
        set.setDrawIcons(false);
        set.setHighlightEnabled(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.parseColor("#DADADA"));
        set.setDrawValues(false);
        return set;
    }

    public BubbleDataSet setYdDataSet(ArrayList<BubbleEntry> bubbleEntries){
        if (bubbleEntries.size() > 0) {
            BubbleDataSet set = new BubbleDataSet(bubbleEntries, "yidong");
        set.setDrawIcons(false);
        set.setHighlightEnabled(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.parseColor("#DADADA"));
        set.setDrawValues(false);
            return set;
        } else {
            return null;
        }


    }


    protected void moveToLast(AppCombinedChart chart) {
        if (mData.size() > INIT_COUNT) {
            chart.moveViewToX(mData.size() - INIT_COUNT);
        }
    }

    /**
     * set the count of k chart
     */
    public void setCount(int init, int max, int min) {
        INIT_COUNT = init;
        MAX_COUNT = max;
        MIN_COUNT = min;
    }

    protected void setiiiDescription(Chart chart, String text) {
        Description description = chart.getDescription();
        float dx = description.getXOffset();
        description.setPosition(-dx, description.getTextSize());
        description.setText(text);
    }

    public KLineFullData getLastData() {
        if (mData != null && !mData.isEmpty()) {
            return mData.get(mData.size() - 1);
        }
        return null;
    }

    public void setDateFormat(String mDateFormat) {
        this.mDateFormat = mDateFormat;
    }

    public void setFullListener(fullScreenListener listener) {
        this.listener = listener;
    }

    public interface fullScreenListener {
        void fullScreenClick();
    }
}
