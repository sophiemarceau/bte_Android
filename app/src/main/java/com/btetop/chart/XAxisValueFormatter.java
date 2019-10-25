package com.btetop.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * X轴转换器
 */
public class XAxisValueFormatter implements IAxisValueFormatter{

    protected List<String> xlineNames;

    public XAxisValueFormatter(List<String> xlineName) {
        this.xlineNames = xlineName;
    }


    @Override
    public String getFormattedValue(float value, AxisBase axis) {
         return xlineNames.get((int) value ).split(" ")[0];
    }
}
