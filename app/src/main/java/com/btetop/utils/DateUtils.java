package com.btetop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * string 转换成 date
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getDateTime(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date convertDate = null;
        try {
            convertDate = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertDate;
    }


    /**
     * 展示剩余天、时、分、秒：
     *
     * @param date
     * @return
     */
    public static String formatLongToTimeStr(Long date) {
        long day = date / (60 * 60 * 24)/1000;
        long hour = (date / (60 * 60) - day * 24);
        long min = ((date / 60) - day * 24 * 60 - hour * 60);
        long s = (date - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String strtime = "剩余：" + day + "天" + hour + "小时" + min + "分" + s + "秒";
        return strtime;
    }
}
