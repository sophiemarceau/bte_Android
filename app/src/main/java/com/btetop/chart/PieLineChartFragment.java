package com.btetop.chart;

import android.view.View;

import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.StackedBarChartNeagtiveBean;
import com.btetop.net.RetrofitManager;
import com.btetop.utils.RxUtil;
import com.github.mikephil.charting.charts.PieChart;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 正负柱状图
 */
public class PieLineChartFragment extends BaseFragment {
    @BindView(R.id.pieChart)
    PieChart pieChart;

    public PieLineChartFragment() {
        // Required empty public constructor
    }

    public static PieLineChartFragment getInstance() {
        PieLineChartFragment fragment = new PieLineChartFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_pie_line_chart_fragment;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() throws NullPointerException {

        final PieLineChartManager pieLineChartManager = new PieLineChartManager();

        RetrofitManager.getInstance().getNeagtiveBeanObservable("BTC")
                .compose(RxUtil.<BaseBean<StackedBarChartNeagtiveBean.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<StackedBarChartNeagtiveBean.DataBean>>() {
                    @Override
                    public void call(BaseBean<StackedBarChartNeagtiveBean.DataBean> bean) {
                        pieLineChartManager.initChart(getContext(),
                                pieChart,
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
