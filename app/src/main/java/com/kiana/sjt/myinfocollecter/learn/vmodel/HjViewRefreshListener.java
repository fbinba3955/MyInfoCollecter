package com.kiana.sjt.myinfocollecter.learn.vmodel;

import com.kiana.sjt.myinfocollecter.learn.model.HjJpModel;

import java.util.List;

/**
 * 沪江日语推荐的界面操作接口
 * Created by taodi on 2018/4/27.
 */

public interface HjViewRefreshListener {

    /**
     * 刷新页面列表
     * @param dataList
     */
    public void onRefreshListLinstener(List<HjJpModel.Newslist> dataList);

}
