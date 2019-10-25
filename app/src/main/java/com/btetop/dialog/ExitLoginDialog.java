package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.application.BteTopApplication;

/**
 * Created by ouyou on 2018/5/28.
 */

public class ExitLoginDialog {
    private static Dialog mExitDialog;
    private static volatile ExitLoginDialog instance = null;

    private ExitLoginDialog() {
    }

    public static ExitLoginDialog getInstance() {
        if (instance == null) {
            synchronized (ExitLoginDialog.class) {
                if (instance == null) {
                    instance = new ExitLoginDialog();
                }
            }
        }
        return instance;
    }

    /**
     * 显示分享对话框
     */
    public void showDeveiceExitDialog(Context context, String title, String message) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.exit_login_dialog_layout, null);
        initDialogView(context, title, message, view);
        mExitDialog = new Dialog(context, R.style.MyDialogStyle);
        mExitDialog.setContentView(view);
        mExitDialog.setCancelable(false);
        mExitDialog.setCanceledOnTouchOutside(false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mExitDialog.getWindow().setGravity(Gravity.CENTER);
        mExitDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        mExitDialog.show();
    }

    private void initDialogView(final Context context, String title, String message, View view) {
        final TextView mTvContent = view.findViewById(R.id.dialog_tv_content);
        TextView mTvCancel = view.findViewById(R.id.dialog_tv_cancel);
        TextView mTvSure = view.findViewById(R.id.dialog_tv_sure);
        TextView mTvTitle = view.findViewById(R.id.dialog_tv_title);
        mTvTitle.setText(title);
        mTvContent.setText(message);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissLogoutDialog();
            }
        });
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YZMLoginActivity.class);
                intent.putExtra("openFlag", "MineFragment");
                context.startActivity(intent);
                dismissLogoutDialog();

            }
        });
    }

    private void dismissLogoutDialog() {
        if (mExitDialog != null) {
            mExitDialog.dismiss();
        }
    }
}
