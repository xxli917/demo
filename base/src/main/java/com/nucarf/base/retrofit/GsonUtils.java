package com.nucarf.base.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public enum GsonUtils {
    INSTANCE;
    private Gson gson;

    GsonUtils() {
        GsonBuilder builder = new GsonBuilder();
        //builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = builder.serializeNulls().create();

    }

    public Gson getGson() {
        return gson;
    }
}
