package com.kiana.sjt.myinfocollecter.home.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.blankj.utilcode.util.SPUtils;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.CommonActivityListener;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.home.model.LoginModel;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
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
        NetWorkUtil.doPostData(mainActivity, makeUserUrl(CmdConstants.LOGIN),params, new NetCallBack<LoginModel>() {

            @Override
            public void onSuccess(LoginModel bean) {
                if (!BaseResponseModel.SUCCESS.equals(bean.getResultCode())) {
                    commonActivityListener.onTip(bean.getResultMsg());
                }
                else {
                    //登录成功
                    String userInfo = JsonUtil.fromObjectToJsonString(bean.getUser());
                    SPUtils.getInstance().put("user", userInfo);
                    commonActivityListener.onFinish();
                }
            }

            @Override
            public void onError(ANError error) {
                commonActivityListener.onTip(error.getErrorDetail());
            }
        });
    }
}
