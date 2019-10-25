package com.btetop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserLoginByMesBean;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.DeviceUtils;
import com.btetop.utils.PhoneNumUtils;
import com.btetop.utils.PwdCheckUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaListener;

import rx.functions.Action1;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView title;
    private String opneFlag;
    private String enterFlag;
    private EditText mYanMaEt, mPwdEt;
    private TextView mSendMa, mBtnLogin;
    private String mUserYanMa;
    private String mUserPwd;
    private ImageView pwdEyes;
    private boolean mbDisplayFlg = false;
    private TextView userAccount;
    private String userAccountNum;
    private LinearLayout mAgreement;
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
                sendYanCode(validate);
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

    private void toastMsg(String msg) {
//        LogUtils.e(msg);
//        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
        opneFlag = getIntent().getStringExtra("openFlag");
        enterFlag = getIntent().getStringExtra("enterFlag");
        userAccountNum = getIntent().getStringExtra("userLoginAccount");

        userAccount = findViewById(R.id.user_account);
        if (userAccountNum.indexOf("@") != -1) {
            userAccount.setText(PhoneNumUtils.EmailHideNum(userAccountNum));
        } else {
            userAccount.setText(PhoneNumUtils.phoneHideNum(userAccountNum));
        }

        mBack = findViewById(R.id.img_fan_hui);
        mBack.setOnClickListener(this);

        title = findViewById(R.id.title_context);
        pwdEyes = findViewById(R.id.pwd_eyes);
        pwdEyes.setOnClickListener(this);

        mYanMaEt = findViewById(R.id.register_phone_num);
        mYanMaEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserYanMa = s.toString();
                checkLoginCondition();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPwdEt = findViewById(R.id.register_phone_yaMa);
        mPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUserPwd = s.toString();
                checkLoginCondition();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSendMa = findViewById(R.id.tv_register_yanZhengMa);
        if (userAccountNum.length() == 0) {
            mSendMa.setClickable(false);
            mSendMa.setEnabled(false);
            mSendMa.setBackgroundResource(R.drawable.rectangle);
            mSendMa.setTextColor(getResources().getColor(R.color.color_626A75_50));
        } else {
            mSendMa.setBackgroundResource(R.drawable.rectangle_blue);
            mSendMa.setTextColor(getResources().getColor(R.color.color_308CDD));
            mSendMa.setClickable(true);
            mSendMa.setEnabled(true);
        }
        mSendMa.setOnClickListener(this);
        mBtnLogin = findViewById(R.id.tv_register_login);
        mBtnLogin.setClickable(false);
        mBtnLogin.setEnabled(false);
        mBtnLogin.setOnClickListener(this);
        mBtnLogin.setText("确定");

        mAgreement = findViewById(R.id.register_ll_agreement);
        mAgreement.setOnClickListener(this);

        if (Constant.FORGET_PASSWORD.equals(enterFlag)) {
            title.setText("设置密码");
            mAgreement.setVisibility(View.GONE);
        } else if (Constant.REGISTER_ID.equals(enterFlag)) {
            title.setText("设置密码");
            mAgreement.setVisibility(View.VISIBLE);
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
            case R.id.img_fan_hui:
                finish();
                break;
            case R.id.tv_register_yanZhengMa:
                //必填：初始化 captcha框架
                mCaptcha.start();
                mLoginTask = new UserLoginTask();
                //关闭mLoginTask任务可以放在myCaptchaListener的onCancel接口中处理
                mLoginTask.execute();
                //可直接调用验证函数Validate()，本demo采取在异步任务中调用（见UserLoginTask类中）
                //mCaptcha.Validate();

                break;
            case R.id.tv_register_login:
                submitMyInfo();
                break;
            case R.id.pwd_eyes:
                if (!mbDisplayFlg) {
                    mPwdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Glide.with(this).load(R.mipmap.eyes_open_login).into(pwdEyes);
                } else {
                    mPwdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Glide.with(this).load(R.mipmap.eyes_closed_login).into(pwdEyes);
                }
                mbDisplayFlg = !mbDisplayFlg;
                break;
            case R.id.register_ll_agreement:

                Intent intent = new Intent(this, CommonWebViewActivity.class);
                intent.putExtra("url", UrlConfig.AGREEMENT_URL);
                startActivity(intent);
                break;
        }
    }

    /**
     * 提交信息
     */
    private void submitMyInfo() {
        boolean isRight = PwdCheckUtil.isLetterDigit(mUserPwd);
        if (isRight == false) {
            ToastUtils.showShortToast("密码格式有误,请重新输入");
            return;
        }
        final String deviceId = DeviceUtils.getUniqueId(this);
        System.out.println("MainActivity-->uuid---" + deviceId);
        BteTopService.getInstallPassword(userAccountNum, mUserYanMa, mUserPwd, deviceId)
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

    /**
     * 发送验证码
     * @param validate
     */
    private void sendYanCode(String validate) {
        BteTopService.sendCode(userAccountNum, validate).compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        BaseBean userLoginByCodeBean = baseBean;
                        if (userLoginByCodeBean != null) {
                            String code = userLoginByCodeBean.getCode();
                            String message = userLoginByCodeBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    daoJiShi();
                                    ToastUtils.showShortToast("验证码已发送");
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
                        System.out.println("RegisterActivity-->发送验证码---链接服务器失败");
                        ToastUtils.showShortToast("服务器连接超时，请稍后重试");
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
                mSendMa.setClickable(false);
                mSendMa.setEnabled(false);
                mSendMa.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                mSendMa.setClickable(true);
                mSendMa.setEnabled(true);
                mSendMa.setText("发送验证码");
            }
        }.start();
    }

    /**
     * 检验登录条件
     */
    private void checkLoginCondition() {
        if ((!TextUtils.isEmpty(mUserYanMa) && mUserYanMa != null)
                && (!TextUtils.isEmpty(mUserPwd) && mUserPwd != null)) {
            mBtnLogin.setClickable(true);
            mBtnLogin.setEnabled(true);
            mBtnLogin.setBackgroundResource(R.drawable.rectangle_full_blue);
            mBtnLogin.setTextColor(getResources().getColor(R.color.color_white));
        } else {
            mBtnLogin.setClickable(false);
            mBtnLogin.setEnabled(false);
            mBtnLogin.setBackgroundResource(R.drawable.rectangle_full);
            mBtnLogin.setTextColor(getResources().getColor(R.color.color_626A75_50));
        }

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

}
