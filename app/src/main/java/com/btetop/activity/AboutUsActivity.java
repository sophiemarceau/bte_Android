package com.btetop.activity;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.BuildConfig;
import com.btetop.R;
import com.btetop.base.BaseActivity;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mVersion;
    private TextView mPhone;
    private TextView mNetWork;
    private Dialog mLogoutDialog;

    @Override
    public int intiLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
        mBack = findViewById(R.id.about_fan_hui);
        mBack.setOnClickListener(this);
        mVersion = findViewById(R.id.about_version);
        mVersion.setText("当前版本v" + BuildConfig.VERSION_NAME);
        mNetWork = findViewById(R.id.about_official_netWork);
        mNetWork.setOnClickListener(this);
        mPhone = findViewById(R.id.about_tel);
        mPhone.setOnClickListener(this);
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
            case R.id.about_fan_hui:
                finish();
                break;
            case R.id.about_official_netWork:

                break;

        }
    }

}
