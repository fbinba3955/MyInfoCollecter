package com.kiana.sjt.myinfocollecter.home.model;

import com.kiana.sjt.myinfocollecter.utils.net.BaseDataBean;

public class LoginBean extends BaseDataBean{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
