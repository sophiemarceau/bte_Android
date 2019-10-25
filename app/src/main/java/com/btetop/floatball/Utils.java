package com.btetop.floatball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Utils {

    // 状态栏高度
    private static  int statusBarHeight = 0;
    // 屏幕像素点
    private static final Point screenSize = new Point();

    // 获取屏幕像素点
    public static Point getScreenSize(Activity context) {

        if (context == null) {
            return screenSize;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display diplay = wm.getDefaultDisplay();
            if (diplay != null) {
                diplay.getMetrics(mDisplayMetrics);
                int W = mDisplayMetrics.widthPixels;
                int H = mDisplayMetrics.heightPixels;
                if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
                    screenSize.set(W, H);
                }
            }
        }
        return screenSize;
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight <= 0) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        if (statusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    public static int getVirtualBarHeigh(Activity activity) {
        int titleHeight = 0;
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusHeight = frame.top;
        titleHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop() - statusHeight;
        return titleHeight;
    }

}
