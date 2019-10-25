package com.btetop.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuwencai on 15/12/2.
 */

public class RxUtil {

    /**
     * via https://yongjhih.gitbooks.io/feed/content/RxJava.html
     * io线程执行 ui线程回调
     */
    public static <T> Observable.Transformer<T, T> mainAsync() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
