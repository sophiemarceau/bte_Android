package com.example.zylaoshi.library.mvp;

import com.example.zylaoshi.library.base.IBasePresenter;
import com.example.zylaoshi.library.base.IBaseView;

/**
 * @auther zylaoshi
 * @date 2017/11/30 17:49
 */

public interface IContent {
    interface View extends IBaseView<Presenter> {
        /**
         * 获取文字并展示
         */
        void onShowSaveSuccess(String string);
    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void togetData();
    }

}
