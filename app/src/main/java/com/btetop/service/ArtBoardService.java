package com.btetop.service;

import com.btetop.bean.AirBoardBean;
import com.btetop.bean.BaseBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import rx.functions.Action1;

public class ArtBoardService {
    private static final String TAG = "ArtBoardService";
    private static ArtBoardService instance = null;

    private static List<AirBoardBean> mdata;
    private List<DataListener> listenerList=new ArrayList<>();

    private ArtBoardService() {

    }

    public static ArtBoardService getInstance() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if (instance == null) {
                instance = new ArtBoardService();
            }
        } finally {
            lock.unlock();

        }
        return instance;
    }

    public void addListener(DataListener listener) {
        if (!listenerList.contains(listener)) {
            listenerList.add(listener);
        }

    }
    public void unSetListener(DataListener listener){
        listenerList.remove(listener);
    }

    public void getOnLineData() {
        BteTopService.getAirBoardList()
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<List<AirBoardBean>>>() {
                    @Override
                    public void call(BaseBean<List<AirBoardBean>> listBaseBean) {
                        if (listBaseBean == null ) {
                            return;
                        }
                        mdata = listBaseBean.getData();

                        for (DataListener listener : listenerList) {
                            listener.onData(listBaseBean.getData());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void getMenmoryData() {
        if (mdata != null) {
            for (DataListener listener : listenerList) {
                listener.onData(mdata);
            }
        }
    }

    public void getData() {
        if (mdata == null) {
            getOnLineData();
        } else {
            getMenmoryData();

        }

    }

    public void setData(List<AirBoardBean> listBean){
        mdata=listBean;
    }


    public interface DataListener {
        void onData(List<AirBoardBean> data);
    }

}
