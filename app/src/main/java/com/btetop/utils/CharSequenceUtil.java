package com.btetop.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.btetop.R;

/**
 * Created by ouyou on 2018/6/19.
 */

public class CharSequenceUtil {
    public static CharSequence setColor(Context context, String text, String text1, String text2) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        // 关键字“孤舟”变色，0-text1.length()
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 关键字“寒江雪”变色，text1.length() + 6-text1.length() + 6 + text2.length()
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)), text1.length() + 6, text1.length() + 6 + text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }
}
