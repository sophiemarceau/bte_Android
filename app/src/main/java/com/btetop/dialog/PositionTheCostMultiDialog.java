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
import android.widget.RadioButton;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.utils.KeyBoardUtils;

/**
 * Created by ouyou on 2018/5/9.
 */

public class PositionTheCostMultiDialog {
    private String costPrice;
    private String rightBtnStr = "跳过";
    private Dialog mActiveDialog;

    private onBtnClickListener btnClickListener;
    int Group1type = 0;
    int Group2type = 0;

    private final int BOND = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BOND:
                    if (mActiveDialog != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) mActiveDialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    }
            }
        }

    };

    private PositionTheCostMultiDialog() {
    }

    public static PositionTheCostMultiDialog getInstance() {
        PositionTheCostMultiDialog instance = new PositionTheCostMultiDialog();
        return instance;
    }

    public static PositionTheCostMultiDialog getEditInstance(String costPrice, String rightBtnStr, int kongType, int ratioType) {
        PositionTheCostMultiDialog instance = new PositionTheCostMultiDialog();
        instance.Group1type=kongType;
        if (ratioType==10) {
            instance.Group2type=0;
        }else {
            instance.Group2type=1;
        }
        instance.costPrice = costPrice;
        instance.rightBtnStr = rightBtnStr;
        return instance;
    }

    public void showDialog(Activity activity, onBtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_position_cost_multi, null);
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
        handler.sendEmptyMessageDelayed(BOND, 100);
    }

    private void initDialogView(View view) {
        TextView activeImg = view.findViewById(R.id.dialog_tv_sure);
        TextView closeImg = view.findViewById(R.id.dialog_tv_cancel);
        TextView tv_cost_notice = view.findViewById(R.id.tv_cost_notice);
        EditText editText = view.findViewById(R.id.et_nick_name);
        RadioButton rb_kongdan = view.findViewById(R.id.rb_kongdan);
        RadioButton rb_duodan = view.findViewById(R.id.rb_duodan);
        RadioButton rb_10bei = view.findViewById(R.id.rb_10bei);
        RadioButton rb_20bei = view.findViewById(R.id.rb_20bei);
        closeImg.setText(rightBtnStr);
        if (costPrice==null || costPrice.equalsIgnoreCase("0.0")) {
            activeImg.setText("添加");
        } else {
            activeImg.setText("修改");
            editText.setText(costPrice);
            editText.setSelection(editText.getText().length());
        }


        if (Group1type==0) {
            rb_kongdan.setChecked(true);
        }else {
            rb_duodan.setChecked(true);
        }

        if (Group2type==0) {
            rb_10bei.setChecked(true);
        }else {
            rb_20bei.setChecked(true);
        }

        activeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editInfo = editText.getText().toString();
                if (TextUtils.isEmpty(editInfo)) {
                    tv_cost_notice.setVisibility(View.VISIBLE);
                } else {
                    if (rb_kongdan.isChecked())
                        Group1type = 0;
                    if (rb_duodan.isChecked())
                        Group1type = 1;
                    if (rb_10bei.isChecked())
                        Group2type = 0;
                    if (rb_20bei.isChecked())
                        Group2type = 1;
                    tv_cost_notice.setVisibility(View.GONE);
                    int ratio = 0;
                    if (Group2type == 0) {
                        ratio = 10;
                    } else {
                        ratio = 20;
                    }
                    btnClickListener.onAddClick(editInfo, Group1type, ratio);
                    KeyBoardUtils.hiddenKeyBoard(editText, mActiveDialog.getContext());
                    mActiveDialog.dismiss();


                }


            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActiveDialog != null && mActiveDialog.isShowing()) {
                    btnClickListener.onSkipClick();
                    KeyBoardUtils.hiddenKeyBoard(editText, mActiveDialog.getContext());
                    mActiveDialog.dismiss();
                }
            }
        });

    }

    public interface onBtnClickListener {
        void onAddClick(String costPrice, int kong, int ratio);

        void onSkipClick();
    }

}
