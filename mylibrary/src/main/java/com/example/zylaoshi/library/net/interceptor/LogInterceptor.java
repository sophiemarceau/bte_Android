package com.example.zylaoshi.library.net.interceptor;


import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;


/**
 *  作者：gaoyin
 *  电话：18810474975
 *  邮箱：18810474975@163.com
 *  版本号：1.0
 *  类描述：  网络日志过滤器
 *  备注消息：
 *  修改时间：16/9/18 下午2:25
 **/

public class LogInterceptor implements Interceptor {
    private static final String TAG = "LogInterceptor";
    private static final Charset UTF8 = Charset.forName("UTF-8"); //urf8编码

    @Override
    public Response intercept(Chain chain) throws IOException {  //实现Interceptor接口方法
        Log.d(TAG,"before chain,request()");
        Request request = chain.request();  //获取request
        String acid = request.url().queryParameter("ACID"); //在url中获取ACID的参数值；
        Response response;
        try {
            long t1 = System.nanoTime();
            response = chain.proceed(request); //OkHttp链式调用
            long t2 = System.nanoTime();
            double time = (t2 - t1) / 1e6d;   //用请求后的时间减去请求前的时间得到耗时

            String userId = request.url().queryParameter("userId");
            String type = "";
            if (request.method().equals("GET")) {    //判断Method类型
                type = "GET";
            } else if (request.method().equals("POST")) {
                type = "POST";
            } else if (request.method().equals("PUT")) {
                type = "PUT";
            } else if (request.method().equals("DELETE")) {
                type = "DELETE";
            }
            BufferedSource source = response.body().source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            String logStr = "\n--------------------".concat(TextUtils.isEmpty(acid) ? "" : acid).concat("  begin--------------------\n")
                    .concat(type)
                    .concat("\nacid->").concat(TextUtils.isEmpty(acid) ? "" : acid)
                    .concat("\nuserId->").concat(TextUtils.isEmpty(userId) ? "" : userId)
                    .concat("\nnetwork code->").concat(response.code() + "")
                    .concat("\nurl->").concat(request.url() + "")
                    .concat("\ntime->").concat(time + "")
                    .concat("\nrequest headers->").concat(request.headers() + "")
                    .concat("request->").concat(bodyToString(request.body()))
                    .concat("\nbody->").concat(buffer.clone().readString(UTF8)); //响应体转String
            Log.i(TAG, logStr);
        } catch (Exception e) {
            Log.d(TAG,e.getClass().toString()+", error:acid = "+acid);  //网络出错，log 出错的acid
            throw e; //不拦截exception，由上层处理网络错误
        }
        return response;
    }

    /**
     * 请求体转String
     * @param request
     * @return
     */
    private static String bodyToString(final RequestBody request) {

        try {
            final Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}