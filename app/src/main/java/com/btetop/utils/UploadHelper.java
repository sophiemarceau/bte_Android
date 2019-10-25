package com.btetop.utils;

import android.text.format.DateFormat;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.bean.OssConfigBean;
import com.btetop.bean.OssInfoBean;
import com.btetop.net.BteTopService;

import java.io.File;
import java.util.Date;

import rx.functions.Action1;


public class UploadHelper {

    //上传仓库名
    private static String bucketName = "bte-test";
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String securityToken;

    public interface Upload {
        void InitSuccess();
        void UploadSuccess();
    }

    public Upload mUpload;

    public UploadHelper(Upload upload) {
        this.mUpload = upload;
        initOss();
    }

    private OSS getOSSClient() {

        //if null , default will be init
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // connction time out default 15s
        conf.setSocketTimeout(15 * 1000); // socket timeout，default 15s
        conf.setMaxConcurrentRequest(5); // synchronous request number，default 5
        conf.setMaxErrorRetry(2); // retry，default 2
        OSSLog.enableLog(); //write local log file ,path is SDCard_path\OSSLog\logs.csv
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken);
        OSS oss = new OSSClient(BteTopApplication.getContext(), endpoint, credentialProvider, conf);
        return oss;
    }

    public void initOss() {
        BteTopService.getOSSConfigInfo()
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<OssConfigBean>>() {
                    @Override
                    public void call(BaseBean<OssConfigBean> ossConfigBeanBaseBean) {
                        if (ossConfigBeanBaseBean != null && "0000".equals(ossConfigBeanBaseBean.getCode())) {
                            bucketName = ossConfigBeanBaseBean.getData().getBucketName();
                            endpoint = ossConfigBeanBaseBean.getData().getEndPoint();
                            getOssInfo();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public void getOssInfo() {
        BteTopService.getOSSInfo()
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<OssInfoBean>>() {
                    @Override
                    public void call(BaseBean<OssInfoBean> ossInfoBeanBaseBean) {
                        if (ossInfoBeanBaseBean != null
                                && "0000".equals(ossInfoBeanBaseBean.getCode())) {
                            accessKeyId = ossInfoBeanBaseBean.getData().getAccessKeyId();
                            accessKeySecret = ossInfoBeanBaseBean.getData().getAccessKeySecret();
                            securityToken = ossInfoBeanBaseBean.getData().getSecurityToken();
                            mUpload.InitSuccess();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    /**
     * 上传方法
     *
     * @param objectKey 标识
     * @param path      需上传文件的路径
     * @return 外网访问的路径
     */
    public String upload(String objectKey, String path) {
        // 构造上传请求
        PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, path);
        try {
            //得到client
            OSS client = getOSSClient();
            //上传获取结果
            PutObjectResult result = client.putObject(request);
            //获取可访问的url
            String url = client.presignPublicObjectURL(bucketName, objectKey);
            //格式打印输出
//            LogUtils.e(String.format("PublicObjectURL:%s", url));
//            LogUtils.e("upload==url===" + url);

            OSSAsyncTask task = client.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    mUpload.UploadSuccess();
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // Request exception
                    if (clientExcepion != null) {
                        // Local exception, such as a network exception
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        // Service exception
//                        LogUtils.e("ErrorCode", serviceException.getErrorCode());
//                        LogUtils.e("RequestId", serviceException.getRequestId());
//                        LogUtils.e("HostId", serviceException.getHostId());
//                        LogUtils.e("RawMessage", serviceException.getRawMessage());
                    }
                }
            });
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 上传普通图片
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public String uploadImage(String path) {
        String key = getObjectImageKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public String uploadPortrait(String path) {
        String key = getObjectPortraitKey(path);
        return upload(key, path);
    }

    /**
     * 上传audio
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public String uploadAudio(String path) {
        String key = getObjectAudioKey(path);
        return upload(key, path);
    }


    /**
     * 获取时间
     *
     * @return 时间戳 例如:201805
     */
    private static String getDateString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    /**
     * 返回key
     *
     * @param path 本地路径
     * @return key
     */
    //格式: image/201805/sfdsgfsdvsdfdsfs.jpg
    private static String getObjectImageKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("image/%s/%s.jpg", dateString, fileMd5);
    }

    //格式: portrait/201805/sfdsgfsdvsdfdsfs.jpg
    private static String getObjectPortraitKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("portrait/%s/%s.jpg", dateString, fileMd5);
    }

    //格式: audio/201805/sfdsgfsdvsdfdsfs.mp3
    private static String getObjectAudioKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();
        return String.format("audio/%s/%s.mp3", dateString, fileMd5);
    }

}
