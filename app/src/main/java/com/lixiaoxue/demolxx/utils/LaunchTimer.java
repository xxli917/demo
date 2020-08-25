package com.lixiaoxue.demolxx.utils;


import com.nucarf.base.utils.LogUtils;

public class LaunchTimer {
    private static Long sTime;
    public static void startRecord(){
        sTime =System.currentTimeMillis();
    }

    public static void endRecord(){
        endRecord("");
    }
    public static void endRecord(String msg){
        long cost = System.currentTimeMillis()-sTime;
        LogUtils.e("--------------------LaunchTimer",msg+"cost = "+cost);
    }
}


