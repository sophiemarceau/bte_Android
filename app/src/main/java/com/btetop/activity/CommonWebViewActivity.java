package com.btetop.activity;

import android.view.KeyEvent;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.fragment.CommonWebViewFragment;

public class CommonWebViewActivity extends BaseActivity {
    private CommonWebViewFragment webViewFragment;

    @Override
    public int intiLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView() {
        String url = getIntent().getStringExtra("url");
        if (getIntent().getBooleanExtra("showShare",true)) {
            webViewFragment = CommonWebViewFragment.newInstance(url, "commonWebViewFragment");
        }else {
            webViewFragment = CommonWebViewFragment.newInstance(url, "commonWebViewFragment",true,true,false,true);
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, webViewFragment)
                .commit();
    }

    @Override
    public void initData() {
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            webViewFragment.goBack();
        }
        return true;
    }
}
