package com.kiana.sjt.myinfocollecter.home.model;

import android.databinding.Bindable;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

/**
 * 欢迎页背景图片model
 * Created by taodi on 2018/4/23.
 */

public class WelcomeBgModel extends BaseResponseModel{

    private String url;

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
