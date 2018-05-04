package com.kiana.sjt.myinfocollecter.learn.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by taodi on 2018/5/3.
 */

public class JpWebWholeModel extends BaseResponseModel implements Serializable{

    private List<JpWebModel> newslist;

    public List<JpWebModel> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<JpWebModel> newslist) {
        this.newslist = newslist;
    }
}
