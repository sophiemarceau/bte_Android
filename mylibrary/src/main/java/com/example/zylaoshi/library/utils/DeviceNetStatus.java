package com.example.zylaoshi.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class DeviceNetStatus {
	public static final int NETWORKTYPE_INVALID = 0;
	public static final int NETWORKTYPE_WAP = 1;
	public static final int NETWORKTYPE_2G = 2;
	public static final int NETWORKTYPE_3G = 3;
	public static final int NETWORKTYPE_WIFI = 4;
	public static int mNetWorkType;
	public static String netWorkType;

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

	/**
	 * 检查当前网络是否可用
	 *
	 * @param context
	 * @return
	 */

	public static boolean isNetworkAvailable(Context context)
	{

		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null)
		{
			return false;
		}
		else
		{
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0)
			{
				for (int i = 0; i < networkInfo.length; i++)
				{
					System.out.println(i + "===状态===" + networkInfo[i].getState());
					System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

}
