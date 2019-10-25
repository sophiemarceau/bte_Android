package com.btetop.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.MarketCoinPairAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MarketCoinPairBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class MarketCoinPairFragment extends BaseFragment {

    private static MarketCoinPairFragment _instance = null;
    @BindView(R.id.rv_fragment_market_coin_pair)
    RecyclerView rvFragmentMarketCoinPair;

    private MarketCoinPairAdapter marketCoinPairAdapter;
    private String index = "BTC";
    private int pageNo = 0;

    public static MarketCoinPairFragment newInstance() {
        if (_instance == null)
            _instance = new MarketCoinPairFragment();
        return _instance;
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_market_coin_pair;
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
        rvFragmentMarketCoinPair.setLayoutManager(layoutManager);
        marketCoinPairAdapter = new MarketCoinPairAdapter(getActivity(), marketCoinPairBeans, new MarketCoinPairAdapter.ItemClick() {
            @Override
            public void ItemClick(String type) {
            }
        });
        rvFragmentMarketCoinPair.setAdapter(marketCoinPairAdapter);
    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }
}
