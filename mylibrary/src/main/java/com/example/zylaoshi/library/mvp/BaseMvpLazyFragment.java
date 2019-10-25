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
 * @date 2017/11/30 17:10
 */

public abstract class BaseMvpLazyFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView<T> {

    protected T presenter;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    
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

    

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }
    //数据的绑定
    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
