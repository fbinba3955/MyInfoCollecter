package com.kiana.sjt.myinfocollecter.utils;

import android.content.Context;

import java.io.InputStream;
import java.util.Properties;

/**
 * properties文件操作
 * Created by taodi on 2018/4/23.
 */

public class PropertiesUtil {

    public static Properties getProperties(Context c){
        Properties urlProps;
        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            //注意这地方的参数appConfig在eclipse中应该是appConfig.properties才对,但在studio中不用写后缀
            //InputStream in = c.getAssets().open("appConfig.properties");
            InputStream in = c.getAssets().open("appConfig");
            //方法二：通过class获取setting.properties的FileInputStream
            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));
            props.load(in);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        urlProps = props;
        return urlProps;
    }

    /**
     * 获取appConfig里面的配置
     * @param c
     * @param key
     * @return
     */
    public String getAppConfigValue(Context c, String key) {
        Properties proper = getProperties(c);
        String value = proper.getProperty(key);
        return value;
    }
}
