package com.example.zylaoshi.library.mvp;

import android.os.Bundle;

import com.example.zylaoshi.library.base.IBaseView;
import com.example.zylaoshi.library.base.BaseActivity;
import com.example.zylaoshi.library.base.IBasePresenter;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * @auther zylaoshi
 * @date 2017/11/30 16:06
 */

public abstract class BaseMvpActivity<T extends IBasePresenter> extends BaseActivity implements IBaseView<T> {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setPresenter(mPresenter);
        super.onCreate(savedInstanceState);
    }

    /**
     * 绑定生命周期
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
