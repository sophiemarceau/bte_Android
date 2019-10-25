package com.btetop.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.base.EventBus.EventBusUtils;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserInfo;
import com.btetop.config.Constant;
import com.btetop.message.RouteMessage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.DeviceUtils;
import com.btetop.utils.PhoneNumUtils;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.example.zylaoshi.library.utils.LogUtil;

import rx.functions.Action1;

public class UserLoginByPwdActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mTitle;
    private TextView userAccount;
    private EditText mPwd;
    private ImageView pwdEyes;
    private TextView mBtnLogin;
    private TextView forgetPwd;
    private String mUserPwd;
    private String userAccountName;
    private boolean mbDisplayFlg = false;
    private String opneFlag;

    @Override
    public int intiLayout() {
        return R.layout.activity_user_login_by_pwd;
    }

    @Override
    public void initView() {
        mBack = findViewById(R.id.img_fan_hui);
        mBack.setOnClickListener(this);
        mTitle = findViewById(R.id.title_context);
        mTitle.setText("登录密码");
        userAccount = findViewById(R.id.user_account_login_pwd);
        mPwd = findViewById(R.id.login_pwd_et);
        mPwd.addTextChangedListener(new TextWatcher() {
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
        pwdEyes = findViewById(R.id.login_pwd_eyes);
        pwdEyes.setOnClickListener(this);
        mBtnLogin = findViewById(R.id.tv_login_pwd);
        mBtnLogin.setClickable(false);
        mBtnLogin.setEnabled(false);
        mBtnLogin.setOnClickListener(this);
        forgetPwd = findViewById(R.id.forget_login_pwd);
        forgetPwd.setOnClickListener(this);

        opneFlag = getIntent().getStringExtra("openFlag");
        userAccountName = getIntent().getStringExtra("userLoginAccount");
        if (userAccountName.indexOf("@") != -1) {
            userAccount.setText(PhoneNumUtils.EmailHideNum(userAccountName));

        } else {
            userAccount.setText(PhoneNumUtils.phoneHideNum(userAccountName));
        }
    }

    @Override
    public void initData() {

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
            case R.id.login_pwd_eyes:
                if (!mbDisplayFlg) {
                    mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Glide.with(this).load(R.mipmap.eyes_open_login).into(pwdEyes);
                } else {
                    mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Glide.with(this).load(R.mipmap.eyes_closed_login).into(pwdEyes);
                }
                mbDisplayFlg = !mbDisplayFlg;
                break;
            case R.id.tv_login_pwd:
                loginUserAccountByPwd();
                break;
            case R.id.forget_login_pwd:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("openFlag", opneFlag);
                intent.putExtra("enterFlag", Constant.FORGET_PASSWORD);
                intent.putExtra("userLoginAccount", userAccountName);
                startActivity(intent);
                finish();
                break;

        }
    }

    /**
     * 用户登录
     * 密码形式
     */
    private void loginUserAccountByPwd() {
        final String deviceId = DeviceUtils.getUniqueId(this);
        System.out.println("MainActivity-->uuid---" + deviceId);
        BteTopService.loginByPwd(userAccountName, mUserPwd, deviceId)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<UserInfo>>() {
                    @Override
                    public void call(BaseBean<UserInfo> userLoginByPwdBean) {
                        BaseBean<UserInfo> userLoginByMesBean = userLoginByPwdBean;
                        if (userLoginByMesBean != null) {
                            String code = userLoginByMesBean.getCode();
                            String message = userLoginByMesBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    UserService.setCurrentUserToken(userLoginByMesBean.getUserToken());
                                    ToastUtils.showShortToast("登录成功");
                                    EventBusUtils.sendEvent(new RouteMessage(RouteMessage.MESSAGE_LOGIN_SUCCESS));
                                    startActivity(new Intent(UserLoginByPwdActivity.this, MainActivity.class));
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
                        System.out.println("PwdLoginActivity-->密码登录---链接服务器失败");
                        LogUtil.print("网络未连接");
                    }
                });
    }

    /**
     * 检验登录条件
     */
    private void checkLoginCondition() {
        if (!TextUtils.isEmpty(mUserPwd) && mUserPwd != null) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}