package com.example.zylaoshi.library.net.listener;

/**
 * @description: 上传进度回调类
 * @autour: zylaoshi
 * @date: 2017/12/4 14:19
*/

public interface UploadProgressListener {
    /**
     * 上传进度
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(long currentBytesCount, long totalBytesCount);

}