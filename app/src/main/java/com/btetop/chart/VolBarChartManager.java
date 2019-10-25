package com.btetop.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class VolBarChartManager {
    private static final String TAG = "StackedBarChartNegative";
    private static Typeface mTf;
    private BarChart mChart;

    public void initChart(Context context, BarChart mChart, String[] ranges, List<Float> list) {
        this.mChart = mChart;
        initDataStyle(context, mChart, ranges);
        final List<Data> data = new ArrayList<>();
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (i % 4 == 0) {
                //,new float[]{list.get(i), list.get(i)}
                data.add(new Data(i, list.get(i), ranges[i], ranges[i]));
            }
            data.add(new Data(i, list.get(i), "", ""));
        }
        setData(data);
        mChart.animateXY(3000, 3000);

    }

    /**
     * Demo class representing data.
     */
    private class Data {

        public String yAxisValue;
        public String xAxisValue;
        public float yValue;
        public float xValue;

        public Data(float xValue, float yValue, String xAxisValue, String yAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yAxisValue = yAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    private void setData(List<Data> dataList) {

        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        List<Integer> colors = new ArrayList<Integer>();

        int green = Color.rgb(0, 190, 255);
//        int red = Color.rgb(211, 74, 88);
        int red = Color.rgb(0, 190, 255);
        for (int i = 0; i < dataList.size(); i++) {

            Data d = dataList.get(i);
            BarEntry entry;
            float yValue = d.yValue;
            if (yValue < 0) {
                entry = new BarEntry(d.xValue, -d.yValue);
            } else {
                entry = new BarEntry(d.xValue, d.yValue);
            }

            values.add(entry);

            // specific colors
            if (d.yValue >= 0)
                colors.add(red);
            else
                colors.add(green);
        }

        BarDataSet set;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            set.setValueTextColors(colors);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);
            data.setValueTypeface(mTf);
            data.setValueFormatter(new ValueFormatter());
            data.setBarWidth(0.5f);
            data.setDrawValues(false);

            mChart.setData(data);
            mChart.invalidate();
        }
    }

    private static void initDataStyle(Context context, BarChart mChart, final String[] ranges) {


        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(10f);
        xAxis.setLabelCount(5);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(ranges));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = mChart.getAxisRight();
        yAxis.setZeroLineColor(Color.GRAY);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        yAxis.setEnabled(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setScaleEnabled(false);
        //隐藏右下角的描述信息
        mChart.getDescription().setEnabled(false);

    }


    private class ValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public ValueFormatter() {
            mFormat = new DecimalFormat("######.0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }
}
