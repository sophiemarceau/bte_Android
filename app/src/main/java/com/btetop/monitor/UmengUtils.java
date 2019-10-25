package com.btetop.monitor;

import android.util.Log;

/**
 * fragment使用
 */
public class UmengUtils {

    /**
     * 开始跳转统计
     */
    public static void fragmentStartStatistics(Class aClass, String mTagName) {
//        MobclickAgent.onPageStart(aClass.getName());
        M.monitor().onPageStart(aClass.getName());
        Log.d("umeng----", aClass.getName() + "开始统计");
    }

    /**
     * 结束跳转统计
     */
    public static void fragmentEndStatistics(Class aClass, String mTagName) {
//        MobclickAgent.onPageEnd(aClass.getName());
        M.monitor().onPageEnd(aClass.getName());
        Log.d("umeng----", aClass.getName() + "结束统计");
    }
}
