package com.example.zylaoshi.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.zylaoshi.library.config.Contants;
/**
 * @description: 获取设备信息
 * @autour: zylaoshi
 * @date: 2017/12/4 14:30
*/

public class DeviceUtil {
    public static final int NETWORKTYPE_INVALID = 0;
	public static final int NETWORKTYPE_WAP = 1;
	public static final int NETWORKTYPE_2G = 2;
	public static final int NETWORKTYPE_3G = 3;
	public static final int NETWORKTYPE_WIFI = 4;
	public static int mNetWorkType;
	public static String netWorkType;

	private static String VERSION;//软件版本
	private static String MANUFACTURE;//手机生产商
	private static String MODEL;//型号
	private static String CHANNEL_ID;//渠道ID
	private static String SDK_VERSION;//sdk版本
	private static String NETWORKTYPE;//网络状态
	private static String TERMINAL = Contants.APP_NAME;//本APP的标志

	public static String getManufacture() {
		return Build.MANUFACTURER.replace(" ", "");
	}

	public static String getNetWorkType(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			String type = networkInfo.getTypeName();

			if (type.equalsIgnoreCase("WIFI")) {
				mNetWorkType = NETWORKTYPE_WIFI;
			} else if (type.equalsIgnoreCase("MOBILE")) {
				String proxyHost = android.net.Proxy.getDefaultHost();

				mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
						: NETWORKTYPE_2G)
						: NETWORKTYPE_WAP;
			}
		} else {
			mNetWorkType = NETWORKTYPE_INVALID;
		}
		if (mNetWorkType == 1) {
			netWorkType = "WAP";
		} else if (mNetWorkType == 2) {
			netWorkType = "2G";
		} else if (mNetWorkType == 3) {
			netWorkType = "3G";
		} else if (mNetWorkType == 4) {
			netWorkType = "WIFI";
		} else {
			netWorkType = "INVALID";

		}
		return netWorkType;
	}

	private static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			return false; 
		case TelephonyManager.NETWORK_TYPE_CDMA:
			return false; 
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return false; 
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			return true; 
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			return true; 
		case TelephonyManager.NETWORK_TYPE_GPRS:
			return false; 
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			return true;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			return true;
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			return true; 
		case TelephonyManager.NETWORK_TYPE_UMTS:
			return true; 
		case TelephonyManager.NETWORK_TYPE_EHRPD:
			return true; 
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return true; 
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return true; 
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return false;
		case TelephonyManager.NETWORK_TYPE_LTE:
			return true;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			return false;
		default:
			return false;
		}





	}

//	public static String getHeadInfo(Context context) {
//		StringBuilder sb = new StringBuilder();
//		VERSION = getVersion(context);
//		sb.append(VERSION).append("/");
//
//		MANUFACTURE = DeviceUtil.getManufacture();
//		sb.append(MANUFACTURE).append("_");
//
//		MODEL = Build.MODEL.replace(" ", "");
//		sb.append(MODEL).append("_android/");
//
//		CHANNEL_ID = ChannelNameUtil.getChannelName(context);
//		if (!TextUtils.isEmpty(CHANNEL_ID)) {
//			sb.append(CHANNEL_ID).append("/");
//		} else {
//			// 默认渠道zhuanbei
//			CHANNEL_ID = Contants.APP_NAME+"/";
//			sb.append(CHANNEL_ID);
//		}
//		SDK_VERSION = Build.VERSION.RELEASE;
//		sb.append(SDK_VERSION).append("/");
//
//		NETWORKTYPE = DeviceUtil.getNetWorkType(context);
//		sb.append(NETWORKTYPE).append("/").append(TERMINAL);
//
//		return sb.toString();
//	}

	public static String getHeadInfo(Context context) {
		StringBuilder sb = new StringBuilder();
		sb.append(TERMINAL).append("/");
		VERSION = getVersion(context);
		sb.append(VERSION).append(" (").append("Android ");


		SDK_VERSION = android.os.Build.VERSION.RELEASE;
		sb.append(SDK_VERSION).append("; ");

		MANUFACTURE = DeviceNetStatus.getManufacture();
		sb.append(MANUFACTURE).append("_");

		MODEL = android.os.Build.MODEL.replace(" ", "");
		sb.append(MODEL).append("; ");

		CHANNEL_ID = ChannelNameUtil.getChannelName(context);

		if(!TextUtils.isEmpty(CHANNEL_ID)) {
			sb.append(CHANNEL_ID).append("; ");
		} else {
			// 默认渠道wanglibao
			CHANNEL_ID = "goldbox; ";
			sb.append(CHANNEL_ID);
		}
//        LogUtil.e("CHANNEL_ID---->" + CHANNEL_ID);


		NETWORKTYPE = DeviceNetStatus.getNetWorkType(context);
		sb.append(NETWORKTYPE).append(")");

		Log.e("DeviceUtils----sb----" , sb.toString());
		return sb.toString();
	}

	/**
	 * 获取APP版本号
	 *
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}