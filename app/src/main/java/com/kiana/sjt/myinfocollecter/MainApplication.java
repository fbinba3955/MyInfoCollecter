package com.kiana.sjt.myinfocollecter;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.kingja.loadsir.core.LoadSir;

/**
 * Created by taodi on 2018/4/23.
 */

public class MainApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化androidUtils
        Utils.init(this);
    }
}
