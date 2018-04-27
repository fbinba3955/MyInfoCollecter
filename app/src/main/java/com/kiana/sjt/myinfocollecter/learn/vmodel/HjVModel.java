package com.kiana.sjt.myinfocollecter.learn.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.MainActivity;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.learn.model.HjJpModel;
import com.kiana.sjt.myinfocollecter.learn.view.HjActivity;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

/**
 * Created by taodi on 2018/4/27.
 */

public class HjVModel extends MainVModel {

    HjViewRefreshListener listener;

    Context context;

    public HjVModel(Context context) {
        this.context = context;
        if (context instanceof HjActivity) {
            this.listener = (HjActivity)context;
        }
        requestData();
    }

    /**
     * 请求数据
     */
    public void requestData() {
        NetWorkUtil.doGetNullDate(context, makeLearnUrl(CmdConstants.HJJP),
                new NetCallBack<HjJpModel>() {

                    @Override
                    public void onSuccess(HjJpModel bean) {
                        listener.onRefreshListLinstener(bean.getNewslist());
                    }

                    @Override
                    public void onError(ANError error) {
                        ((MainActivity)context).tip(error.getErrorDetail());
                    }
                });
    }
}
