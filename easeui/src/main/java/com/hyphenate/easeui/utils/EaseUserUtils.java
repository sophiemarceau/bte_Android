package com.hyphenate.easeui.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.EaseUI.EaseUserProfileProvider;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.exceptions.HyphenateException;

public class EaseUserUtils {

    static EaseUserProfileProvider userProvider;

    static {
        userProvider = EaseUI.getInstance().getUserProfileProvider();
    }

    /**
     * get EaseUser according username
     *
     * @param username
     * @return
     */
    public static EaseUser getUserInfo(String username) {
        if (userProvider != null)
            return userProvider.getUser(username);

        return null;
    }

    /**
     * set user avatar
     * @param username
     */
//    public static void setUserAvatar(Context context, String username, ImageView imageView){
//    	EaseUser user = getUserInfo(username);
//        if(user != null && user.getAvatar() != null){
//            try {
//                int avatarResId = Integer.parseInt(user.getAvatar());
//                Glide.with(context).load(avatarResId).into(imageView);
//            } catch (Exception e) {
//                use default avatar
//                Glide.with(context).load(user.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ease_default_avatar).into(imageView);
//            }
//        }else{
//            Glide.with(context).load(R.drawable.ease_default_avatar).into(imageView);
//        }
//    }

    /**
     * 显示头像
     * set user avatar
     *
     * @param message
     */
    public static void setUserAvatar(Context context, EMMessage message, ImageView imageView) {
        if (message == null) {
            return;
        }

        //发送消息 显示本地头像
        if (message.direct() == EMMessage.Direct.SEND) {
            try {
                String avatar = message.getStringAttribute("avatar");
                Glide.with(context).load(avatar).placeholder(R.drawable.inviting_friends_logo).into(imageView);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return;
        } else {
            EaseUser easeUser = UserProfileCache.GetSpCacheUser(context, message.getFrom());
            //本地已缓存了这个用户信息 直接绑定
            if (easeUser != null) {
                String avatar = easeUser.getAvatar();
                if (avatar != null) {
                    try {
                        Glide.with(context).load(avatar).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                    } catch (Exception e) {
                        //use default avatar
                        Glide.with(context).load(R.drawable.inviting_friends_logo).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                    }
                } else {
                    Glide.with(context).load(R.drawable.inviting_friends_logo).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                }
            }
            //本地没有缓存了这个用户信息 保存本地
            else {
                try {
                    String avatar = message.getStringAttribute("avatar");

                    if (avatar != null) {
                        try {
                            Glide.with(context).load(avatar).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                        } catch (Exception e) {
                            //use default avatar
                            Glide.with(context).load(R.drawable.inviting_friends_logo).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                        }
                    } else {
                        Glide.with(context).load(R.drawable.inviting_friends_logo).placeholder(R.drawable.inviting_friends_logo).into(imageView);
                    }
                    EaseUserUtils.getUserInfo(context, message);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static EaseUser getUserInfo(Context context, EMMessage message) {

        EaseUser user = null;
        try {
            String avatar = message.getStringAttribute("avatar");
            String nick = message.getStringAttribute("username");

            user = new EaseUser(message.getFrom());
            user.setAvatar(avatar);
            user.setNickname(nick);
            EaseCommonUtils.setUserInitialLetter(user);
            UserProfileCache.setSpCacheUser(context, message.getFrom(), user);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(String username, TextView textView) {
        if (textView != null) {
            EaseUser user = getUserInfo(username);
            if (user != null && user.getNick() != null) {
                textView.setText(user.getNick());
            } else {
                textView.setText(username);
            }
        }
    }

    /**
     * set user's nickname
     */
    public static void setUserNick(EMMessage message, TextView textView) {
        if (textView != null) {
            try {
                String nick = message.getStringAttribute("username");
                textView.setText(nick);
            } catch (HyphenateException e) {
                e.printStackTrace();
            }

        }
    }

}
