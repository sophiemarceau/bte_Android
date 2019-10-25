package com.btetop.monitor;

import android.content.Context;

public interface MonitorManager {

    void onPause(Context context, String activityName);

    void onResume(Context context, String activityName);

    void enableEncrypt(boolean enable);

    void onEvent(Context context, String key);

    void setDebugMode(boolean enable);

    void onPageStart(String activityName);

    void onPageEnd(String activityName);
}
