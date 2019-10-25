package com.example.zylaoshi.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * @description: 渠道名称转化为指定的字符串
 * @autour: zylaoshi
 * @date: 2017/12/4 14:26
*/
public class ChannelNameUtil {
    //联合后端指定的渠道标识
    //101 baidu   102 qh360
    //103 xiaomi  104 tengxun
    //106 huawei  107 oppo
    //108 meizu   110 vivo
    //112 leishi  113 chuizi
    //114 wandoujia     117  sogou 搜狗
    //118 anzhi 安智    119  jinli 金立
    //120 samsung 三星  121  lenovo  联想
    //122 mumayi 木蚂蚁 123  yingyonghui 应用汇
    //124 liantongwo 联通沃  125  yidongmm 移动MM

    /**
     * 获取渠道名
     * @param context 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context context) {
        if (context == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.
                        getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = String.valueOf(applicationInfo.metaData.get("BUGLY_APP_CHANNEL"));
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 转化渠道名称为指定字符
     * @param channelName getChannelName()返回的字符串
     * @return 返回指定的字符 用于后端交互
     */
    public static String tranChannelName(String channelName){

        String channel_name = "";
        switch (channelName){
            case "baidu":
                channel_name = "101";
                break;
            case "qh360":
                channel_name = "102";
                break;
            case "xiaomi" :
                channel_name = "103";
                break;
            case "tencent":
                channel_name = "104";
                break;
            case "huawei":
                channel_name = "106";
                break;
            case "oppo":
                channel_name = "107";
                break;
            case "meizu":
                channel_name = "108";
                break;
            case "vivo":
                channel_name = "110";
                break;
            case "leshi":
                channel_name = "112";
                break;
            case "smartisan":
                channel_name = "113";
                break;
            case "wandoujia":
                channel_name = "114";
                break;
            case "sogou":
                channel_name = "117";
                break;
            case "anzhi":
                channel_name = "118";
                break;
            case "jinli":
                channel_name = "119";
                break;
            case "samsung":
                channel_name = "120";
                break;
            case "lenovo":
                channel_name = "121";
                break;
            case "mumayi":
                channel_name = "122";
                break;
            case "yingyonghui":
                channel_name= "123";
                break;
            case "liantongwo":
                channel_name = "124";
                break;
            case "yidongmm":
                channel_name = "125";
                break;
            case "naerjie":
                channel_name = "3";
                break;
            default:
                channel_name = "104";
                break;
        }
        return channel_name;
    }

}
