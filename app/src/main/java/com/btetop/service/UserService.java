package com.btetop.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.bean.MyQrCodeBean;
import com.btetop.bean.UserInfo;
import com.btetop.config.UrlConfig;
import com.btetop.net.BteTopService;
import com.btetop.utils.CheckPermisson;
import com.btetop.utils.RxUtil;
import com.btetop.utils.SPUtils;
import com.btetop.widget.floatprice.FloatPriceService;
import com.example.zylaoshi.library.utils.LogUtil;

import rx.functions.Action1;

import static com.btetop.activity.OverDrawActivity.conn;

public class UserService {

    /**
     * tel : 19913141981
     * email : null
     * name : 19913141981
     * avator : null
     * reset : 1
     * isLzDogUser : 0
     * isBandDogUser : 0
     * isFutureDogUser : 1
     * point : 100
     * inviteCode : 25626136
     */


    private static void setCurrentUserShareUrl(String currentUserShareUrl) {
        UserService.currentUserShareUrl = currentUserShareUrl;
    }

    private static void setCurrentUserbase64Url(String currentUserbase64Url) {
        UserService.currentUserbase64Url = currentUserbase64Url;
    }

    private static void setCurrentUserInviteCode(String currentUserInviteCode) {
        UserService.currentUserInviteCode = currentUserInviteCode;
    }

    private static String currentUserbase64Url = "";
    private static String currentUserMiningbase64Url = "";
    private static String currentUserInviteCode = "";
    private static String currentUserShareUrl = "";
    private static String currentUserTel="";

    public static String getCurrentUserMiningbase64Url() {
        return currentUserMiningbase64Url;
    }

    public static void setCurrentUserMiningbase64Url(String currentUserMiningbase64Url) {
        UserService.currentUserMiningbase64Url = currentUserMiningbase64Url;
    }

    public static String getCurrentUserTel() {
        return currentUserTel;
    }

    public static void setCurrentUserTel(String currentUserTel) {
        UserService.currentUserTel = currentUserTel;
    }

    public static String getCurrentUserShareUrl() {
        return currentUserShareUrl;
    }

    public static String getCurrentUserbase64Url() {
        return currentUserbase64Url;
    }

    public static String getCurrentUserInviteCode() {
        return currentUserInviteCode;
    }


    private static String currentUserToken = "";

    public static String getCurrentUserToken() {
        return currentUserToken;
    }

    //聊天时的昵称
    private static String chatUserName;
    //聊天时的头像
    private static String chatUserAvatar;




    public static String getChatUserName() {
        return chatUserName;
    }

    public static void setChatUserName(String chatUserName) {
        SPUtils.put("chatUserName", chatUserName);
        UserService.chatUserName = chatUserName;
    }

    public static String getChatUserAvatar() {
        return chatUserAvatar;
    }

    public static void setChatUserAvatar(String chatUserAvatar) {
        SPUtils.put("chatUserAvatar", chatUserAvatar);
        UserService.chatUserAvatar = chatUserAvatar;
    }

    public static void setCurrentUserToken(String token) {
        currentUserToken = token;
        SPUtils.put("userToken", token);
        loadUserInfo(token);
    }

    //从远程加载
    public static void loadUserInfo() {
        setCurrentUserToken(SPUtils.get("userToken", ""));

        //从本地读取用户昵称和头像
        setChatUserAvatar(SPUtils.get("chatUserAvatar", ""));
        setChatUserName(SPUtils.get("chatUserName", ""));

        loadUserInfo(getCurrentUserToken());
        loadInviteCode();
    }


    private static void loadUserInfo(String userToken) {
        if (TextUtils.isEmpty(userToken)) {
            setCurrentUserInfo(null);
            setCurrentUserInviteCode("");
            setCurrentUserTel("");
            setCurrentUserbase64Url("");
            setCurrentUserMiningbase64Url("");
            setCurrentUserShareUrl("");
            return;
        }
        try {
            BteTopService.UserInfo().compose(RxUtil.<BaseBean<UserInfo>>mainAsync())
                    .subscribe(new Action1<BaseBean<UserInfo>>() {
                                   @Override
                                   public void call(BaseBean<UserInfo> userInfoBaseBean) {
                                       if (userInfoBaseBean != null) {
                                           String code = userInfoBaseBean.getCode();
                                           if ("0000".equals(code)) {
                                               UserInfo userInfo = userInfoBaseBean.getData();
                                               setCurrentUserInfo(userInfo);
                                               if (userInfo.isOpenBoard()) {
                                                   openOverDrawService();//打开悬浮窗
                                               }
                                           } else {
                                               setCurrentUserToken("");
                                           }
                                       } else {
                                           setCurrentUserToken("");
                                       }
                                   }
                               }, new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {
                                       LogUtil.print(throwable.getMessage());
                                   }
                               }
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void openOverDrawService() {
        if (CheckPermisson.checkDrawOverPermisson()) {

            if (FloatPriceService.isStarted) {
                return;
            }
            FloatPriceService.isStarted = true;
            Intent intent = new Intent(BteTopApplication.getInstance(), FloatPriceService.class);
//        intent.putStringArrayListExtra(AirBoardBean.SerializableTag,mdata);
            BteTopApplication.getInstance().startService(intent);
            BteTopApplication.getInstance().bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }


    private static UserInfo currentUserInfo = null;

    public static UserInfo getCurrentUserInfo() {
        return currentUserInfo;
    }

    public static void setCurrentUserInfo(UserInfo currentUserInfo) {
        UserService.currentUserInfo = currentUserInfo;
    }

    //从远程获取邀请码
    private static void loadInviteCode() {
        try {
            BteTopService.getUserMyQrCode(UrlConfig.MINE_MY_QRCODE_URL).compose(RxUtil.<BaseBean<MyQrCodeBean.MyQrCodeData>>mainAsync())
                    .subscribe(new Action1<BaseBean<MyQrCodeBean.MyQrCodeData>>() {
                        @Override
                        public void call(BaseBean<MyQrCodeBean.MyQrCodeData> myQrCodeBean) {
                            if (myQrCodeBean != null) {
                                String code = myQrCodeBean.getCode();
                                String message = myQrCodeBean.getMessage();
                                if (code != null && !"".equals(code)) {
                                    if ("0000".equals(code)) {
                                        currentUserbase64Url = myQrCodeBean.getData().getBase64();
                                        currentUserShareUrl = myQrCodeBean.getData().getUrl();
                                        currentUserInviteCode = myQrCodeBean.getData().getInviteCode();
                                        //获取挖矿的邀请base64 的二维码
                                        loadMiningInviteCode();
                                    }
                                }
                            }
                        }
                    },new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtil.print(throwable.getMessage());
                        }
                    });
        } catch (Exception e) {}
    }

    //从远程获取邀请码
    private static void loadMiningInviteCode() {
        try {
            BteTopService.getUserMyQrCode(UrlConfig.MINING_INDEX_URL+"register").compose(RxUtil.<BaseBean<MyQrCodeBean.MyQrCodeData>>mainAsync())
                    .subscribe(new Action1<BaseBean<MyQrCodeBean.MyQrCodeData>>() {
                        @Override
                        public void call(BaseBean<MyQrCodeBean.MyQrCodeData> myQrCodeBean) {
                            if (myQrCodeBean != null) {
                                String code = myQrCodeBean.getCode();
                                String message = myQrCodeBean.getMessage();
                                if (code != null && !"".equals(code)) {
                                    if ("0000".equals(code)) {
                                        currentUserMiningbase64Url = myQrCodeBean.getData().getBase64();
                                    }
                                }
                            }
                        }
                    },new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtil.print(throwable.getMessage());
                        }
                    });
        } catch (Exception e) {}
    }
}
