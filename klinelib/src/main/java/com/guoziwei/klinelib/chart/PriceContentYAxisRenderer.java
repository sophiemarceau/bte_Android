package com.guoziwei.klinelib.chart;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * 为每个label设置颜色，把两端的label绘制在内容区域内
 */

public class PriceContentYAxisRenderer extends ColorContentYAxisRenderer {


    public PriceContentYAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
        super(viewPortHandler, yAxis, trans);
    }


    @Override
    protected void drawYLabels(Canvas c, float fixedPosition, float[] positions, float offset) {

        final int from = mYAxis.isDrawBottomYLabelEntryEnabled() ? 0 : 1;
        final int to = mYAxis.isDrawTopYLabelEntryEnabled()
                ? mYAxis.mEntryCount
                : (mYAxis.mEntryCount - 1);

        mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(7f));
        int originalColor = mAxisLabelPaint.getColor();

        float textHeight = Utils.calcTextHeight(mAxisLabelPaint, "A");
        float yoffset = textHeight / 2.5f + mYAxis.getYOffset();
        float space = Utils.convertDpToPixel(5f);
        if (!mUseDefaultLabelXOffset) {
            if (mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                fixedPosition -= mYAxis.getXOffset();
            } else {
                fixedPosition += mYAxis.getXOffset();
            }
        }

        // draw
        for (int i = from; i < to; i++) {
            if (i == 0 || i == to - 1) {
                continue;
            }

            if (mLabelColorArray != null && i >= 0 && i < mLabelColorArray.length) {
                int labelColor = mLabelColorArray[i];
                mAxisLabelPaint.setColor(labelColor);
            } else {
                mAxisLabelPaint.setColor(defaultColor);
            }
            String text = mYAxis.getFormattedLabel(i);

            float y = positions[i * 2 + 1] + offset;

            if (mLabelInContent) {
                if (i == from) {
                    y = y - offset - space - 1F;
                } else if (i == (to - 1)) {
                    y = y - yoffset + textHeight + space + 1F;
                }
            }

            float right = mViewPortHandler.getContentRect().right;
            c.drawText(text, fixedPosition, y, mAxisLabelPaint);
            //标签外面的小横线-方便看数字
            float labelPosionStarx = right;
            float labelPosionStary = y - (textHeight / 2);
            float labelPosionendx = right + (fixedPosition - right) * 0.5f;
            float labelPosionendy = labelPosionStary;

            c.drawLine(labelPosionStarx, labelPosionStary, labelPosionendx, labelPosionendy, mAxisLabelPaint);
            //绘制gridview
            c.drawLine(0, labelPosionStary, labelPosionStarx, labelPosionendy, mGridPaint);

        }
        mAxisLabelPaint.setColor(originalColor);
    }

}
