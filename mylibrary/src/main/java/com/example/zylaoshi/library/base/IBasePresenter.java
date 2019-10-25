package com.example.zylaoshi.library.base;

/**
 * @auther zylaoshi
 * @date 2017/11/30 15:34
 */

public interface IBasePresenter<T extends IBaseView> {
    /**
     * 刷新数据
     */
    void doRefresh();

    /**
     * 显示网络错误
     */
    void doShowNetError();

//    /**
//     * 绑定View
//     * @param view
//     */
//    void attachView(T view);
//
//    void detachView();
}
