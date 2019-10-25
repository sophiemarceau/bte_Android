package com.btetop.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.btetop.R;
import com.btetop.adapter.HXRegistAdapter;
import com.btetop.application.BteTopApplication;
import com.btetop.bean.BaseBean;
import com.btetop.bean.UserHeadImage;
import com.btetop.net.BteTopService;
import com.btetop.service.UserService;
import com.btetop.utils.RxUtil;
import com.btetop.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import rx.functions.Action1;

public class AvatarSettingDialog{


    private Dialog dialog;

    private RecyclerView rvregistheadicon;

    private EditText tvnamevalue;

    private ImageView ivheadicon;
    private ImageView ivconfirm, iv_cancel;
    private List<String> userHeadImages;

    private String userHeadUrl;

    private Context mContext;


    public void show(Context context){
         mContext = context;
         View view = View.inflate(context, R.layout.activity_hx_regist_dialog, null);

         dialog = new Dialog(context, R.style.MyDialogStyle);
         dialog.setContentView(view);
         dialog.setCancelable(false);
         dialog.setCanceledOnTouchOutside(false);
         initViews(view);
         initData();
         dialog.show();

    }

    private void initData() {
        BteTopService.getUserHeadImage()
                .compose(RxUtil.<BaseBean<UserHeadImage>>mainAsync())
                .subscribe(new Action1<BaseBean<UserHeadImage>>() {
                    @Override
                    public void call(BaseBean<UserHeadImage> userHeadImageList) {
                        if (userHeadImageList != null
                                && userHeadImageList.getData().getResult() != null
                                && userHeadImageList.getData().getResult().size() > 0) {
//                            userHeadImages = userHeadImageList.getData().subList(0, 7);
                            userHeadImages = userHeadImageList.getData().getResult().subList(1, 9);
                            initAvatarView(userHeadImages);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
    }


    private void initAvatarView(List<String> avatarList) {
        LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 4);
        rvregistheadicon.setLayoutManager(layoutManager);
        final HXRegistAdapter adapter = new HXRegistAdapter(mContext, avatarList, new HXRegistAdapter.CoinPairListen() {

            @Override
            public void getCoinPairInfo(int position) {
                userHeadUrl = userHeadImages.get(position);
                Glide.with(mContext).load(userHeadUrl).into(ivheadicon);

            }
        });
        rvregistheadicon.setAdapter(adapter);
    }

    private void initViews(View view) {
        ivconfirm = (ImageView) view.findViewById(R.id.iv_confirm);
        ivheadicon = (ImageView) view.findViewById(R.id.iv_head_icon);
        tvnamevalue = (EditText) view.findViewById(R.id.tv_name_value);
        rvregistheadicon = (RecyclerView) view.findViewById(R.id.rv_regist_head_icon);

        iv_cancel = view.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(settingListener!=null) settingListener.onCancel();
            }
        });

        ivconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = tvnamevalue.getText().toString();
                if (TextUtils.isEmpty(nickName)) {
                    ToastUtils.showShortToast("请输入昵称");
                    return;
                }
                UserService.setChatUserName(nickName);
                UserService.setChatUserAvatar(userHeadUrl);
                dialog.dismiss();
                if(settingListener!=null) settingListener.onSuccess();
            }
        });
    }

    private AvatarSettingListener settingListener;

    public AvatarSettingListener getSettingListener() {
        return settingListener;
    }

    public void setSettingListener(AvatarSettingListener settingListener) {
        this.settingListener = settingListener;
    }

    public interface AvatarSettingListener{
        public void onCancel();
        public void onSuccess();
    }
}
