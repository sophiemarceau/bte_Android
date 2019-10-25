package com.example.zylaoshi.library.net.request;

import com.example.zylaoshi.library.net.converter.ResponseConvertFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @description: 获取RetrofitClient对象
 * @autour: zylaoshi
 * @date: 2017/12/4 13:55
*/
public class RetrofitClient {

    private static OkHttpClient mOkHttpClient;
//    初始化BaseUrl
    private  static String baseUrl;

    private  static Retrofit retrofit;
    /**
     * RetrofitClient 构造 函数
     * 获取OKhttpClient 实例
     * @param mOkHttpClient
     */
    public RetrofitClient(String baseUrl, OkHttpClient mOkHttpClient)
    {
        this.baseUrl=baseUrl;
        this.mOkHttpClient=mOkHttpClient;
    }


    /**
     *  修改BaseUrl地址
     * @param baseUrl
     */
    public RetrofitClient setBaseUrl(String baseUrl)
    {
        this.baseUrl=baseUrl;
        return this;
    }

    /**
     *  获得对应的ApiServcie对象
     * @param service
     * @param <T>
     * @return
     */
    public  <T> T  builder(Class<T> service)
    {
        if(baseUrl==null)
        {
            throw new RuntimeException("baseUrl is null!");
        }
        if (service == null) {
            throw new RuntimeException("api Service is null!");
        }
        retrofit=new Retrofit.Builder()
                .addConverterFactory(ResponseConvertFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }

}
