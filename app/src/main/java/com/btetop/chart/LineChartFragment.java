package com.btetop.chart;

import android.view.View;

import com.btetop.R;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.LineChartBean;
import com.btetop.net.RetrofitManager;
import com.btetop.utils.RxUtil;
import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import rx.functions.Action1;

public class LineChartFragment extends BaseFragment {
    @BindView(R.id.lineChart)
    LineChart mLineChart;

    public LineChartFragment() {
        // Required empty public constructor
    }

    public static LineChartFragment getInstance() {
        LineChartFragment fragment = new LineChartFragment();
        return fragment;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_line_chart_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() throws NullPointerException {
        RetrofitManager.getInstance().getLineChartBean()
                .compose(RxUtil.<BaseBean<LineChartBean.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<LineChartBean.DataBean>>() {
                    @Override
                    public void call(BaseBean<LineChartBean.DataBean> lineChartBean) {
                        LineChartManager.initSingleLineChart(getContext(), mLineChart,
                                lineChartBean.getData().getxAxis(),
                                lineChartBean.getData().getData(),
                                lineChartBean.getData());
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
