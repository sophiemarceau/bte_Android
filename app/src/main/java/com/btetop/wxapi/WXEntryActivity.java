package com.btetop.wxapi;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.dialog.BindWXSuccessDialog;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import rx.functions.Action1;


//public class WXEntryActivity extends WXCallbackActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//}

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
//        LogUtils.e(resp.errStr);
//        LogUtils.e("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) com.btetop.utils.ToastUtils.showShortToast("分享失败");
                else com.btetop.utils.ToastUtils.showShortToast("登录失败");
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
//                        LogUtils.e("code = " + code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        bindWX(code);
                        break;

                    case RETURN_MSG_TYPE_SHARE:
//                        com.btetop.utils.ToastUtils.showShortToast("微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    //该方法执行umeng登陆的回调的处理
    @Override
    public void a(com.umeng.weixin.umengwx.b b) {
//        super.a(b);
    }

    @Override
    protected void a(Intent intent) {
        super.a(intent);
    }

    //在onResume中处理从微信授权通过以后不会自动跳转的问题，手动结束该页面
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

    public void bindWX(String code) {
        BteTopService.bindWx(code)
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        if (baseBean != null && "0000".equals(baseBean.getCode())) {
                            ToastUtils.showShortToast("绑定微信成功");
                            BindWXSuccessDialog.getInstance().showDialog(WXEntryActivity.this, new BindWXSuccessDialog.bindWxSuccess() {
                                @Override
                                public void bindWxSuccess() {
                                    // 从API11开始android推荐使用android.content.ClipboardManager
                                    // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    // 将文本内容放到系统剪贴板里。
                                    cm.setText("bte-top");
                                    ToastUtils.showShortToast("复制成功,搜索关注公众号");
                                    /**
                                     * 跳转到微信
                                     */
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setComponent(cmp);
                                        startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        com.btetop.utils.ToastUtils.showShortToast("检查到您手机没有安装微信，请安装后使用该功能");
                                    }

//                            String appId = Constant.WEIXIN_APP_BTE_ID;//开发者平台ID
//                            IWXAPI api = WXAPIFactory.createWXAPI(PersonSettingActivity.this, appId, false);
//
//                            if (api.isWXAppInstalled()) {
//                                JumpToBizProfile.Req req = new JumpToBizProfile.Req();
//                                req.toUserName = Constant.WEIXIN_APP_ORIGIN_ID; // 公众号原始ID
//                                req.extMsg = "";
//                                req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE; // 普通公众号
//                                api.sendReq(req);
//                            } else {
//                                Toast.makeText(PersonSettingActivity.this, "微信未安装", Toast.LENGTH_SHORT).show();
//                            }
                                    finish();
                                }

                                @Override
                                public void bindCancel() {
                                    finish();
                                }
                            });
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

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //如果没回调onResp，八成是这句没有写
        BteTopApplication.mWxApi.handleIntent(getIntent(), this);
    }
}

