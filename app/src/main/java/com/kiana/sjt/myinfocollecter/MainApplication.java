package com.kiana.sjt.myinfocollecter;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.smtt.sdk.QbSdk;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static com.xiaomi.mipush.sdk.Constants.APP_ID;

/**
 * Created by taodi on 2018/4/23.
 */

public class MainApplication extends Application{

    public static final String APP_ID = "2882303761517812841";
    public static final String APP_KEY = "5201781289841";
    public static final String TAG = "com.kiana.sjt.myinfocollecter881023";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化androidUtils
        Utils.init(this);
        //初始化网络
        AndroidNetworking.initialize(getApplicationContext());
        //初始化imageloader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        initPushLog();
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

    private void initPushLog() {
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @SuppressLint("LongLogTag")
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @SuppressLint("LongLogTag")
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
