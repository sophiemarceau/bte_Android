package com.btetop.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;
import com.btetop.base.BaseActivity;
import com.btetop.bean.MiningBean;
import com.btetop.config.UrlConfig;
import com.btetop.service.MiningService;
import com.btetop.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.OnClick;

public class MiningTaskActivity extends BaseActivity {
    @BindView(R.id.ll_mining_task_wechat)
    RelativeLayout llMiningTaskWechat;
    @BindView(R.id.ll_mining_task_gzh)
    RelativeLayout llMiningTaskGzh;
    @BindView(R.id.ll_mining_task_join_group)
    RelativeLayout llMiningTaskJoinGroup;
    @BindView(R.id.ll_mining_task_user_report)
    RelativeLayout llMiningTaskUserReport;
    @BindView(R.id.img_fan_hui)
    ImageView imgFanHui;
    @BindView(R.id.tran_title_detail)
    TextView tranTitleDetail;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.iv_mining_task_wechat)
    ImageView ivMiningTaskWechat;
    @BindView(R.id.tv_mining_task_wechat)
    TextView tvMiningTaskWechat;
    @BindView(R.id.iv_mining_task_gzh)
    ImageView ivMiningTaskGzh;
    @BindView(R.id.tv_mining_task_gzh)
    TextView tvMiningTaskGzh;
    @BindView(R.id.iv_mining_task_join_group)
    ImageView ivMiningTaskJoinGroup;
    @BindView(R.id.tv_mining_task_join_group)
    TextView tvMiningTaskJoinGroup;
    @BindView(R.id.iv_mining_task_user_report)
    ImageView ivMiningTaskUserReport;
    @BindView(R.id.tv_mining_task_user_report)
    TextView tvMiningTaskUserReport;
    private MiningBean.TaskBean taskBean;

    @Override
    public int intiLayout() {
        return R.layout.activity_mining_task;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        tranTitleDetail.setText("固定任务");

        MiningService.loadMiningInfo(new MiningService.loadMiningInfo() {

            @Override
            public void success(MiningBean data) {
                taskBean = data.getTask();
                if (taskBean.getBindWx() == 5) {
                    tvMiningTaskWechat.setText("未完成");
                    tvMiningTaskWechat.setTextColor(getResources().getColor(R.color.color_308CDD));
                } else {
                    tvMiningTaskWechat.setText("已完成");
                    tvMiningTaskWechat.setTextColor(getResources().getColor(R.color.color_626A75_60));
                }

                if (taskBean.getFollowWx() == 5) {
                    tvMiningTaskGzh.setText("未完成");
                    tvMiningTaskGzh.setTextColor(getResources().getColor(R.color.color_308CDD));
                } else {
                    tvMiningTaskGzh.setText("已完成");
                    tvMiningTaskGzh.setTextColor(getResources().getColor(R.color.color_626A75_60));
                }

                if (taskBean.getSign() == 5) {
                    tvMiningTaskJoinGroup.setText("未完成");
                    tvMiningTaskJoinGroup.setTextColor(getResources().getColor(R.color.color_308CDD));
                } else {
                    tvMiningTaskJoinGroup.setText("已完成");
                    tvMiningTaskJoinGroup.setTextColor(getResources().getColor(R.color.color_626A75_60));

                }

                if (taskBean.getFeedback() == 5) {
                    tvMiningTaskUserReport.setText("未完成");
                    tvMiningTaskUserReport.setTextColor(getResources().getColor(R.color.color_308CDD));
                } else {
                    tvMiningTaskUserReport.setText("已完成");
                    tvMiningTaskUserReport.setTextColor(getResources().getColor(R.color.color_626A75_60));
                }
            }

            @Override
            public void open() {

            }

            @Override
            public void noOpen(String inviteCount) {

            }
        });
    }

    @Override
    public boolean statisticsActivity() {
        return false;
    }

    @OnClick({R.id.img_fan_hui,
            R.id.ll_mining_task_wechat,
            R.id.ll_mining_task_gzh,
            R.id.ll_mining_task_join_group,
            R.id.ll_mining_task_user_report})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                finish();
                break;
            case R.id.ll_mining_task_wechat:
                wxLogin();
                break;
            case R.id.ll_mining_task_gzh:
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "OfficialAccounts");
                break;
            case R.id.ll_mining_task_join_group:
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "joinGroup");
                break;
            case R.id.ll_mining_task_user_report:
                startActivityWithUrl(UrlConfig.MINING_INDEX_URL + "userResearch");
                break;
        }
    }

    public void startActivityWithUrl(String url) {
        Intent amount = new Intent(this, CommonWebViewActivity.class);
        amount.putExtra("url", url);
        startActivity(amount);
    }

    public void wxLogin() {
        if (!BteTopApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showShortToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        BteTopApplication.mWxApi.sendReq(req);
    }
}
