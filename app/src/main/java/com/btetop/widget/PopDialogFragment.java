package com.btetop.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.btetop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopDialogFragment extends DialogFragment {
    private String contentString;
    private String leftString;
    private String rightString;
    private PopDialogListener listener;

    @BindView(R.id.tvContent)
    TextView mContentTextView;
    @BindView(R.id.left)
    TextView mLeftTextview;
    @BindView(R.id.right)
    TextView mRightTextview;


    public PopDialogFragment() {

    }

    public static PopDialogFragment newInstance(String contentString, String leftString, String rightString,
                                                PopDialogListener listener) {
        PopDialogFragment fragment = new PopDialogFragment();
        fragment.contentString = contentString;
        fragment.leftString = leftString;
        fragment.rightString = rightString;
        fragment.listener = listener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        int screenWidth = ScreenUtils.getScreenWidth();
        windowParams.width = (int) (screenWidth * 0.8);
        windowParams.height = SizeUtils.dp2px(178);
        window.setAttributes(windowParams);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_fragment, container, false);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    private void initView() {
        mContentTextView.setText(contentString);
        mLeftTextview.setText(leftString);
        mRightTextview.setText(rightString);


    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (listener!=null) {
            listener.cancleClick();
        }
    }

    @OnClick({R.id.left, R.id.right})
    public void setOnClick(View view) {

        switch (view.getId()) {
            case R.id.left:
                dismiss();
                if (listener != null) {
                    listener.leftClick();
                }
                break;
            case R.id.right:
                dismiss();
                if (listener != null) {
                    listener.rightClick();
                }
                break;
        }
    }


    public interface PopDialogListener {
        void leftClick();

        void rightClick();

        void cancleClick();
    }
}
