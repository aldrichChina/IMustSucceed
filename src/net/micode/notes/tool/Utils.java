package net.micode.notes.tool;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import net.micode.notes.R;

import org.apache.http.message.BasicNameValuePair;

import android.Manifest;
import android.R.anim;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

public class Utils {
	private static Toast mToast;
	private static final String TAG = "Utils";

	public static void ToastMessage(Context context, String strMessage) {
		Toast toast = Toast.makeText(context, strMessage, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void Toast(Context context, String txt) {
		if (mToast == null) {
			mToast = Toast.makeText(context, txt, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(txt);
			mToast.setDuration(Toast.LENGTH_LONG);
		}
		mToast.show();
	}

	public static void cancelToast() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	/**
	 * 关闭 Activity
	 * 
	 * @param activity
	 */
	public static void finish(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
	}

	/**
	 * 打开Activity
	 * 
	 * @param activity
	 * @param cls
	 * @param name
	 */
	public static void start_Activity(Activity activity, Class<?> cls,
			BasicNameValuePair... name) {
		Intent intent = new Intent();
		intent.setClass(activity, cls);
		if (name != null)
			for (int i = 0; i < name.length; i++) {
				intent.putExtra(name[i].getName(), name[i].getValue());
			}
		activity.startActivity(intent);
		activity.overridePendingTransition(R.animator.fragment_slide_left_enter,
				R.animator.fragment_slide_left_exit);

	}

	/**
	 * 判断是否有网络
	 */
	public static boolean isNetworkAvailable(Context context) {
		if (context.checkCallingOrSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
			return false;
		} else {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connectivity == null) {
				Log.w("Utility", "couldn't get connectivity manager");
			} else {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) {
					for (int i = 0; i < info.length; i++) {
						if (info[i].isAvailable()) {
							Log.d("Utility", "network is available");
							return true;
						}
					}
				}
			}
		}
		Log.d("Utility", "network is not available");
		return false;
	}

	/**
	 * 发送文字通知
	 * 
	 * @param context
	 * @param Msg
	 * @param Title
	 * @param content
	 * @param i
	 */
	@SuppressWarnings("deprecation")
	public static void sendText(Context context, String Msg, String Title,
			String content, Intent i) {
		NotificationManager mn = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon_app, Msg,
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, Title, content, contentIntent);
		mn.notify(0, notification);
	}

	/**
	 * 移除SharedPreference
	 * 
	 * @param context
	 * @param key
	 */
	public static final void RemoveValue(Context context, String key) {
		Editor editor = getSharedPreference(context).edit();
		editor.remove(key);
		boolean result = editor.commit();
		if (!result) {
			Log.e("移除Shared", "save " + key + " failed");
		}
	}

	private static final SharedPreferences getSharedPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 获取SharedPreference 值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static final String getValue(Context context, String key) {
		return getSharedPreference(context).getString(key, "");
	}

	public static final Boolean getBooleanValue(Context context, String key) {
		return getSharedPreference(context).getBoolean(key, false);
	}

	public static final void putBooleanValue(Context context, String key,
			boolean bl) {
		Editor edit = getSharedPreference(context).edit();
		edit.putBoolean(key, bl);
		edit.commit();
	}

	public static final int getIntValue(Context context, String key) {
		return getSharedPreference(context).getInt(key, 0);
	}

	public static final long getLongValue(Context context, String key,
			long default_data) {
		return getSharedPreference(context).getLong(key, default_data);
	}

	public static final boolean putLongValue(Context context, String key,
			Long value) {
		Editor editor = getSharedPreference(context).edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static final Boolean hasValue(Context context, String key) {
		return getSharedPreference(context).contains(key);
	}

	/**
	 * 设置SharedPreference 值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final boolean putValue(Context context, String key,
			String value) {
		value = value == null ? "" : value;
		Editor editor = getSharedPreference(context).edit();
		editor.putString(key, value);
		boolean result = editor.commit();
		if (!result) {
			return false;
		}
		return true;
	}

	/**
	 * 设置SharedPreference 值
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final boolean putIntValue(Context context, String key,
			int value) {
		Editor editor = getSharedPreference(context).edit();
		editor.putInt(key, value);
		boolean result = editor.commit();
		if (!result) {
			return false;
		}
		return true;
	}

	public static Date stringToDate(String str) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date = null;
		try {
			// Fri Feb 24 00:00:00 CST 2012
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d(TAG, "e" + e);
		}
		return date;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	/**
	 * 验证手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 验证是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
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
			Log.d(TAG, "e" + e);
			return "";
		}
	}

	private static float sDensity = 0;

	/**
	 * DP转换为像素
	 * 
	 * @param context
	 * @param nDip
	 * @return
	 */
	public static int dipToPixel(Context context, int nDip) {
		if (sDensity == 0) {
			final WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			sDensity = dm.density;
		}
		return (int) (sDensity * nDip);
	}

	/**
	 * TODO 加密字符串
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 * @author yuanlz
	 * @date 2015-12-22
	 */
	public static String AESEncode(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			while (content.getBytes("utf-8").length % 16 != 0) {
				content += ' ';
			}
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return Base64.encodeToString(result, Base64.DEFAULT);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * TODO 解密字符串
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @author yuanlz
	 * @date 2015-12-22
	 */
	public static String AESDecode(String content, String password) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(Base64.decode(content.getBytes(),
					Base64.DEFAULT));
			return new String(result).trim();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

			Log.d(TAG, "e" + e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			Log.d(TAG, "e" + e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			Log.d(TAG, "e" + e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			Log.d(TAG, "e" + e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			Log.d(TAG, "e" + e);
		}
		return null;
	}

	public static void Logger(Context context, String logTxt) {
		Log.d("jia", context.getClass().getName() + logTxt);
	}
}
