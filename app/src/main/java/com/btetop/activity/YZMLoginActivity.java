package com.btetop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserInfo;
import com.btetop.config.Constant;
import com.btetop.config.UrlConfig;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.CheckPhoneUtil;
import com.btetop.utils.DeviceUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SoftInputUtil;
import com.btetop.utils.ToastUtils;
import com.btetop.widget.AddSpaceTextWatcher;
import com.example.zylaoshi.library.utils.LogUtil;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaListener;

import rx.functions.Action1;

/**
 * 手机号验证码登录页面
 * by:xulei
 */
public class YZMLoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private EditText mPhoneNum, mYanMa, ed_tv_invite;
    private TextView mSendMa, mLogin, mChangeLoginType;
    private String mPhone;//手机号
    private String mYanZhMa;//验证码
    private String inviteCode = "";
    private boolean judge;
    private LinearLayout mAgreement;
    private String fromSource;
    private String gotoTarget;
    private AddSpaceTextWatcher[] addSpaceTextWatchers = new AddSpaceTextWatcher[1];
    private TextView title, tv_change_login;

    private CountDownTimer timer;
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

    private void toastMsg(String msg) {
//        LogUtils.e(msg);
//        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public int intiLayout() {
        return R.layout.activity_yzmlogin;
    }

    @Override
    public void initView() {
        mBack = findViewById(R.id.img_fan_hui);
        mBack.setOnClickListener(this);

        title = findViewById(R.id.title_context);
        title.setText("比特易");

        mSendMa = findViewById(R.id.tv_yanZhengMa);
        mSendMa.setClickable(false);
        mSendMa.setEnabled(false);
        mSendMa.setOnClickListener(this);

        mLogin = findViewById(R.id.tv_login);
        mLogin.setClickable(false);
        mLogin.setEnabled(false);
        mLogin.setOnClickListener(this);
        tv_change_login = findViewById(R.id.tv_change_login);
        tv_change_login.setOnClickListener(this);


        mPhoneNum = findViewById(R.id.ed_phone_num);
        ed_tv_invite = findViewById(R.id.ed_tv_invite);
        addSpaceTextWatchers[0] = new AddSpaceTextWatcher(mPhoneNum, 13);//手机号
        addSpaceTextWatchers[0].setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        mPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhone = addSpaceTextWatchers[0].getTextNotSpace().trim();
                if (mPhone.length() == 11) {
                    mSendMa.setBackgroundResource(R.drawable.rectangle_blue);
                    mSendMa.setTextColor(getResources().getColor(R.color.color_308CDD));
                    mSendMa.setClickable(true);
                    mSendMa.setEnabled(true);

                } else {
                    mSendMa.setClickable(false);
                    mSendMa.setEnabled(false);
                    mSendMa.setBackgroundResource(R.drawable.rectangle);
                    mSendMa.setTextColor(getResources().getColor(R.color.color_626A75_50));
                }
                checkLoginCondition();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mYanMa = findViewById(R.id.ed_phone_yaMa);
        mYanMa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mYanZhMa = s.toString();
                checkLoginCondition();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_tv_invite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inviteCode = s.toString();
                checkLoginCondition();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAgreement = findViewById(R.id.ll_agreement);
        mAgreement.setOnClickListener(this);
        gotoTarget = getIntent().getStringExtra("gotoTarget");
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
     * 点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fan_hui:
                SoftInputUtil.closeKeybord(mPhoneNum, this);
                finish();
                break;
            case R.id.tv_yanZhengMa:

                //必填：初始化 captcha框架
                mCaptcha.start();
                mLoginTask = new UserLoginTask();
                //关闭mLoginTask任务可以放在myCaptchaListener的onCancel接口中处理
                mLoginTask.execute();
                //可直接调用验证函数Validate()，本demo采取在异步任务中调用（见UserLoginTask类中）
                //mCaptcha.Validate();


                break;
            case R.id.tv_login:
                loginMyID();
                break;
            case R.id.ll_agreement:
                Intent intent = new Intent(this, CommonWebViewActivity.class);
                intent.putExtra("url", UrlConfig.AGREEMENT_URL);
                startActivity(intent);
                break;
            case R.id.tv_change_login:
                startActivity(new Intent(this, PwdLoginActivity.class));
                finish();
                break;
        }

    }



    /**
     * 发送验证码
     * @param validate
     */
    private void sendMyCode(String validate) {
        judge = CheckPhoneUtil.isMobile(mPhone);
        if (judge == false) {
            ToastUtils.showShortToast("手机号有误,请重新输入");
        } else {

            BteTopService.sendCode(mPhone,validate)
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
    }

    /**
     * 检验登录条件
     */
    private void checkLoginCondition() {
        if ((!TextUtils.isEmpty(mPhone) && mPhone != null && mPhone.length() == 11)
                && (!TextUtils.isEmpty(mYanZhMa) && mYanZhMa != null)) {
            mLogin.setClickable(true);
            mLogin.setEnabled(true);
            mLogin.setBackgroundResource(R.drawable.rectangle_full_blue);
            mLogin.setTextColor(getResources().getColor(R.color.color_white));
        } else {
            mLogin.setClickable(false);
            mLogin.setEnabled(false);
            mLogin.setBackgroundResource(R.drawable.rectangle_full);
            mLogin.setTextColor(getResources().getColor(R.color.color_626A75_50));
        }

    }


    /**
     * 手机号+验证码
     * 登录实现
     */
    private void loginMyID() {
        judge = CheckPhoneUtil.isMobile(mPhone);
        if (judge == false) {
            ToastUtils.showShortToast("手机号有误,请重新输入");
            return;
        }
        final String deviceId = DeviceUtils.getUniqueId(this);
        System.out.println("MainActivity-->uuid---" + deviceId);

        BteTopService.loginByCode(mPhone, mYanZhMa, deviceId, inviteCode)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<UserInfo>>() {
                    @Override
                    public void call(BaseBean<UserInfo> loginDataBaseBean) {

                        BaseBean<UserInfo> userLoginByMesBean = loginDataBaseBean;
                        if (userLoginByMesBean != null) {
                            String code = userLoginByMesBean.getCode();
                            String message = userLoginByMesBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {

                                    UserService.setCurrentUserToken(userLoginByMesBean.getUserToken());
                                    UserService.loadUserInfo();

                                    ToastUtils.showShortToast("登录成功");
                                    //跳转至相应页面
                                    if (!TextUtils.isEmpty(gotoTarget)) gotoTarget = RouteMessage.MESSAGE_SHOW_HOME_PAGE;
                                    EventBusUtils.sendEvent(new RouteMessage(gotoTarget));
                                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_LOGIN_SUCCESS));
                                    SoftInputUtil.closeKeybord(mPhoneNum,YZMLoginActivity.this);
                                    SoftInputUtil.closeKeybord(mYanMa,YZMLoginActivity.this);
                                    finish();
                                } else {
                                    if (message != null) {
                                        ToastUtils.showShortToast(message);
                                    }
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        System.out.println("YZMLoginActivity-->验证码登录---链接服务器失败");
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null) timer.cancel();
    }

    /**
     * 倒计时60s
     */

    private void daoJiShi() {
        /**
         * 倒计时60秒，一次1秒
         * */
        if(timer!=null) timer.cancel();

            timer = new CountDownTimer(60 * 1000, 1000) {
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
                this.cancel();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        UserLoginTask() {}

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
