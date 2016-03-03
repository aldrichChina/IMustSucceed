package net.micode.notes.ui;

import java.util.ArrayList;
import java.util.List;

import net.micode.notes.InvoicePreference;
import net.micode.notes.R;
import net.micode.notes.activity.BaseActivity;
import net.micode.notes.activity.MyActivity;
import net.micode.notes.data.ConstantUtil;
import net.micode.notes.tool.DeviceInfoUtil;
import net.micode.notes.tool.Utils;
import net.micode.notes.tool.HttpUtils.HttpService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "jia";// 日志标记
	private EditText etvUserId;
	private EditText etvPassword;
	private Button loginbtn;
	private boolean ifLogin = false;// 是否点击登录按钮进行的登录
	private boolean netState;// 网络连接状态
	// 异步操作类
	private LoginTask mTask;
	private ProgressDialog dialog;
	private String deviceID;// 手机（平板）唯一识别码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {/*
		switch (v.getId()) {
		case R.id.loginbtn:
			if (judgeInfor()) {
				ifLogin = true;// 是点击登录按钮进行的登录

				// 判断网络是否连接
				netState = NetWorkConnect
						.isNetworkConnected(LoginActivity.this);
				if (netState) {
					loginFirst("0");
				} else {
					Utils.Toast(context,getString(R.string.splashactivity_novailablenetwork));
				}
			}
			break;
		default:
			break;
		}

	*/}

	@Override
	protected void initViews() {
//		etvUserId = (EditText) findViewById(R.id.phone);
//		etvPassword = (EditText) findViewById(R.id.password);
//		loginbtn = (Button) findViewById(R.id.loginbtn);
	}

	@Override
	protected void initEvents() {
		// myDataSource = DataSource.getInstance();
		// 缓存中的信息
		deviceID = DeviceInfoUtil.getid(this);
		ConstantUtil.sessionMap.put("deviceID", deviceID);
	}

	@Override
	protected void setListener() {
		loginbtn.setOnClickListener(this);
	}

	private boolean judgeInfor() {
		String strInfor = "";
		strInfor = etvUserId.getText().toString();
		if (strInfor.length() <= 0) {
			Utils.Toast(this, getString(R.string.login_please_input_username));
			return false;
		}
		strInfor = etvPassword.getText().toString();
		if (strInfor.length() <= 0) {
			Utils.Toast(this, getString(R.string.login_please_input_password));
			return false;
		}
		return true;
	}

	private void loginFirst(String type) {
		String userNameValue = etvUserId.getText().toString();
		String passwordValue = etvPassword.getText().toString();
		
		passwordValue = GetMD5Code(passwordValue);
		mTask = new LoginTask();
		// mTask.SetContent(this);
		mTask.execute("/user/login.html", "name=" + userNameValue, "pwd="
				+ passwordValue, "mobileid=" + deviceID, "cv="
				+ ConstantUtil.sessionMap.get("version"), "force=" + type);
	}

	private class LoginTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute() called");
			dialog = new ProgressDialog(LoginActivity.this, R.style.dialog);
			// 设置进度条风格，风格为圆形，旋转的
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMessage(LoginActivity.this
					.getString(R.string.login_progress_dialog));
			dialog.setIndeterminate(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		// String... params 变长数组
		@Override
		protected Boolean doInBackground(String... params) {
			// if (!super.doInBackground(params)) {
			// return Boolean.FALSE;
			// }
			List<String> list = new ArrayList<String>();
			for (String temp : params) {
				list.add(temp);
			}
			// userService.SetConnect();
			Boolean flag = HttpService.login(list,LoginActivity.this);

			return flag;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				ToastMessage(ConstantUtil.RTMSG);
				dialog.dismiss();
				// //bOver = true;
			} else {
				// 登录成功记住用户名和密码
				savePassword();
				// MobclickAgent.onProfileSignIn(userService.getUserId());
			}
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Log.i(TAG, "onCancelled() called");
		}
	}

	private void savePassword() {

		InvoicePreference.getInstance(LoginActivity.this).SetLoginName(
				etvUserId.getText().toString());
		InvoicePreference.getInstance(LoginActivity.this).SetPassword(
				etvPassword.getText().toString());
		String MD5passwordValue = GetMD5Code(InvoicePreference.getInstance(
				LoginActivity.this).getPassword());
		InvoicePreference.getInstance(LoginActivity.this).SetMD5Password(
				MD5passwordValue);

		ConstantUtil.sessionMap.put("currentname", InvoicePreference
				.getInstance(LoginActivity.this).getLoginName());
		ConstantUtil.sessionMap.put("currentpwd", InvoicePreference
				.getInstance(LoginActivity.this).getMD5Password());

		InvoicePreference.getInstance(LoginActivity.this).SetIsSavePwd(true);
		if (!ifLogin)// 如果是自动登录
		{
			Intent intent = new Intent(LoginActivity.this, MyActivity.class);
			intent.putExtra("from", "LoginActivity");
			startActivity(intent);
			Utils.finish(LoginActivity.this);
		} else {
			InvoicePreference.getInstance(LoginActivity.this).SetNewLogin(true);
			// IndexAsyncTask indexAsyncTask = new IndexAsyncTask(
			// LoginActivity.this, new IndexCallback() {
			//
			// @Override
			// public void send(Boolean result) {
			Boolean result = true;
			dialog.dismiss();
			if (result) {
				Intent intent = new Intent(LoginActivity.this, MyActivity.class);
				intent.putExtra("from", "LoginActivity");
				startActivity(intent);
				Utils.finish(LoginActivity.this);
			} else {
				Utils.Toast(context, getString(R.string.login_loginfailed));
			}
			// }
			// });
			// indexAsyncTask.execute();
		}
	}


}
