package com.guoziwei.klinelib.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by hou on 2015/8/14.
 */
public class DoubleUtil {

    public static double parseDouble(String parserDouble) {
        try {
            return Double.parseDouble(parserDouble);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String format2Decimal(Double d) {
        NumberFormat instance = DecimalFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        instance.setMaximumFractionDigits(2);
        return instance.format(d);
    }

    public static String formatDecimal(Double d) {
        NumberFormat instance = DecimalFormat.getInstance();
        instance.setMinimumFractionDigits(0);
        instance.setMaximumFractionDigits(8);
        return instance.format(d).replace(",", "");
    }

    //double 如果是以.00结尾 则取整
    public static String formatDataCompare10(double num) {

        if (num > 10) {
            if (num % 1.0 == 0) {
                return (long) num + "";
            } else {
                return DoubleUtil.format2Decimal(num);
            }
        } else {
            return String.valueOf(formatDecimal(num));
        }
    }

//    //double 如果是以.00结尾 则取整
//    public static double doubleTrans(double num) {
//        if (num % 1.0 == 0) {
//            return (long) num;
//        }
//        return num;
//    }

    /**
     * converting a double number to string by digits
     */
    public static String getStringByDigits(double num, int digits) {
       /* if (digits == 0) {
            return (int) num + "";
        } else {
            NumberFormat instance = DecimalFormat.getInstance();
            instance.setMinimumFractionDigits(digits);
            instance.setMaximumFractionDigits(digits);
            return instance.format(num).replace(",", "");
        }*/
        return String.format(Locale.getDefault(), "%." + digits + "f", num);
    }

    //万保留整数  亿保留2为xiaoshu
    public static String getDaDanFormat(float num) {
        String hi = ""+num;
        if (num>100000000) {
            float yi = num/100000000;
            String format = new DecimalFormat("0.00").format(yi)+"亿";
            hi=format;
        }else if (num>10000){
            float wan= num/10000;
            String format = new DecimalFormat("0").format(wan)+"万";
            hi=format;
        }else if (num>1000){
            String format = new DecimalFormat("0.00").format(num);
            hi=format;
        }

        return hi;
    }

    public static String getDadanPriceFormat(float price){
        String hi=""+price;
        if (price>999) {
            String format = new DecimalFormat("0").format(price);
            hi=format;
        }else if(price>10){

            String format = new DecimalFormat("0.0").format(price);
            hi=format;
        }else if (price>0){
            String format = new DecimalFormat("0.00").format(price);
            hi=format;
        }
        return hi;

    }
}
