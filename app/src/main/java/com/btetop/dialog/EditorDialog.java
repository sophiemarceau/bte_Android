package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;

public class EditorDialog {
    private OnClickListener listener;
    private Dialog mActiveDialog;

    public static EditorDialog getEditorDialog(){
        EditorDialog instance=new EditorDialog();
        return instance;
    }

    public void showDialog(Activity activity, OnClickListener onClickListener) {
        this.listener=onClickListener;
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_editor, null);
        initDialog(view);
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

    private void initDialog(View view) {
        TextView activeImg = view.findViewById(R.id.dialog_tv_sure);
        TextView closeImg = view.findViewById(R.id.dialog_tv_cancel);
        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    mActiveDialog.dismiss();
                    listener.onCancelClick();
                }
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    mActiveDialog.dismiss();
                    listener.onExitClick();
                }
            }
        });


    }

    public interface OnClickListener {
        void onCancelClick();

        void onExitClick();
    }
}
