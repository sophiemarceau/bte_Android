package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;

/**
 * Created by ouyou on 2018/5/9.
 */

public class BindWXSuccessDialog {
    private static Dialog mActiveDialog;
    private static volatile BindWXSuccessDialog instance = null;
    private bindWxSuccess mBindWxSuccess;

    private BindWXSuccessDialog() {
    }

    public static BindWXSuccessDialog getInstance() {
        if (instance == null) {
            synchronized (BindWXSuccessDialog.class) {
                if (instance == null) {
                    instance = new BindWXSuccessDialog();
                }
            }
        }
        return instance;
    }


    public void showDialog(Activity activity,bindWxSuccess bindWxSuccess) {
        mBindWxSuccess = bindWxSuccess;
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_bind_wx_success, null);
        initDialogView(view, activity);
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

    private void initDialogView(View view, final Activity activity) {
        TextView activeImg = view.findViewById(R.id.dialog_tv_sure);
        TextView closeImg = view.findViewById(R.id.dialog_tv_cancel);
        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBindWxSuccess.bindWxSuccess();
                mActiveDialog.dismiss();
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActiveDialog != null && mActiveDialog.isShowing()) {
                    mBindWxSuccess.bindCancel();
                    mActiveDialog.dismiss();

                }
            }
        });

    }

    public interface bindWxSuccess{
        void bindWxSuccess();
        void bindCancel();
    }

}
