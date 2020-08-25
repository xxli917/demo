package com.nucarf.base.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Description: 从asset下读取数据
 * Author: wang
 * Time: 2017/7/10 11:08
 */
public class AssetUtil {
    public static AssetUtil instance;

    public static AssetUtil getInstance() {
        if (null == instance) {
            instance = new AssetUtil();
        }
        return instance;
    }

    //从asset下读取文本
    public static String getText(Context pContext, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(pContext.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
