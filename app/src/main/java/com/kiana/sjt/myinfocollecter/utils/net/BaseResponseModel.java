package com.kiana.sjt.myinfocollecter.utils.net;

import android.databinding.BaseObservable;

import java.io.Serializable;

/**
 * 基础的网络返回数据
 * Created by taodi on 2018/4/23.
 */

public class BaseResponseModel extends BaseObservable implements Serializable{

    private String result;

    private String resultMsg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
