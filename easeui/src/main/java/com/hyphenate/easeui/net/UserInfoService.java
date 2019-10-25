package com.hyphenate.easeui.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserInfoService {

    @FormUrlEncoded
    @POST("app/api/emChat/getUserInfo")
    Call<HXUserInfo> getNewsData(@Field("username") String key);
}
