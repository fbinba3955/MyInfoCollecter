package com.kiana.sjt.myinfocollecter.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by taodi on 2018/4/23.
 */

public class JsonUtil {

    /** The Constant GSON. */
    private static final Gson GSON = new Gson();

    JsonUtil() {

    }

    private static synchronized Gson getInstance() {
        return GSON;
    }

    /**
     * From json string to obejct.
     *
     * @param <T>        the generic type
     * @param jsonString the json string
     * @param classOfT   the class of t
     * @return the t
     */
    public static <T> T fromJsonStringToObejct(String jsonString,
                                               Class<T> classOfT) {
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                return getInstance().fromJson(jsonString, classOfT);

            } catch (Exception e) {

            }

        }
        return null;

    }

    public static <T> T fromJsonStringToCollection(String jsonString,
                                                   Type typeOfT) {
        if (!TextUtils.isEmpty(jsonString)) {

            try {
                return getInstance().fromJson(jsonString, typeOfT);

            } catch (Exception e) {
            }

        }
        return null;

    }

    /**
     * From object to json string.
     *
     * @param object the object
     * @return the string
     */
    public static String fromObjectToJsonString(Object object) {
        String result = null;
        if (object != null) {
            result = getInstance().toJson(object);
        }
        return result;

    }
}
