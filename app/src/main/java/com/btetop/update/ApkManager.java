package com.btetop.update;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;


import com.btetop.application.BteTopApplication;

import java.io.File;

/**
 * APK管理
 * 
 * @author Administrator
 * 
 */
public class ApkManager {
    private static ApkManager instance = null;// apk管理实例
    private String apkRootPath = "";// apk存储的路径
	private Context mContext;


	private ApkManager() {
		mContext = BteTopApplication.getInstance().getApplicationContext();
	}

	public static ApkManager getInstance() {
		if (instance == null) {
			return new ApkManager();
		}
		return instance;
	}

	public String getApkPath() {
		return apkRootPath;
	}

	/**

	/**
	 * apk安装
	 * 
	 * @param context
	 * @param apkName
	 *            apk名称
	 */
	public void installApk(Context context, String apkName) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(
				Uri.fromFile(new File(apkRootPath + File.separator + apkName)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}


	public void installApk_2(Context context, String absApkPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		try {
			intent.putExtra(
					Intent.EXTRA_UID,
					context.getPackageManager().getPackageInfo(
							context.getPackageName(),
							PackageManager.GET_SIGNATURES).applicationInfo.uid);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + absApkPath),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 通过隐式意图调用系统安装程序安装APK
	 */
	public static void installApk_3(Context context, File apkFile) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// 由于没有在Activity环境下启动Activity,设置下面的标签
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
			//参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
			Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", apkFile);
			//添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
			intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
		}else{
			intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
		}
		context.startActivity(intent);
	}


	// 删除指定的包
	public void deletApk(String apkName) {
		String apkPath = apkRootPath + File.separator + apkName;
		File file = new File(apkPath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**

	 */
//	public boolean compareApkVersion(AppInfoEntity apk1, AppInfoEntity apk2) {
//
//		boolean res = false;
//		if (apk1 != null && apk2 != null && apk1.getVersionName() != null
//				&& apk2.getVersionName() != null) {
//			String ratio = "00000000";
//			int ratioLen = ratio.length();
//			try {
//				String oldVersion = apk2.getVersionName().replaceAll("\\.", "");
//				String newVersion = apk1.getVersionName().replaceAll("\\.", "");
//				if (newVersion.length() <= ratioLen) {
//					newVersion = newVersion
//							+ ratio.substring(newVersion.length());
//				} else {
//					newVersion = newVersion.substring(0, ratioLen);
//				}
//
//				if (oldVersion.length() <= ratioLen) {
//					oldVersion = oldVersion
//							+ ratio.substring(oldVersion.length());
//				} else {
//					oldVersion = oldVersion.substring(0, ratioLen);
//				}
//				long oldVerNumber = Integer.valueOf(oldVersion);
//				long newVerNumber = Integer.valueOf(newVersion);
//				if (newVerNumber > oldVerNumber) {
//					res = true;
//				}
//			} catch (Exception e) {
//			}
//		}
//		return res;
//	}

}