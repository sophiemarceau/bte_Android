package com.btetop.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.CheckNewOldUserPwdBean;
import com.btetop.config.Constant;
import com.btetop.net.BteTopService;
import com.btetop.utils.CheckPhoneUtil;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SoftInputUtil;
import com.btetop.utils.ToastUtils;

import rx.functions.Action1;

/**
 * 密码登录页面
 * by:xulei
 */
public class PwdLoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private EditText mPhoneNum;
    private TextView mLogin, mChangeType;
    private String mPhone;//手机号和邮箱
    private boolean checkPhone;
    private boolean checkEmail;
    private String opneFlag;
    private String intoFlag;
    private TextView title;

    @Override
    public int intiLayout() {
        return R.layout.activity_password;
    }

    @Override
    public void initView() {
        mBack = findViewById(R.id.img_fan_hui);
        mBack.setOnClickListener(this);

        title = findViewById(R.id.title_context);
        title.setText("比特易");

        mPhoneNum = findViewById(R.id.ed_phone_num);
        mPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPhone = s.toString();
                checkLoginCondition();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLogin = findViewById(R.id.tv_login);
        mLogin.setClickable(false);
        mLogin.setEnabled(false);
        mLogin.setOnClickListener(this);
        mChangeType = findViewById(R.id.tv_change_login);
        mChangeType.setOnClickListener(this);
        opneFlag = getIntent().getStringExtra("openFlag");
        intoFlag = getIntent().getStringExtra("intoFlag");
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fan_hui:
                SoftInputUtil.closeKeybord(mPhoneNum, this);
                if (null != intoFlag && !"".equals(intoFlag)) {
                    if ("AccountException".equals(intoFlag)) {
                        Intent intent = new Intent(PwdLoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                finish();
                break;
            case R.id.tv_login:
                SoftInputUtil.closeKeybord(mPhoneNum, this);
                loginMyID();
                break;
            case R.id.tv_change_login:
                Intent intent = new Intent(this, YZMLoginActivity.class);
                intent.putExtra("openFlag", opneFlag);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 手机号码+密码
     * 实现登录
     */
    private void loginMyID() {
        if (mPhone.indexOf("@") != -1) {
            checkEmail = CheckPhoneUtil.isEmail(mPhone);
            if (checkEmail == false) {
                ToastUtils.showShortToast("账号有误,请重新输入");
                return;
            }

        } else {
            if (mPhone.length() != 11) {
                ToastUtils.showShortToast("账号有误,请重新输入");
                return;
            }
            checkPhone = CheckPhoneUtil.isMobile(mPhone);
            if (checkPhone == false) {
                ToastUtils.showShortToast("账号有误,请重新输入");
                return;
            }
        }
        BteTopService.getCheckNewOldUserPassword(mPhone).compose(RxUtil.<BaseBean<CheckNewOldUserPwdBean.CheckNewOldUserPwdData>>mainAsync())
                .subscribe(new Action1<BaseBean<CheckNewOldUserPwdBean.CheckNewOldUserPwdData>>() {
                    @Override
                    public void call(BaseBean<CheckNewOldUserPwdBean.CheckNewOldUserPwdData> baseBean) {
                        boolean result = baseBean.getData().isResult();
                        if (result == true) {//设置过密码，跳转到输入密码页面
                            Intent intent = new Intent(PwdLoginActivity.this, UserLoginByPwdActivity.class);
                            intent.putExtra("openFlag", opneFlag);
                            intent.putExtra("userLoginAccount", mPhone);
                            startActivity(intent);
                        } else if (result == false) {//没有设置过密码，跳转到设置密码页面
                            Intent intent = new Intent(PwdLoginActivity.this, RegisterActivity.class);
                            intent.putExtra("openFlag", opneFlag);
                            intent.putExtra("enterFlag", Constant.REGISTER_ID);
                            intent.putExtra("userLoginAccount", mPhone);
                            startActivity(intent);

                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortToast("链接服务器超时，请稍后重试");
                    }
                });
    }


    /**
     * 检验登录条件
     */
    private void checkLoginCondition() {
        if (!TextUtils.isEmpty(mPhone) && mPhone != null) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (null != intoFlag && !"".equals(intoFlag)) {
                if ("AccountException".equals(intoFlag)) {
                    Intent intent = new Intent(PwdLoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}