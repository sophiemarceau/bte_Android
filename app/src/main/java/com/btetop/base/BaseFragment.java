package com.btetop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.btetop.base.EventBus.BindEventBus;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.monitor.UmengUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/23.
 * 记得子类根据需求来绑定eventbus 绑定方式:@BindEventBus
 */


public abstract class BaseFragment extends com.trello.rxlifecycle.components.support.RxFragment {

    private static final String TAG = "Base";
    private String mTagName;


    private Unbinder unbinder;

    //是否显示
    private boolean show;
    //是否启动activity
    private boolean startActivity;

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int attachLayoutId();

    /**
     * 初始化视图控件
     */
    protected abstract void initView(View view);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 友盟是否统计Activity
     */


    protected abstract   boolean statisticsFragment();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(attachLayoutId(), container, false);

        unbinder = ButterKnife.bind(this,view);


        // 若使用BindEventBus注解，则绑定EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);
        }

        initView(view);
        initData();
        onShow();
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(unbinder!=null) unbinder.unbind();
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onHidden();
        } else {
            onShow();
        }
    }

    public String getFragmentNameTag() {
        return mTagName;
    }

    public void setFragmentNameTag(String TagName) {
        mTagName = TagName;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (startActivity) {
            onShow();
        }

    }


    @Override
    public void onPause() {
        //如果是onPause（）肯定开启了startActivity
        super.onPause();
        if (show) {
            startActivity = true;
            onHidden();
        }
    }


    protected void onShow() {
        show = true;
        startActivity = false;

        if (statisticsFragment()) {
            UmengUtils.fragmentStartStatistics(getClass(), mTagName);
        }
    }


    protected void onHidden() {
        show = false;
        if (statisticsFragment()) {
            UmengUtils.fragmentEndStatistics(getClass(), mTagName);
        }
        hiddenKeyBoard();
    }

    public void onViewPagerShow(){

    }

    public void onViewPagerHidden(){

    }

    public void hiddenKeyBoard() {
        View view = getActivity().findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
