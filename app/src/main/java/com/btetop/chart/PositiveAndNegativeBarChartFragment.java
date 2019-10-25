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
public class PositiveAndNegativeBarChartFragment extends BaseFragment {
    @BindView(R.id.bar_chart_no_seek_bar)
    BarChart barChart;

    public PositiveAndNegativeBarChartFragment() {
        // Required empty public constructor
    }

    public static PositiveAndNegativeBarChartFragment getInstance() {
        PositiveAndNegativeBarChartFragment fragment = new PositiveAndNegativeBarChartFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_barchart_noseekbar;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() throws NullPointerException {

        final PositiveAndNegativeBarChartManager positiveAndNegativeBarChartManager = new PositiveAndNegativeBarChartManager();

        RetrofitManager.getInstance().getNeagtiveBeanObservable("BTC")
                .compose(RxUtil.<BaseBean<StackedBarChartNeagtiveBean.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<StackedBarChartNeagtiveBean.DataBean>>() {
                    @Override
                    public void call(BaseBean<StackedBarChartNeagtiveBean.DataBean> bean) {
                        positiveAndNegativeBarChartManager.initChart(getContext(),
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
        return true;
    }
}
