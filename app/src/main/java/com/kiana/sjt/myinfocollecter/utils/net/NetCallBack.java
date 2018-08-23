package com.kiana.sjt.myinfocollecter.utils.net;

import com.androidnetworking.error.ANError;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by taodi on 2018/4/23.
 */

public abstract class NetCallBack<T> {

    /**
     * M type.
     */
    private Type mType;

    public abstract void onSuccess(T bean);

    public abstract void onError(ANError error);

    public abstract void onInterError(String errCode,String errMsg);

    public NetCallBack() {
        mType = getSuperclassTypeParameter(getClass());

    }

    public Type getType() {
        return mType;
    }

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }
}
