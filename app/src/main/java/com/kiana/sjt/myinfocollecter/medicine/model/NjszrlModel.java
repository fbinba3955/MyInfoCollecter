package com.kiana.sjt.myinfocollecter.medicine.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 市中医信息列表
 * Created by taodi on 2018/4/24.
 */

public class NjszrlModel extends BaseResponseModel implements Serializable{

    private List<Newslist> newslist;

    public List<Newslist> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Newslist> newslist) {
        this.newslist = newslist;
    }

    public class Newslist{

        private String title;

        private String date;

        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
