package com.btetop.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.btetop.R;
import com.btetop.base.EventBus.BindEventBus;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.config.UrlConfig;
import com.btetop.monitor.M;
import com.bugtags.library.Bugtags;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.commonsdk.UMConfigure;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by:lje
 * create on 2017/1/10 13:18
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    private Unbinder unbinder;
    public ImmersionBar mImmersionBar;
    protected Activity mContext;

    private final int BOND = 1;
    private final int hidden_soft_keybord=2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case BOND:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
                    break;
                case hidden_soft_keybord:
                    View view = mContext.findViewById(android.R.id.content);
                    if (view != null) {
                        InputMethodManager imm2 = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm2.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    break;
            }
        }

    };

    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局
        setContentView(intiLayout());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        /***初始化沉浸栏**/
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_white)
                .init();
        /***若使用BindEventBus注解，则绑定EventBus**/
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.register(this);

        }

        /***初始化友盟**/
        if (!UrlConfig.onLine()) {
            /**
             * 设置组件化的Log开关
             * 参数: boolean 默认为false，如需查看LOG设置为true
             */
            UMConfigure.setLogEnabled(true);
            /**
             * 设置日志加密
             * 参数：boolean 默认为false（不加密）
             */
            UMConfigure.setEncryptEnabled(true);
        } else {
            UMConfigure.setLogEnabled(false);
            UMConfigure.setEncryptEnabled(false);
        }


        //初始化控件
        initView();
        //设置数据
        initData();
        //友盟统计是否统计当前Activity
        statisticsActivity();

    }

    /**
     * 设置布局
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 友盟是否统计Activity
     */
    public abstract boolean statisticsActivity();


    @Override
    public void onResume() {
        super.onResume();
        if (statisticsActivity()) {
            M.monitor().onResume(this, getClass().getName());
        }
        Bugtags.onResume(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (statisticsActivity()) {
            M.monitor().onPause(this, getClass().getName());
        }
        //隐藏键盘
        hiddenKeyBoard();
        Bugtags.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        // 若使用BindEventBus注解，则解绑定EventBus
        if (this.getClass().isAnnotationPresent(BindEventBus.class)) {
            EventBusUtils.unregister(this);
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
    }

    public void hiddenKeyBoard() {
        handler.sendEmptyMessageDelayed(hidden_soft_keybord,100);
    }

    /**
     * 显示软键盘
     */
    public void showSoftKeyboard() {
        handler.sendEmptyMessageDelayed(BOND,100);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

}