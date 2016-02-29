package net.micode.notes.activity;

import net.micode.notes.BaseWebActivity;
import net.micode.notes.jni.JniManager;
import net.micode.notes.util.NetWorkUtils.NetWorkState;
import android.os.Bundle;
import android.webkit.WebSettings;

public class ProtocolActivity extends BaseWebActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (mNetWorkUtils.getConnectState() != NetWorkState.NONE) {
			mWebView.loadUrl(JniManager.getInstance().getProtocolUrl());
			mWebView.getSettings().setLayoutAlgorithm(
					WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		} else {
			showCustomToast("当前网络不可用,请检查");
		}
	}

	@Override
	protected void onResume() {
		AboutTabsActivity.mHeaderLayout.setDefaultTitle("用户协议", null);
		super.onResume();
	}
}
