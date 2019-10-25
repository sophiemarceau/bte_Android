package com.btetop.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.adapter.InvitingFriendsAdapter;
import com.btetop.base.BaseActivity;
import com.btetop.bean.BaseBean;
import com.btetop.bean.InviteFriendsBean.InviteFriendsData;
import com.btetop.net.BteTopService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.utils.ToastUtils;
import com.example.zylaoshi.library.utils.LogUtil;

import java.util.ArrayList;

import rx.functions.Action1;

public class InvitingFriendsResultActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private LinearLayout friendLayout;
    private TextView friendCount;
    private ListView friendListView;
    private LinearLayout noFriendLayout;
    private InvitingFriendsAdapter friendsAdapter;
    private String token;
    private ArrayList<InviteFriendsData> friendsList;


    @Override
    public int intiLayout() {
        return R.layout.activity_inviting_friends_result;
    }

    @Override
    public void initView() {

        mImmersionBar.statusBarColor(R.color.base_color).statusBarDarkFont(true).init();
                mBack = findViewById(R.id.invitingFriends_back);
        mBack.setOnClickListener(this);
        friendLayout = findViewById(R.id.friends_layout);
        friendCount = findViewById(R.id.friends_count);
        friendListView = findViewById(R.id.friends_listView);
        noFriendLayout = findViewById(R.id.result_no_data_layout);
    }

    @Override
    public void initData() {

        friendsList = new ArrayList<>();
        token = SPUtils.get("userToken", "");
        BteTopService.inviteList().compose(RxUtil.<BaseBean<ArrayList<InviteFriendsData>>>mainAsync())
                .subscribe(new Action1<BaseBean<ArrayList<InviteFriendsData>>>() {
                    @Override
                    public void call(BaseBean<ArrayList<InviteFriendsData>> inviteFriendsBean) {

                        if (null != inviteFriendsBean) {
                            String code = inviteFriendsBean.getCode();
                            String message = inviteFriendsBean.getMessage();
                            if (code != null && !"".equals(code)) {
                                friendsList = inviteFriendsBean.getData();
                                showInvitingFriends(friendsList);
                            } else {
                                if (message != null && !"".equals(message)) {
                                    ToastUtils.showShortToast(message);
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                        showInvitingFriends(friendsList);
                        LogUtil.print("网络未连接");
                    }
                });
    }

    @Override
    public boolean statisticsActivity() {
        return true;
    }

    /**
     * 显示我邀请朋友的列表
     *
     * @param friendsList
     */
    private void showInvitingFriends(ArrayList<InviteFriendsData> friendsList) {
        if (null != friendsList) {
            int size = friendsList.size();
            if (size == 0) {
                friendLayout.setVisibility(View.GONE);
                noFriendLayout.setVisibility(View.VISIBLE);
            } else {
                noFriendLayout.setVisibility(View.GONE);
                friendLayout.setVisibility(View.VISIBLE);
                friendCount.setText(size + "");
                friendsAdapter = new InvitingFriendsAdapter(this, friendsList);
                friendListView.setAdapter(friendsAdapter);
            }
        } else {
            friendLayout.setVisibility(View.GONE);
            noFriendLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击事件操作
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invitingFriends_back:
                finish();
                break;
        }
    }
}
