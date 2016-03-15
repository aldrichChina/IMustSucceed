/**   
 * @title: Installation.java 
 * @package: com.founder.medical.dao 
 * @description: TODO
 * @author x.yan  
 * @date 2015年4月13日 上午11:50:02 
 * @version 1.0.0 
 */
package net.micode.notes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * @description: 获取设备的编号
 * @author: x.yan
 * @date: 2015年4月13日 上午11:50:02
 * @version: V1.0.0
 */
public class DeviceInfoUtil {
	
	private static final String TAG = "DeviceInfoUtil";
	
	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	public synchronized static String id(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists())
					writeInstallationFile(installation);
				sID = readInstallationFile(installation);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return sID;
	}

	private static String readInstallationFile(File installation)
			throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeInstallationFile(File installation)
			throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();

	}

	public synchronized static String getid(Context context) {
		if (sID == null) {
			sID = getIMEI(context);
			if (sID == null) {
				sID = getMacAddress(context);
				return sID;
			}
		}
		return sID;
	}

	/**
	 * GSM手机的 IMEI 和 CDMA手机的 MEID.
	 */
	private static String getIMEI(Context context) {
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTm.getDeviceId();
		return imei + imei.charAt(0);
	}

	/**
	 * 获取手机MAC地址 只有手机开启wifi才能获取到mac地址
	 */
	private static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}
}
