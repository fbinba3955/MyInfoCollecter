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
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;

import java.util.HashMap;
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
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * 普通的get请求
     * @param context
     * @param url
     * @param netCallBack
     * @param <T>
     */
    public static <T> void doGetNullData(Context context,
                                         String url,
                                         final NetCallBack<T> netCallBack) {
        initCookie(context);
        LogUtils.json(url);
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsString(new StringRequestListener() {

                        @Override
                        public void onResponse(String response) {
                            LogUtils.json(TAG_NET, response);
                            T respBean = JsonUtil.fromJsonStringToCollection(response, netCallBack.getType());
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
     * post请求 带参数
     * @param context
     * @param url 地址
     * @param params 参数 不能为null
     * @param netCallBack
     * @param <T>
     */
    public static <T> void doPostData(Context context,
                                      final String url,
                                      HashMap<String, String> params,
                                      final NetCallBack<T> netCallBack) {
        initCookie(context);
        //放入token
        if (UserUtil.isLogin()) {
            String token = UserUtil.getUserInfo().getToken();
            params.put("token", token);
        }
        LogUtils.json(url);
        LogUtils.json(params.toString());
        AndroidNetworking.post(url)
                .addBodyParameter(params)
                .setTag("info")
                .setPriority(Priority.MEDIUM)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.json(TAG_NET, response);
                        T respBean = JsonUtil.fromJsonStringToCollection(response, netCallBack.getType());
                        netCallBack.onSuccess(respBean);
                    }

                    @Override
                    public void onError(ANError anError) {
                        netCallBack.onError(anError);
                    }
                });
    }
}
