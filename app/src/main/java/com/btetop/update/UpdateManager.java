package com.btetop.update;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.btetop.BuildConfig;
import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UpdateInfoEntity;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.ToastUtils;
import com.example.zylaoshi.library.net.download.FileCallBack;
import com.example.zylaoshi.library.net.download.HttpDownLoadManager;
import com.example.zylaoshi.library.utils.LogUtil;
import com.example.zylaoshi.library.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import rx.functions.Action1;


/**
 * Created by android on 7/11/15.
 */
public class UpdateManager {

    private static final String TAG = "UpdateManager";
    protected static final int DOWNLOAD_SUCCESS = 5;

    private static String versionName;
    private String apkPath;
    String fileName = "btetop.apk";

    private Dialog mUpdateDialog;
    private ProgressBar progressBar;
    private TextView desc;
    private TextView tv_update_title, tv_updating_cancel;
    private File apkFile;


    private WeakReference<BaseActivity> activityWeakReference;

    private MyHandler mMyHandler;
    private BaseBean<UpdateInfoEntity.UpdateInfoData> mUpdateInfoNewEntity;
    private TextView tv_update_now;
    private TextView tv_update_later;
    private TextView tv_update_progress;
    private LinearLayout ll_ifupdate_view;
    private LinearLayout progressbar_rl;
    FileCallBack<ResponseBody> callBack;
    private static UpdateManager instance = null;
    private String force;
    private String downLoadUrl;
    //判断是否点击取消按钮 true 没点击，进行安装 false 点击了，不安装
    private boolean isInstall = true;

    public static UpdateManager getInstance() {
        if (null == instance) {
            synchronized (UpdateManager.class) {
                if (null == instance) {
                    instance = new UpdateManager();
                }

            }
        }
        return instance;
    }

    public void checkUpdate(BaseActivity activity) {
        if(activity == null) return;

        this.activityWeakReference = new WeakReference<>(activity);

        mMyHandler = new MyHandler();

        if (!getSavePath()) {
            return;
        }

        versionName = BuildConfig.VERSION_NAME;
        Log.e(TAG, "checkUpdate: "+versionName );
        if (NetworkUtil.isNetworkAvailable(BteTopApplication.getInstance())) {
            if (mUpdateDialog != null && mUpdateDialog.isShowing()) {
                return;
            }
            checkUpdateInfo(activity, versionName);
        } else {
            LogUtil.print("网络未连接");
        }

    }

    private void checkUpdateInfo(BaseActivity context, String versionName) {

        BteTopService.getAppUpdate("0", versionName)
                .compose(context.bindToLifecycle())
                .compose(RxUtil.<BaseBean<UpdateInfoEntity.UpdateInfoData>>mainAsync())
                .subscribe(new Action1<BaseBean<UpdateInfoEntity.UpdateInfoData>>() {
                    @Override
                    public void call(BaseBean<UpdateInfoEntity.UpdateInfoData> updateInfoDataBaseBean) {
                        mUpdateInfoNewEntity = updateInfoDataBaseBean;
                        if (mUpdateInfoNewEntity != null) {
                            String code = mUpdateInfoNewEntity.getCode();
                            String message = mUpdateInfoNewEntity.getMessage();
                                if ("0000".equals(mUpdateInfoNewEntity.getCode())) {
                                    String isUpdata = mUpdateInfoNewEntity.getData().getUpdate();
                                    Log.e(TAG, "call: "+isUpdata );
                                    if (!TextUtils.isEmpty(isUpdata)) {
                                        if ("1".equals(isUpdata)) {
                                            Log.e(TAG, "call: "+"显示" );
                                            showUpdateInfo(mUpdateInfoNewEntity);
                                        }
                                    } else {
                                        if (message != null && !"".equals(message)) {
                                            ToastUtils.showShortToast(message);
                                        }
                                    }
                                    boolean minePageTrade = mUpdateInfoNewEntity.getData().getTrade();
                                    SPUtils.put("mineTrade", minePageTrade);
                                }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("UpDateManager-->检查APP更新---链接服务器失败");
                    }
                });

    }


    private boolean getSavePath() {
        boolean flag = true;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED) && getAvailSdSize() >= 20) {
            apkPath = Environment.getExternalStorageDirectory().toString()
                    + File.separator + "btetop";
        } else if (getInternalAvailSize() >= 20) {
            apkPath = Environment.getDataDirectory().toString()
                    + File.separator + "btetop";
        } else {
            ToastUtils.showShortToast("手机内存不足");
            flag = false;
        }
        return flag;
    }

    /**
     * 获取SD卡可用空间
     *
     * @return
     */
    public long getAvailSdSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / 1024 / 1024;
    }

    /**
     * 获取内部存储设备的可用空间
     */
    public long getInternalAvailSize() {
        StatFs mDataFileStats = new StatFs("/data");
        long freeStorage = (long) mDataFileStats.getAvailableBlocks()
                * mDataFileStats.getBlockSize();

        return freeStorage / 1024 / 1024;
    }


    /**
     * 显示更新对话框
     *
     * @param dataBean
     */
    public void showUpdateInfo(BaseBean<UpdateInfoEntity.UpdateInfoData> dataBean) {
        View view = View.inflate(BteTopApplication.getInstance(), R.layout.dialog_update_layout, null);
        initDialogView(view, dataBean);

        BaseActivity context= activityWeakReference.get();
        mUpdateDialog = new Dialog(context, R.style.MyDialogStyle);
        mUpdateDialog.setContentView(view);
        mUpdateDialog.setCancelable(false);
        mUpdateDialog.setCanceledOnTouchOutside(false);
        mUpdateDialog.show();
    }


    private void initDialogView(View view, final BaseBean<UpdateInfoEntity.UpdateInfoData> updateEntity) {
        tv_update_now = (TextView) view.findViewById(R.id.tv_update_now);
        tv_update_later = (TextView) view.findViewById(R.id.tv_cancel_update);
        tv_update_progress = (TextView) view.findViewById(R.id.update_progress);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        progressbar_rl = (LinearLayout) view.findViewById(R.id.progressbar_rl);
        ll_ifupdate_view = (LinearLayout) view.findViewById(R.id.ll_ifupdate_view);
        desc = (TextView) view.findViewById(R.id.tv_des);
        tv_update_title = (TextView) view.findViewById(R.id.tv_des_tittle);
        tv_updating_cancel = (TextView) view.findViewById(R.id.tv_updating_cancel);

        String descContent = updateEntity.getData().getDesc();
        String[] strarray = descContent.split("[|]");
        StringBuilder strDesc = new StringBuilder();
        for (int i = 0; i < strarray.length; i++) {
            strDesc.append(strarray[i] + "\n");
        }
        desc.setText(strDesc.toString());
        tv_update_title.setText(updateEntity.getData().getName());
        // 强制更新
        force = updateEntity.getData().getForce();
        if (TextUtils.equals(force, "1")) {
            tv_update_later.setVisibility(View.GONE);
        } else {
            tv_update_later.setVisibility(View.VISIBLE);

        }
        dialogListener(updateEntity);
    }


    private void dialogListener(final BaseBean<UpdateInfoEntity.UpdateInfoData> updateEntity) {
        File dir = new File(apkPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        apkFile = new File(apkPath, fileName);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        if (!apkFile.exists()) {
            tv_update_now.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    checkPhonePermission(apkFile);
                }
            });


        } else if (apkFile.exists()) {
            tv_update_now.setText("安装更新");
            tv_update_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPhonePermission(apkFile);
                }
            });
        }

        tv_update_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateDialog != null) {
                    mUpdateDialog.dismiss();
                }
            }
        });
        tv_updating_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInstall = false;
                if (TextUtils.equals(force, "1")) {

                } else {
                    if (mUpdateDialog != null) {
                        mUpdateDialog.dismiss();
                        if (callBack != null) {
                            callBack.unsubscribe();
                        }
                    }
                }
            }
        });
    }

    /**
     * 下载app
     */

    public void updateWidthDownload() {

        downLoadUrl = mUpdateInfoNewEntity.getData().getUrl();
        progressbar_rl.setVisibility(View.VISIBLE);
        ll_ifupdate_view.setVisibility(View.GONE);
        load(downLoadUrl);
    }

    public void load(String url) {
        HttpDownLoadManager apiManager = new HttpDownLoadManager();
        FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(apkPath, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
            }

            @Override
            public void progress(long progress, long total) {
                Logger.d("下载中的数据====》" + progress + "=====>" + total);
                int progress1 = (int) (progress * 100 / total);
                tv_update_progress.setText(progress1 + "%");
                progressBar.setProgress(progress1);

            }

            @Override
            public void onStart() {
                if (TextUtils.equals(force, "1")) {
                    tv_updating_cancel.setVisibility(View.GONE);
                } else {
                    tv_updating_cancel.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCompleted() {
                //下载成功后，根据是否点击取消按钮来判断是否安装
                if (isInstall) {
                    mMyHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Logger.e(e.toString());
            }
        };
        apiManager.load(url, callBack);
    }

    private class MyHandler extends Handler {


        public MyHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = activityWeakReference.get();

            if (activity != null) {
                switch (msg.what) {
                    case DOWNLOAD_SUCCESS:
                        progressbar_rl.setVisibility(View.GONE);
                        ll_ifupdate_view.setVisibility(View.VISIBLE);
                        tv_update_now.setText("安装更新");
                        tv_update_now.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if (apkFile.exists()) {
                                    ApkManager.getInstance().installApk_3(
                                            BteTopApplication.getContext(),
                                            apkFile);
                                } else if (!apkFile.exists()) {
                                    //ToastUtils.showShortToast("安装包不存在，请重新下载!");
                                    //如果不存在则需要从新下载
                                    updateWidthDownload();
                                }
                            }

                        });
                        // 自动安装
                        ApkManager.getInstance().installApk_3(
                                BteTopApplication.getContext(), apkFile);
                        break;
                    default:
                        break;
                }
            }
        }
    }



    private void checkPhonePermission(final File apkFile) {

        if(activityWeakReference == null || activityWeakReference.get() == null) return;

        Activity  activity = activityWeakReference.get();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(BteTopApplication.getInstance(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        125);
            } else {
                updateWidthDownload();
            }
        } else {
            //6.0以下支持
            updateWidthDownload();
        }
    }
}