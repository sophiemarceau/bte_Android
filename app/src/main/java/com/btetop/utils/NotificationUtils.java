package com.btetop.utils;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.btetop.R;
import com.btetop.application.BteTopApplication;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/4/27.
 */

public class NotificationUtils {
    private static Dialog mNotificationDialog = null;
    /**
     * 判断是否开启通知权限
     *
     * @param context
     * @return
     */
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";


    @SuppressLint("NewApi")
    private static boolean isNotificationEnabled(Context context) {
        boolean defualt = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            defualt = notifyManager.areNotificationsEnabled();
        } else {
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;
            Class appOpsClass = null;
            /* Context.APP_OPS_MANAGER */
            try {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                defualt = ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
                System.out.println("NotificationUtils----->defualt---->" + defualt);
                return defualt;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return defualt;
    }

    public static void showNotificationDialog(final Context context) {

        boolean open = isNotificationEnabled(context);

        if (!open) {
            View view = View.inflate(BteTopApplication.getInstance(), R.layout.logout_dialog_layout, null);
            initDialogView(context, view);

            mNotificationDialog = new Dialog(context, R.style.MyDialogStyle);
            mNotificationDialog.setContentView(view);
            mNotificationDialog.setCancelable(false);
            mNotificationDialog.setCanceledOnTouchOutside(false);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
            view.setLayoutParams(layoutParams);
            mNotificationDialog.getWindow().setGravity(Gravity.CENTER);
            mNotificationDialog.show();
        }
    }

    private static void initDialogView(final Context context, View view) {
        TextView mTvContent = view.findViewById(R.id.dialog_content);
        TextView mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotificationDialog != null && mNotificationDialog.isShowing()) {
                    mNotificationDialog.dismiss();
                }
            }
        });
        TextView mTvSure = view.findViewById(R.id.tv_sure);
        mTvContent.setText("为了提供更好的服务，建议您打开消息通知");
        mTvSure.setText("去设置");
        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转设置界面

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(intent);
                if (mNotificationDialog != null && mNotificationDialog.isShowing()) {
                    mNotificationDialog.dismiss();
                }
            }
        });
    }
}
