package com.example.zylaoshi.library.base;

import android.app.Activity;
import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @auther zylaoshi
 * @date 2017/11/30 15:37
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected Activity mContext;
    private Unbinder mUnBinder;

    protected abstract int getLayout();

    protected abstract void initView(Bundle bundle);

    protected abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initView(savedInstanceState);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();
    }
}
