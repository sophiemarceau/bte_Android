package com.btetop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.btetop.application.BteTopApplication;

/**
 * toast工具类
 * by:baozi
 */
public class ToastUtils {

    public static void showShortToast(String showString) {
        if (!TextUtils.isEmpty(showString)) {
            showShortToast(BteTopApplication.getInstance().getApplicationContext(), showString);

        }
    }

    public static void showShortToast(Context context, String showString) {
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER,0,0);
        com.blankj.utilcode.util.ToastUtils.showShort(showString);
    }

    /**
     * 多次点击只显示一次
     */
    private static Toast toast;

    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(text);
            toast.show();
        }
    }
}
