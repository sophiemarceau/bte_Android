package com.btetop.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Build;

import com.blankj.utilcode.util.DeviceUtils;
import com.btetop.BuildConfig;
import com.btetop.application.BteTopApplication;
import com.btetop.config.UrlConfig;
import com.btetop.service.UserService;
import com.btetop.utils.GZIPUtils;
import com.btetop.utils.SSLSocketFactoryUtils;
import com.example.zylaoshi.library.utils.DeviceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/18.
 */

public class RetrofitManager {
    private static BteTopServiceInterface bteTopApiService;

    private RetrofitManager() {

    }

    public static synchronized BteTopServiceInterface getInstance() {
        if (bteTopApiService == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls().create();

            Retrofit mRetrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(genericClient())
                    .build();
            bteTopApiService = mRetrofit.create(BteTopServiceInterface.class);
        }
        return bteTopApiService;
    }


    private static OkHttpClient genericClient() {
        //日志打印拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        //添加统一头
        HeaderInterceptor headerInterceptor = new HeaderInterceptor();
        //gzip 压缩 解压缩
        NetworkInterceptor networkInterceptor = new NetworkInterceptor();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(networkInterceptor)
                .connectTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return httpClient;
    }


    /**
     * 头部拦截器
     */
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            String token = UserService.getCurrentUserToken();
            String version = DeviceUtil.getVersion(BteTopApplication.getContext());
            String sdkVersionName = DeviceUtils.getSDKVersionName();
            String sdkVersionCode = DeviceUtils.getSDKVersionCode() + "";


            Request request = chain.request()
                    .newBuilder()
                    .addHeader("bte-token", token)
                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cache-Control", String.format("public, max-age=%d", 60))
                    .addHeader("channel", "android")
                    .addHeader("version", version)
                    .addHeader("sdkVersionName", sdkVersionName)
                    .addHeader("sdkVersionCode", sdkVersionCode)
                    .addHeader("Brand", Build.BOARD)
                    .addHeader("Model", Build.MODEL)
                    .addHeader("SystemName", Build.VERSION.RELEASE)
                    .addHeader("SystemCode", Build.VERSION.SDK_INT + "")
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * Url拦截器,设置刷新
     */
    private static class UrlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString();
            return chain.proceed(request);
        }
    }

    public static class NetworkInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            boolean checked = true;
            if (response.code() == 200) {
                //这里是网络拦截器，可以做错误处理
                MediaType mediaType = response.body().contentType();
                //当调用此方法java.lang.IllegalStateException: closed，原因为OkHttp请求回调中response.body().string()只能有效调用一次
                //String content = response.body().string();
                byte[] data = response.body().bytes();
                if (GZIPUtils.isGzip(response.headers())) {
                    //请求头显示有gzip，需要解压
                    data = GZIPUtils.uncompress(data);
                }
//                //获取签名
//                String sdkSign = response.header("sdkSign");
//                try {
//                    //效验签名
//                    checked = RSAUtils.verify(data, GlobalField.APP_SERVICE_KEY(), sdkSign);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                if (!checked) {
                    return null;
                } else {
                    //创建一个新的responseBody，返回进行处理
                    return response.newBuilder()
                            .body(ResponseBody.create(mediaType, data))
                            .build();
                }
            } else {
                return response;
            }

        }
    }
}
