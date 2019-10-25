package com.btetop.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.DadanAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.AbnormityBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.config.UrlConfig;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class DadanFragment extends BaseFragment {
    private static final String TAG = "dadan";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private DadanAdapter adapter;


    private List<AbnormityBean> mData = new ArrayList<>();

    private CoinPairBean coinPair = null;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            endLoop();
            refreshData();
            super.handleMessage(msg);

        }
    };


    public DadanFragment() {
    }

    public static DadanFragment newInstance() {
        DadanFragment fragment = new DadanFragment();
        return fragment;
    }


    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        adapter = new DadanAdapter(getActivity());
        adapter.setdata(mData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        if (coinPair != null) {

            BteTopService.getAbnormity(coinPair.getExchange(), coinPair.getBaseAsset(), coinPair.getQuoteAsset())
                    .compose(this.<BaseBean<List<AbnormityBean>>>bindToLifecycle())
                    .compose(RxUtil.<BaseBean<List<AbnormityBean>>>mainAsync())
                    .subscribe(new Action1<BaseBean<List<AbnormityBean>>>() {
                        @Override
                        public void call(BaseBean<List<AbnormityBean>> listBaseBean) {
                            List<AbnormityBean> data = listBaseBean.getData();
                            //比较一下新获取的数据的最后一条和现有数据的最后一条，如果时间戳是一样的，不需要更新
                            try {
                                if (mData != null && mData.size() > 1) {
                                    //第0条是头部
                                    AbnormityBean old = mData.get(1);
                                    if (data.size() > 0) {
                                        AbnormityBean newOne = data.get(0);
                                        if (newOne.getDatetime() == old.getDatetime()) return;
                                    }

                                }
                            } catch (Exception e) {}

                            mData.clear();
                            mData.add(new AbnormityBean());
                            mData.addAll(listBaseBean.getData());
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

    private void startLoop(){
        handler.sendEmptyMessageDelayed(0, UrlConfig.REFRESH_TIMES);
    }

    private void endLoop() {
        handler.removeMessages(0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        endLoop();
    }

    public CoinPairBean getCoinPair() {
        return coinPair;
    }

    public void setCoinPair(CoinPairBean coinPair) {
        if(coinPair == this.coinPair) return;
        if(mData!=null)  mData.clear();
       // if(adapter!=null)  adapter.notifyDataSetChanged();
        this.coinPair = coinPair;
    }
}
