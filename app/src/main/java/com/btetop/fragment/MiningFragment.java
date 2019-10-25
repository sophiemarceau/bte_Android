package com.btetop.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MiningFragment extends BaseFragment {
    @BindView(R.id.img_fan_hui)
    ImageView imgFanHui;
    @BindView(R.id.tran_title_detail)
    TextView tranTitleDetail;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_mining_rule)
    TextView tvMiningRule;
    @BindView(R.id.tv_mining_middle1)
    TextView tvMiningMiddle1;
    @BindView(R.id.tv_calculate_add)
    TextView tvCalculateAdd;
    @BindView(R.id.tv_fixed_task)
    TextView tvFixedTask;
    @BindView(R.id.ll_mining_1)
    LinearLayout llMining1;
    @BindView(R.id.ll_mining_2)
    LinearLayout llMining2;
    @BindView(R.id.ll_mining_3)
    LinearLayout llMining3;
    @BindView(R.id.ll_mining_4)
    LinearLayout llMining4;
    @BindView(R.id.tv_mining_invite_friend)
    TextView tvMiningInviteFriend;
    @BindView(R.id.tv_mining_keep_login)
    TextView tvMiningKeepLogin;
    @BindView(R.id.tv_mining_day_share)
    TextView tvMiningDayShare;
    @BindView(R.id.tv_mining_read_report)
    TextView tvMiningReadRetport;

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_mining;
    }

    @Override
    protected void initView(View view) {
        tranTitleDetail.setText("全民挖矿");
    }

    @Override
    protected void initData() {
        tvMiningInviteFriend.setText("500");
        tvMiningKeepLogin.setText("500");
        tvMiningDayShare.setText("500");
        tvMiningReadRetport.setText("500");
    }

    @Override
    protected boolean statisticsFragment() {
        return false;
    }


    @OnClick({R.id.img_fan_hui, R.id.ll_mining_1, R.id.ll_mining_2, R.id.ll_mining_3, R.id.ll_mining_4})
    public void setClick(View view) {
        switch (view.getId()) {
            case R.id.img_fan_hui:
                getActivity().finish();
                break;
            //邀请好友
            case R.id.ll_mining_1:
                break;
            //连续签到
            case R.id.ll_mining_2:
                break;
            //每日分享
            case R.id.ll_mining_3:
                break;
            //阅读报告
            case R.id.ll_mining_4:
                break;
        }
    }
}
