package com.btetop.activity;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.SoftInputUtil;
import com.btetop.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.functions.Action1;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private EditText feedContent;
    private TextView contentAmount;
    private ImageView mBack;
    private TextView mSubmit;
    private int maxNum = 240;
    private String token;
    private String content;

    @Override
    public int intiLayout() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void initView() {
        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
        feedContent = findViewById(R.id.feed_content_ed);
        feedContent.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(240)});
        feedContent.addTextChangedListener(new myTextWatcher());
        contentAmount = findViewById(R.id.feed_account);
        mBack = findViewById(R.id.feed_fan_hui);
        mBack.setOnClickListener(this);
        mSubmit = findViewById(R.id.feed_submit);
        mSubmit.setOnClickListener(this);
        mSubmit.setEnabled(false);
        mSubmit.setClickable(false);
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    /**
     * 输入内容控制器
     */
    InputFilter inputFilter = new InputFilter() {
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？^a-zA-Z0-9\\u4E00-\\u9FA5_]");

        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            Matcher matcher = pattern.matcher(charSequence.toString().trim());
            if (matcher.find()) {
                return null;
            } else {
                if (charSequence.equals("")) {
                    return null;
                } else {
                    return "";
                }

            }
        }


    };

    /**
     * 点击时间处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feed_fan_hui:
                SoftInputUtil.closeKeybord(feedContent, this);
                finish();
                break;
            case R.id.feed_submit:
                SoftInputUtil.closeKeybord(feedContent, this);
                submitFeedContent();
                break;
        }
    }

    /**
     * 提交意见反馈
     */
    private void submitFeedContent() {
        token = SPUtils.get("userToken", "");
        content = feedContent.getText().toString();
        if (null == content || "".equals(content)) {
            ToastUtils.showShortToast("请填写反馈意见");
            return;
        }
        if (content.length() < 5) {
            ToastUtils.showShortToast("请输入不少于5个字的反馈描述");
            return;
        }

        BteTopService.getFeedBack(content).compose(RxUtil.<BaseBean>mainAsync())
                .subscribe(new Action1<BaseBean>() {
                    @Override
                    public void call(BaseBean bean) {

                        if (null != bean && !"".equals(bean)) {
                            String code = bean.getCode();
                            String message = bean.getMessage();
                            if (null != code && !"".equals(code)) {
                                if ("0000".equals(code)) {
                                    ToastUtils.showShortToast("提交成功");
                                    finish();
                                } else {
                                    if (null != message && !"".equals(message)) {
                                        ToastUtils.showShortToast(message);
                                    }
                                }
                            } else {
                                if (null != message && !"".equals(message)) {
                                    ToastUtils.showShortToast(message);
                                }
                            }
                        } else {
                            ToastUtils.showShortToast("意见反馈提交失败了");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        ToastUtils.showShortToast("意见反馈提交失败了");
                    }
                });

    }

    class myTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int count = s.toString().length();
            contentAmount.setText(count + "/240");
            if (count == 240) {
                ToastUtils.showShortToast("已到最大输入内容");
            }
            if (count >= 5) {
                mSubmit.setBackground(getResources().getDrawable(R.drawable.bg_button_blue));
                mSubmit.setTextColor(getResources().getColor(R.color.color_white));
                mSubmit.setEnabled(true);
                mSubmit.setClickable(true);
            } else {
                mSubmit.setBackground(getResources().getDrawable(R.drawable.feed_button_gray_bg));
                mSubmit.setTextColor(getResources().getColor(R.color.color_93A0B5));
                mSubmit.setEnabled(false);
                mSubmit.setClickable(false);
            }
        }
    }
}
