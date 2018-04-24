package com.kiana.sjt.myinfocollecter.home.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.ImageContants;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.home.model.WelcomeBgModel;
import com.kiana.sjt.myinfocollecter.databinding.ActivityWelcomeBinding;
import com.kiana.sjt.myinfocollecter.utils.PropertiesUtil;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

/**
 * 欢迎页VM
 * Created by taodi on 2018/4/23.
 */

public class WelcomeVModel extends MainVModel{

    WelcomeBgModel welcomeBgModel = null;

    private ActivityWelcomeBinding binding;

    public WelcomeVModel(Context context, ActivityWelcomeBinding binding){

        this.binding = binding;

        questData(context);

    }

    //请求数据
    private void questData(Context context) {
        //发送数据请求
        NetWorkUtil.doGetNullDate(context, makeImagesUrl(ImageContants.WELCOME_ID),
                new NetCallBack<WelcomeBgModel>() {

                    @Override
                    public void onError(ANError error) {

                    }

                    @Override
                    public void onSuccess(WelcomeBgModel bean) {
                        initData(bean);
                    }
                });
    }

    //装载数据刷新页面
    private void initData(WelcomeBgModel welcomeBgModel) {

        this.welcomeBgModel = welcomeBgModel;

        binding.setWelcomebg(welcomeBgModel);
    }
}
