package com.btetop.service;

import android.os.Message;
import android.text.TextUtils;

import com.btetop.bean.AbnormalOrderBean;
import com.btetop.bean.BaseBean;
import com.btetop.bean.KlineBean;
import com.btetop.bean.KlineVolumeBean;
import com.btetop.bean.ResistanceBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.example.zylaoshi.library.utils.LogUtil;
import com.guoziwei.klinelib.model.KLineFullData;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action1;

import static com.guoziwei.klinelib.util.DataUtils.isInHistoryData;

public class FutureDataService {

    //key格式 OKEX-BTC-USDT-15m
    private static Map<String,List<KLineFullData>> dataCacheMap = new HashMap<>();
    private static Map<String,KlineBean.TickerBean> tickerCacheMap = new HashMap<>();
    private int intervalTime = 3 * 1000;
    private android.os.Handler handler;

    private DataListener listener = null;

    private String currentKey;

    public DataListener getListener() {
        return listener;
    }

    public void setListener(DataListener listener) {
        this.listener = listener;
    }


    private RxFragment fragment;

    public static FutureDataService newInstance(RxFragment fragment){
       return new FutureDataService(fragment);

    }

    private FutureDataService(RxFragment fragment){
        this.fragment = fragment;
    }
    //开始获取数据
    public void start(String key) {

        currentKey = key ;
        if (dataCacheMap.get(key) == null) {
            dataCacheMap.put(key, new ArrayList<>());
        }

        //开启数据获取

        if(handler == null){

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
            if(listener!=null) listener.onData(key,0,cacheDataList,tickerCacheMap.get(key));
        }
        getData();
    }

    private void getData(){
        int mode = 0;
        long timestamp = 0;
        if(TextUtils.isEmpty(currentKey)) return;

        String key = currentKey;

        List<KLineFullData> cacheDataList = dataCacheMap.get(key);

        if(cacheDataList.size()>0){
            mode = 1;
            timestamp = cacheDataList.get(cacheDataList.size()-1).getDate();
        }

        long start = 0, end = 0;

        if (mode == 1) {
                //从数组中取出最大的一个时间戳作为向前传的数据
            if (timestamp > start) start = timestamp;
        }


        String[] keys = key.split("-");
        if(keys.length<4) return;

        //key格式 OKEX-BTC-USDT-15m
        String exchange = keys[0];
        String baseAsset = keys[1];
        String quoteAsset =  keys[2];
        String timeType = keys[3];

        final int dataMode = mode;
            BteTopService.getFutureKlineBean(exchange, baseAsset, quoteAsset, end, start, timeType)
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
                                    handler.sendEmptyMessageDelayed(0, intervalTime);
                                return;
                            }

                            KlineBean.TickerBean ticker = klineBeanBaseBean.getData().getTicker();
                            tickerCacheMap.put(key,ticker);

                            ArrayList<KLineFullData> lists = new ArrayList<>(klineBeanBaseBean.getData().getKline().size());
                            for (KlineBean.KlineBeanInner item : klineBeanBaseBean.getData().getKline()) {
                                KLineFullData data = new KLineFullData();
                                data.setHigh(item.getHigh());
                                data.setOpen(item.getOpen());
                                data.setLow(item.getLow());
                                data.setDate(item.getDate());
                                data.setClose(item.getClose());
                                data.setVol(item.getVol());

                                if (item.getExtra() != null) {
                                    ResistanceBean resistance = item.getExtra().getResistance();
                                    //region 压力线
                                    if (resistance != null) {
                                        data.setBuyPrice5(resistance.getBuyPrice5());
                                        data.setBuyPrice5Amount(resistance.getBuyCumulativeAmount5());
                                        data.setSellPrice5(resistance.getSellPrice5());
                                        data.setSellPrice5Amount(resistance.getSellCumulativeAmount5());

                                    }
                                    //endregion

                                    //region 异动
                                    List<AbnormalOrderBean> abnormity = item.getExtra().getAbnormity();
                                    if (abnormity != null && abnormity.size() > 0) {
                                          for (AbnormalOrderBean abnormalOrderItem : abnormity) {
                                            switch (abnormalOrderItem.getOrderType().toLowerCase()) {
                                                case "buy":
                                                    data.setYcBuyCount(abnormalOrderItem.getCount());
                                                    data.setYcBuyPrice(abnormalOrderItem.getPrice());
                                                    break;
                                                case "sell":
                                                    data.setYcSellCount(abnormalOrderItem.getCount());
                                                    data.setYcSellPrice(abnormalOrderItem.getPrice());
                                                    break;
                                                case "cancelbuy":
                                                    data.setYcCancelBuyCount(abnormalOrderItem.getCount());
                                                    break;
                                                case "cancelsell":
                                                      data.setYcCancelSellCount(abnormalOrderItem.getCount());
                                                    break;
                                                case "tradebuy":
                                                    data.setTradeBuyCount(abnormalOrderItem.getCount());
                                                    break;
                                                case "tradesell":
                                                    data.setTradeSellCount(abnormalOrderItem.getCount());
                                                    break;

                                                case "bigbuy":
                                                    data.setBigBuyCount(abnormalOrderItem.getCount());
                                                    break;
                                                case "bigsell":
                                                    data.setBigSellCount(abnormalOrderItem.getCount());
                                                    break;

                                                case "buyburned":
                                                    data.setYcBuyBurnedCount(abnormalOrderItem.getCount());
                                                    data.setYcBuyBurnedPrice(abnormalOrderItem.getPrice());
                                                    break;
                                                case "sellburned":
                                                    data.setYcSellBurnedCount(abnormalOrderItem.getCount());
                                                    data.setYcSellBurnedPrice(abnormalOrderItem.getPrice());
                                                    break;
                                                case "threshold":
                                                    data.setThreshold(abnormalOrderItem.getCount());
                                                    break;
                                            }

                                        }//for

                                    }

                                    /*
                                    KlineVolumeBean volumeBean = item.getExtra().getVolume();
                                    if(volumeBean!=null){
                                        data.setBuyCount(volumeBean.getBuyCount());
                                        data.setSellCount(volumeBean.getSellCount());
                                    }
                                    */
                                    //endregion

                                }
                                lists.add(data);

                            }

                            //如果历史记录不为空，再次取的数据需要将原始数据的最后一条删掉
                            if(dataCacheMap.get(key).size()>0){
                                dataCacheMap.get(key).remove(dataCacheMap.get(key).size()-1);
                            }


                            for(int i=0;i<lists.size();i++){
                                KLineFullData newData = lists.get(i);
                                int index = isInHistoryData(newData,dataCacheMap.get(key));
                                if(index == -1){
                                    dataCacheMap.get(key).add(newData);
                                }
                                else {
                                    dataCacheMap.get(key).remove(index);
                                    dataCacheMap.get(key).add(index,newData);
                                }
                            }


                            if(listener!=null && TextUtils.equals(key,currentKey)){
                                listener.onData(key,dataMode,lists,ticker);
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
    public void stop(){
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
    }

    public interface DataListener{
        //0为现有全部数据，用于K线首次获取 1为向后追加(追加到数组最后 追加最新数据) -1为向前追加(追加到数组向前 追加历史数据)
        void onData(String key,int mode,List<KLineFullData> data,KlineBean.TickerBean ticker);
    }

}
