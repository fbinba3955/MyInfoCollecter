package com.kiana.sjt.myinfocollecter.home.vmodel;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.error.ANError;
import com.blankj.utilcode.util.SPUtils;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.CommonActivityListener;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.home.model.LoginBean;
import com.kiana.sjt.myinfocollecter.home.model.LoginModel;
import com.kiana.sjt.myinfocollecter.home.view.HomeActivity;
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
import com.kiana.sjt.myinfocollecter.utils.net.BaseDataBean;
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

import java.util.HashMap;

/**
 * Created by taodi on 2018/5/8.
 */

public class LoginVModel extends MainVModel{

    MainActivity mainActivity;

    CommonActivityListener commonActivityListener;

    public LoginVModel(Context context){
        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
            this.commonActivityListener = (CommonActivityListener) context;
        }
    }

    public void doRequestData(String username, String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        NetWorkUtil.doGetData(mainActivity, CmdConstants.LOGIN, params, new NetCallBack<BaseNetStatusBean<LoginBean>>() {

            @Override
            public void onSuccess(BaseNetStatusBean<LoginBean> bean) {
                if (!BaseResponseModel.SUCCESS.equals(bean.getData().getResult())) {
                    commonActivityListener.onTip(bean.getData().getReusltNote());
                }
                else {
                    String userInfo = JsonUtil.fromObjectToJsonString(bean.getData());
                    SPUtils.getInstance().put("user", userInfo);
                    commonActivityListener.onFinish();
                }

            }

            @Override
            public void onError(ANError error) {
                commonActivityListener.onTip(error.getErrorDetail());
            }

            @Override
            public void onInterError(String errCode, String errMsg) {
                commonActivityListener.onTip(errMsg);
            }
        });
    }
}
