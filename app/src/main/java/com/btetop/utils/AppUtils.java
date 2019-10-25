package com.btetop.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.btetop.bean.BaseBean;
import com.btetop.net.BteTopService;

import rx.functions.Action1;

public class AppUtils {

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    public static void addEvent(String module,
                                String type, String target,
                                String exchange, String base,
                                String quote) {
        BteTopService.addEvent("android",
                module, type, target, exchange, base, quote)
                .compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

    }
}
