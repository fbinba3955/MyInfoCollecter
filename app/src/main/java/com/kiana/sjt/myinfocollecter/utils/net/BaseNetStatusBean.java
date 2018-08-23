package com.kiana.sjt.myinfocollecter.utils.net;

/**
 * phaiApi基本接口
 */
public class BaseNetStatusBean<T> {

    private String ret;

    private String msg;

    private T data;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
