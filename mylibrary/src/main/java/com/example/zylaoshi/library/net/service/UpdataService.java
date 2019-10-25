package com.example.zylaoshi.library.net.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;
/**
 * @description: 下载文件的接口
 * @autour: zylaoshi
 * @date: 2017/12/4 14:13
*/
public interface UpdataService {

    @Streaming  //文件比较大的时候使用此注解
    @GET
    Observable<ResponseBody> download(@Url String url);
}
