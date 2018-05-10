package com.kiana.sjt.myinfocollecter.utils.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;

import im.delight.android.webview.AdvancedWebView;

/**
 * 展示网页
 * Created by taodi on 2018/4/25.
 */

public class ContentWebView extends MainActivity implements AdvancedWebView.Listener{

    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    private String url = "";
    private String title = "";

    private LinearLayout mLinearLayout;

    private ImageView backIv,frontIv,refreshIv;

    private AdvancedWebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brower);
        initBefore();
        initView();
    }

    private void initBefore() {
        Intent intent = getIntent();
        url = intent.getStringExtra(PARAM_URL);
        title = intent.getStringExtra(PARAM_TITLE);


    }

    private void initView() {
        setDefaultToolbar(title);
        backIv = (ImageView) findViewById(R.id.iv_back);
        backIv.setOnClickListener(onClickListener);
        frontIv = (ImageView) findViewById(R.id.iv_front);
        frontIv.setOnClickListener(onClickListener);
        refreshIv = (ImageView) findViewById(R.id.iv_refresh);
        refreshIv.setOnClickListener(onClickListener);
        webView = (AdvancedWebView) findViewById(R.id.webview);
        initWebView(webView);
        webView.setListener(this, this);
        webView.loadUrl(url);
    }

    private void initWebView(AdvancedWebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        // 自适应屏幕
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.setHorizontalScrollBarEnabled(false);// 水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        // webview漏洞,android3.0~android4.2移除searchBoxJavaBridge
        if (Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT <= 17) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_back) {
                if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                }
            }
            else if (view.getId() == R.id.iv_front) {
                if(webView != null && webView.canGoForward()) {
                    webView.goForward();
                }
            }
            else if (view.getId() == R.id.iv_refresh) {
                webView.reload();
            }
        }
    };

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }


}
