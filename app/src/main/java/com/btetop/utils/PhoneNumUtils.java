package com.btetop.utils;

/**
 * Created by Administrator on 2018/3/30.
 */

public class PhoneNumUtils {

    public static String phoneNum(String str) {
        String newPhoneNum = null;
        if (str != null && !"".equals(str)) {
            int length = str.length();
            if (length == 11) {
                String str1 = str.substring(0, 3);
                String str2 = str.substring(3, 7);
                String str3 = str.substring(7, 11);
                StringBuilder builder = new StringBuilder();
                builder.append(str1).append(" ").append(str2).append(" ").append(str3);
                newPhoneNum = builder.toString();
            } else {
                newPhoneNum = "";
            }
        }
        return newPhoneNum;
    }

    /**
     * 手机号截取
     *
     * @param str
     * @return
     */
    public static String phoneHideNum(String str) {
        String newPhoneNum = null;
        if (str != null && !"".equals(str)) {
            int length = str.length();
            if (length == 11) {
                String str1 = str.substring(0, 3);
                String str2 = str.substring(7, 11);
                StringBuilder builder = new StringBuilder();
                builder.append(str1).append("****").append(str2);
                newPhoneNum = builder.toString();
            } else {
                newPhoneNum = "";
            }
        }
        return newPhoneNum;
    }
    /**
     * 邮箱截取
     *
     * @param str
     * @return
     */
    public static String EmailHideNum(String str) {
        String newEmail = null;
        if (str != null && !"".equals(str)) {
            String str1 = str.substring(0, 1);
            String str2 = str.substring(str.indexOf("@") - 1, str.length());
            StringBuilder builder = new StringBuilder();
            builder.append(str1).append("****").append(str2);
            newEmail = builder.toString();
        } else {
            newEmail = str;
        }
        return newEmail;
    }
}
