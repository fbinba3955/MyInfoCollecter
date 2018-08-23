package com.kiana.sjt.myinfocollecter.utils.net;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.blankj.utilcode.util.LogUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 网络工具
 * Created by taodi on 2018/4/23.
 */

public class NetWorkUtil {

    public static final String TAG_NET = "Net";

    public static ClearableCookieJar cookieJar = null;

    public static OkHttpClient okHttpClient = null;

    /**
     * 普通的post请求
     */
    public static void doPostDefault() {

    }

    public static void initCookie(Context context) {
        if (null == cookieJar) {
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
            okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * 普通的get请求
     * @param context
     * @param cmd
     * @param netCallBack
     * @param <T>
     */
    public static <T> void doGetNullData(Context context,
                                         String cmd,
                                         final NetCallBack<BaseNetStatusBean<T>> netCallBack) {
        initCookie(context);
        LogUtils.json(cmd);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("service", cmd);
        AndroidNetworking.get(Constants.serverUrl)
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsString(new StringRequestListener() {

                        @Override
                        public void onResponse(String response) {
                            LogUtils.json(TAG_NET, response);
                            BaseNetStatusBean<T> respBean = JsonUtil.fromJsonStringToCollection(response, netCallBack.getType());
                            netCallBack.onSuccess(respBean);
                        }

                        @Override
                        public void onError(ANError anError) {
                            netCallBack.onError(anError);
                        }

                });
    }

    public static void doGetNullDataForString(Context context,
                                              String url,
                                              final NetCallBackForString netCallBackForString) {
        initCookie(context);
        LogUtils.json(url);
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        netCallBackForString.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        netCallBackForString.onError(anError);
                    }
                });
    }

    /**
     * 普通的get请求
     * @param context
     * @param cmd
     * @param params
     * @param netCallBack
     */
    public static <T> void doGetData(Context context,
                                 String cmd,
                                 HashMap<String,String> params,
                                 final NetCallBack<BaseNetStatusBean<T>> netCallBack) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put("service", cmd);
        initCookie(context);
        LogUtils.json(cmd);
        AndroidNetworking.get(Constants.serverUrl)
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .addQueryParameter(params)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.json(TAG_NET, response);
                        BaseNetStatusBean<T> respBean = JsonUtil.fromJsonStringToCollection(response, netCallBack.getType());
                        if ("200".equals(respBean.getRet())) {
                            netCallBack.onSuccess(respBean);
                        }
                        else {
                            netCallBack.onInterError(respBean.getRet(), respBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        netCallBack.onError(anError);
                    }
                });
    }

    /**
     * post请求 带参数， 取消post方法，引用get
     * @param context
     * @param cmd 功能命令
     * @param params 参数 不能为null
     * @param netCallBack
     * @param <T>
     */
    public static <T> void doPostData(Context context,
                                      final String cmd,
                                      HashMap<String, String> params,
                                      final NetCallBack<BaseNetStatusBean<T>> netCallBack) {
        doGetData(context, cmd, params, netCallBack);
    }
}
