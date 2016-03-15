/**   
 * @title: NetWorkInfor.java 
 * @package: com.founder.medical.util 
 * @description: TODO
 * @author x.yan  
 * @date 2015年4月16日 上午10:23:10 
 * @version 1.0.0 
 */
package net.micode.notes.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/** 
 * @description: 获取网络连接相关信息
 * @author: x.yan
 * @date: 2015年4月16日 上午11:06:43 
 * @version: V1.0.0
 */	
public class NetWorkConnect {
	
	 /** 
	 * @description: 判断是否有网络连接
	 * @param context
	 * @return
	 * @author: x.yan
	 * @date: 2015年4月16日 上午11:06:57 
	 */	
	public static boolean isNetworkConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null) {  
	            return mNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	 /** 
	 * @description: 判断WIFI网络是否可用
	 * @param context
	 * @return
	 * @author: x.yan
	 * @date: 2015年4月16日 上午11:02:23 
	 */	
	public static boolean isWifiConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
	        if (mWiFiNetworkInfo != null) {  
	            return mWiFiNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	
	 /** 
	 * @description: 判断MOBILE网络是否可用
	 * @param context
	 * @return
	 * @author: x.yan
	 * @date: 2015年4月16日 上午11:04:54 
	 */	
	public static boolean isMobileConnected(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mMobileNetworkInfo = mConnectivityManager  
	                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
	        if (mMobileNetworkInfo != null) {  
	            return mMobileNetworkInfo.isAvailable();  
	        }  
	    }  
	    return false;  
	}
	
	 /** 
	 * @description: 获取当前网络连接的类型信息
	 * @param context
	 * @return
	 * @author: x.yan
	 * @date: 2015年4月16日 上午11:05:35 
	 */	
	public static int getConnectedType(Context context) {  
	    if (context != null) {  
	        ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
	        if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
	            return mNetworkInfo.getType();  
	        }  
	    }  
	    return -1;  
	}
	
}
