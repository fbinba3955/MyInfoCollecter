package com.kiana.sjt.myinfocollecter.learn.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by taodi on 2018/4/25.
 */

public class HjJpModel extends BaseResponseModel implements Serializable {

    private List<Newslist> newslist;

    public class Newslist implements Serializable{

        private String title;

        private String type;

        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public List<Newslist> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<Newslist> newslist) {
        this.newslist = newslist;
    }
}
