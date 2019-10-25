package com.example.zylaoshi.library.net.transformer;


import com.example.zylaoshi.library.net.service.HttpResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/** 
 * @description: 封装一层后的解析器
 * @autour: zylaoshi
 * @date: 2017/11/29 17:31
*/
public class DefaultTransformer<T>  implements Observable.Transformer<HttpResponse<T>,T>{
    @Override
    public Observable<T> call(Observable<HttpResponse<T>> httpResponseObservable) {

        return httpResponseObservable.subscribeOn(Schedulers.io())
                                                .observeOn(Schedulers.newThread())
                                                .compose(ErrorTransformer.<T>getInstance())
                                                .observeOn(AndroidSchedulers.mainThread());
    }
}
