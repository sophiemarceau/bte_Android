package com.example.zylaoshi.library.net.interceptor;

import android.content.Context;
import android.util.Log;

import com.example.zylaoshi.library.MyLibrary;
import com.example.zylaoshi.library.config.SPKeys;
import com.example.zylaoshi.library.utils.DeviceUtil;
import com.example.zylaoshi.library.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;

/**
 * @description:  请求拦截器  统一添加请求头使用
 * @autour: zylaoshi
 * @date: 2017/11/28 17:48
*/

public class HeaderInterceptor implements Interceptor {


    private Context context;

    public HeaderInterceptor() {
        super();
        this.context = MyLibrary.getApplication();

    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();
        final String headInfo = DeviceUtil.getHeadInfo(context);
        Observable.just(SPUtils.get(SPKeys.COOKIE, ""))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String cookie) {
                        //添加cookie
                        Log.e("cookie------------>",cookie);
                        builder.addHeader("X-Wlb-Token", cookie)
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("User-Agent",headInfo)
                                .header("Cache-Control", String.format("public, max-age=%d", 60))
                                .removeHeader("Pragma")
                                .build();
                    }
                });
        return chain.proceed(builder.build());
    }


}
