package com.example.zylaoshi.library.net.download;


import com.example.zylaoshi.library.config.Contants;
import com.example.zylaoshi.library.net.interceptor.ProgressInterceptor;
import com.example.zylaoshi.library.net.service.UpdataService;
import com.example.zylaoshi.library.net.subscribers.FileSubscriber;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HttpDownLoadManager {
    private UpdataService mUpdataService;

    public HttpDownLoadManager() {

    }
    public void load(String url, final FileCallBack<ResponseBody> callBack){
        ProgressInterceptor progressInterceptor = new ProgressInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(100, TimeUnit.SECONDS);
        builder.addInterceptor(progressInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Contants.BASE_URL)
                .build();
        mUpdataService = retrofit.create(UpdataService.class);
        mUpdataService.download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        callBack.saveFile(body);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(callBack));
    }

}
