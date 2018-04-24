package com.kiana.sjt.myinfocollecter;

/**
 * Created by taodi on 2018/4/24.
 */

public class MainVModel {

    /**
     * 医学
     * @param cmd
     * @return
     */
    public String makeMedicineUrl(String cmd) {
        return Constants.serverUrl + Constants.MEDICINE + Constants.divide + cmd + Constants.php;
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
}
