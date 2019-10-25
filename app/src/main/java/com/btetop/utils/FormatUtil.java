package com.btetop.utils;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Administrator on 2018/3/16.
 */

public class FormatUtil {

    /**
     * 当浮点数小数点后面为.0000，自动取整数
     *
     * @param num
     */
    public static String doubleTrans1(double num) {
        if (num % 1.0 == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    /**
     * 把字符串转换为货币格式
     *
     * @param string
     * @return
     */
    public static String getMoneyType(String string) {
        // 把string类型的货币转换为double类型。
        Double numDouble = Double.parseDouble(string);
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        // 把转换后的货币String类型返回
        String numString = format.format(numDouble);
        return numString;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
