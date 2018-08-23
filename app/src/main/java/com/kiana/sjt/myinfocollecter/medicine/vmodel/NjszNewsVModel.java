package com.kiana.sjt.myinfocollecter.medicine.vmodel;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.kiana.sjt.myinfocollecter.CmdConstants;
import com.kiana.sjt.myinfocollecter.Constants;
import com.kiana.sjt.myinfocollecter.ImageContants;
import com.kiana.sjt.myinfocollecter.MainVModel;
import com.kiana.sjt.myinfocollecter.home.model.WelcomeBgModel;
import com.kiana.sjt.myinfocollecter.medicine.model.NjszrlModel;
import com.kiana.sjt.myinfocollecter.medicine.view.NjszNewsActivity;
import com.kiana.sjt.myinfocollecter.utils.net.BaseNetStatusBean;
import com.kiana.sjt.myinfocollecter.utils.net.NetCallBack;
import com.kiana.sjt.myinfocollecter.utils.net.NetWorkUtil;

/**
 * Created by taodi on 2018/4/24.
 */

public class NjszNewsVModel extends MainVModel{

    NjszNewsActivity activity;

    public NjszNewsVModel(Context context){
        if (context instanceof NjszNewsActivity) {
            this.activity = (NjszNewsActivity) context;
            activity.showLoadingDialog();
            questData();
        }
    }

    public void questData() {
        //发送数据请求
        NetWorkUtil.doGetData(activity, CmdConstants.NJSZRL,
                null,
                new NetCallBack<BaseNetStatusBean<NjszrlModel>>() {

                    @Override
                    public void onError(ANError error) {
                        activity.hideLoadingDialog();
                        activity.tip(error.getErrorDetail());
                    }

                    @Override
                    public void onInterError(String errCode, String errMsg) {
                        activity.tip(errMsg);
                    }

                    @Override
                    public void onSuccess(BaseNetStatusBean<NjszrlModel> bean) {
                        activity.hideLoadingDialog();
                        initData(bean.getData());
                    }
                });
    }

    private void initData(NjszrlModel bean) {
        if (Constants.SUCCESS_ID.equals(bean.getResultCode())) {
            activity.setRecyclerViewData(bean.getNewslist());
        }
    }
}
