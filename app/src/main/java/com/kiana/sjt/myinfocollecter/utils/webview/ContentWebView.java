package com.kiana.sjt.myinfocollecter.utils.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.LinearLayout;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;

import im.delight.android.webview.AdvancedWebView;

/**
 * Created by taodi on 2018/4/25.
 */

public class ContentWebView extends MainActivity implements AdvancedWebView.Listener{

    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";

    private String url = "";
    private String title = "";

    private LinearLayout mLinearLayout;

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
