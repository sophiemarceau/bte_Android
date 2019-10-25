package com.btetop.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserInfo;
import com.btetop.dialog.ChoosePictureDialog;
import com.btetop.dialog.SetNickNameDialog;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.GlideCircleTransform;
import com.btetop.utils.RealPathFromUriUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.btetop.utils.UploadHelper;
import com.btetop.widget.PopDialogFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class PersonSettingActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_bind_wx)
    TextView tvBindWx;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.tv_bind_wx_get_nick)
    TextView tv_bind_wx_get_nick;
    @BindView(R.id.ll_head)
    RelativeLayout llHead;
    @BindView(R.id.ll_nick)
    RelativeLayout llNick;
    @BindView(R.id.ll_bind_wx)
    RelativeLayout llBindWx;
    @BindView(R.id.ll_login_pwd)
    RelativeLayout llLoginPwd;
    @BindView(R.id.img_fan_hui)
    ImageView img_fan_hui;
    @BindView(R.id.tran_title_detail)
    TextView tran_title_detail;

    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int REQUEST_PICK_IMAGE = 11101;
    public final static int REQUEST_IMAGE_CAPTURE = 101;
    UploadHelper uploadHelper;
    String wxBindStatus;
    String avator;
    String name;
    String UploadSuccess = "";
    PopDialogFragment popDialogFragment;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateViews();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        handler.sendEmptyMessageDelayed(0, 700);
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_person_setting;
    }

    @Override
    public void initView() {
        tran_title_detail.setText("个人设置");

    }

    private void updateViews() {
        UserInfo currentUserInfo = UserService.getCurrentUserInfo();
        if (currentUserInfo != null) {
            avator = currentUserInfo.getAvator();
//            LogUtils.e("avator===" + avator);
            name = currentUserInfo.getName();
            wxBindStatus = currentUserInfo.getWxBindStatus();


            updateAvator(avator);
            tvNickName.setText(name);
            if (currentUserInfo.getReset() == 1) {
                tvLoginPwd.setText("未设置");
            } else {
                tvLoginPwd.setText("修改密码");
            }

            if ("0".equals(wxBindStatus)) {
                tv_bind_wx_get_nick.setVisibility(View.VISIBLE);
                tvBindWx.setText("未绑定");
            } else {
                tv_bind_wx_get_nick.setVisibility(View.GONE);
                tvBindWx.setText("已绑定");
            }
        }
    }

    public void updateAvator(String avator) {
        Glide.with(PersonSettingActivity.this)
                .load(avator)
                .placeholder(R.mipmap.inviting_friends_logo)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .transform(new GlideCircleTransform(PersonSettingActivity.this))
                .into(ivHead);
    }

    @Override
    public void initData() {
        initPhotoError();

    }

    private void initPhotoError() {
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    @OnClick({R.id.ll_head, R.id.ll_nick
            , R.id.ll_bind_wx, R.id.ll_login_pwd
            , R.id.tv_bind_wx_get_nick, R.id.img_fan_hui
    })
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                grantPermissions();
                new ChoosePictureDialog(this, new ChoosePictureDialog.Listener() {
                    //选择本地图片
                    @Override
                    public void choosePic() {
                        ActivityCompat.requestPermissions(PersonSettingActivity.this, mPermissionList, 100);
                    }

                    //拍照
                    @Override
                    public void chooseCamera() {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                    }

                    @Override
                    public void chooseWx() {
                        wxLogin();
                    }
                }).show();
                break;

            case R.id.ll_nick:
                SetNickNameDialog.getInstance().showDialog(PersonSettingActivity.this, tvNickName.getText().toString(), new SetNickNameDialog.setNickName() {
                    @Override
                    public void setNickName(String editInfo) {
                        changeUserInfo(editInfo, "", new File(""));
                    }
                });
                break;

            case R.id.ll_bind_wx:

                //0未绑定 1已绑定
                if ("未绑定".equals(tvBindWx.getText().toString() + "")) {
                    wxLogin();
                } else {
                    popDialogFragment = PopDialogFragment.newInstance(
                            "确定要解绑微信吗？", "取消", "确定", new PopDialogFragment.PopDialogListener() {
                                @Override
                                public void leftClick() {
                                    popDialogFragment.dismiss();

                                }

                                @Override
                                public void rightClick() {
                                    popDialogFragment.dismiss();
                                    unBindWx();

                                }

                                @Override
                                public void cancleClick() {
                                    popDialogFragment.dismiss();

                                }
                            });
                    popDialogFragment.setStyle(popDialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
                    popDialogFragment.show(getSupportFragmentManager(), "popDialogFragment");
                }

                break;

            case R.id.ll_login_pwd:
                startActivity(new Intent(this, SetLoginPwdActivity.class));
                break;
            case R.id.tv_bind_wx_get_nick:
                wxLogin();
                break;
            case R.id.img_fan_hui:
                finish();
                break;
        }
    }

    public void wxLogin() {
        if (!BteTopApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showShortToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        BteTopApplication.mWxApi.sendReq(req);
    }

    public void unBindWx() {
        BteTopService.unBindWx()
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean != null && "0000".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast("解绑微信成功");
                            tv_bind_wx_get_nick.setVisibility(View.VISIBLE);
                            tvBindWx.setText("未绑定");
                        } else {
                            ToastUtils.showShortToast(baseBean.getMessage() + "");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    private void changeUserInfo(String editInfo, String avator, File file) {

        BteTopService.UpdateUserInfo(editInfo, avator)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean != null && "0000".equals(baseBean.getCode())) {
                            if (!android.text.TextUtils.isEmpty(editInfo)) {
                                tvNickName.setText(editInfo);
                                ToastUtils.showShortToast("修改昵称成功");
                            }
                            if (!android.text.TextUtils.isEmpty(avator)) {
                                UserService.setChatUserAvatar(avator);
                                ToastUtils.showShortToast("修改头像成功");
                                updateAvator(avator);
                            }
                        } else {
                            ToastUtils.showShortToast(baseBean.getMessage() + "");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    //选择本地图片
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //选择本地图片
    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    File takePhotoUri;

    //选择本地图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
//                        LogUtils.e("realPathFromUri====" + realPathFromUri);
                        compressPic(realPathFromUri);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                    break;

                //拍照
                case REQUEST_IMAGE_CAPTURE:
                    if (data != null) {
                        Bitmap photo = data.getParcelableExtra("data");
                        String takePhotoUri = BitmapUtil.saveBitmap(this, photo);
                        compressPic(takePhotoUri);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新拍摄", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public String getObjectKey() {
        return "user/avtor/" + UserService.getCurrentUserInviteCode() + ".jpg";
    }


    public void compressPic(String takePhotoUri) {
        Luban.with(this)
                .load(takePhotoUri)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI

                    }

                    @Override
                    public void onSuccess(File file) {
                        //  压缩成功后调用，返回压缩后的图片文件
                        uploadHelper = new UploadHelper(new UploadHelper.Upload() {
                            @Override
                            public void InitSuccess() {
                                UploadSuccess = uploadHelper.upload(getObjectKey(), file.getPath());
                            }

                            @Override
                            public void UploadSuccess() {
//                                LogUtils.e("file.getAbsolutePath()===" + file.getAbsolutePath());
//                                LogUtils.e("file.getPath()===" + file.getPath());
//                                LogUtils.e("上传后得到的阿里图片地址===" + UploadSuccess);
                                avator = UploadSuccess;
                                changeUserInfo("", UploadSuccess, file);
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用
                    }
                }).launch();
    }
    private static final int MY_PERMISSION_REQUEST_CODE = 10001;
    //申请权限
    private void grantPermissions(){
        if(this == null  ) return;
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }
        );

        if (!isAllGranted){
            // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    },
                    MY_PERMISSION_REQUEST_CODE
            );
        }

    }
    private boolean checkPermissionAllGranted(String[] permissions) {

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
}
