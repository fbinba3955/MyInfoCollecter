package com.kiana.sjt.myinfocollecter.my.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.CommonActivityListener;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.learn.model.JpWebWholeModel;
import com.kiana.sjt.myinfocollecter.my.view.MyHomeActivity;
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

import java.util.HashMap;

/**
 * Created by taodi on 2018/5/4.
 */

public class MyHomeVModel extends MainVModel {

    private MyHomeListener myHomeListener;

    private CommonActivityListener listener;

    private Context context;

    public MyHomeVModel(Context context) {
        this.context = context;
        if (context instanceof MyHomeActivity) {
            this.myHomeListener = (MyHomeListener) context;
            this.listener = (CommonActivityListener) context;
            requestData();
        }
    }

    public void requestData() {
        NetWorkUtil.doPostData(context,
                CmdConstants.MY,
                new HashMap<String, String>(),
                new NetCallBack<BaseNetStatusBean<JpWebWholeModel>>() {

            @Override
            public void onSuccess(BaseNetStatusBean<JpWebWholeModel> bean) {
                myHomeListener.onRefreshCards(bean.getData().getNewslist());
            }

            @Override
            public void onError(ANError error) {
                listener.onTip(error.getErrorDetail());
            }

            @Override
            public void onInterError(String errCode, String errMsg) {
                listener.onTip(errMsg);
            }
        });
    }
}
