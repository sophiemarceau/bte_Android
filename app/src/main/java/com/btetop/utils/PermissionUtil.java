package com.btetop.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;

/**
 */

public class PermissionUtil {
    /**
     *
     * @param context
     * @param permission
     * @return true 已经授权，false未授权
     */
    public static boolean selfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
        if (context!=null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
                if (targetSdkVersion>= Build.VERSION_CODES.M) {
                    // targetSdkVersion >= Android M, we can
                    // use Context#checkSelfPermission
                    result = context.checkSelfPermission(permission)
                            == PackageManager.PERMISSION_GRANTED;
                } else {
                    // targetSdkVersion < Android M, we have to use PermissionChecker
                    result = PermissionChecker.checkSelfPermission(context, permission)
                            == PermissionChecker.PERMISSION_GRANTED;
                }
            }
        }

        return result;
    }
}
