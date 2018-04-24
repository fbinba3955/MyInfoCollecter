package com.kiana.sjt.myinfocollecter;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
    }
}
