package com.btetop.service;

import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.btetop.bean.AbnormalOrderBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.HourShortCommentBean;
import com.btetop.bean.KlineBean;
import com.btetop.bean.ResistanceBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.guoziwei.klinelib.model.KLineFullData;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

import static com.guoziwei.klinelib.util.DataUtils.isInHistoryData;

public class KlineDataService {

    //key格式 OKEX-BTC-USDT-15m
    private static Map<String, List<KLineFullData>> dataCacheMap = new HashMap<>();
    private static Map<String, KlineBean.TickerBean> tickerCacheMap = new HashMap<>();
    private int intervalTime = 3 * 1000;
    private android.os.Handler handler;

    private DataListener listener = null;

    private String currentKey;
    private RxFragment fragment;

    private KlineDataService(RxFragment fragment) {
        this.fragment = fragment;
    }

    public static KlineDataService newInstance(RxFragment fragment) {
        return new KlineDataService(fragment);

    }

    public DataListener getListener() {
        return listener;
    }

    public void setListener(DataListener listener) {
        this.listener = listener;
    }

    //开始获取数据
    public void start(String key) {

        currentKey = key;
        if (dataCacheMap.get(key) == null) {
            dataCacheMap.put(key, new ArrayList<>());
        }

        //开启数据获取

        if (handler == null) {

            handler = new android.os.Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    removeMessages(0);
                    getData();
                }
            };
        }

        List cacheDataList = dataCacheMap.get(key);
        //存在历史数据
        if (cacheDataList.size() > 0) {
            if (listener != null) listener.onData(key, 0, cacheDataList, tickerCacheMap.get(key));
        }
        getData();
    }

    private void getData() {
        int mode = 0;
        long timestamp = 0;
        if (TextUtils.isEmpty(currentKey)) return;

        String key = currentKey;

        List<KLineFullData> cacheDataList = dataCacheMap.get(key);

        if (cacheDataList.size() > 0) {
            mode = 1;
            timestamp = cacheDataList.get(cacheDataList.size() - 1).getDate();
        }

        long start = 0, end = 0;

        if (mode == 1) {
            //从数组中取出最大的一个时间戳作为向前传的数据
            if (timestamp > start) start = timestamp;
        }


        String[] keys = key.split("-");
        if (keys.length < 4) return;

        //key格式 OKEX-BTC-USDT-15m
        String exchange = keys[0];
        String baseAsset = keys[1];
        String quoteAsset = keys[2];
        String timeType = keys[3];

        if ("1h".equalsIgnoreCase(timeType) && mode == 0) {
            //首先获取Comment 数据
            getCommentData(mode, key, end, start);

        } else {
            getKlineData(mode, key, end, start, null);
        }

    }

    private void getCommentData(int mode, String key, long end, long start) {
        String[] keys = key.split("-");
        if (keys.length < 4) return;

        //key格式 OKEX-BTC-USDT-15m
        String exchange = keys[0];
        String baseAsset = keys[1];
        String quoteAsset = keys[2];
        String timeType = keys[3];

        BteTopService.getHourShortComment(baseAsset)
                .compose(fragment.bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<HourShortCommentBean>>>() {
                    @Override
                    public void call(BaseBean<List<HourShortCommentBean>> listBaseBean) {
                        Map<Long, HourShortCommentBean> comments = null;
                        if (listBaseBean != null && listBaseBean.getData() != null && listBaseBean.getData().size() > 0) {
                            comments = new HashMap<>();
                            for(HourShortCommentBean comment : listBaseBean.getData() ){
                                comments.put(comment.getKlineDateTime(),comment);
                            }


                        }
                        getKlineData(mode, key, end, start, comments);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        getKlineData(mode, key, end, start, null);
                    }
                });
    }


    private void getKlineData(int mode, String key, long end, long start, Map<Long,HourShortCommentBean> comments) {
        String[] keys = key.split("-");
        if (keys.length < 4) return;

        //key格式 OKEX-BTC-USDT-15m
        String exchange = keys[0];
        String baseAsset = keys[1];
        String quoteAsset = keys[2];
        String timeType = keys[3];


        BteTopService.getKlineBean(exchange, baseAsset, quoteAsset, end, start, timeType)
                .compose(fragment.bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<KlineBean>>() {
                    @Override
                    public void call(BaseBean<KlineBean> klineBeanBaseBean) {
                        //验证
                        if (!"0000".equalsIgnoreCase(klineBeanBaseBean.getCode()) ||
                                klineBeanBaseBean.getData() == null ||
                                klineBeanBaseBean.getData().getKline() == null ||
                                klineBeanBaseBean.getData().getKline().size() == 0) {
                            return;
                        }

                        KlineBean.TickerBean ticker = klineBeanBaseBean.getData().getTicker();
                        tickerCacheMap.put(key, ticker);

                        ArrayList<KLineFullData> lists = new ArrayList<>(klineBeanBaseBean.getData().getKline().size());
                        for (KlineBean.KlineBeanInner item : klineBeanBaseBean.getData().getKline()) {
                            KLineFullData data = new KLineFullData();
                            data.setHigh(item.getHigh());
                            data.setOpen(item.getOpen());
                            data.setLow(item.getLow());
                            data.setDate(item.getDate());
                            data.setClose(item.getClose());
                            data.setVol(item.getVol());
                            if(comments!=null && comments.get(item.getDate())!=null){
                                HourShortCommentBean comment = comments.get(item.getDate());
                                data.setBubbleText(comment.getContent());
                                if (comment.getMarkerPlace() == 1) {
                                    data.setBubbleY(item.getHigh());
                                } else {
                                    data.setBubbleY(item.getLow());
                                }
                            }
                            lists.add(data);

                        }

                        //如果历史记录不为空，再次取的数据需要将原始数据的最后一条删掉
                       // if (dataCacheMap.get(key).size() > 0) {
                         //   dataCacheMap.get(key).remove(dataCacheMap.get(key).size() - 1);
                       // }


                        for(int i=0;i<lists.size();i++){
                            KLineFullData newData = lists.get(i);
                            int index = isInHistoryData(newData,dataCacheMap.get(key));
                            if(index == -1){
                                dataCacheMap.get(key).add(newData);
                            }
                            else{
                                dataCacheMap.get(key).remove(index);
                                dataCacheMap.get(key).add(index,newData);
                            }
                        }


                        if (listener != null && TextUtils.equals(key, currentKey)) {
                            listener.onData(key, mode, lists, ticker);
                        }

                        handler.sendEmptyMessageDelayed(0, intervalTime);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        handler.sendEmptyMessageDelayed(0, intervalTime);
                    }
                });
    }

    //停止获取数据
    public void stop() {
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
    }

    public interface DataListener {
        //0为现有全部数据，用于K线首次获取 1为向后追加(追加到数组最后 追加最新数据) -1为向前追加(追加到数组向前 追加历史数据)
        void onData(String key, int mode, List<KLineFullData> data, KlineBean.TickerBean ticker);
    }

}
