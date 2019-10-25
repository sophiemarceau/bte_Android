package com.btetop.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.btetop.R;
import com.btetop.bean.LineChartBean;
import com.btetop.bean.LineChartExtraBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class LineChartManager {

    /**
     * @param context
     * @param mLineChart
     * @param xValues
     * @param yValue
     * @param extraData  额外信息，用popWindow
     */
    public static void initSingleLineChart(Context context, LineChart mLineChart, List<String> xValues,
                                           List<Float> yValue, LineChartBean.DataBean extraData) {
        LineData data = getData(xValues.size(), extraData);
        initDataStyle(context, mLineChart, xValues);

        ArrayList<Entry> values = new ArrayList<>();
        int count = xValues.size();
        for (int i = 0; i < count; i++) {
            LineChartExtraBean extraBean = new LineChartExtraBean(extraData.getxMills().get(i),
                    extraData.getVolume().get(i), extraData.getData().get(i));
            values.add(new Entry(i, yValue.get(i), extraBean));
        }

        LineDataSet set1 = new LineDataSet(values, "sdfdf");

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(ColorTemplate.getHoloBlue());
        // 不显示坐标点的小圆点
        set1.setDrawCircles(false);
        // 不显示坐标点的数据
        set1.setDrawValues(false);
        // 不显示定位线
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //设置阴影区域的颜色值
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_blue);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets

        // set data
        mLineChart.setData(data);

        //设置动画效果
        mLineChart.animateX(2000, Easing.EasingOption.Linear);
        mLineChart.invalidate();
    }

    private static LineData getData(int count, LineChartBean.DataBean range) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float val = range.getData().get(i);
            yVals.add(new Entry(i, val));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(3f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setHighLightColor(Color.BLUE);
        set1.setDrawValues(false);

        // create a data object with the datasets
        LineData data = new LineData(set1);

        return data;
    }

    /**
     * @Description:初始化图表的样式
     */
    public static void initDataStyle(Context context, LineChart chart, List<String> data) {
//        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(Color.BLUE);
        XAxis xAxis = chart.getXAxis();
        chart.getDescription().setEnabled(false);
//        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
//        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
//        chart.setPinchZoom(false);
//        chart.setViewPortOffsets(101, 10, 101, 10);
//        chart.getAxisLeft().setEnabled(false);
//        chart.getAxisLeft().setSpaceTop(10);
//        chart.getAxisLeft().setSpaceBottom(10);
        chart.getAxisRight().setEnabled(false);
//        chart.getXAxis().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(data));
    }

}
