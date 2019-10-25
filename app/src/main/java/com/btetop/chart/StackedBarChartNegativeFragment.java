package com.btetop.chart;

import android.view.View;

import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.StackedBarChartNeagtiveBean;
import com.btetop.net.RetrofitManager;
import com.btetop.utils.RxUtil;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import butterknife.BindView;
import rx.functions.Action1;

public class StackedBarChartNegativeFragment extends BaseFragment {
    @BindView(R.id.stackedBarChartNegative)
    HorizontalBarChart mStackedBarChartNegative;

    public StackedBarChartNegativeFragment() {
        // Required empty public constructor
    }

    public static StackedBarChartNegativeFragment getInstance() {
        StackedBarChartNegativeFragment fragment = new StackedBarChartNegativeFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_stackec_bar_chart_negative;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() throws NullPointerException {
        RetrofitManager.getInstance().getNeagtiveBeanObservable("BTC")
                .compose(RxUtil.<BaseBean<StackedBarChartNeagtiveBean.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<StackedBarChartNeagtiveBean.DataBean>>() {
                    @Override
                    public void call(BaseBean<StackedBarChartNeagtiveBean.DataBean> bean) {

                        StackedBarChartNegativeManager.initChart(getContext(),
                                mStackedBarChartNegative,
                                bean.getData().getSpot().getRanges(),
                                bean.getData().getSpot().getBuy(),
                                bean.getData().getSpot().getSell()
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
