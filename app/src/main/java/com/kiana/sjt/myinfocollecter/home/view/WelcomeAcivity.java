package com.kiana.sjt.myinfocollecter.home.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.R;
import com.kiana.sjt.myinfocollecter.databinding.ActivityWelcomeBinding;
import com.kiana.sjt.myinfocollecter.home.vmodel.WelcomeVModel;
import com.kiana.sjt.myinfocollecter.utils.PropertiesUtil;

import java.util.List;

/**
 * 欢迎页
 * Created by taodi on 2018/4/23.
 */

public class WelcomeAcivity extends MainActivity{

    ActivityWelcomeBinding binding = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        setNoTitle();
        requestPermissions();
    }

    /**
     * 获取权限
     */
    private void requestPermissions() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.PHONE, PermissionConstants.CAMERA)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {

                    }


                }).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                initConstants();
                new WelcomeVModel(WelcomeAcivity.this, binding);
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                finish();
            }
        }).request();
    }

    /**
     * 拿到权限后初始化全局变量
     */
    private void initConstants() {
        Constants.serverUrl = PropertiesUtil.getAppConfigValue(this, "serverUrl");
    }
}
