package net.micode.notes.activity;

import net.micode.notes.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class BaseWebActivity extends BaseActivity {

    private ImageView topback;
    private View mLoadingView;
    protected WebView mWebView;
    private ProgressBar progressbarWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_baseweb);
        super.onCreate(savedInstanceState);
        initViews();
        initEvents();
    }

    @Override
    protected void initViews() {
        findViewById();
        WebSettings settings = mWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");
        mWebView.setFocusableInTouchMode(true);
        mWebView.setFocusable(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    private void findViewById() {
        topback = (ImageView) findViewById(R.id.topback);
        topback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressbarWeb = (ProgressBar) findViewById(R.id.progressbar_extraweb);
        progressbarWeb.setMax(100);
        mLoadingView = findViewById(R.id.baseweb_loading_indicator);
        mWebView = (WebView) findViewById(R.id.baseweb_webview);
    }

    @Override
    protected void initEvents() {
        mWebView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                    }
                    break;
                }
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                Log.i("tag", "h5 webview progress:::" + newProgress);
                if (newProgress == 100) {
                    progressbarWeb.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progressbarWeb.getVisibility()) {
                        progressbarWeb.setVisibility(View.VISIBLE);
                    }
                    progressbarWeb.setProgress(newProgress);

                }
                super.onProgressChanged(view, newProgress);

            }

        });
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgress();
            }

            private void showProgress() {
                mLoadingView.setVisibility(View.VISIBLE);
            }

            private void dismissProgress() {
                mLoadingView.setVisibility(View.GONE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgress();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void setListener() {
        // TODO Auto-generated method stub

    }
}
