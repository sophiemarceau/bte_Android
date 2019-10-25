package com.btetop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;

public class SignInDialog {

    ImageView ivSignInClose;
    TextView tvSignInSuccess;
    TextView tv1;
    View iv1;
    TextView tv2;
    View iv2;
    TextView tv3;
    View iv3;
    TextView tv4;
    View iv4;
    TextView tv5;
    View iv5;
    TextView tv6;
    View iv6;
    TextView tv7;
    ImageView ivSignInOnDay;
    TextView tvSignInExplain;
    TextView tvSignInCertain;
    ImageView iv_head_1, iv_head_2, iv_head_3, iv_head_4, iv_head_5, iv_head_6, iv_head_7;

    private Dialog dialog;
    private Context mContext;
    private int signStatus;

    public void show(Context context, int signStatus,SignInListener settingListener) {
        signInListener= settingListener;
        this.signStatus = signStatus;
        mContext = context;
        View view = View.inflate(context, R.layout.dialog_sign_in, null);
        dialog = new Dialog(context, R.style.MyDialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        initViews(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        initData(signStatus);

    }

    private void initData(int signStatus) {
        switch (signStatus) {
            case 1:
                iv_head_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                iv_head_2.setVisibility(View.VISIBLE);
                break;
            case 3:
                iv_head_3.setVisibility(View.VISIBLE);
                break;
            case 4:
                iv_head_4.setVisibility(View.VISIBLE);
                break;
            case 5:
                iv_head_5.setVisibility(View.VISIBLE);
                break;
            case 6:
                iv_head_6.setVisibility(View.VISIBLE);
                break;
            case 7:
                iv_head_7.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void initViews(View view) {


        ivSignInClose = (ImageView) view.findViewById(R.id.iv_sign_in_close);
        tvSignInSuccess = (TextView) view.findViewById(R.id.tv_sign_in_success);
        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        tv4 = (TextView) view.findViewById(R.id.tv_4);
        tv5 = (TextView) view.findViewById(R.id.tv_5);
        tv6 = (TextView) view.findViewById(R.id.tv_6);
        tv7 = (TextView) view.findViewById(R.id.tv_7);
        tvSignInExplain = (TextView) view.findViewById(R.id.tv_sign_in_explain);
        tvSignInCertain = (TextView) view.findViewById(R.id.tv_sign_in_certain);

        iv_head_1 = view.findViewById(R.id.iv_head_1);
        iv_head_2 = view.findViewById(R.id.iv_head_2);
        iv_head_3 = view.findViewById(R.id.iv_head_3);
        iv_head_4 = view.findViewById(R.id.iv_head_4);
        iv_head_5 = view.findViewById(R.id.iv_head_5);
        iv_head_6 = view.findViewById(R.id.iv_head_6);
        iv_head_7 = view.findViewById(R.id.iv_head_7);


        ivSignInClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (signInListener != null) signInListener.onCancel();
            }
        });

        tvSignInCertain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (signInListener != null) signInListener.onCertain();
            }
        });
    }

    private SignInListener signInListener;

    public interface SignInListener {
        void onCancel();

        void onCertain();
    }
}
