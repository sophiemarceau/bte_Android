package com.example.zylaoshi.library.net.subscribers;

import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.example.zylaoshi.library.net.exception.ResponeThrowable;
import com.example.zylaoshi.library.net.exception.ServerException;
import com.example.zylaoshi.library.net.listener.ProgressCancelListener;
import com.example.zylaoshi.library.net.listener.SubscriberOnNextListener;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @description: 显示一个dialog
 * @autour: zylaoshi
 * @date: 2017/11/29 17:33
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    //出错提示
    private String networkMsg = "网利连接错误";
    private String parseMsg = "json解析格式错误";
    private String unknownMsg = "不能识别的错误";

    /**
     *
     * @param mSubscriberOnNextListener 回调
     * @param context 上下文对象
     * @param isShow  是否显示progressbar
     */
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context,boolean isShow) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;

        mProgressDialogHandler = new ProgressDialogHandler(context, this, true,isShow);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
//        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
//        if (e instanceof SocketTimeoutException) {
//            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
//        } else if (e instanceof ConnectException) {
//            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
//        } else{
//            mSubscriberOnNextListener.onError(e);
//        }

        Throwable throwable = e;
        //获取最根源的异常
        while(throwable.getCause() != null){
            e = throwable;
            throwable = throwable.getCause();
        }

        ResponeThrowable ex;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, httpException.code());
            switch(httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setMessage(networkMsg);  //均视为网络错误
                    mSubscriberOnNextListener.onError(ex);
                    break;
            }
        } else if (e instanceof ServerException){    //服务器返回的错误
            ServerException serverException = (ServerException) e;
            ex = new ResponeThrowable(serverException, serverException.getMessage());
            mSubscriberOnNextListener.onError(ex);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ResponeThrowable(e, ResponeThrowable.PARSE_ERROR);
            ex.setMessage(parseMsg);            //均视为解析错误
            mSubscriberOnNextListener.onError(ex);
        } else {
            ex = new ResponeThrowable(e, ResponeThrowable.UNKNOWN);
            ex.setMessage(unknownMsg);          //未知错误
            mSubscriberOnNextListener.onError(ex);
        }
        dismissProgressDialog();

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}