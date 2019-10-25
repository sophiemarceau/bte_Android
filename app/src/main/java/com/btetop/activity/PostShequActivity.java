package com.btetop.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.dialog.EditorDialog;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;


public class PostShequActivity extends BaseActivity {
    public static final String HINT_TAG="hint_tag";
    @BindView(R.id.edittext)
    EditText editText;


    @Override
    public int intiLayout() {
        return R.layout.activity_post_shequ;
    }

    @Override
    public void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            String string = extras.getString(HINT_TAG);
            editText.setHint(string);
        }

    }

    @Override
    public void initData() {

        showSoftKeyboard();
    }

    @OnClick({R.id.post,R.id.img_fan_hui})
    public void setOnClick(View view){
        switch (view.getId()) {
            case R.id.post:
                addShequPost();
                break;
            case R.id.img_fan_hui:
                String string = editText.getText().toString();
                if (TextUtils.isEmpty(string)) {
                    this.finish();
                }else {
                    EditorDialog dialog=EditorDialog.getEditorDialog();
                    dialog.showDialog(this, new EditorDialog.OnClickListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onExitClick() {
                            PostShequActivity.this.finish();
                        }
                    });
                }
                break;
        }

    }


    @Override
    public boolean statisticsActivity() {
        return false;
    }

    private void addShequPost(){
        String string = editText.getText().toString();
        if (TextUtils.isEmpty(string)) {
            ToastUtils.showShortToast("请填写内容");
            return;
        }

        BteTopService.addShequPost(string)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean baseBean) {
                        ToastUtils.showShortToast("系统审核后将发布在社区");
                        PostShequActivity.this.finish();

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }
}
