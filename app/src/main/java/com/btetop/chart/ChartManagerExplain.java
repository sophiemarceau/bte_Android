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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChartManagerExplain {

    /**
     *
     * @param context
     * @param mLineChart
     * @param xValues
     * @param yValue
     * @param extraData 额外信息，用popWindow
     */
    public static void initSingleLineChart(Context context, LineChart mLineChart, List<String> xValues,
                                           List<Float> yValue, LineChartBean.DataBean extraData) {
        initDataStyle(context, mLineChart,xValues);

        ArrayList<Entry>  values=new ArrayList<>();
        int count=xValues.size();
        for (int i = 0; i < count; i++) {
            LineChartExtraBean extraBean=new LineChartExtraBean(extraData.getxMills().get(i),
                    extraData.getVolume().get(i),extraData.getData().get(i));
            values.add(new Entry(i,yValue.get(i),extraBean));
        }

        LineDataSet set1=new LineDataSet(values,"sdfdf");

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
        }
        else {
            set1.setFillColor(Color.BLACK);
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        mLineChart.setData(data);

        //设置动画效果
        mLineChart.animateX(2000, Easing.EasingOption.Linear);
        mLineChart.invalidate();
    }

    /**
     * @Description:初始化图表的样式
     */
    private static void initDataStyle(Context context, LineChart mLineChart, List<String> xValues) {
        mLineChart.setNoDataText("没有数据");
        //设置图表是否支持触控操作
        mLineChart.setTouchEnabled(true);
        mLineChart.setScaleEnabled(false);
        mLineChart.getLegend().setEnabled(false);
        //设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView(context, R.layout.item_mark_layout);
//        mLineChart.setMarkerView(mv);
        //设置折线的描述的样式（默认在图表的左下角）
        Legend title = mLineChart.getLegend();
        title.setForm(Legend.LegendForm.LINE);
        //设置x轴的样式
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(0);
        xAxis.setDrawGridLines(false);
        XAxisValueFormatter xvalueFormatter=new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xvalueFormatter);
        //设置是否显示x轴的数值
        xAxis.setEnabled(true);
        //不显示x轴的轴线
        xAxis.setDrawAxisLine(false);

        //设置左边y轴的样式
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setGridLineWidth(3);
        yAxisLeft.enableGridDashedLine(3,3,3);
        yAxisLeft.setEnabled(true);
        yAxisLeft.setDrawLabels(false);
//        yAxisLeft.setDrawAxisLine(false);


        //设置右边y轴的样式
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);

        //隐藏描述信息
        Description description = new Description();
        description.setText("");
        mLineChart.setDescription(description);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart
    }

}
