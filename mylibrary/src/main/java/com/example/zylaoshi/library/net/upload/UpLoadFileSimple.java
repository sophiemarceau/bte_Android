package com.example.zylaoshi.library.net.upload;

import com.example.zylaoshi.library.MyLibrary;
import com.example.zylaoshi.library.net.exception.ResponeThrowable;
import com.example.zylaoshi.library.net.http.HttpUtils;
import com.example.zylaoshi.library.net.listener.SubscriberOnNextListener;
import com.example.zylaoshi.library.net.listener.UploadProgressListener;
import com.example.zylaoshi.library.net.service.FileEntity;
import com.example.zylaoshi.library.net.service.HttpUploadService;
import com.example.zylaoshi.library.net.subscribers.ProgressSubscriber;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @description: 文件上传使用例子
 * @autour: zylaoshi
 * @date: 2017/12/4 14:06
*/
public class UpLoadFileSimple {

    //使用rxjava+retrofit 上传图片

    public void UpLoadFile(String filename,String uploadFileUrl){
        //转换为bitmap 对象
//        Bitmap bitmap = BitmapFactory.decodeFile(filename);
        //压缩图片处理
//        BitmapUtil.writeBitmap2Path(bitmap, filename);
        File file=new File(filename);
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part part= MultipartBody.Part.createFormData("filename",filename.substring(filename.lastIndexOf("/")), new ProgressRequestBody(requestBody,
                new UploadProgressListener() {
                    @Override
                    public void onProgress(final long currentBytesCount, final long totalBytesCount) {
                        /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
//                                Logger.d("上传图片中=======》"+totalBytesCount);
//                                Logger.d("上传图片中=======》"+currentBytesCount);
                                //显示文件上传的进度处理
                            }
                        });
                    }
                }));
        ProgressSubscriber progressSubscriber = new ProgressSubscriber(new SubscriberOnNextListener<FileEntity>() {
            @Override
            public void onNext(FileEntity fileEntity) {

            }

            @Override
            public void onError(ResponeThrowable ex) {

            }
        }, MyLibrary.getApplication(),false);

        HttpUtils.getInstance().getRetofitClinet().builder(HttpUploadService.class).uploadImage(uploadFileUrl,part)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(progressSubscriber);

    }

}
