package com.kiana.sjt.myinfocollecter.learn.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.CommonActivityListener;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.learn.model.JpWebWholeModel;
import com.kiana.sjt.myinfocollecter.learn.view.LearnHomeActivity;
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

/**
 * Created by taodi on 2018/5/3.
 */

public class LearnHomeVModel extends MainVModel {

    private LearnHomeListener learnHomeListener;

    private CommonActivityListener listener;

    private Context context;

    public LearnHomeVModel(Context context) {
        this.context = context;
        if (context instanceof LearnHomeActivity) {
            this.learnHomeListener = (LearnHomeActivity)context;
            this.listener = (CommonActivityListener) context;
            requestData();
        }
    }

    public void requestData() {
        NetWorkUtil.doGetNullData(context, CmdConstants.JPWEB, new NetCallBack<BaseNetStatusBean<JpWebWholeModel>>() {

            @Override
            public void onSuccess(BaseNetStatusBean<JpWebWholeModel> bean) {
                learnHomeListener.onRefreshCards(bean.getData().getNewslist());
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
