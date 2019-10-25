package com.btetop.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.btetop.R;
import com.btetop.adapter.BaocangAdapter;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.BurnedBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

import static com.btetop.config.UrlConfig.REFRESH_TIMES;

public class BaochangFragment extends BaseFragment {
    private static final String TAG = "dadanFragment";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private BaocangAdapter adapter;
    private CoinPairBean coinPair = null;
    private List<BurnedBean> mData = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            endLoop();
            refreshData();
            super.handleMessage(msg);

        }
    };


    public BaochangFragment() {

    }
    public static BaochangFragment newInstance() {
        BaochangFragment fragment = new BaochangFragment();
        return fragment;
    }

    protected int attachLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
           //添加header占位符

        adapter = new BaocangAdapter(getActivity(), mData);
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
        return true;
    }


    private void refreshData() {
        startLoop();
        if (coinPair != null) {
            BteTopService.getBurned(coinPair.getExchange(), coinPair.getBaseAsset(), coinPair.getQuoteAsset())
                    .compose(this.<BaseBean<List<BurnedBean>>>bindToLifecycle())
                    .compose(RxUtil.<BaseBean<List<BurnedBean>>>mainAsync())
                    .subscribe(new Action1<BaseBean<List<BurnedBean>>>() {
                        @Override
                        public void call(BaseBean<List<BurnedBean>> listBaseBean) {
                            if (("0000").equals(listBaseBean.getCode())) {

                                //比较一下新获取的数据的最后一条和现有数据的最后一条，如果时间戳是一样的，不需要更新
                                try {
                                    if (mData != null && mData.size() > 1) {
                                        //第0条是头部
                                        BurnedBean old = mData.get(1);
                                        if(listBaseBean.getData().size()>0){
                                            BurnedBean newOne = listBaseBean.getData().get(0);
                                            if(newOne.getDatetime() == old.getDatetime()) return;
                                        }

                                    }
                                }
                                catch (Exception e) {}
                                mData.clear();
                                mData.add(new BurnedBean());
                                mData.addAll(listBaseBean.getData());
                                adapter.notifyDataSetChanged();

                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });

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

    public CoinPairBean getCoinPair() {
        return coinPair;
    }

    public void setCoinPair(CoinPairBean coinPair) {

        if(coinPair == this.coinPair) return;
        if(mData!=null)  mData.clear();
        if(adapter!=null)  adapter.notifyDataSetChanged();

        this.coinPair = coinPair;
    }
}
