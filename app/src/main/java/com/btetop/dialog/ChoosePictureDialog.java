package com.btetop.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.bean.UserInfo;
import com.btetop.service.UserService;
import com.btetop.utils.PermissionUtil;
import com.btetop.utils.ToastUtils;

/**
 * @Description: 图片选择Dialog
 */


public class ChoosePictureDialog extends Dialog {

    private TextView btnSelectPhoto;
    private TextView btnCamera;
    private TextView btnCancel, bind_wx_auto_get;
    private Listener mListener;

    public ChoosePictureDialog(Context context, Listener l) {
        super(context, R.style.style_dialog);
        setContentView(R.layout.dialog_select_photo_tips);

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mListener = l;

        btnCancel = (TextView) findViewById(R.id.choose_from_cancel);
        bind_wx_auto_get = findViewById(R.id.bind_wx_auto_get);

        UserInfo currentUserInfo = UserService.getCurrentUserInfo();
        if (currentUserInfo != null) {
            String wxBindStatus = currentUserInfo.getWxBindStatus();
            if ("0".equals(wxBindStatus)) {
                bind_wx_auto_get.setVisibility(View.VISIBLE);
            } else {
                bind_wx_auto_get.setVisibility(View.GONE);
            }
        }

        bind_wx_auto_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.chooseWx();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        btnSelectPhoto = (TextView) findViewById(R.id.choose_from_album);

        if (!PermissionUtil.selfPermissionGranted(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !PermissionUtil.selfPermissionGranted(context, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                !PermissionUtil.selfPermissionGranted(context, Manifest.permission.CAMERA)) {
            ToastUtils.showShortToast("请开启相应权限");
        } else {

            btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    mListener.choosePic();//从手机相册选择
                }
            });

            btnCamera = (TextView) findViewById(R.id.choose_from_camera);
            btnCamera.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                    mListener.chooseCamera();  //拍一张
                }
            });
        }

    }

    public interface Listener {
        void choosePic();

        void chooseCamera();

        void chooseWx();
    }

}
