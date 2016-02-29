package net.micode.notes.activity;

import net.micode.notes.BaseActivity;
import net.micode.notes.R;
import net.micode.notes.view.HandyTextView;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends BaseActivity implements OnClickListener {

	private HandyTextView mHtvVersionName;
	private Button mBtnCheckNewVersion;
	private Button mBtnIntroduction;
	private Button mBtnGoOfficialWebsite;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initViews();
		initEvents();
		mHandler = new Handler();
	}

	@Override
	protected void initViews() {
		mHtvVersionName = (HandyTextView) findViewById(R.id.about_htv_versionname);
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			mHtvVersionName.setText("版本: Android " + packageInfo.versionName);
		} catch (NameNotFoundException e) {
			mHtvVersionName.setVisibility(View.GONE);
		}
		mBtnCheckNewVersion = (Button) findViewById(R.id.about_btn_checknewversion);
		mBtnIntroduction = (Button) findViewById(R.id.about_btn_introduction);
		mBtnGoOfficialWebsite = (Button) findViewById(R.id.about_btn_go_official_website);
	}

	@Override
	protected void initEvents() {
		mBtnCheckNewVersion.setOnClickListener(this);
		mBtnIntroduction.setOnClickListener(this);
		mBtnGoOfficialWebsite.setOnClickListener(this);
	}

	private void markMomo() {
		String str = "market://details?id=com.immomo.momo";
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(str));
			startActivity(intent);
		} catch (Exception e) {
			showCustomToast("找不到应用市场,无法对陌陌评分");
		}
	}

	@Override
	protected void onResume() {
		AboutTabsActivity.mHeaderLayout.setDefaultTitle("关于陌陌", null);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.about_btn_checknewversion:
			showLoadingDialog(null);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					dismissLoadingDialog();
					showCustomToast("当前已是最新版");

				}
			}, 1500);
			break;

		case R.id.about_btn_introduction:
			startActivity(UserGuiDeActivity.class);
			break;

		case R.id.about_btn_go_official_website:
			markMomo();
			break;
		}
	}
}
