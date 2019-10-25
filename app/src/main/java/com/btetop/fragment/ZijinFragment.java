package com.btetop.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.ZijinAdapter;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.ZiJinBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.CommonUtils;
import com.btetop.utils.RxUtil;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.guoziwei.klinelib.model.KLineFullData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class ZijinFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.chart)
    CandleStickChart mChart;

    private ZijinAdapter adapter;

    private CoinPairBean coinPair = null;
    private List<KLineFullData> mKlineData;

    public ZijinFragment() {
    }

    public static ZijinFragment newInstance() {
        ZijinFragment fragment = new ZijinFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.zijin_fragment;
    }

    @Override
    protected void initView(View view) {
        initChartView();
    }

    private void initChartView() {
        mChart.setScaleEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.getDescription().setEnabled(false);
        mChart.getLegend().setEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setNoDataText("加载中");
        mChart.setNoDataTextColor(ContextCompat.getColor(getContext(),R.color.color_666666));
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.color_half_626A75));
        xAxis.setTextSize(8f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (mKlineData.isEmpty()) {
                    return "";
                }
                if (value < 0) {
                    value = 0;
                }

                if (value < mKlineData.size()) {
                    return mKlineData.get((int) value).getDateString();
                }
                return "";
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(7, false);
        leftAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.color_half_626A75));
        leftAxis.setTextSize(8f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
              return CommonUtils.formatEng((long) value);
            }
        });


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setStartAtZero(false);
    }


    @Override
    protected void initData() {
        if (coinPair==null) {return; }
        //资金流向

        if(adapter == null) adapter =  new ZijinAdapter(BteTopApplication.getContext());
        if(recyclerView!=null){
            recyclerView.setLayoutManager(new LinearLayoutManager(BteTopApplication.getContext()));
            recyclerView.setAdapter(adapter);
        }



        BteTopService.getZijin(coinPair.getBaseAsset())
                .compose(this.bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<ZiJinBean>>() {
                    @Override
                    public void call(BaseBean<ZiJinBean> ziJinBeanBaseBean) {

                        if (!"0000".equals(ziJinBeanBaseBean.getCode())) {
                            return;
                        }
                        if (ziJinBeanBaseBean.getData() == null || ziJinBeanBaseBean.getData() == null) {
                            return;
                        }
                        if (ziJinBeanBaseBean.getData().getFlowList() != null && ziJinBeanBaseBean.getData().getFlowList().size() > 0) {
                            initListView(ziJinBeanBaseBean.getData().getFlowList());
                        }

                        if (ziJinBeanBaseBean.getData().getKlineList() != null && ziJinBeanBaseBean.getData().getKlineList().size() > 0) {
                            initKlineChart(ziJinBeanBaseBean.getData().getKlineList());
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void initListView(List<ZiJinBean.TradeBean> flowList) {
        flowList.add(0, new ZiJinBean.TradeBean());
        if(adapter!=null)
        {
            adapter.setData(flowList);
            adapter.notifyDataSetChanged();
        }

    }

    private void initKlineChart(List<KLineFullData> klineList) {
                        mKlineData = klineList;
                        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();
//
                        for (int i = 0; i < mKlineData.size(); i++) {
                            KLineFullData hisData = klineList.get(i);
                            yVals1.add(new CandleEntry(i, (float) hisData.getHigh(), (float) hisData.getLow(), (float) hisData.getOpen(), (float) hisData.getClose()));
                        }
//                        }
//
                        CandleData candleData=new CandleData(setKLine(yVals1));
//
                        if(mChart!=null){
                            mChart.setData(candleData);
                            mChart.invalidate();
                        }

    }



    @Override
    public boolean statisticsFragment() {
        return false;
    }


    @android.support.annotation.NonNull
    public CandleDataSet setKLine( ArrayList<CandleEntry> lineEntries) {

        CandleDataSet set = new CandleDataSet(lineEntries, "KLine");
        set.setDrawIcons(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setShadowColor(Color.DKGRAY);
        set.setShadowWidth(0.75f);
        set.setDecreasingColor(Color.parseColor("#FF4040"));
        set.setDecreasingPaintStyle(Paint.Style.FILL);
        set.setShadowColorSameAsCandle(true);
        set.setIncreasingColor(Color.parseColor("#228B22"));
        set.setIncreasingPaintStyle(Paint.Style.FILL);
        set.setNeutralColor(Color.parseColor("#29AC4E"));
        set.setDrawValues(false);
        set.setValueTextSize(10);
        set.setHighlightEnabled(true);
        return set;
    }

    public void setCoinPair(CoinPairBean coinPair) {
        if (coinPair == this.coinPair) return;
        if (adapter != null) adapter.notifyDataSetChanged();

        this.coinPair = coinPair;
        initData();
    }
}
