package com.btetop.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.DetailDepthAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinInfo;
import com.btetop.bean.DetailDadanBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.btetop.config.UrlConfig.REFRESH_TIMES;

public class DetailDadanFragment extends BaseFragment {
    private static final String TAG = "detailDadanFragment";


    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;


    private DetailDepthAdapter adapter;


    private List<DetailDadanBean.MergeDetailDadanBean> mData = new ArrayList<>();


    private CoinInfo coinPair = null;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            endLoop();
            refreshData();
            super.handleMessage(msg);

        }
    };


    public DetailDadanFragment() {
    }


    public static DetailDadanFragment newInstance() {
        DetailDadanFragment fragment = new DetailDadanFragment();
        return fragment;
    }


    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }
    public void gotoTop(){
        recyclerView.smoothScrollToPosition(0);
    }
    @Override
    protected void initView(View view) {
        adapter=new DetailDepthAdapter(getContext(),mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
        startLoop();
        Log.e(TAG, "refreshData: " );
        if (coinPair != null) {
            BteTopService.getDetailDadanBean(coinPair.getSymbol())
                    .compose(bindToLifecycle())
                    .compose(RxUtil.mainAsync())
                    .subscribe(new Action1<BaseBean<DetailDadanBean>>() {
                        @Override
                        public void call(BaseBean<DetailDadanBean> detailDadanBeanBaseBean) {
                            if (detailDadanBeanBaseBean==null || detailDadanBeanBaseBean.getData()==null || !detailDadanBeanBaseBean.getCode().equals("0000")) {
                                return;
                            }
                            mData.clear();
                            mData.addAll(detailDadanBeanBaseBean.getData().getAllData());
                            adapter.notifyDataSetChanged();

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();

                        }
                    });
//            BteTopService.getDepth(coinPair.getExchange(), coinPair.getBaseAs, coinPair.getQuoteAsset())
//                    .compose(this.<BaseBean<List<DepthBean>>>bindToLifecycle())
//                    .compose(RxUtil.<BaseBean<List<DepthBean>>>mainAsync())
//                    .subscribe(new Action1<BaseBean<List<DepthBean>>>() {
//                        @Override
//                        public void call(BaseBean<List<DepthBean>> listBaseBean) {
//                            if (listBaseBean != null && listBaseBean.getData().size() >= 0) {
//                                mData.clear();
//                                mData.add(new DepthBean());
//                                mData.addAll(listBaseBean.getData());
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    }, new Action1<Throwable>() {
//                        @Override
//                        public void call(Throwable throwable) {
//                            throwable.printStackTrace();
//                        }
//                    });
        }

    }


    private void startLoop(){
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
