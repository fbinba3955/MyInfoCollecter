package com.kiana.sjt.myinfocollecter.home.vmodel;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.ImageContants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.databinding.ActivityWelcomeBinding;
import com.kiana.sjt.myinfocollecter.home.model.WelcomeBgModel;
import com.kiana.sjt.myinfocollecter.home.view.HomeActivity;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

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
