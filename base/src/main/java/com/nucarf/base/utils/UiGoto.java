package com.nucarf.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Young on 2017/10/30.
 */

public class UiGoto {

    //Bundle参数
    public static final String BUNDLE = "params";
    public static final String EXT = "ext";

    /**
     * 开启Activity
     *
     * @param context
     * @param cls
     */
    public static void startAty(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 开启Activity
     */
    public static void startAtyWithBundle(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(BUNDLE, bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 开启Activity
     */
    public static void startAtyWithType(Context context, Class<?> cls, String value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(EXT, value);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 带返回结果
     *
     * @param context
     * @param cls
     * @param value
     * @param requestCode
     */
    public static void startAtyResWithType(Activity context, Class<?> cls, String value, int requestCode) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(EXT, value);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 带返回结果多参数
     *
     * @param context
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public static void startAtyResWithBundle(Activity context, Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(BUNDLE, bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * action开启
     *
     * @param context
     */
    public static void startAtyASAction(Context context, String action) {
        try {
            Intent intent = new Intent(action);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * action开启
     *
     * @param context
     */
    public static void startAtyResASAction(Activity context, String action, int requestCode) {
        try {
            Intent intent = new Intent(action);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带参数 action开启
     *
     * @param context
     * @param action
     * @param bundle
     */
    public static void startAtyASActionWithBundle(Context context, String action, Bundle bundle) {
        try {
            Intent intent = new Intent(action);
            intent.putExtra(BUNDLE, bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

