/******************************************************************
 *    Package:     net.micode.notes.activity
 *
 *    Filename:    X5WebViewActivity.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年7月6日 下午4:28:54
 *
 *    Revision:
 *
 *    2016年7月6日 下午4:28:54
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes.activity;

import net.micode.notes.util.NetWorkUtils.NetWorkState;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.smtt.sdk.WebSettings;

/**
 * @ClassName X5WebViewActivity
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月6日 下午4:28:54
 * @version 1.0.0
 */
public class X5WebViewActivity extends BaseWebActivity {

    /*
     * (非 Javadoc) Description:
     * @see net.micode.notes.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String webURL = bundle.getString("articalUrl");
        if (mNetWorkUtils.getConnectState() != NetWorkState.NONE) {
            mWebView.loadUrl(webURL);
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        } else {
            showCustomToast("当前网络不可用,请检查");
        }

    }

}
