package com.btetop.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class BTEGroupActivity extends BaseActivity {

    @BindView(R.id.tv_copy)
    TextView tv_copy;
    @BindView(R.id.about_fan_hui)
    ImageView about_fan_hui;

    @Override
    public int intiLayout() {
        return R.layout.activity_bte_group;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    @OnClick({R.id.tv_copy,R.id.about_fan_hui})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText("bte-top");
                ToastUtils.showShortToast("复制成功");
                break;
            case R.id.about_fan_hui:
                finish();
                break;
        }
    }
}
