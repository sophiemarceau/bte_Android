package com.btetop.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.btetop.R;
import com.btetop.activity.YZMLoginActivity;
import com.btetop.base.BaseFragment;
import com.btetop.bean.BaseBean;
import com.btetop.bean.GroupUserInfo;
import com.btetop.bean.HXRegisterBean;
import com.btetop.bean.HXUserInfo;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import rx.functions.Action1;

public class ChatContainerFragment extends BaseFragment {
    private static final String TAG = "chatContainer";
    @BindView(R.id.chat_container)
    RelativeLayout chatContainer;


    private static final int MY_PERMISSION_REQUEST_CODE = 10000;


    //是否登录聊天群组
    private boolean isLogin = false;

    //聊天群组名
    private String groupName = "BTC";

    //群组id
    private String groupId;


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    /**
     * 可见
     */

    private boolean isViewCreated = false;

    @Override
    public void onResume() {
        super.onResume();
        if (isViewCreated == true) onVisible();
        isViewCreated = true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            grantPermissions();
            onVisible();


        }

    }

    protected void onVisible() {
        if (!isLogin) {
            BteTopService.getUserWhetherRegister()
                    .compose(bindToLifecycle())
                    .compose(RxUtil.mainAsync())
                    .subscribe(new Action1<BaseBean<HXRegisterBean>>() {
                        @Override
                        public void call(BaseBean<HXRegisterBean> baseBean) {
                            //-1 登录失效或超时,请重新登录
                            if (("-1").equalsIgnoreCase(baseBean.getCode())) {
                                //未登录用户，跳转至登录页面
                                startActivity(new Intent(getContext(), YZMLoginActivity.class));
                                if (getActivity() != null) getActivity().finish();
                            } else {
                                //使用头像，昵称登录聊天室
                                loginChatRoom(groupName, UserService.getChatUserName(), UserService.getChatUserAvatar());
//                                //执行登录动作
//                                if(android.text.TextUtils.isEmpty(UserService.getChatUserName())){
//                                    //如果没有设置过头像，去设置头像
//                                    AvatarSettingDialog dialog = new AvatarSettingDialog();
//                                    dialog.setSettingListener(new AvatarSettingDialog.AvatarSettingListener() {
//                                        @Override
//                                        public void onCancel() {}
//                                        @Override
//                                        public void onSuccess() {
//                                            loginChatRoom(groupName,UserService.getChatUserName(),UserService.getChatUserAvatar());
//                                        }
//                                    });
//
//                                    dialog.show(getContext());
//                                    //Intent intent = new Intent(getContext(), AvatarSettingActivity.class);
//                                    //startActivity(intent);
//                                }
//                                else{
//                                    //使用头像，昵称登录聊天室
//                                    loginChatRoom(groupName,UserService.getChatUserName(),UserService.getChatUserAvatar());
//                                }

                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtils.showShortToast("进入聊天室失败，请您重新尝试");
                        }
                    });
        }//if(isLogin)
        else {
            showChatFragment();
        }
    }

    public int getUnreadMessageCount() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(groupId);
        if (conversation != null) {
            return conversation.getUnreadMsgCount();
        }
        return 0;
    }


    public void getGroupUserCount(Action1<String> callBack) {
        BteTopService.getGroupUserCount(groupName)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<GroupUserInfo>>() {
                    @Override
                    public void call(BaseBean<GroupUserInfo> hxUserInfoBaseBean) {
                        if (hxUserInfoBaseBean != null
                                && hxUserInfoBaseBean.getData().getResult() != null) {
                            String chatUserCount = hxUserInfoBaseBean.getData().getResult();
                            callBack.call(chatUserCount);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }

    private void loginChatRoom(String roomName, String Name, String userHeadUrl) {
        BteTopService.addUserAndRoom(roomName, Name, userHeadUrl)
                .compose(bindToLifecycle())
                .compose(RxUtil.mainAsync())
                .subscribe(new Action1<BaseBean<HXUserInfo>>() {
                    @Override
                    public void call(BaseBean<HXUserInfo> hxUserInfoBaseBean) {
                        if (hxUserInfoBaseBean != null && !("-1").equalsIgnoreCase(hxUserInfoBaseBean.getCode()) && hxUserInfoBaseBean.getData() != null
                                && hxUserInfoBaseBean.getData().getResult() != null) {
                            //得到groupid 成功之后跳转到聊天室

                            final String gid = hxUserInfoBaseBean.getData().getGroupid();
                            String chatUserId = hxUserInfoBaseBean.getData().getResult().getUserId();
                            String password = hxUserInfoBaseBean.getData().getResult().getPassword();

                            EMClient.getInstance().login(chatUserId, password, new EMCallBack() {//回调
                                @Override
                                public void onSuccess() {
                                    EMClient.getInstance().groupManager().loadAllGroups();
                                    EMClient.getInstance().chatManager().loadAllConversations();
                                    isLogin = true;
                                    groupId = gid;
                                    //初始化ChatFragment
                                    chatFramentHandler.sendEmptyMessage(1);

                                }

                                @Override
                                public void onProgress(int progress, String status) {
                                }

                                @Override
                                public void onError(int code, String message) {
                                    ToastUtils.showShortToast("登录聊天服务器失败:" + message);
                                    isLogin = false;
                                }
                            });
                            //设置状态为已登录
                            isLogin = true;

                        }//if
                        else {
                            ToastUtils.showShortToast("登录聊天室失败");

                            isLogin = false;
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void showChatFragment() {
        ChatFragment hxGroupFragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
        args.putString(EaseConstant.EXTRA_USER_ID, groupId);
        hxGroupFragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.chat_container, hxGroupFragment).show(hxGroupFragment).commit();
    }

    private Handler chatFramentHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                showChatFragment();
            }
        }
    };


    public ChatContainerFragment() {
    }

    public static ChatContainerFragment newInstance() {
        ChatContainerFragment fragment = new ChatContainerFragment();
        return fragment;
    }


    protected int attachLayoutId() {
        return R.layout.fragment_chat_container;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initData() {
    }


    @Override
    public boolean statisticsFragment() {
        return true;
    }


    //申请权限
    private void grantPermissions() {
        if (getActivity() == null || getContext() == null) return;
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                }
        );

        if (!isAllGranted) {
            // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    },
                    MY_PERMISSION_REQUEST_CODE
            );
        }

    }

    private boolean checkPermissionAllGranted(String[] permissions) {

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
}
