package com.btetop.application;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;

import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.service.UserService;
import com.btetop.utils.AppUtils;
import com.btetop.utils.DeviceUtils;
import com.bugtags.library.Bugtags;
import com.example.zylaoshi.library.BuildConfig;
import com.example.zylaoshi.library.MyLibrary;
import com.example.zylaoshi.library.utils.LogUtil;
import com.hyphenate.easeui.EaseUI;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lje on 2018/1/11.
 */

public class BteTopApplication extends MultiDexApplication {
    private static final String TAG = "BteTopApplication";
    private static BteTopApplication instance;
    public static SharedPreferences preferences;
    private static Context mContext;
    public static IWXAPI mWxApi;

    public static BteTopApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        if (!shouldInit()) {
           return;
        }
        initApp();
        initJiGuang();
        initUmeng();
        initLeakCanary();
        initBugTag();
        registToWX();


        initHuanxin();

        MiPushClient.getRegId(getApplicationContext());
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Constant.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(Constant.WEIXIN_APP_ID);
    }


    private void initHuanxin() {
        //使用easeUI初始化
        EaseUI.getInstance().init(this, null);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

    }


    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initBugTag() {
        Bugtags.start(com.btetop.BuildConfig.BUG_TAG_KEY, this, Bugtags.BTGInvocationEventBubble);
    }

    /**
     * 初始化极光
     */
    private void initJiGuang() {

        JPushInterface.setDebugMode(!UrlConfig.onLine());
        JPushInterface.init(this);

        String registrationId = JPushInterface.getRegistrationID(this);
        LogUtil.print("极光--registrationId----" + registrationId);

        //将此设备的唯一android Id设置为 alias
        JPushInterface.setAlias(this, 0, DeviceUtils.getUniqueId(this));

        Set<String> tags = new TreeSet();

        tags.add("android");

        tags.add(AppUtils.getVerName(this));

        //2.1.0 有180个debug 用户，忘记关闭debug
        //以后测试版本 使用test test2018年08月15日10:43:43 test用户为0
        if (!UrlConfig.onLine()) tags.add("debug");

        JPushInterface.setTags(this, tags, mTagsCallback);

    }

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "推送注册成功";
                    break;
                case 6002:
                    logs = "推送注册失败";
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
            }
            //Log.e("标签设置", logs);
            //System.out.println("推送状态:"+logs);
        }
    };

    /**
     * 初始化友盟
     */
    private void initUmeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         * 参数1:上下文，必须的参数，不能为空

         参数2:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
         参数3:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
         */

        //Fix me 检查这里面传的key 和 secret是否是对的
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx82c2aa4fde147029", "c5b6fc7cc21fbfb5feee9361fbf03798");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("949809257", "9cd222420c53f0e27fd5b371fdc4be1d", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106634295", "fxyUfCoMOxyWgKZz");
    }

    private void initApp() {
        MyLibrary.init(this, BuildConfig.DEBUG);
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Logger.addLogAdapter(new AndroidLogAdapter());
        LeakCanary.install(this);
        UserService.loadUserInfo();
    }

    public static Context getContext() {
        return mContext;
    }
    private boolean shouldInit() {
    ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
    List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
    String mainProcessName = getPackageName();
    int myPid = android.os.Process.myPid();

    for (ActivityManager.RunningAppProcessInfo info : processInfos) {
        if (info.pid == myPid && mainProcessName.equals(info.processName)) {
            return true;
        }
    }
    return false;
}
}

