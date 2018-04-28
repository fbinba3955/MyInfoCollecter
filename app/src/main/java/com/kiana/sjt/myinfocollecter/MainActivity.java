package com.kiana.sjt.myinfocollecter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;

/**
 * 基本的activity类
 * Created by taodi on 2018/4/23.
 */

public class MainActivity extends AppCompatActivity implements CommonActivityListener{

    MaterialDialog loadingDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 使用默认标题栏
     * @param title
     */
    public void setDefaultToolbar(String title) {
        setToolbarTitle(title);
        initToolbar();
        setBackNav();
    }

    public void setDefaultToolbar(int id) {
        setDefaultToolbar(getString(id));
    }

    /**
     * 设置为无标题栏
     */
    public void setNoTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置透明标题栏
     */
    public void setTranslateToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    /**
     * 设置标题栏标题
     * @param title
     */
    public void setToolbarTitle(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setTitle(title);
        }
    }

    /**
     * 初始化返回事件
     */
    public void setBackNav() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setNavigationIcon(getDrawable(R.drawable.icon_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    /**
     * 初始化标题栏
     */
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        if (null != toolbar) {
            toolbar.setTitleTextColor(getColor(R.color.white));
            setSupportActionBar(toolbar);
        }
    }

    /**
     * 显示加载框
     */
    public void showLoadingDialog() {
        loadingDialog = new MaterialDialog.Builder(this)
                .title(R.string.loading_title)
                .content(R.string.laoding_detail)
                .progress(true, 0)
                .show();
    }

    /**
     * 隐藏加载框
     */
    public void hideLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    public void tip(String content) {
        ToastUtils.showLong(content);
    }

    @Override
    public void onTip(String content) {
        tip(content);
    }

    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void onShowLoadingDialgo() {
        showLoadingDialog();
    }

    @Override
    public void onHideLoadingDialog() {
        hideLoadingDialog();
    }
}
