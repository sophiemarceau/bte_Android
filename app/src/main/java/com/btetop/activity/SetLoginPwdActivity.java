package com.btetop.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserInfo;
import com.btetop.bean.UserLoginByMesBean;
import com.btetop.config.Constant;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.DeviceUtils;
import com.btetop.utils.PhoneNumUtils;
import com.btetop.utils.PwdCheckUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.example.zylaoshi.library.utils.LogUtil;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaListener;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

public class SetLoginPwdActivity extends BaseActivity {

    @BindView(R.id.tv_register_yanZhengMa)
    TextView tvRegisterYanZhengMa;
    @BindView(R.id.register_phone_num)
    TextView register_phone_num;
    @BindView(R.id.tv_current_num)
    TextView tvCurrentNum;
    @BindView(R.id.tv_pwd1)
    EditText tvPwd1;
    @BindView(R.id.tv_pwd2)
    EditText tvPwd2;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;
    @BindView(R.id.img_fan_hui)
    ImageView img_fan_hui;
    @BindView(R.id.tran_title_detail)
    TextView tran_title_detail;


    private boolean judge;
    String tel;
    private String mUserPwd;
    private String mYanZhMa;

    //易盾
    private final static String TAG = "Captcha";
    Captcha mCaptcha = null;
    // UI references.
    private Context mContext = null;


    /*验证码SDK,该Demo采用异步获取方式*/
    private UserLoginTask mLoginTask = null;
    //自定义Listener格式如下
    CaptchaListener myCaptchaListener = new CaptchaListener() {

        @Override
        public void onValidate(String result, String validate, String message) {
            //验证结果，valiadte，可以根据返回的三个值进行用户自定义二次验证
            if (validate.length() > 0) {
                toastMsg("验证成功，validate = " + validate);
                //验证成功后发送短信
                sendMyCode(validate);
            } else {
                toastMsg("验证失败：result = " + result + ", validate = " + validate + ", message = " + message);

            }
        }

        @Override
        public void closeWindow() {
            //请求关闭页面
            toastMsg("关闭页面");
        }

        @Override
        public void onError(String errormsg) {
            //出错
            toastMsg("错误信息：" + errormsg);
        }

        @Override
        public void onCancel() {
            toastMsg("取消线程");
            //用户取消加载或者用户取消验证，关闭异步任务，也可根据情况在其他地方添加关闭异步任务接口
            if (mLoginTask != null) {
                if (mLoginTask.getStatus() == AsyncTask.Status.RUNNING) {
                    Log.i(TAG, "stop mLoginTask");
                    mLoginTask.cancel(true);
                }
            }
        }

        @Override
        public void onReady(boolean ret) {
            //该为调试接口，ret为true表示加载Sdk完成
            if (ret) {
                toastMsg("验证码sdk加载成功");
            }
        }

    };

    @Override
    public int intiLayout() {
        return R.layout.activity_set_pwd;
    }

    @Override
    public void initView() {
        tran_title_detail.setText("设置登录密码");
        UserInfo currentUserInfo = UserService.getCurrentUserInfo();
        if (currentUserInfo != null) {
            tel = currentUserInfo.getTel();
            if (TextUtils.isEmpty(tel)) {
                tel = currentUserInfo.getEmail();
                tvCurrentNum.setText("当前绑定的邮箱:" + PhoneNumUtils.EmailHideNum(tel));
            } else {
                tvCurrentNum.setText("当前绑定手机号：" + PhoneNumUtils.phoneHideNum(tel));
            }
        } else {
            tvCurrentNum.setText("");
        }
    }

    @Override
    public void initData() {
        /*
         * v2.0
         * 拖动 a05f036b70ab447b87cc788af9a60974
         * */
        mContext = this;

        //初始化验证码SDK相关参数，设置CaptchaId、Listener最后调用start初始化。
        if (mCaptcha == null) {
            mCaptcha = new Captcha(mContext);
        }
        mCaptcha.setCaptchaId(Constant.YI_DUN_ID);
        mCaptcha.setCaListener(myCaptchaListener);
        //可选:设置验证码语言为英文，默认为中文
        //mCaptcha.setEnglishLanguage();
        //可选：开启debug
        mCaptcha.setDebug(false);
        //可选：设置超时时间
        mCaptcha.setTimeout(10000);

        //设置验证码弹框的坐标位置: 只能设置left，top和宽度，高度为自动计算。默认无须设置为窗口居中。
        //mCaptcha.setPosition(1, 200, 1040, -1);
        //设置弹框时背景页面是否模糊，默认无须设置，默认显示弹框时背景页面模糊，Android默认风格。
        //mCaptcha.setBackgroundDimEnabled(false);
        //设置弹框时点击对话框之外区域是否自动消失，默认为消失。如果设置不自动消失请设置为false。
        //mCaptcha.setCanceledOnTouchOutside(false);
        //登陆操作
    }

    private void toastMsg(String msg) {
//        LogUtils.e(msg);
//        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean statisticsActivity() {
        return true;
    }

    @OnClick({R.id.tv_register_yanZhengMa, R.id.tv_confirm, R.id.img_fan_hui})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_yanZhengMa:
                //必填：初始化 captcha框架
                mCaptcha.start();
                mLoginTask = new UserLoginTask();
                //关闭mLoginTask任务可以放在myCaptchaListener的onCancel接口中处理
                mLoginTask.execute();
                //可直接调用验证函数Validate()，本demo采取在异步任务中调用（见UserLoginTask类中）
                //mCaptcha.Validate();
                break;

            case R.id.tv_confirm:
                submitMyInfo();
                break;

            case R.id.img_fan_hui:
                finish();
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param validate
     */
    private void sendMyCode(String validate) {
        BteTopService.sendCode(tel, validate)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {

                        BaseBean userLoginByCodeBean = baseBean;
                        if (userLoginByCodeBean != null) {
                            String code = userLoginByCodeBean.getCode();
                            String message = userLoginByCodeBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    ToastUtils.showShortToast("验证码已发送");
                                    daoJiShi();
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

                        System.out.println("YZMLoginActivity-->发送验证码---链接服务器失败");
                        LogUtil.print("网络未连接");
                    }
                });
    }

    /**
     * 倒计时60s
     */

    private void daoJiShi() {
        /**
         * 倒计时60秒，一次1秒
         * */
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvRegisterYanZhengMa != null) {
                    tvRegisterYanZhengMa.setClickable(false);
                    tvRegisterYanZhengMa.setEnabled(false);
                    tvRegisterYanZhengMa.setText(millisUntilFinished / 1000 + "s");
                }
            }

            @Override
            public void onFinish() {
                if (tvRegisterYanZhengMa != null) {
                    tvRegisterYanZhengMa.setClickable(true);
                    tvRegisterYanZhengMa.setEnabled(true);
                    tvRegisterYanZhengMa.setText("发送验证码");
                }
            }
        }.start();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserLoginTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //可选：简单验证DeviceId、CaptchaId、Listener值
            return mCaptcha.checkParams();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (success) {
                //必填：开始验证
                mCaptcha.Validate();

            } else {
                toastMsg("验证码SDK参数设置错误,请检查配置");
            }
        }

        @Override
        protected void onCancelled() {
            mLoginTask = null;
        }
    }


    /**
     * 提交信息
     */
    private void submitMyInfo() {

        final String deviceId = DeviceUtils.getUniqueId(this);
        toastMsg("MainActivity-->uuid---" + deviceId);
        mYanZhMa = register_phone_num.getText().toString();
        if (android.text.TextUtils.isEmpty(mYanZhMa)) {
            ToastUtils.showShortToast("验证码收入有误");
            return;
        }

        String pwd1 = tvPwd1.getText().toString();
        String pwd2 = tvPwd2.getText().toString();

        if (pwd1.equals(pwd2)) {
            mUserPwd = pwd1;
        } else {
            ToastUtils.showShortToast("两次设置的密码不一致");
        }

        boolean isRight = PwdCheckUtil.isLetterDigit(pwd2);
        if (isRight == false) {
            ToastUtils.showShortToast("密码格式有误,请重新输入");
            return;
        }

        BteTopService.getInstallPassword(tel, mYanZhMa, mUserPwd, deviceId)
                .compose(RxUtil.<BaseBean<UserLoginByMesBean.LoginData>>mainAsync())
                .subscribe(new Action1<BaseBean<UserLoginByMesBean.LoginData>>() {
                    @Override
                    public void call(BaseBean<UserLoginByMesBean.LoginData> loginDataBaseBean) {
                        if (loginDataBaseBean != null) {
                            String code = loginDataBaseBean.getCode();
                            String message = loginDataBaseBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    UserService.setCurrentUserToken(loginDataBaseBean.getUserToken());
                                    Log.e("登录成功：", loginDataBaseBean.toString());
                                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_LOGIN_SUCCESS));
                                    ToastUtils.showShortToast(message);
                                    finish();
                                } else {
                                    if (message != null) {
                                        ToastUtils.showShortToast(message);
                                    }
                                }
                            } else {
                                if (message != null) {
                                    ToastUtils.showShortToast(message);
                                }
                            }
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("RegisterActivity-->设置密码---链接服务器失败");
                        ToastUtils.showShortToast("服务器连接超时，请稍后重试");
                    }
                });

    }
}
