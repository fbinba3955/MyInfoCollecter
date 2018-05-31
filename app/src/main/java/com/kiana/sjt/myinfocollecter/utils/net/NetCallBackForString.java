package com.kiana.sjt.myinfocollecter.utils.net;

import com.androidnetworking.error.ANError;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by taodi on 2018/4/23.
 */

public abstract class NetCallBackForString {

    public abstract void onSuccess(String response);

    public abstract void onError(ANError error);

}
