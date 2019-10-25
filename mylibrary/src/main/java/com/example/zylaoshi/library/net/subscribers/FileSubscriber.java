package com.example.zylaoshi.library.net.subscribers;

import com.example.zylaoshi.library.net.download.FileCallBack;

import rx.Subscriber;

/**
 * @description: 绑定FileCallBack 到Subscriber
 * @autour: zylaoshi
 * @date: 2017/12/4 14:11
*/
public class FileSubscriber<T> extends Subscriber<T> {
    private FileCallBack fileCallBack;

    public FileSubscriber( FileCallBack fileCallBack) {
        this.fileCallBack = fileCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fileCallBack != null)
            fileCallBack.onStart();
    }

    @Override
    public void onCompleted() {
        if (fileCallBack != null)
            fileCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null)
            fileCallBack.onError(e);
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null)
            fileCallBack.onSuccess(t);
    }

}
