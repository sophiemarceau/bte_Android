package com.btetop.service;

import com.btetop.bean.BaseBean;
import com.btetop.bean.MiningBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;

import rx.functions.Action1;

public class MiningService {

    private static MiningBean miningBean = null;

    public static MiningBean getMiningBean() {
        return miningBean;
    }

    private static void setMiningBean(MiningBean miningBean) {
        MiningService.miningBean = miningBean;
    }

    public static void loadMiningInfo(loadMiningInfo callback) {
        BteTopService.getMiningInfo()
                .compose(RxUtil.<BaseBean<MiningBean>>mainAsync())
                .subscribe(new Action1<BaseBean<MiningBean>>() {
                    @Override
                    public void call(BaseBean<MiningBean> baseBean) {
                        if (baseBean != null) {
                            MiningBean data = baseBean.getData();
                            setMiningBean(data);
                            callback.success(data);
                            if (data.getInfo().getStatus() == 1) {
                                callback.open();
                            } else {
                                callback.noOpen(baseBean.getData().getCode());
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

    public interface loadMiningInfo {
        void success(MiningBean data);

        void open();

        void noOpen(String inviteCode);
    }
}
