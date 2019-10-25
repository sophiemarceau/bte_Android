package com.btetop.chart;

import android.view.View;

import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.StackedBarChartNeagtiveBean;
import com.btetop.net.RetrofitManager;
import com.btetop.utils.RxUtil;
import com.github.mikephil.charting.charts.BarChart;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 正负柱状图
 */
public class VolBarChartFragment extends BaseFragment {
    @BindView(R.id.vol_bar_chart)
    BarChart barChart;

    public VolBarChartFragment() {
        // Required empty public constructor
    }

    public static VolBarChartFragment getInstance() {
        VolBarChartFragment fragment = new VolBarChartFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_barchart_vol;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() throws NullPointerException {

        final VolBarChartManager volBarChartManager = new VolBarChartManager();

        RetrofitManager.getInstance().getNeagtiveBeanObservable("BTC")
                .compose(RxUtil.<BaseBean<StackedBarChartNeagtiveBean.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<StackedBarChartNeagtiveBean.DataBean>>() {
                    @Override
                    public void call(BaseBean<StackedBarChartNeagtiveBean.DataBean> bean) {
                        volBarChartManager.initChart(getContext(),
                                barChart,
                                bean.getData().getSpot().getRanges(),
                                bean.getData().getSpot().getNetList()
                        );
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    public boolean statisticsFragment() {
        return false;
    }
}
