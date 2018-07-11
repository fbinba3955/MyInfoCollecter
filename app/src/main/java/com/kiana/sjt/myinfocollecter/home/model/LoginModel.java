package com.kiana.sjt.myinfocollecter.home.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseResponseModel;

import java.io.Serializable;

/**
 * 登录结果
 * Created by taodi on 2018/5/8.
 */

public class LoginModel extends BaseResponseModel implements Serializable{

    private User user;

    public class User implements Serializable{

        private String username;

        private String token;

        private String level;

        private String levelname;

        private String nickname;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
