package com.kiana.sjt.myinfocollecter.home.vmodel;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.error.ANError;
import com.blankj.utilcode.util.SPUtils;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.ImageContants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.databinding.ActivityWelcomeBinding;
import com.kiana.sjt.myinfocollecter.home.model.LoginModel;
import com.kiana.sjt.myinfocollecter.home.model.WelcomeBgModel;
import com.kiana.sjt.myinfocollecter.home.view.HomeActivity;
import com.kiana.sjt.myinfocollecter.tts.TTSZenTaoService;
import com.kiana.sjt.myinfocollecter.utils.JsonUtil;
import com.kiana.sjt.myinfocollecter.utils.UserUtil;
import com.kiana.sjt.myinfocollecter.utils.music.MusicService;
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

import java.util.HashMap;

/**
 * 欢迎页VM
 * Created by taodi on 2018/4/23.
 */

public class WelcomeVModel extends MainVModel{

    WelcomeBgModel welcomeBgModel = null;

    private ActivityWelcomeBinding binding;

    Context context;

    public WelcomeVModel(Context context, ActivityWelcomeBinding binding){

        this.context = context;

        this.binding = binding;

        questData(context);
        if (UserUtil.isLogin()) {
            autoLogin();
        }

        //启动音乐服务
        Intent intent = new Intent(context, MusicService.class);
        context.startService(intent);
    }

    //请求数据
    private void questData(Context context) {
        //发送数据请求
        NetWorkUtil.doGetData(context, ImageContants.WELCOME_ID,null,
                new NetCallBack<BaseNetStatusBean<WelcomeBgModel>>() {

                    @Override
                    public void onError(ANError error) {

                    }

                    @Override
                    public void onInterError(String errCode, String errMsg) {

                    }

                    @Override
                    public void onSuccess(BaseNetStatusBean<WelcomeBgModel> bean) {
                        initData(bean.getData());
                    }
                });
    }

    //发送自动登录请求，替换token
    private void autoLogin() {
        NetWorkUtil.doPostData(context, CmdConstants.LOGIN,new HashMap<String, String>(), new NetCallBack<BaseNetStatusBean<LoginModel>>() {

            @Override
            public void onSuccess(BaseNetStatusBean<LoginModel> bean) {
                if (!BaseResponseModel.SUCCESS.equals(bean.getData().getResultCode())) {
                    UserUtil.setLogout();
                }
                else {
                    //登录成功
                    String userInfo = JsonUtil.fromObjectToJsonString(bean.getData().getUser());
                    SPUtils.getInstance().put("user", userInfo);
                }
            }

            @Override
            public void onError(ANError error) {
                UserUtil.setLogout();
            }

            @Override
            public void onInterError(String errCode, String errMsg) {

            }
        });
    }

    //装载数据刷新页面
    private void initData(WelcomeBgModel welcomeBgModel) {
        if (Constants.SUCCESS_ID.equals(welcomeBgModel.getResultCode())) {
            this.welcomeBgModel = welcomeBgModel;
            binding.setWelcomebg(welcomeBgModel);
            new Thread(new WaitForAMoment()).start();
        }
        else {
            ((MainActivity)context).finish();
        }
    }

    //跳转到首页
    private void jumpToHome(Context context) {
        if (context instanceof MainActivity) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }
    }

    public class WaitForAMoment implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                jumpToHome(context);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
