package com.btetop.service;

import com.btetop.bean.BaseBean;
import com.btetop.bean.WalletTransaction;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.ref.WeakReference;

import rx.functions.Action1;

public class BigWalletTransactionDataService {

    private static WalletTransaction transaction;

    public static WalletTransaction getWalletTransation(){
        return transaction;

    }

    //开始获取数据
    public static void start(RxFragment fragment, DataListener onDataListener) {

        WeakReference<RxFragment> fragmentWeakReference = new WeakReference<>(fragment);
        WeakReference<DataListener> listenerWeakReference = new WeakReference<>(onDataListener);


        getData(fragmentWeakReference,listenerWeakReference);
    }

    private static void getData(WeakReference<RxFragment> fragmentRef, WeakReference<DataListener> dataListenerRef) {

            RxFragment fragment =  fragmentRef.get();
            if(fragmentRef == null) return;


            DataListener listener = dataListenerRef.get();


            BteTopService.getBigWalletTranstion()
                    .compose(fragment.bindToLifecycle())
                    .compose(RxUtil.mainAsync())
                    .subscribe(new Action1<BaseBean<WalletTransaction>>() {
                        @Override
                        public void call(BaseBean<WalletTransaction> returnBaseBean) {
                            if (returnBaseBean != null && returnBaseBean.getData()!=null) {
                               transaction = returnBaseBean.getData();
                                if(listener!=null){
                                    listener.onData(transaction);
                                }
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });

    }

    //停止获取数据
    public static void stop() {
    }

    public interface DataListener {
        //0为现有全部数据，用于K线首次获取 1为向后追加(追加到数组最后 追加最新数据) -1为向前追加(追加到数组向前 追加历史数据)
        void onData(WalletTransaction data);
    }

}
