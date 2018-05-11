package com.kiana.sjt.myinfocollecter;

/**
 * ViewModel基类
 * Created by taodi on 2018/4/24.
 */

public class MainVModel {

    /**
     * 医学
     * @param cmd
     * @return
     */
    public String makeMedicineUrl(String cmd) {
        return Constants.serverUrl
                + Constants.MEDICINE
                + Constants.divide
                + cmd
                + Constants.php;
    }

    /**
     * 图片
     * @param cmd
     * @return
     */
    public String makeImagesUrl(String cmd) {
        return Constants.serverUrl
                + Constants.IMAGES
                + Constants.divide
                + Constants.GET_IMAGE
                + Constants.php
                + "?"
                + Constants.ID
                + "="
                + cmd;
    }

    /**
     * 用户相关
     * @param cmd
     * @return
     */
    public String makeUserUrl(String cmd) {
        return Constants.serverUrl
                + Constants.USER
                + Constants.divide
                + cmd
                + Constants.php;
    }

    /**
     * 学习
     * @param cmd
     * @return
     */
    public String makeLearnUrl(String cmd) {
        return Constants.serverUrl
                + Constants.LEARN
                + Constants.divide
                + cmd
                + Constants.php;
    }

    /**
     * 自娱自乐
     * @param cmd
     * @return
     */
    public String makeMyUrl(String cmd) {
        return Constants.serverUrl
                + Constants.MY
                + Constants.divide
                + cmd
                + Constants.php;
    }

    /**
     * 音乐
     * @param cmd
     * @return
     */
    public String makeMusicUrl(String cmd) {
        return Constants.serverUrl
                + Constants.MUSIC
                + Constants.divide
                + cmd
                + Constants.php;
    }

}
