package com.example.zylaoshi.library.net.interceptor;

import com.example.zylaoshi.library.net.download.ProgressResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/** 
 * @description: 定义一个Interceptor ，下载事件监听
 * @autour: zylaoshi
 * @date: 2017/12/4 14:19
*/
public class ProgressInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }
}
