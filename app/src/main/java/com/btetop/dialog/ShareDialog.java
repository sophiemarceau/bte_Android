package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.config.UrlConfig;
import com.btetop.service.UserService;
import com.btetop.utils.ShareUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2018/3/16.
 */

public class ShareDialog {
    private static Dialog mShareDialog;
    private static volatile ShareDialog instance = null;


    private ShareDialog() {
    }

    public static ShareDialog getInstance() {
        if (instance == null) {
            synchronized (ShareDialog.class) {
                if (instance == null) {
                    instance = new ShareDialog();
                }
            }
        }
        return instance;
    }

    /**
     * 显示分享对话框
     */

    public void showShareDialog(Activity context) {
        showShareDialog(context, null, null, null, null);
    }


    public void showShareDialog(Activity context, String shareUrl, String shareTitle, String shareDesc) {
        showShareDialog(context, shareUrl, shareTitle, shareDesc, null);
    }

    public void showShareDialog(Activity context, String shareUrl) {
        showShareDialog(context, shareUrl, null, null, null);
    }

    public void showShareDialog(final Activity context, final String shareUrl, final String shareTitle, final String shareDesc, final String shareIcon) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.share_dialog_layout, null);


        mShareDialog = new Dialog(context, R.style.MyDialogStyle);
        mShareDialog.setContentView(view);
        mShareDialog.setCancelable(false);
        mShareDialog.setCanceledOnTouchOutside(true);

        String finalUrl = "";
        String url = StringUtils.isEmpty(shareUrl) ? UrlConfig.DEFAULT_SHARE_URL : shareUrl;
        //需要把inviteCode加上
        boolean contains = url.contains("?inviteCode=");
        if (contains) {
            finalUrl = url;
        } else {
            finalUrl = url + "?inviteCode=" + UserService.getCurrentUserInviteCode();
        }

        final String title = StringUtils.isEmpty(shareTitle) ? UrlConfig.DEFAULT_SHARE_TITLE : shareTitle;
        final String desc = StringUtils.isEmpty(shareDesc) ? UrlConfig.DEFAULT_SHARE_DESC : shareDesc;

        LinearLayout weiXin = view.findViewById(R.id.wechat);
        String finalUrl1 = finalUrl;
        weiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShare(context, finalUrl1, title, desc, shareIcon, SHARE_MEDIA.WEIXIN);
                //點擊分享按鈕 就增加積分

            }
        });

        LinearLayout wxcircle = view.findViewById(R.id.wxcircle);
        wxcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShare(context, finalUrl1, title, desc, shareIcon, SHARE_MEDIA.WEIXIN_CIRCLE);

            }
        });

        TextView tvCancel = view.findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });


        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mShareDialog.getWindow().setGravity(Gravity.BOTTOM);

        mShareDialog.show();

    }


    private void doShare(Activity context, String url, String shareTitle, String shareDesc, String shareIcon, SHARE_MEDIA platform) {
        ShareUtils.shareUrl(context, url, shareTitle, shareDesc, null, platform);
        if (mShareDialog != null && mShareDialog.isShowing()) {
            mShareDialog.dismiss();
        }
    }
}
