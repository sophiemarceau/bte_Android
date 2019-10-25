
package com.guoziwei.klinelib.chart;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.guoziwei.klinelib.R;
import com.guoziwei.klinelib.model.KLineFullData;

import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private RelativeLayout rlContainer;
    private List<KLineFullData> mdata;

    public MyMarkerView(Context context, int layoutResource, List<KLineFullData> data) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.content_tv);
        this.mdata = data;
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        int value = (int) e.getX();

        if (e instanceof BubbleEntry) {
            tvContent.setVisibility(VISIBLE);
            if (mdata != null && value < mdata.size()) {
                tvContent.setText(mdata.get(value).getBubbleText());
            }
        } else {
            tvContent.setVisibility(GONE);
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        float v = getWidth() * 0.85f;
        return new MPPointF(-v, 0);
    }
}
