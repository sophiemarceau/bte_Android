package com.btetop.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.btetop.R;
import com.scwang.smartrefresh.layout.util.DensityUtil;


/**
 * @Description: 带删除按钮的 搜索框
 */

@SuppressLint("AppCompatCustomView")
public class SearchEdittext extends EditText implements
        View.OnFocusChangeListener, TextWatcher, TextView.OnEditorActionListener {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;


    private int clearDrawableSize = 14;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private OnFocusChangeListener listener;
    private Drawable mSearchDrawable;

    public SearchEdittext(Context context) {
        this(context, null);
    }

    public SearchEdittext(Context context, AttributeSet attrs) {
//这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public SearchEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {

        clearDrawableSize = DensityUtil.dp2px( clearDrawableSize);


        if(getCompoundDrawables()[0]==null){
            mSearchDrawable = getResources().getDrawable(R.mipmap.ic_search);
        }
        mSearchDrawable.setBounds(0, 0, clearDrawableSize, clearDrawableSize);

        setCompoundDrawables(mSearchDrawable,
                getCompoundDrawables()[1], mClearDrawable, getCompoundDrawables()[3]);

//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//	throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.mipmap.clearedittext);
        }



        mClearDrawable.setBounds(0, 0, clearDrawableSize, clearDrawableSize);
//默认设置隐藏图标
        setClearIconVisible(false);
//设置焦点改变的监听
        setOnFocusChangeListener(this);
//设置输入框里面内容发生改变的监听
        addTextChangedListener(this);

        setOnEditorActionListener(this);
    }



    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(this);
        if (l != this) {
            listener = l;
        }
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }

        if (listener != null) {
            listener.onFocusChange(v, hasFocus);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(mSearchDrawable,
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        if (i == EditorInfo.IME_ACTION_SEARCH) {
            if (searchListener != null) {//设置搜索回调
                searchListener.onSearch(textView.getText().toString());
            } else {
                Toast.makeText(this.getContext(), "未设置Edittext搜索监听", Toast.LENGTH_SHORT).show();
            }
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) this.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    ((Activity) this.getContext()).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            return true;
        }
        return false;
    }

    //点击搜索 按钮时的回调
    private onSearchListener searchListener;

    public void setSearchListener(onSearchListener l) {
        this.searchListener = l;
    }

    public interface onSearchListener {
        // do search action
        void onSearch(String content);
    }
}


