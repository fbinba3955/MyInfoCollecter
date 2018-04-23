package com.kiana.sjt.myinfocollecter.utils.net;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;

import org.json.JSONArray;

/**
 * 网络工具
 * Created by taodi on 2018/4/23.
 */

public class NetWorkUtil {

    /**
     * 普通的post请求
     */
    public static void doPostDefault() {

    }

    /**
     * 普通的get请求
     * @param context
     * @param cmd
     * @param netCallBack
     * @param <T>
     */
    public static <T> void deGetNullDate(Context context,
                                         final String cmd,
                                         final NetCallBack<T> netCallBack) {
            AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        String resp = response.toString();
                        T respBean = JsonUtil.fromJsonStringToCollection(resp, netCallBack.getType());
                        netCallBack.onSuccess(respBean);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        netCallBack.onError(error);
                    }
                });
    }
}
