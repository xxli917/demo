package com.nucarf.base.utils;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

/**
 * author : lixiaoxue
 * date   : 2020/11/16/4:57 PM
 * https://blog.csdn.net/aikongmeng/article/details/72373748
 */
public class VisibilityCheckUtil {
    public static boolean getViewVisililty(View view){
        Rect rect  = new Rect();
        //检测到了原点是屏幕，也就是View的左上角坐标大概（10，20）
       boolean visibleRect =  view.getGlobalVisibleRect(rect);
       return visibleRect;
    }

    /**
     * 所以只要这个View的左上角在屏幕中，它的LocalVisibleRect的左上角坐标就一定是(0,0)，
     * 如果View的右下角在屏幕中，
     * 它的LocalVisibleRect右下角坐标就一定是(view.getWidth(), view.getHeight())。
     * @param view
     * @return
     */
    public static boolean getViewVisililty2(View view){
        Rect rect  = new Rect();
        //检测到的原点是当前View（0，0）
        boolean visibleRect =  view.getLocalVisibleRect(rect);
        return visibleRect;
    }

}
