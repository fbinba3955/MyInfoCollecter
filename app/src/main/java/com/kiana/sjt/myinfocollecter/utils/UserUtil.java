package com.kiana.sjt.myinfocollecter.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.kiana.sjt.myinfocollecter.home.model.LoginModel;

/**
 * 用户相关的操作
 * Created by taodi on 2018/5/10.
 */

public class UserUtil {

    /**
     * 判断登录状态
     * @return
     */
    public static boolean isLogin() {
        String userStr = SPUtils.getInstance().getString("user", null);
        if (!TextUtils.isEmpty(userStr)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 注销
     */
    public static void setLogout() {
        SPUtils.getInstance().put("user", "");
    }

    /**
     * 获取用户信息
     * @return
     */
    public static LoginModel.User getUserInfo() {
        if (isLogin()) {
            return JsonUtil.fromJsonStringToObejct(SPUtils.getInstance().getString("user"), LoginModel.User.class);
        }
        else {
            return null;
        }
    }
}
