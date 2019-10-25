package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.btetop.config.Constant;

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context, View layout, int style, int type) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if (type == Constant.DIALOG_CENTER) {
            params.gravity = Gravity.CENTER;
        } else if (type == Constant.DIALOG_BOTTOM) {
            params.gravity = Gravity.BOTTOM;
        }
        window.setAttributes(params);
    }
}