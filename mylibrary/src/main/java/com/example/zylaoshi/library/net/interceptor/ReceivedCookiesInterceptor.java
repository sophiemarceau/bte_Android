package com.example.zylaoshi.library.net.interceptor;


import android.util.Log;

import com.example.zylaoshi.library.config.SPKeys;
import com.example.zylaoshi.library.utils.SPUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static java.util.Calendar.getInstance;

/** 
 * @description:  接受服务器发的cookie   并保存到本地
 * @autour: zylaoshi
 * @date: 2017/11/28 17:49
*/

public class ReceivedCookiesInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
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


    /**
     * 将时间转换为时间戳
     *
     * @param s date
     * @return long
     * @throws android.net.ParseException
     */
    public static long dateToStamp(String s) throws android.net.ParseException {
        //转换为标准时间对象
        Date date = new Date(s);
        Calendar calendar = getInstance();
        calendar.setTime(date);
        long mTimeInMillis = calendar.getTimeInMillis();
        return mTimeInMillis;
    }
}
