package com.nucarf.base.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.nucarf.base.BuildConfig;

/**
 * Created by Administrator on 2018/5/10.
 */

public class LogUtils {
    public static final String TAG = "member---";
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(msg);

        return buffer.toString();
    }

    public static void i(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void json(Object msg) {
        if (BuildConfig.LOG_DEBUG) {
            String jsonStr = null;
            if (msg instanceof String) {
                jsonStr = (String) msg;
            } else {
                jsonStr = new Gson().toJson(msg);
            }
            v(TAG, stringToJSON(jsonStr));
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Exception e) {
        if (BuildConfig.LOG_DEBUG) {
            Log.e(TAG, buildMsg(msg));
        }
    }

//    public static void v(String tag, String msg) {
//        if (BuildConfig.LOG_DEBUG) {
//            Log.v(tag, msg);
//        }
//    }

    public static void w(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void json(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void z(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void a(String tag, String msg) {
        if (BuildConfig.LOG_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                System.out.print(msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                System.out.print(msg.substring(start, strLength));
                break;
            }
        }
        System.out.println();
    }

    /**
     * @date 2017/8/24
     * @description 将字符串格式化成JSON的格式
     */
    private static String stringToJSON(String strJson) {
        // 计数tab的个数
        int tabNum = 0;
        StringBuffer jsonFormat = new StringBuffer();
        int length = strJson.length();

        char last = 0;
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == '}') {
                tabNum--;
                jsonFormat.append("\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
                jsonFormat.append(c);
            } else if (c == ',') {
                jsonFormat.append(c + "\n");
                jsonFormat.append(getSpaceOrTab(tabNum));
            } else if (c == ':') {
                jsonFormat.append(c + " ");
            } else if (c == '[') {
                tabNum++;
                char next = strJson.charAt(i + 1);
                if (next == ']') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append(c + "\n");
                    jsonFormat.append(getSpaceOrTab(tabNum));
                }
            } else if (c == ']') {
                tabNum--;
                if (last == '[') {
                    jsonFormat.append(c);
                } else {
                    jsonFormat.append("\n" + getSpaceOrTab(tabNum) + c);
                }
            } else {
                jsonFormat.append(c);
            }
            last = c;
        }
        return jsonFormat.toString();
    }

    private static String getSpaceOrTab(int tabNum) {
        StringBuffer sbTab = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sbTab.append('\t');
        }
        return sbTab.toString();
    }
}
