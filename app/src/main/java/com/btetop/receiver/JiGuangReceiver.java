package com.btetop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.btetop.activity.MainActivity;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.config.UrlConfig;
import com.btetop.message.RouteMessage;
import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JiGuangReceiver extends BroadcastReceiver {
    private static final String TAG = "JiGuangReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Logger.d(TAG, "onReceive - " + intent.getAction());

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Logger.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Logger.d(TAG, "接受到推送下来的通知");

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Logger.d(TAG, "用户点击打开了通知");
            openNotification(context, bundle);

        } else {
            Logger.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }


    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String target = "";
        String id = "";
        String baseAsset = "";
        String exchange = "";
        String symbol = "";
        String quote = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            target = extrasJson.optString("target");
            id = extrasJson.optString("id");
            baseAsset = extrasJson.optString("baseAsset");
            exchange = extrasJson.optString("exchange");
            symbol = extrasJson.optString("symbol");
            quote = extrasJson.optString("quote");
        } catch (Exception e) {
            Logger.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        Intent mIntent = new Intent(context, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);


        //市场分析报告
        /*
        if ("report".equals(target)) {

            Bundle bundle1 = new Bundle();
            bundle1.putString("url", UrlConfig.MARKET_REPORT_URL+id);
            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_URL,bundle1));
        //撸庄狗周报
        } else if ("weekPaper".equals(target)) {
            Bundle bundle1 = new Bundle();
            bundle1.putString("url", UrlConfig.LU_DOG_REPORT_RUL);
            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_URL,bundle1));
        //推送活动页 根据url 跳转相应页面
        } else if ("promotion".equals(target)) {
            Bundle bundle1 = new Bundle();
            bundle1.putString("url", UrlConfig.LU_DOG_REPORT_RUL);
            EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_SHOW_URL,bundle1));
        }
    */
    }
}
