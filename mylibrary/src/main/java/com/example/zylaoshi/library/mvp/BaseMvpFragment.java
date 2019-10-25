package com.example.zylaoshi.library.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.zylaoshi.library.base.BaseFragment;
import com.example.zylaoshi.library.base.IBasePresenter;
import com.example.zylaoshi.library.base.IBaseView;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.FragmentEvent;

/**
 * @auther zylaoshi
 * @date 2017/11/30 15:38
 */

public abstract class BaseMvpFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView<T> {

    protected T presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(presenter);
    }

    /**
     * 绑定生命周期
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }

}
