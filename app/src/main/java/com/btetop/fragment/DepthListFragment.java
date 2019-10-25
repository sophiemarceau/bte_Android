package com.btetop.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.DepthAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.DepthBean;
import com.btetop.service.DepthDataService;

import java.util.List;

import butterknife.BindView;

public class DepthListFragment extends BaseFragment {
    private static final String TAG = "depthFragment";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    private DepthAdapter adapter;


    private CoinPairBean coinPair = null;



    public DepthListFragment() {
    }


    public static DepthListFragment newInstance() {
        DepthListFragment fragment = new DepthListFragment();
        return fragment;
    }


    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        adapter = new DepthAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean statisticsFragment() {
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DepthDataService.stop();
    }

    public CoinPairBean getCoinPair() {
        return coinPair;
    }

    public void setCoinPair(CoinPairBean coinPair) {

        if (coinPair == this.coinPair) return;

        this.coinPair = coinPair;

        DepthDataService.start(coinPair,this,dataListener);


    }

    private DepthDataService.DataListener dataListener = new DepthDataService.DataListener() {
        @Override
        public void onData(List<DepthBean> data) {
            if (adapter != null) {
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }

        }
    };
}
