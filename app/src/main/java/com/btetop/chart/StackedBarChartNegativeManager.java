package com.btetop.chart;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StackedBarChartNegativeManager {
    private static final String TAG = "StackedBarChartNegative";


    public static void initChart(Context context, HorizontalBarChart barChart, String[] xvalues, List<Float> leftValues, List<Float> rightValues) {
        initDataStyle(context, barChart, xvalues);
        ArrayList<BarEntry> yValues = new ArrayList<>();
        for (int i = 0; i < leftValues.size(); i++) {
            BarEntry barEntry = new BarEntry(i, new float[]{-leftValues.get(i), rightValues.get(i)});
            yValues.add(barEntry);
        }

        BarDataSet set = new BarDataSet(yValues, "Age Distribution");
        set.setDrawIcons(false);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColors(new int[]{Color.rgb(67, 67, 72), Color.rgb(124, 181, 236)});

        set.setStackLabels(new String[]{
                "买方", "卖方"
        });
        BarData data = new BarData(set);

        barChart.setData(data);
        barChart.animateXY(3000, 3000);
        barChart.invalidate();


    }


    private static void initDataStyle(Context context, HorizontalBarChart barChart, final String[] xValues) {

        barChart.setTouchEnabled(false);
        barChart.setScaleEnabled(false);
        //隐藏右下角的描述信息
        barChart.getDescription().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);


        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(10);


//        xAxis.setValueFormatter(new XAxisValueFormatter {
//        });

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setEnabled(false);


        YAxis axisRight = barChart.getAxisRight();

        axisRight.setValueFormatter(new CustomFormatter());
//        barChart.moveViewToX(20);
    }


    private static class CustomFormatter implements IAxisValueFormatter {
        private DecimalFormat format;

        public CustomFormatter() {
            format = new DecimalFormat("###");
        }


        // YAxis
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            //取绝对值后，再格式化
            return format.format(Math.abs(value));
        }
    }
}
