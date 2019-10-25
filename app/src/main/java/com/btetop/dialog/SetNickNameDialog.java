package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;

/**
 * Created by ouyou on 2018/5/9.
 */

public class SetNickNameDialog {
    private static Dialog mActiveDialog;
    private static volatile SetNickNameDialog instance = null;

    private setNickName mSetNickNam;

    private SetNickNameDialog() {
    }

    public static SetNickNameDialog getInstance() {
        if (instance == null) {
            synchronized (SetNickNameDialog.class) {
                if (instance == null) {
                    instance = new SetNickNameDialog();
                }
            }
        }
        return instance;
    }


    public void showDialog(Activity activity, String name, setNickName setNickNam) {
        mSetNickNam = setNickNam;
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_set_nick_name, null);
        initDialogView(view,name);
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

    private void initDialogView(View view, String name) {
        TextView activeImg = view.findViewById(R.id.dialog_tv_sure);
        TextView closeImg = view.findViewById(R.id.dialog_tv_cancel);
        EditText editText = view.findViewById(R.id.et_nick_name);
//        editText.setText(name);
//        Glide.with(activity).load(imgUrl).skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(activeImg);
        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editInfo = editText.getText().toString();
                mSetNickNam.setNickName(editInfo);
                mActiveDialog.dismiss();
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

    public interface setNickName {
        void setNickName(String editInfo);
    }

}
