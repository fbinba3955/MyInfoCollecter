package com.kiana.sjt.myinfocollecter.learn.vmodel;

import com.kiana.sjt.myinfocollecter.learn.model.JpWebModel;

import java.util.List;

/**
 * Created by taodi on 2018/5/3.
 */

public interface LearnHomeListener {

    public void onRefreshCards(List<JpWebModel> dataList);
}
