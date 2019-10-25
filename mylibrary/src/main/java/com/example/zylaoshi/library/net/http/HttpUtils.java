package com.example.zylaoshi.library.net.http;

import android.content.Context;
import android.util.Log;

import com.example.zylaoshi.library.MyLibrary;
import com.example.zylaoshi.library.config.SPKeys;
import com.example.zylaoshi.library.net.config.NetWorkConfiguration;
import com.example.zylaoshi.library.net.cookie.SimpleCookieJar;
import com.example.zylaoshi.library.net.interceptor.HeaderInterceptor;
import com.example.zylaoshi.library.net.interceptor.ReceivedCookiesInterceptor;
import com.example.zylaoshi.library.net.request.RetrofitClient;
import com.example.zylaoshi.library.utils.DeviceUtil;
import com.example.zylaoshi.library.utils.NetworkUtil;
import com.example.zylaoshi.library.utils.SPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @description: Rxjava+Retrofit网络访问工具类
 * @autour: zylaoshi
 * @date: 2017/11/29 14:51
*/
public class HttpUtils {
    public static final String TAG = "HttpUtils";
    //获得HttpUtils实例
    private static HttpUtils mInstance;
    //OkHttpClient对象
    private OkHttpClient mOkHttpClient;
    private static NetWorkConfiguration configuration;
    private Context context;
    /**
     * 是否需要显示load进度
     * 默认显示true
     */
    public boolean isLoadProgress = true;
    /**
     *  是否加载本地缓存数据
     *  默认为True
     */
    private boolean isLoadDiskCache = true;
    /**
     *  ---> 针对有网络情况
     *  是否加载内存缓存数据
     *   默认为False
     */
    private boolean isLoadMemoryCache = false;
    /**
     *   ---> 针对无网络情况
     *  是否加载本地缓存数据
     * @param isCache  true为加载 false不进行加载
     * @return
     */
    public HttpUtils setLoadDiskCache(boolean isCache) {
        this.isLoadDiskCache = isCache;
        return this;
    }
    /**
     *  是否加载内存缓存数据
     * @param isCache  true为加载 false不进行加载
     * @return
     */
    public  HttpUtils setLoadMemoryCache(boolean isCache) {
        this.isLoadMemoryCache = isCache;
        return this;
    }
    public HttpUtils setLoadProgress(boolean isLoadProgress){
        this.isLoadProgress = isLoadProgress;
        return this;
    }

    public RetrofitClient getRetofitClinet() {
//        Log.i("configuration:====>",configuration.toString());
        return new RetrofitClient(configuration.getBaseUrl(),mOkHttpClient);
    }

    /**
     *  设置HTTPS客户端带证书访问
     * @param certificates  本地证书
     */
    public HttpUtils setCertificates(InputStream... certificates) {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
                .build();
        return this;
    }

    /**
     *  设置是否打印网络日志
     * @param falg
     */
    public HttpUtils setDBugLog(boolean falg) {
        if(falg){
            mOkHttpClient=getOkHttpClient().newBuilder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return this;
    }

    /**
     *  设置Coolie
     * @return
     */
    public HttpUtils addCookie()
    {
        mOkHttpClient=getOkHttpClient().newBuilder()
                .cookieJar(new SimpleCookieJar())
                .build();
        return this;
    }

    /**
     *  获得OkHttpClient实例
     * @return
     */
    public OkHttpClient getOkHttpClient(){
        return  mOkHttpClient;
    }
    /**
     *  获取请求网络实例
     * @return
     */
    public static HttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (HttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    public HttpUtils() {
        this.context= MyLibrary.getApplication();
        if(configuration==null) {
            configuration=new NetWorkConfiguration(context);
        }
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zy","OkHttp====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        if(configuration.getIsCache()) {
            mOkHttpClient=new OkHttpClient.Builder()
                    //网络缓存拦截器
//                    .addInterceptor(interceptor)
//                    .addNetworkInterceptor(interceptor)
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new ReceivedCookiesInterceptor())
                    //自定义网络Log显示
//                    .addInterceptor(new LogInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .cache(configuration.getDiskCache())
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();
        } else {
            mOkHttpClient=new OkHttpClient.Builder()
//                    .addInterceptor(new LogInterceptor())
                    .addInterceptor(new HeaderInterceptor())
                    .addInterceptor(new ReceivedCookiesInterceptor())
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(configuration.getConnectTimeOut(), TimeUnit.SECONDS)
                    .connectionPool(configuration.getConnectionPool())
                    .retryOnConnectionFailure(true)
                    .build();

        }
        /**
         *
         *  判断是否在AppLication中配置Https证书
         *
         */
        if(configuration.getCertificates()!=null) {
            mOkHttpClient = getOkHttpClient().newBuilder()
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(configuration.getCertificates(), null, null))
                    .build();
        }
    }
    /**
     *  设置网络配置参数
     * @param configuration
     */
    public  static void setConFiguration(NetWorkConfiguration configuration) {
        if(configuration == null) {
            throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
        } else {
            if(HttpUtils.configuration == null) {
                Log.d("Initialize NetWorkConfiguration with configuration","");
                HttpUtils.configuration = configuration;
            } else {
                Log.e("Try to initialize NetWorkConfiguration which had already been initialized before. To re-init NetWorkConfiguration with new configuration ","");
            }
        }
        if(configuration!=null) {
            Log.i("ConFiguration:======>" , configuration.toString());
        }
    }


    /**
     *  网络拦截器
     *  进行网络操作的时候进行拦截
     */
    final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            final Request.Builder builder = chain.request().newBuilder();
            final String headInfo = DeviceUtil.getHeadInfo(context);
            /**
             *  断网后是否加载本地缓存数据
             */
            Observable.just(SPUtils.get(SPKeys.COOKIE, ""))
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String cookie) {
                        Log.e("cookie------------>",cookie);
                                if(!NetworkUtil.isNetworkAvailable(configuration.context)&&isLoadDiskCache){
                                    builder.cacheControl(CacheControl.FORCE_CACHE);
                                } else if(isLoadMemoryCache) {
                                    //加载内存缓存数据
                                    builder.cacheControl(CacheControl.FORCE_CACHE);
                                }else {
                                    builder.cacheControl(CacheControl.FORCE_NETWORK);
                                }
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

//            Response response = chain.proceed(builder.build());
            Response originalResponse = chain.proceed(chain.request());
            //有网进行内存缓存数据
            if (NetworkUtil.isNetworkAvailable(configuration.context)&&configuration.getIsMemoryCache()) {
                originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + configuration.getmemoryCacheTime())
                        .removeHeader("Pragma")
                        .build();
            } else {//进行本地缓存数据
                if(configuration.getIsDiskCache()) {
                    originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + configuration.getDiskCacheTime())
                            .removeHeader("Pragma")
                            .build();
                }
            }

            //这里获取请求返回的cookie
            if (!originalResponse.headers("X-Wlb-Token").isEmpty()) {
                final StringBuffer cookieBuffer = new StringBuffer();
                Observable.from(originalResponse.headers("X-Wlb-Token"))
                        .map(new Func1<String, String>() {
                            @Override
                            public String call(String s) {
                                String[] cookieArray = s.split(";");
                                return cookieArray[0];
                            }
                        })
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String cookie) {
                                cookieBuffer.append(cookie);
                            Log.e("cookie-----------save>",cookieBuffer.toString());
                            }
                        });
                SPUtils.put(SPKeys.COOKIE, cookieBuffer.toString());
            }
            return originalResponse;
        }
    };

}
