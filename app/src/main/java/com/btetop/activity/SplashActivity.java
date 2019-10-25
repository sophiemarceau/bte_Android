package com.btetop.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.service.ArtBoardService;
import com.btetop.utils.SPUtils;
import com.example.zylaoshi.library.utils.Util;

import static com.btetop.config.Constant.FIRST_INSTALL;

public class SplashActivity extends BaseActivity {
    ImageView iv_image;
    @Override
    public int intiLayout() {
        return R.layout.activity_splash_layout;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.color_FCFEFF).statusBarDarkFont(true).init();
    }

    @Override
    public void initData() {
        iv_image = findViewById(R.id.iv_image);

    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    private void startActivityToWeb() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);
    }

    private void startActivityToGuide() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                SplashActivity.this.finish();
            }
        }, 3000);
    }

    @Override
    public void onResume() {
        ArtBoardService.getInstance().getData();
        super.onResume();
      /*  SharedPreferences sharedPreferences = SplashActivity.this.getApplicationContext().getSharedPreferences( SPUtils.FILE_NAME,MODE_PRIVATE);
        boolean b = sharedPreferences.getBoolean(FIRST_INSTALL, false);*/
        boolean b = SPUtils.get(FIRST_INSTALL, false);
        if (!b) {
            startActivityToGuide();
        } else {
            startActivityToWeb();
        }
        SPUtils.put("versionCode", Util.getVersionCode(this));
        SPUtils.put("versionName", Util.getVersionName(this));
    }
}