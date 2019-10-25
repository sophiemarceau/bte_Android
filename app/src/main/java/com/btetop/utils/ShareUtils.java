package com.btetop.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import rx.functions.Action1;

/**
 * Created by Eric 2018/8/7.
 */

public class ShareUtils {
    /**
     * 分享链接
     */
    public static void shareUrl(final Activity context, final String url, String title, String description, String imageUrl, SHARE_MEDIA shareMedia) {
        UMWeb web = new UMWeb(url);//连接地址
        web.setTitle(title);//标题
        web.setDescription(description);//描述

        if (TextUtils.isEmpty(imageUrl)) {
            web.setThumb(new UMImage(context, R.mipmap.app_share_icon)); //本地缩略图
        } else {
            web.setThumb(new UMImage(context, imageUrl)); //网络缩略图
        }

        new ShareAction(context)
                .setPlatform(shareMedia)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(final SHARE_MEDIA share_media) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                submitShare(share_media.getName(), "Page", url);
                            }
                        });
                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cancelShare(share_media.getName(), "Page", url);
                            }
                        });
                    }
                }).share();
    }

    /**
     * 分享图片
     */
    public static void sharePic(final Activity context, Bitmap bitmap, SHARE_MEDIA platform) {
        if (bitmap == null) return;
        UMImage image = new UMImage(context, bitmap);
        new ShareAction(context)
                .setPlatform(platform)
                .withMedia(image)
                .withText("分享")
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                    }
                }).share();

    }


    public static void cancelShare(String type, String pageType, String shareUrl) {
        BteTopService.cancelShare(UserService.getCurrentUserToken(),
                "Android",
                DeviceUtils.getUniqueId(BteTopApplication.getContext()),
                type,
                pageType,
                shareUrl,
                System.currentTimeMillis(),
                0l).compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

    }

    public static void submitShare(String type, String pageType, String shareUrl) {
        BteTopService.createShare(UserService.getCurrentUserToken(),
                "Android",
                DeviceUtils.getUniqueId(BteTopApplication.getContext()),
                type,
                pageType,
                shareUrl,
                System.currentTimeMillis(),
                0l
        ).compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

    }
}