package com.btetop.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.DepthAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.DepthBean;
import com.btetop.bean.DetailDepthBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.btetop.config.UrlConfig.REFRESH_TIMES;

public class DetailShenduFragment extends BaseFragment {
    private static final String TAG = "depthFragment";


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    private DepthAdapter adapter;


    private List<DepthBean> mData = new ArrayList<>();


    private CoinInfo coinPair = null;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            endLoop();
            refreshData();
            super.handleMessage(msg);

        }
    };


    public DetailShenduFragment() {
    }


    public static DetailShenduFragment newInstance() {
        DetailShenduFragment fragment = new DetailShenduFragment();
        return fragment;
    }


    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        adapter = new DepthAdapter(getActivity());
        adapter.setData(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        Log.e(TAG, "initView: ");
    }

    @Override
    protected void initData() {

        refreshData();
    }

    @Override
    public boolean statisticsFragment() {
        return false;
    }

    private void refreshData() {
        Log.e(TAG, "refreshData: " );
        startLoop();
        if (coinPair != null) {
            BteTopService.getDetailDepth(coinPair.getSymbol())
                    .compose(bindToLifecycle())
                    .compose(RxUtil.mainAsync())
                    .subscribe(new Action1<BaseBean<DetailDepthBean>>() {
                        @Override
                        public void call(BaseBean<DetailDepthBean> detailDepthBeanBaseBean) {
                            if (detailDepthBeanBaseBean == null || detailDepthBeanBaseBean.getData() == null || detailDepthBeanBaseBean.getData().getDepth() == null) {
                                return;
                            }
                            mData.clear();
                            mData.add(new DepthBean());
                            mData.addAll(detailDepthBeanBaseBean.getData().getAllItem());
                            adapter.notifyDataSetChanged();

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();

                        }
                    });
        }

    }


    private void startLoop() {
        handler.sendEmptyMessageDelayed(0, REFRESH_TIMES);

    }

    private void endLoop() {
        handler.removeMessages(0);
    }


    @Override
    public void onPause() {
        super.onPause();
        endLoop();
    }

    @Override
    public void onResume() {
        super.onResume();
        startLoop();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        endLoop();
    }

    public CoinInfo getCoinPair() {
        return coinPair;
    }

    public void setCoinPair(CoinInfo coinPair) {

        if (coinPair == this.coinPair) return;
        if (mData != null) mData.clear();
        if (adapter != null) adapter.notifyDataSetChanged();

        this.coinPair = coinPair;
    }
}
