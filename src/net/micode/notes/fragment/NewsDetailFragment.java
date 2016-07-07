package net.micode.notes.fragment;

import net.micode.notes.BaseFragment;
import net.micode.notes.R;
import net.micode.notes.entities.NewsDetailContent;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class NewsDetailFragment extends BaseFragment {

    private NewsDetailContent detailContent;
    private WebView myWebView;
    protected String mCurrentUrl = "";
    private ProgressBar progressbar_extraweb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_detail_fragment, container, false);
        myWebView = (WebView) view.findViewById(R.id.newsWebView);
        progressbar_extraweb = (ProgressBar) view.findViewById(R.id.progressbar_extraweb);
        progressbar_extraweb.setMax(100);
        Bundle arguments = getArguments();
        detailContent = (NewsDetailContent) arguments.getSerializable("news_detailContent");
        updateArticleView(detailContent);
        return view;
    }

    public void updateArticleView(NewsDetailContent detailContent) {

        // 打开网页
        myWebView.loadUrl(detailContent.getLink());// 百度链接

        // JavaScript使能(如果要加载的页面中有JS代码，则必须使能JS)
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultTextEncodingName("utf-8");
        // 在WebView中打开链接（默认行为是使用浏览器，设置此项后都用WebView打开）
        // myWebView.setWebViewClient(new WebViewClient());
        // 这样设置后所有的链接都会在当前WebView中打开

        // 更强的打开链接控制：自己覆写一个WebViewClient类：除了指定链接从WebView打开，其他的链接默认打开
        myWebView.setWebChromeClient(new WebViewClient1());
        myWebView.setOnTouchListener(new OnTouchListener() {

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
        myWebView.setWebViewClient(new WebViewClient() {

            ProgressDialog progressDialog = new ProgressDialog(getActivity());

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                mCurrentUrl = url;

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                Log.i("tag", "onPageStarted mCurrentUrl:" + mCurrentUrl);
                mCurrentUrl = url;
                progressDialog.setMessage("正在加载");
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mCurrentUrl = url;
                progressDialog.cancel();
            }
        });

    }

    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            // 返回键退回
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up
        // to the default
        // system behavior (probably exit the activity)
        return false;
    }

    @Override
    protected void initViews() {
        Log.d("jia", "BaseFragment==initViews");
    }

    @Override
    protected void initEvents() {
        Log.d("jia", "BaseFragment==initEvents");
    }

    // 顶部进度条
    private class WebViewClient1 extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.i("tag", "h5 webview progress:::" + newProgress);
            progressbar_extraweb.setProgress(newProgress);
            if (newProgress == 100) {
                progressbar_extraweb.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @SuppressWarnings("unused")
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            mCurrentUrl = url;

            return false;
        }
    }
}
