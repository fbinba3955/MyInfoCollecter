package com.kiana.sjt.myinfocollecter.music.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 音乐列表model
 * Created by taodi on 2018/5/11.
 */

public class SongsModel extends BaseResponseModel implements Serializable{

    private List<Datalist> datalist;

    public List<Datalist> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<Datalist> datalist) {
        this.datalist = datalist;
    }

    public class Datalist implements Serializable{

        private String name;

        private String music;

        private String cover;

        /**
         * 播放状态
         */
        private boolean playing = false;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public boolean isPlaying() {
            return playing;
        }

        public void setPlaying(boolean playing) {
            this.playing = playing;
        }

        public void toggle() {
            this.playing = !playing;
        }
    }
}
