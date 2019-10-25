package com.btetop.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/3/28.
 */

public class PagerSnapHelper extends LinearSnapHelper {
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        final View currentView = findSnapView(layoutManager);
        if (targetPos != RecyclerView.NO_POSITION && currentView != null) {
            int currentPostion = layoutManager.getPosition(currentView);
            int first = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int last = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            currentPostion = targetPos < currentPostion ? last : (targetPos > currentPostion ? first : currentPostion);
            targetPos = targetPos < currentPostion ? currentPostion - 1 : (targetPos > currentPostion ? currentPostion + 1 : currentPostion);
        }
        return targetPos;
    }
}
