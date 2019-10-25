package com.btetop.utils;

import android.os.Build;
import android.provider.Settings;

import com.btetop.application.BteTopApplication;

public class CheckPermisson {

    public static boolean checkDrawOverPermisson() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(BteTopApplication.getInstance());
        }
        return true;

    }
}
