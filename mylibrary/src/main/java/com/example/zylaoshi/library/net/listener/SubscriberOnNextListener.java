package com.example.zylaoshi.library.net.listener;

import com.example.zylaoshi.library.net.exception.ResponeThrowable;

/**
 * @description: 成功获取数据显示的框
 * @autour: zylaoshi
 * @date: 2017/11/29 18:02
*/
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(ResponeThrowable ex);
}
