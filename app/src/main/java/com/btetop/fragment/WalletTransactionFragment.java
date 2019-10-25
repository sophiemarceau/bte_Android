package com.btetop.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.WalletTransactionAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.WalletTransaction;
import com.btetop.service.BigWalletTransactionDataService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//场外大额交易
public class WalletTransactionFragment extends BaseFragment {
    private static final String TAG = "walletTransactionFragment";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private WalletTransactionAdapter adapter;

    private List<WalletTransaction.WalletTractionDetail> mData = new ArrayList<>();



    public WalletTransactionFragment() {

    }
    public static WalletTransactionFragment newInstance() {
        WalletTransactionFragment fragment = new WalletTransactionFragment();
        return fragment;
    }

    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        adapter = new WalletTransactionAdapter(getActivity());
        adapter.setData(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        BigWalletTransactionDataService.start(this,dataListener);

    }

    @Override
    public boolean statisticsFragment() {
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    public void setCoinPair(CoinPairBean coinPair) { }


    private BigWalletTransactionDataService.DataListener dataListener = new BigWalletTransactionDataService.DataListener() {
        @Override
        public void onData(WalletTransaction data) {
            if(adapter!=null){
                mData.clear();
                mData.add(new WalletTransaction().new WalletTractionDetail());
                mData.addAll(data.getDetails());
                adapter.notifyDataSetChanged();
            }

        }
    };

}
