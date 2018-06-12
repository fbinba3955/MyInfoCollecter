package com.kiana.sjt.myinfocollecter;

import android.app.Application;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by taodi on 2018/4/23.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化androidUtils
        Utils.init(this);
        //初始化网络
        AndroidNetworking.initialize(getApplicationContext());
        //初始化imageloader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        initX5Core();
    }

    public void initX5Core() {
        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                //x5内核初始化完成回调接口，此接口回调并表示已经加载起来了x5，有可能特殊情况下x5内核加载失败，切换到系统内核。

            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("@@","加载内核是否成功:"+b);
            }
        });

    }
}
