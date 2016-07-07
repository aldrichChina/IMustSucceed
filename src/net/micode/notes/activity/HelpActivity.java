package net.micode.notes.activity;

import net.micode.notes.jni.JniManager;
import net.micode.notes.util.NetWorkUtils.NetWorkState;
import android.os.Bundle;

import com.tencent.smtt.sdk.WebSettings;

public class HelpActivity extends BaseWebActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mNetWorkUtils.getConnectState() != NetWorkState.NONE) {
			mWebView.loadUrl(JniManager.getInstance().getHelpUrl());
			mWebView.getSettings().setLayoutAlgorithm(
					WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		} else {
			showCustomToast("当前网络不可用,请检查");
		}
	}

	@Override
	protected void onResume() {
		AboutTabsActivity.mHeaderLayout.setDefaultTitle("用户帮助", null);
		super.onResume();
	}
}
