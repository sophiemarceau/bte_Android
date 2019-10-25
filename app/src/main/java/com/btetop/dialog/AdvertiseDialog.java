package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.HomeActiveBean;
import com.btetop.config.Constant;
import com.btetop.message.RouteMessage;
import com.btetop.monitor.M;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by ouyou on 2018/5/9.
 */

public class AdvertiseDialog {
    private static Dialog mActiveDialog;
    private static volatile AdvertiseDialog instance = null;

    private AdvertiseDialog() {
    }

    public static AdvertiseDialog getInstance() {
        if (instance == null) {
            synchronized (AdvertiseDialog.class) {
                if (instance == null) {
                    instance = new AdvertiseDialog();
                }
            }
        }
        return instance;
    }


    public void showActiveDialog(Activity activity, BaseBean<HomeActiveBean.HomeActiveData> activeBean) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.active_dialog_layout, null);
        initDialogView(view, activity, activeBean);
        mActiveDialog = new Dialog(activity, R.style.MyDialogStyle);
        mActiveDialog.setContentView(view);
        mActiveDialog.setCancelable(false);
        mActiveDialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = activity.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mActiveDialog.getWindow().setGravity(Gravity.CENTER);
        mActiveDialog.show();
    }

    private void initDialogView(View view, final Activity activity, BaseBean<HomeActiveBean.HomeActiveData> activeBean) {
        ImageView activeImg = view.findViewById(R.id.active_img);
        ImageView closeImg = view.findViewById(R.id.active_close);
        String imgUrl = activeBean.getData().getImage();
        final String detailUrl = activeBean.getData().getUrl();
        final String detailId = activeBean.getData().getId();
        Glide.with(activity).load(imgUrl).skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(activeImg);
        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(detailUrl)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url",detailUrl);
                    RouteMessage routeMessage = new RouteMessage(RouteMessage.MESSAGE_SHOW_URL,bundle);
                    EventBusUtils.sendEvent(routeMessage);
                    if (mActiveDialog != null && mActiveDialog.isShowing()) {
                        mActiveDialog.dismiss();
                    }

                    M.monitor().onEvent(activity, Constant.HOME_ACTION);
                }

            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActiveDialog != null && mActiveDialog.isShowing()) {
                    mActiveDialog.dismiss();
                }
            }
        });

    }


}
