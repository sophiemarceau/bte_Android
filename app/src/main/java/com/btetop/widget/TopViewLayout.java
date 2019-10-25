package com.btetop.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.example.zylaoshi.library.utils.DisplayUtil;

public class TopViewLayout extends RelativeLayout {

    private Context mContext;
    private ImageView mBackImage;

    private TextView mTitleView; //标题

    private ImageView mRightMenu;//右边角的图片

    private TextView mRightText;//右边的文本

    LayoutParams mBackParams;
    LayoutParams mTitleParams;
    LayoutParams mMenuParams;
    String mTitle;
    int mLeftImage;
    int rightImage;
    String rightText;
    int dp8;
    public final static int WARP_CONTENT = LayoutParams.WRAP_CONTENT;
    public final static int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    public TopViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopViewLayout(Context context) {
        this(context, null);
    }

    public TopViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        if (isInEditMode())
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopViewLayout, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TopViewLayout_topview_left:
                    mLeftImage = array.getResourceId(attr, -1);
                    break;
                case R.styleable.TopViewLayout_topview_title:
                    mTitle = array.getString(attr);
                    break;
                case R.styleable.TopViewLayout_topview_right:
                    rightImage = array.getResourceId(attr, -1);
                    break;
                case R.styleable.TopViewLayout_topview_rightText:
                    rightText = array.getString(attr);
                    break;
                default:
                    break;
            }
        }
        dp8 = DisplayUtil.dp2px(mContext, 8);
        mBackImage = new ImageView(mContext);
        mTitleView = new TextView(mContext);
        mRightMenu = new ImageView(mContext);
        mRightText = new TextView(mContext);
        mBackParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mTitleParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mMenuParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);

        mTitleParams.addRule(CENTER_IN_PARENT);

        mMenuParams.addRule(ALIGN_PARENT_RIGHT);
        mMenuParams.addRule(CENTER_VERTICAL);
        mRightMenu.setPadding(dp8, 0, dp8, 0);
        mRightText.setPadding(dp8*2, dp8, dp8, dp8);
        mBackParams.addRule(CENTER_VERTICAL);
        mBackImage.setPadding(dp8, 0, dp8, 0);

        addView(mBackImage, mBackParams);
        addView(mTitleView, mTitleParams);
        addView(mRightMenu, mMenuParams);
        addView(mRightText, mMenuParams);
        if (!TextUtils.isEmpty(mTitle))
            mTitleView.setText(mTitle);
        mTitleView.setMaxLines(1);
        mTitleView.setPadding(5 * dp8, 0, 5 * dp8, 0);

        if (rightImage > 0) {
            mRightMenu.setImageResource(rightImage);
            mRightText.setVisibility(View.GONE);
        }
        if (mLeftImage > 0) {
            mBackImage.setImageResource(mLeftImage);
        }
        if (!TextUtils.isEmpty(rightText)) {
            mRightText.setText(rightText);

            mRightMenu.setVisibility(View.GONE);
        }
        setBackgroundResource(R.color.color_white);
        mTitleView.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        mRightText.setTextColor(Color.WHITE);
        array.recycle();
    }


    public void setTitle(String text) {
        mTitleView.setText(text);
    }
    public void setTitleColor(@StringRes int  titleColor) {
        mTitleView.setTextColor(titleColor);
    }
    public void setTitle(@StringRes int  text) {
        mTitleView.setText(text);
    }

    public void setRightMenuRes(int resId) {
        this.mRightMenu.setImageResource(resId);
    }

    public void setBackImageRes(int resid) {

        this.mBackImage.setImageResource(resid);
    }
    public void setBackImageHide(){
        this.mBackImage.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置右边的文本
     *
     * @param text
     * @param listener 监听
     */
    public void setRightText(String text, OnClickListener listener) {
        this.mRightText.setText(text);
        if (listener != null) {
            this.mRightText.setOnClickListener(listener);
        }
    }

    /**
     * 设置右边的文本
     *
     * @param textId
     * @param listener 监听
     */
    public void setRightText(@StringRes int textId, OnClickListener listener) {
        this.mRightText.setText(textId);
        if (listener != null) {
            this.mRightText.setOnClickListener(listener);
        }
    }

    /**
     * 设置监听
     * @param listener
     */
    public void setRightTextOnClickListener(OnClickListener listener) {
        if (listener != null)
            this.mRightText.setOnClickListener(listener);
    }

    public void setRightMenuListener(OnClickListener mClick) {
        if (mClick != null) {
            mRightMenu.setOnClickListener(mClick);
        }
    }

    public void setBackOnClickListener(OnClickListener mL) {
        if (mL != null) {
            mBackImage.setOnClickListener(mL);
        }
    }

    /**
     * 设置返回按钮的便利方法
     *
     * @param mActivity
     */
    public void setFinishActivity(final Activity mActivity) {
//       if(mBackImage.)
        mBackImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }
}