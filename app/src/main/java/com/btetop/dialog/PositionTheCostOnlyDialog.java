package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.utils.KeyBoardUtils;

/**
 * Created by ouyou on 2018/5/9.
 */

public class PositionTheCostOnlyDialog {
    private Dialog mActiveDialog;
    private OnClickListener onClickListener;
    private String costPrice;
    private String rightBtnStr="跳过";
    private String unitMoney;
    private final int BOND = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BOND:
                    if (mActiveDialog!=null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) mActiveDialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    }
            }
        }

    };


    public static PositionTheCostOnlyDialog getInstance(String unitMoney) {
        PositionTheCostOnlyDialog instance = new PositionTheCostOnlyDialog();
        instance.unitMoney=unitMoney;
        return instance;
    }

    public static PositionTheCostOnlyDialog getEditInstance(String costPrice, String rightBtnStr, String unitMoney) {
        PositionTheCostOnlyDialog instance = new PositionTheCostOnlyDialog();
        instance.costPrice = costPrice;
        instance.rightBtnStr = rightBtnStr;
        instance.unitMoney=unitMoney;
        return instance;
    }

    public void showDialog(Activity activity, OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_position_cost_only, null);
        initDialogView(view);
        mActiveDialog = new Dialog(activity, R.style.MyDialogStyle);
        mActiveDialog.setContentView(view);
        mActiveDialog.setCancelable(false);
        mActiveDialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = activity.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        mActiveDialog.getWindow().setGravity(Gravity.CENTER);
        mActiveDialog.show();
        handler.sendEmptyMessageDelayed(BOND,100);

    }

    private void initDialogView(View view) {
        TextView activeImg = view.findViewById(R.id.dialog_tv_sure);
        TextView closeImg = view.findViewById(R.id.dialog_tv_cancel);
        TextView tv_cost_notice = view.findViewById(R.id.tv_cost_notice);
        TextView tvUsdt=view.findViewById(R.id.tv_usdt);
        EditText editText = view.findViewById(R.id.et_nick_name);
        closeImg.setText(rightBtnStr);
        tvUsdt.setText(unitMoney);

        if (costPrice==null || costPrice.equalsIgnoreCase("0.0")) {
            activeImg.setText("添加");
        } else {
            activeImg.setText("修改");
            editText.setText(costPrice);
            editText.setSelection(editText.getText().length());
        }

        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editInfo = editText.getText().toString();
                if (TextUtils.isEmpty(editInfo)) {
                    tv_cost_notice.setVisibility(View.VISIBLE);
                } else {
                    tv_cost_notice.setVisibility(View.GONE);
                    onClickListener.onAddClick(editInfo);
                    KeyBoardUtils.hiddenKeyBoard(editText, mActiveDialog.getContext());
                    mActiveDialog.dismiss();

                }

            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActiveDialog != null && mActiveDialog.isShowing()) {
                    onClickListener.onSkipClick();
                    KeyBoardUtils.hiddenKeyBoard(editText, mActiveDialog.getContext());
                    mActiveDialog.dismiss();
                }
            }
        });

    }


    public interface OnClickListener {
        void onAddClick(String costPrice);

        void onSkipClick();
    }

}
