package com.btetop.widget.floatprice;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class InterceptLinearLayout extends LinearLayout {
    public InterceptLinearLayout(Context context) {
        super(context);
    }

    public InterceptLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //刚按下是起始位置的坐标
    float startDownX, startDownY;
    float downX, downY;
    float moveX, moveY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDownX = downX = event.getRawX();
                startDownY = downY = event.getRawY();
                return false;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getRawX();
                moveY = event.getRawY();
                downX = moveX;
                downY = moveY;
                return true;
            case MotionEvent.ACTION_UP:
                float upX = event.getRawX();
                float upY = event.getRawY();
                if (upX == startDownX && upY == startDownY)
                    return false;
                else
                    return true;
        }
        return true;
    }
}
