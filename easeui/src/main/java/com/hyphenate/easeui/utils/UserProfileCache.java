package com.hyphenate.easeui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;

public class UserProfileCache {

    private static Gson gson = new Gson();
    public static String SP_CACHE_USER = "sp_cache_user";

    private static SharedPreferences spf;

    public static void setSpCacheUser(Context context, String user_id, EaseUser cacheUser){
        if (user_id == null){
            return;
        }
        if (spf == null){
            spf = context.getSharedPreferences(SP_CACHE_USER, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = spf.edit();
        editor.putString(user_id, gson.toJson(cacheUser));
        editor.commit();
    }

    public static EaseUser GetSpCacheUser(Context context, String userId){
        if (userId == null){
            return null;
        }
        if (spf == null){
            spf = context.getSharedPreferences(SP_CACHE_USER, Context.MODE_PRIVATE);
        }
        String cacheUserString = spf.getString(userId,null);
        if (cacheUserString == null){
            return null;
        }else {
            return gson.fromJson(cacheUserString,EaseUser.class);
        }
    }

}
