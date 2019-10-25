package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.utils.KeyBoardUtils;

public class PostCommentDialog implements  View.OnClickListener {
    private static Dialog mShareDialog;
    private EditText editText;
    private UserInputListener listener;
    private TextView send;
    private RelativeLayout container;
    private static final String TAG = "PostCommentDialog";
    private String hint;
    private final int BOND = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BOND:
                    InputMethodManager inputMethodManager = (InputMethodManager) mShareDialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                    break;
            }
        }

    };



    public void show(Activity activity,UserInputListener listener) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.comment_dialog_layout, null);
        this.listener=listener;
        initDialog(view);
        mShareDialog = new Dialog(activity, R.style.MyDialogStyle);
        Window window = mShareDialog.getWindow();
        mShareDialog.setContentView(view);
        mShareDialog.setCancelable(true);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                if (listener!=null) {
                    listener.onHiddenDialog();
                }
            }
        });
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = activity.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        window.setGravity(Gravity.BOTTOM);
        mShareDialog.show();


        handler.sendEmptyMessageDelayed(BOND, 100);
    }

    public void showHint(Activity activity,UserInputListener listener,String hint) {
        this.hint=hint;
       this.show(activity,listener);
    }


    private void initDialog(View view) {
        editText = view.findViewById(R.id.edittext);
        send = view.findViewById(R.id.send);
        send.setOnClickListener(this);
        container=view.findViewById(R.id.container);
        container.setOnClickListener(this);
        if (!TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>0) {
                    send.setTextColor(ContextCompat.getColor(BteTopApplication.getInstance(),R.color.color_308CDD));
                }else {
                    send.setTextColor(ContextCompat.getColor(BteTopApplication.getInstance(),R.color.color_fafa));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (listener!=null) {
                    if (editText == null || TextUtils.isEmpty(editText.getText().toString())) {
                        return;
                    }
                    listener.userInputContent(editText.getText().toString());
                    KeyBoardUtils.hiddenKeyBoard(editText,mShareDialog.getContext());
                    mShareDialog.dismiss();

                }
                break;
        }
    }



    public interface UserInputListener{
        void userInputContent(String s);

        void onHiddenDialog();
    }
}
