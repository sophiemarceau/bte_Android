package com.btetop.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.MarketHeatIndexAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MarketCoinPairBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class MarketHeatIndexFragment extends BaseFragment {

    private static MarketHeatIndexFragment _instance = null;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.rv_market_heat_index)
    RecyclerView rvMarketHeatIndex;

    public static MarketHeatIndexFragment newInstance() {
        if (_instance == null)
            _instance = new MarketHeatIndexFragment();
        return _instance;
    }

    private MarketHeatIndexAdapter marketHeatIndexAdapter;
    private String index = "BTC";
    private int pageNo = 0;


    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_market_heat_index;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        List<MarketCoinPairBean.ListBean> list = new ArrayList<>();
        BteTopService.getCurrencypair("",
                pageNo + "",
                "10",
                "okex",
                "hot",
                "DESC")
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<MarketCoinPairBean>>() {
                    @Override
                    public void call(BaseBean<MarketCoinPairBean> listBaseBean) {

                        if (listBaseBean != null
                                && listBaseBean.getData() != null
                                && listBaseBean.getData().getList() != null
                                && listBaseBean.getData().getList().size() > 0) {
                            MarketCoinPairBean.ListBean listBean = new MarketCoinPairBean.ListBean();

                            list.add(listBean);//添加头部
                            list.addAll(listBaseBean.getData().getList());
                            initDataToView(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void initDataToView(List<MarketCoinPairBean.ListBean> marketCoinPairBeans) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMarketHeatIndex.setLayoutManager(layoutManager);
        marketHeatIndexAdapter = new MarketHeatIndexAdapter(getActivity(), marketCoinPairBeans, new MarketHeatIndexAdapter.ItemClick() {
            @Override
            public void ItemClick(String type) {
            }
        });
        rvMarketHeatIndex.setAdapter(marketHeatIndexAdapter);
    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }

}
