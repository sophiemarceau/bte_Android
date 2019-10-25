package com.btetop.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MyQrCodeBean;
import com.btetop.config.UrlConfig;
import com.btetop.net.BteTopService;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.zylaoshi.library.utils.LogUtil;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import rx.functions.Action1;

public class InvitingFriendsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    //    private TextView userTel;
    private ImageView myQrCode;
    private TextView invitingResult;
    private LinearLayout wxShare, pyqShare, wbShare, mQQ, mQQkj;
    private String token;
    private String qrCodeUrl;
    private String base64Url;
    private String shareUrl;

    @Override
    public int intiLayout() {
        return R.layout.activity_inviting_friends;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
        mBack = findViewById(R.id.invitingFriends_back);
        mBack.setOnClickListener(this);
//        userTel = findViewById(R.id.invitingFriends_tel);
        myQrCode = findViewById(R.id.invitingFriends_erweima);
        invitingResult = findViewById(R.id.invitingFriends_result);
        invitingResult.setOnClickListener(this);
        wxShare = findViewById(R.id.invitingFriends_wx);
        wxShare.setOnClickListener(this);
        pyqShare = findViewById(R.id.invitingFriends_pyq);
        pyqShare.setOnClickListener(this);
        wbShare = findViewById(R.id.invitingFriends_wb);
        wbShare.setOnClickListener(this);
        mQQ = findViewById(R.id.invitingFriends_qq);
        mQQ.setOnClickListener(this);
        mQQkj = findViewById(R.id.invitingFriends_qqkj);
        mQQkj.setOnClickListener(this);
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invitingFriends_back:
                finish();
                break;
            case R.id.invitingFriends_result://查看邀请结果
                startActivity(new Intent(this, InvitingFriendsResultActivity.class));
                break;
            case R.id.invitingFriends_wx:
                shareMyInviting(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.invitingFriends_pyq:
                shareMyInviting(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.invitingFriends_wb:
                shareMyInviting(SHARE_MEDIA.SINA);
                break;
            case R.id.invitingFriends_qq:
                shareMyInviting(SHARE_MEDIA.QQ);
                break;
            case R.id.invitingFriends_qqkj:
                shareMyInviting(SHARE_MEDIA.QZONE);
                break;
        }
    }

    @Override
    public void initData() {

        token = SPUtils.get("userToken", "");
        qrCodeUrl = UrlConfig.MINE_MY_QRCODE_URL;
        BteTopService.getUserMyQrCode(qrCodeUrl).compose(RxUtil.<BaseBean<MyQrCodeBean.MyQrCodeData>>mainAsync())
                .subscribe(new Action1<BaseBean<MyQrCodeBean.MyQrCodeData>>() {
                    @Override
                    public void call(BaseBean<MyQrCodeBean.MyQrCodeData> myQrCodeBean) {

                        if (myQrCodeBean != null) {
                            String code = myQrCodeBean.getCode();
                            String message = myQrCodeBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    base64Url = myQrCodeBean.getData().getBase64();
                                    shareUrl = myQrCodeBean.getData().getUrl();
//                                    userTel.setText(myQrCodeBean.getData().getTel() + "的二维码");
                                    showMyQrcode(base64Url);
                                } else {
                                    if (message != null && !"".equals(message)) {
                                        ToastUtils.showShortToast(message);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        LogUtil.print("网络未连接");
                    }
                });
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    /**
     * 显示我的邀请二维码
     *
     * @param base64Url
     */
    private void showMyQrcode(String base64Url) {
        if (!"".equals(base64Url) && null != base64Url) {
            myQrCode.setScaleType(ImageView.ScaleType.FIT_XY);
            Bitmap bitmap = BitmapUtil.stringtoBitmap(base64Url);
            myQrCode.setImageBitmap(bitmap);
        } else {
            myQrCode.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(this).load(R.mipmap.inviting_friends_no_data).into(myQrCode);
        }
    }

    private void shareMyInviting(SHARE_MEDIA platform) {
        String url = "";
        if (!"".equals(shareUrl) && null != shareUrl) {
            url = shareUrl;
        } else {
            url = UrlConfig.H5_URL;
        }
        //ShareUtils.shareUrl(this,
        //      url, );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
