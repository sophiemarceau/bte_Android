package com.guoziwei.klinelib.chart;


import android.graphics.Matrix;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

/**
 * http://stackoverflow.com/questions/28521004/mpandroidchart-have-one-graph-mirror-the-zoom-swipes-on-a-sister-graph
 * https://my.oschina.net/u/2002921/blog/673994  加载更多
 */
public class CoupleChartGestureListener implements OnChartGestureListener, OnChartValueSelectedListener,
        View.OnTouchListener {


    private static final String TAG = CoupleChartGestureListener.class.getSimpleName();

    private AppCombinedChart srcChart;
    private AppCombinedChart[] dstCharts;

    private boolean infoViewShow = false;
    private String infoViewShowType = "";

    private GestureDetector mDetector;
    private boolean mIsLongPress = false;

    private InfoListener[] mInfolisteners;

    //左边加载更多回调标志
    private boolean mLeftLoadMore = true;


    public void setmInfoView(ChartInfoView mInfoView) {
        this.mInfoView = mInfoView;
    }

    private ChartInfoView mInfoView;


    public CoupleChartGestureListener(AppCombinedChart srcChart, AppCombinedChart... dstCharts) {
        this.srcChart = srcChart;
        this.dstCharts = dstCharts;
        this.init();
    }

    private void init() {

        mDetector = new GestureDetector(srcChart.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                mIsLongPress = true;
                Highlight h = srcChart.getHighlightByTouchPoint(e.getX(), e.getY());
                if (h != null) {
                    srcChart.highlightValue(h, true);
                    srcChart.disableScroll();
                }
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                //取消高亮显示
//                if (srcChart != null) {
//                    srcChart.highlightValue(null, true);
//                }
//                if (dstCharts != null) {
//                    for (BarLineChartBase dstChart : dstCharts) {
//                        dstChart.highlightValue(null, true);
//                    }
//                }
//                if (infoViewShow) {
//                    setHighValueCancel();
//
//                } else {
                    IHighlighter highlighter = srcChart.getHighlighter();
                    Highlight h = srcChart.getHighlightByTouchPoint(e.getX(), e.getY());
                    if (h != null) {
                        srcChart.highlightValue(h, true);
                    }
//                }
                return super.onSingleTapConfirmed(e);
            }
        });
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

        syncCharts();
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        if (this.dstCharts[0] == null) {
            return;
        }
        int leftIndex = (int) this.srcChart.getLowestVisibleX();
        int rightIndex = (int) this.srcChart.getHighestVisibleX();
        int size = (int) this.srcChart.getData().getXMax();
        if (lastPerformedGesture == ChartTouchListener.ChartGesture.DRAG) {
            if (leftIndex <= 0) {
                //加载更多数据的操作
                leftLoadMore();

            } else if (rightIndex == size - 1 || rightIndex == size) {
                rightLoadMore();
            } else if (rightIndex < size) {
                noNeedRefreshRight();
            }
        }
        syncCharts();
    }



    @Override
    public void onChartLongPressed(MotionEvent me) {
        syncCharts();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        syncCharts();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

        if (srcChart.getData() != null && srcChart.getData().getBubbleData() != null) {

            srcChart.getData().getBubbleData().setHighlightEnabled(!srcChart.getData().getBubbleData().isHighlightEnabled());
            srcChart.invalidate();
        }

        syncCharts();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        syncCharts();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//        Log.d(TAG, "onChartScale " + scaleX + "/" + scaleY + " X=" + me.getX() + "Y=" + me.getY());
        syncCharts();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//        Log.d(TAG, "onChartTranslate " + dX + "/" + dY + " X=" + me.getX() + "Y=" + me.getY());
//        Log.d(TAG, "getHighestVisibleX  " +srcChart.getHighestVisibleX());
        if (this.dstCharts[0] == null) {
            return;
        }
//        if (mLeftIsCanLoad || mRightIsCanLoad) {
//            int leftIndex = (int) this.srcChart.getLowestVisibleX();
//            int rightIndex = (int)this.srcChart.getHighestVisibleX();
//            int size = (int) this.srcChart.getData().getXMax();
//            if (leftIndex <= 0) {
//                mLeftIsCanLoad = false;
//                leftLoadMore();
//            } else if (rightIndex == size - 1 || rightIndex == size) {
//                rightLoadMore();
//            }
//
//        }
        syncCharts();
    }

    private void leftLoadMore() {

        if (mInfolisteners != null && mLeftLoadMore) {
            for (InfoListener infoListener : mInfolisteners) {
                if (infoListener == null) {
                    continue;
                }
                infoListener.leftLoadMore();
            }
        }

    }

    private void rightLoadMore() {
        if (mInfolisteners != null) {
            for (InfoListener infoListener : mInfolisteners) {
                if (infoListener == null) {
                    continue;
                }
                infoListener.RightLoadMore();
            }
        }
    }


    private void noNeedRefreshRight() {
        if (mInfolisteners != null) {
            for (InfoListener infoListener : mInfolisteners) {
                if (infoListener == null) {
                    continue;
                }
                infoListener.noNeedRefreshRight();
            }
        }
    }


    private void syncCharts() {
        Matrix srcMatrix;
        float[] srcVals = new float[9];
        Matrix dstMatrix;
        float[] dstVals = new float[9];
        // get src chart translation matrix:
        srcMatrix = srcChart.getViewPortHandler().getMatrixTouch();
        srcMatrix.getValues(srcVals);
        // apply X axis scaling and position to dst charts:
        for (Chart dstChart : dstCharts) {
            dstMatrix = dstChart.getViewPortHandler().getMatrixTouch();
            dstMatrix.getValues(dstVals);

            dstVals[Matrix.MSCALE_X] = srcVals[Matrix.MSCALE_X];
            dstVals[Matrix.MSKEW_X] = srcVals[Matrix.MSKEW_X];
            dstVals[Matrix.MTRANS_X] = srcVals[Matrix.MTRANS_X];
            dstVals[Matrix.MSKEW_Y] = srcVals[Matrix.MSKEW_Y];
            dstVals[Matrix.MSCALE_Y] = srcVals[Matrix.MSCALE_Y];
            dstVals[Matrix.MTRANS_Y] = srcVals[Matrix.MTRANS_Y];
            dstVals[Matrix.MPERSP_0] = srcVals[Matrix.MPERSP_0];
            dstVals[Matrix.MPERSP_1] = srcVals[Matrix.MPERSP_1];
            dstVals[Matrix.MPERSP_2] = srcVals[Matrix.MPERSP_2];

            dstMatrix.setValues(dstVals);
            dstChart.getViewPortHandler().refresh(dstMatrix, dstChart, true);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        infoViewShow = true;
//        srcChart.setScaleEnabled(false);
//        srcChart.setDragEnabled(false);
//        srcChart.setScaleYEnabled(false);
        if (dstCharts != null) {
            for (Chart aMOtherChart : dstCharts) {
                aMOtherChart.highlightValues(new Highlight[]{new Highlight(h.getX(), Float.NaN, h.getDataSetIndex())});
            }
        }
        if (mInfolisteners != null) {
            int value = (int) e.getX();
            for (InfoListener infoListener : mInfolisteners) {
                if (infoListener == null) {
                    continue;
                }
                infoListener.getMarkerDataIndex(value);
            }
        }
    }

    @Override
    public void onNothingSelected() {
        infoViewShow = false;
        srcChart.setScaleEnabled(true);
        srcChart.setDragEnabled(true);
        srcChart.setScaleYEnabled(false);
        if (mInfolisteners != null) {
            for (InfoListener infoListener : mInfolisteners) {
                if (infoListener == null) {
                    continue;
                }

                infoListener.marketHidden();

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mDetector.onTouchEvent(event);
        int pointerCount = event.getPointerCount();
        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            mIsLongPress = false;
        }
        if (pointerCount == 1 && (mIsLongPress) && event.getAction() == MotionEvent.ACTION_MOVE) {
            Highlight h = srcChart.getHighlightByTouchPoint(event.getX(), event.getY());
            if (h != null) {
                srcChart.highlightValue(h, true);
                srcChart.disableScroll();
            }
            return true;
        } else if (pointerCount == 2 && event.getAction() == MotionEvent.ACTION_HOVER_MOVE) {
            setHighValueCancel();
            return true;
        }
        return false;
    }

    /**
     * 取消高亮显示
     */
    private void setHighValueCancel() {
        if (srcChart != null) {
            IHighlighter highlighter = srcChart.getHighlighter();
            srcChart.highlightValue(null, true);

        }
        if (dstCharts != null) {
            for (BarLineChartBase dstChart : dstCharts) {
                dstChart.highlightValue(null, true);
            }
        }
        srcChart.setScaleEnabled(true);
        srcChart.setDragEnabled(true);
        srcChart.setScaleYEnabled(false);
    }

    public void setInfoListener(InfoListener... infoListener) {
        this.mInfolisteners = infoListener;
    }

    public void setLeftLoadMoreDragTrue() {
        this.mLeftLoadMore = true;
    }

    public void setLeftLoadMoreDragFalse() {
        this.mLeftLoadMore = false;
    }

    public interface InfoListener {
        void getMarkerDataIndex(int dataIndex);

        void marketHidden();

        void leftLoadMore();

        void RightLoadMore();

        void noNeedRefreshRight();

    }
}