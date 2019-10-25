package com.btetop.utils;

import com.guoziwei.klinelib.util.DoubleUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CommonUtils {
    private static final NavigableMap<Long, String> engSuffixes = new TreeMap<>();

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

    public static String HomePriceConvert(double value) {
        String s;
        if (value > 100000000) {
            s = DoubleUtil.format2Decimal(value / 100000000);
        } else if (value > 10000) {
            s = DoubleUtil.format2Decimal(value / 10000);
        } else {
            s = DoubleUtil.format2Decimal(value);
        }
        return s;
    }

    public static String HomeLosePriceConvert(double value) {
        String s;
        if (value < -100000000) {
            s = DoubleUtil.format2Decimal(value / 100000000);
        } else if (value < -10000) {
            s = DoubleUtil.format2Decimal(value / 10000);
        } else {
            s = DoubleUtil.format2Decimal(value);
        }
        return s;
    }
    public static String HomeGetLoseWord(double value) {
        String s;
        if (value < - 100000000) {
            s = "亿";
        } else if (value < - 10000) {
            s = "万";
        } else {
            s = "";
        }
        return s;
    }
    public static String HomeGetWord(double value) {
        String s;
        if (value > 100000000) {
            s = "亿";
        } else if (value > 10000) {
            s = "万";
        } else {
            s = "";
        }
        return s;
    }


    static {
        engSuffixes.put(1_000L, "k");
        engSuffixes.put(1_000_000L, "M");


//        chinaSuffixes.put(1_000_000L, "兆");
//        engSuffixes.put(1_000_000_000L, "G");
//        engSuffixes.put(1_000_000_000_000L, "T");
//        engSuffixes.put(1_000_000_000_000_000L, "P");
//        engSuffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String formatEng(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return formatEng(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + formatEng(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = engSuffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static String formatChina(float value) {

        float abs = Math.abs(value);
        String formatvalue = value + "";


        if (abs > 100000000) {
            formatvalue = String.format("%.2f亿", value / 100000000.0);
        } else if (abs > 10000) {
            formatvalue = String.format("%.2f万", value / 10000.0);
        } else if (abs > 1000) {
            formatvalue = String.format("%.2f千", value / 1000.0);
        }

        return formatvalue;
    }


    public static String formatChina1(float value) {

        float abs = Math.abs(value);
        String formatvalue = value + "";


        if (abs > 100000000) {
            formatvalue = String.format("%.2f亿", value / 100000000.0);
        } else if (abs > 10000) {
            formatvalue = String.format("%.2f万", value / 10000.0);

        } else if (abs > 1000) {
            formatvalue = String.format("%df千", value / 1000.0);
        }

        return formatvalue;
    }

    public static String formatThousand(double str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        return decimalFormat.format(str);
    }
    public static String formathundred(double str) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(str);
    }
    public static List<String> getStrList(String inputString, int length) {
        int size = inputString.length() / length;
        if (inputString.length() % length != 0) {
            size += 1;
        }
        return getStrList(inputString, length, size);
    }

    /**
     * 把原始字符串分割成指定长度的字符串列表
     *
     * @param inputString
     *            原始字符串
     * @param length
     *            指定长度
     * @param size
     *            指定列表大小
     * @return
     */
    public static List<String> getStrList(String inputString, int length,
                                          int size) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < size; index++) {
            String childStr = substring(inputString, index * length,
                    (index + 1) * length);
            list.add(childStr);
        }
        return list;
    }

    /**
     * 分割字符串，如果开始位置大于字符串长度，返回空
     *
     * @param str
     *            原始字符串
     * @param f
     *            开始位置
     * @param t
     *            结束位置
     * @return
     */
    public static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }
}
