package com.btetop.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserWalletAdress;
import com.btetop.net.BteTopService;
import com.btetop.utils.BitmapUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.zylaoshi.library.utils.LogUtil;

import rx.functions.Action1;

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private ImageView rechargeBack;
    private ImageView rechargeImgCode;
    private TextView rechargeAddress;
    private TextView copyAddressBtn;
    private String token;
    private String userAddress = "";

    @Override
    public int intiLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
        token = SPUtils.get("userToken", "");
        copyAddressBtn = findViewById(R.id.copy_address);
        copyAddressBtn.setOnClickListener(this);
        rechargeAddress = findViewById(R.id.recharge_address);
        rechargeImgCode = findViewById(R.id.recharge_img_code);
        rechargeBack = findViewById(R.id.recharge_fan_hui);
        rechargeBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        BteTopService.getWalletAddress().compose(RxUtil.<BaseBean<UserWalletAdress.DataBean>>mainAsync())
                .subscribe(new Action1<BaseBean<UserWalletAdress.DataBean>>() {
                    @Override
                    public void call(BaseBean<UserWalletAdress.DataBean> walletAdress) {

                        if (null != walletAdress && !"".equals(walletAdress)) {
                            String code = walletAdress.getCode();
                            String message = walletAdress.getMessage();
                            if (null != code && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    userAddress = walletAdress.getData().getAddress();
                                    if (null != userAddress && !"".equals(userAddress)) {
                                        rechargeAddress.setText(userAddress);
                                    }
                                    String base64 = walletAdress.getData().getBase64();
                                    showMyQrcode(base64);

                                } else {
                                    if (null != message && !"".equals(message)) {
                                        ToastUtils.showShortToast(message);
                                    }
                                    Glide.with(RechargeActivity.this).
                                            load(R.mipmap.inviting_friends_no_data).into(rechargeImgCode);
                                    rechargeAddress.setText("");
                                }
                            } else {
                                Glide.with(RechargeActivity.this).
                                        load(R.mipmap.inviting_friends_no_data).into(rechargeImgCode);
                                rechargeAddress.setText("");
                            }
                        } else {
                            Glide.with(RechargeActivity.this).
                                    load(R.mipmap.inviting_friends_no_data).into(rechargeImgCode);
                            rechargeAddress.setText("");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {


                        Glide.with(RechargeActivity.this).
                                load(R.mipmap.inviting_friends_no_data).into(rechargeImgCode);
                        rechargeAddress.setText("");
                        LogUtil.print("网络未连接");
                    }
                });
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_fan_hui:
                finish();
                break;
            case R.id.copy_address:
                copyMyAddress();
                break;
        }
    }

    /**
     * 显示我的钱包地址二维码
     *
     * @param base64Url
     */
    private void showMyQrcode(String base64Url) {
        if (!"".equals(base64Url) && null != base64Url) {
            rechargeImgCode.setScaleType(ImageView.ScaleType.FIT_XY);
            Bitmap bitmap = BitmapUtil.stringtoBitmap(base64Url);
            rechargeImgCode.setImageBitmap(bitmap);
        } else {
            rechargeImgCode.setScaleType(ImageView.ScaleType.CENTER);
            Glide.with(this).load(R.mipmap.inviting_friends_no_data).into(rechargeImgCode);
        }
    }

    /**
     * 复制钱包地址
     */
    private void copyMyAddress() {
        if (null != userAddress && !"".equals(userAddress)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", userAddress);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);

            copyAddressBtn.setBackground(getResources().getDrawable(R.drawable.recharge_btn_gray));
            copyAddressBtn.setText("已复制");
            copyAddressBtn.setTextColor(getResources().getColor(R.color.color_93A0B5));
        } else {
            ToastUtils.showShortToast("地址复制失败");
        }


    }
}
