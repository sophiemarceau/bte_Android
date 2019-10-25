package com.btetop.service;

import android.os.Message;

import com.btetop.bean.BaseBean;
import com.btetop.bean.CoinPairBean;
import com.btetop.bean.DepthBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

public class DepthDataService {
    private static final String TAG = "DepthDataService";

    private static Map<String,List<DepthBean>> mData = new HashMap<>();


    private static android.os.Handler handler;


    private static int intervalTime = 3 * 1000;


    public static List<DepthBean> getDepthList(String key){
        return mData.get(key);

    }




    //开始获取数据
    public static void start(CoinPairBean pair, RxFragment fragment, DataListener onDataListener) {
        if(pair == null) return;
            //开启数据获取

        if(handler!=null){
            handler.removeMessages(0);
            handler = null;
        }

            handler = new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    removeMessages(0);
                    getData(pair,fragment,onDataListener);
                }
            };

        getData(pair,fragment,onDataListener);
    }

    private static void getData(CoinPairBean pair, RxFragment fragment, DataListener dataListener) {
            if(pair == null) return;

        if (fragment==null) {
            return;
        }


            //OKEX-BTC-QUARTER
            String key = pair.getExchange()+"-"+pair.getBaseAsset()+"-"+ pair.getQuoteAsset();

            BteTopService.getDepth(pair.getExchange(), pair.getBaseAsset(), pair.getQuoteAsset())
                    .compose(fragment.bindToLifecycle())
                    .compose(RxUtil.mainAsync())
                    .subscribe(new Action1<BaseBean<List<DepthBean>>>() {
                        @Override
                        public void call(BaseBean<List<DepthBean>> listBaseBean) {
                            if (listBaseBean != null && listBaseBean.getData().size() >= 0) {

                                List<DepthBean> list = new ArrayList<>();

                                list.add(new DepthBean());
                                list.addAll(listBaseBean.getData());

                                mData.put(key,list);

                                if(dataListener!=null){
                                    dataListener.onData(mData.get(key));
                                }
                            }
                            handler.sendEmptyMessageDelayed(0, intervalTime);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            handler.sendEmptyMessageDelayed(0, intervalTime);
                            throwable.printStackTrace();
                        }
                    });

    }

    //停止获取数据
    public static void stop() {
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
    }

    public interface DataListener {
        //0为现有全部数据，用于K线首次获取 1为向后追加(追加到数组最后 追加最新数据) -1为向前追加(追加到数组向前 追加历史数据)
        void onData(List<DepthBean> data);
    }

}
