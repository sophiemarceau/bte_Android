package com.guoziwei.klinelib.util;

public class CommonUtils {

    public static String priceConvert(double value) {
        String s;
        if (value > 100000000) {
            s = DoubleUtil.format2Decimal(value / 100000000) + "亿";
        } else if (value > 10000) {
            s = DoubleUtil.format2Decimal(value / 10000) + "万";
        } else {
            s = DoubleUtil.format2Decimal(value) + "";
        }
        return s;
    }

}
