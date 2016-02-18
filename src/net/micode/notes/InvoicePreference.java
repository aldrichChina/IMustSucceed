/**   
 * @title: MyPreference.java 
 * @package: com.founder.medical.preference 
 * @description: TODO
 * @author x.yan  
 * @date 2015年3月4日 下午2:46:39 
 * @version 1.0.0 
 */
package net.micode.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @description: 记录用户名，密码之类的首选项
 * @author: x.yan
 * @date: 2015年3月4日 下午2:46:39
 * @version: V1.0.0
 */
public class InvoicePreference {
	private static InvoicePreference preference = null;
	private SharedPreferences sharedPreference;
	private String packageName = "";

	private static final String LOGIN_NAME = "loginName"; // 登录名
	private static final String PASSWORD = "password"; // 密码
	private static final String PATIENT_PASSWORD_MD5 = "patientpasswordMD5"; // MD5用户密码
	private static final String IS_SAVE_PWD = "isSavePwd"; // 是否保留密码
	private static final String IS_FIRST_LOGIN = "isFirstLogin"; // 是否保留密码

	public static synchronized InvoicePreference getInstance(Context context) {
		if (preference == null)
			preference = new InvoicePreference(context);
		return preference;
	}

	public InvoicePreference(Context context) {
		packageName = context.getPackageName() + "_preferences";
		sharedPreference = context.getSharedPreferences(packageName,
				context.MODE_PRIVATE);
	}

	// 用户名
	public String getLoginName() {
		String loginName = sharedPreference.getString(LOGIN_NAME, "");
		return loginName;
	}

	public void SetLoginName(String loginName) {
		Editor editor = sharedPreference.edit();
		editor.putString(LOGIN_NAME, loginName);
		editor.commit();
	}

	// 密码
	public String getPassword() {
		String password = sharedPreference.getString(PASSWORD, "");
		return password;
	}

	public void SetPassword(String password) {
		Editor editor = sharedPreference.edit();
		editor.putString(PASSWORD, password);
		editor.commit();
	}

	// MD5密码
	public String getMD5Password() {
		return sharedPreference.getString(PATIENT_PASSWORD_MD5, "");
	}

	public void SetMD5Password(String MD5password) {
		Editor editor = sharedPreference.edit();
		editor.putString(PATIENT_PASSWORD_MD5, MD5password);
		editor.commit();
	}

	//
	public boolean IsSavePwd() {
		Boolean isSavePwd = sharedPreference.getBoolean(IS_SAVE_PWD, false);
		return isSavePwd;
	}

	public void SetIsSavePwd(Boolean isSave) {
		Editor edit = sharedPreference.edit();
		edit.putBoolean(IS_SAVE_PWD, isSave);
		edit.commit();
	}

	// 是不是首次登陆
	public boolean IsNewLogin() {
		Boolean isSavePwd = sharedPreference.getBoolean(IS_FIRST_LOGIN, true);
		return isSavePwd;
	}

	public void SetNewLogin(Boolean isSave) {
		Editor edit = sharedPreference.edit();
		edit.putBoolean(IS_FIRST_LOGIN, isSave);
		edit.commit();
	}
}
