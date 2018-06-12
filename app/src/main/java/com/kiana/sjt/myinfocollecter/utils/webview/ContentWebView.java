package com.kiana.sjt.myinfocollecter.utils.webview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * 展示网页
 * Created by taodi on 2018/4/25.
 */

public class ContentWebView extends MainActivity{

    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    private String url = "";
    private String title = "";

    private LinearLayout mLinearLayout;

    private ImageView backIv,frontIv,refreshIv;

    private WebView webView;

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
        webView = (WebView) findViewById(R.id.webview);
        initWebView(webView);
//        webView.setListener(this, this);
        webView.loadUrl(url);
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void initWebView(WebView webView) {
//        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        initDownload(webView);
        initWebViewClient(webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

    }

    /**
     * 设置下载
     * @param webView
     */
    private void initDownload(WebView webView) {
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {

            }
        });
    }

    private void initWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                if (s.startsWith("thunder://")) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", s);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    tip("内容已复制到剪切板");
                    return true;
                }
                else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }
        });
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


}
