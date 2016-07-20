package net.micode.notes.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.UUID;




import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class DeviceInfoUtil {
	
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
				if (sID==null) {
					for (int i = 0; i < 4; i++) {
						sID += ""+(1000+new Random().nextInt(9000));
					}
				}
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
		if (imei != null) {
			if (imei.length() < 16 && imei.length() > 0) {
				while (imei.length() < 16) {
					imei += imei;
				}
				return imei.substring(0, 16);
			} else if (imei.length() <= 0) {
				return null;
			} else {
				return imei.substring(0, 16);
			}
		} else {
			return null;
		}
	}

	/**
	 * 获取手机MAC地址 只有手机开启wifi才能获取到mac地址
	 */
	private static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String macAddress = wifiInfo.getMacAddress();
		if (macAddress != null) {
			if (macAddress.length() < 16 && macAddress.length() > 0) {
				while (macAddress.length() < 16) {
					macAddress += macAddress;
				}
				return macAddress.substring(0, 16);
			} else if (macAddress.length() <= 0) {
				return null;
			} else {
				return macAddress.substring(0, 16);
			}
		} else {
			return null;
		}
	}
}
